/*
 * Copyright (c) 2011-2011 Polly Enterprises Pty Ltd and/or its affiliates.
 *  All rights reserved. This code is not to be distributed in binary
 * or source form without express consent of Polly Enterprises Pty Ltd.
 *
 *
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package au.com.polly.util.graphics;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.ImageFilter;

public class RotateFilter extends ImageFilter
{

    private static ColorModel defaultRGB = ColorModel.getRGBdefault();

    private double angle;
    private double sin;
    private double cos;
    private double coord[] = new double[2];

    private int raster[];
    private int xoffset, yoffset;
    private int srcW, srcH;
    private int dstW, dstH;

    public RotateFilter(double angle) {
        this.angle = angle;
        sin = Math.sin(angle);
        cos = Math.cos(angle);
    }

    public void transform(double x, double y, double[] retcoord) {
        // Remember that the coordinate system is upside down so apply
        // the transform as if the angle were negated.
        // cos(-angle) =  cos(angle)
        // sin(-angle) = -sin(angle)
        retcoord[0] = cos * x + sin * y;
        retcoord[1] = cos * y - sin * x;
    }

    public void itransform(double x, double y, double[] retcoord) {
        // Remember that the coordinate system is upside down so apply
        // the transform as if the angle were negated.  Since inverting
        // the transform is also the same as negating the angle, itransform
        // is calculated the way you would expect to calculate transform.
        retcoord[0] = cos * x - sin * y;
        retcoord[1] = cos * y + sin * x;
    }

    public void transformBBox(Rectangle rect) {
        double minx = Double.POSITIVE_INFINITY;
        double miny = Double.POSITIVE_INFINITY;
        double maxx = Double.NEGATIVE_INFINITY;
        double maxy = Double.NEGATIVE_INFINITY;
        for (int y = 0; y <= 1; y++) {
            for (int x = 0; x <= 1; x++) {
                transform(rect.x + x * rect.width,
                          rect.y + y * rect.height,
                          coord);
                minx = Math.min(minx, coord[0]);
                miny = Math.min(miny, coord[1]);
                maxx = Math.max(maxx, coord[0]);
                maxy = Math.max(maxy, coord[1]);
            }
        }
        rect.x = (int) Math.floor(minx);
        rect.y = (int) Math.floor(miny);
        rect.width = (int) Math.ceil(maxx) - rect.x + 1;
        rect.height = (int) Math.ceil(maxy) - rect.y + 1;
    }

    public void setDimensions(int width, int height) {
        Rectangle rect = new Rectangle(0, 0, width, height);
        transformBBox(rect);
        xoffset = -rect.x;
        yoffset = -rect.y;
        srcW = width;
        srcH = height;
        dstW = rect.width;
        dstH = rect.height;
        raster = new int[srcW * srcH];
        consumer.setDimensions(dstW, dstH);
    }

    public void setColorModel(ColorModel model) {
        consumer.setColorModel(defaultRGB);
    }

    public void setHints(int hintflags) {
        consumer.setHints(TOPDOWNLEFTRIGHT
                          | COMPLETESCANLINES
                          | SINGLEPASS
                          | (hintflags & SINGLEFRAME));
    }

    public void setPixels(int x, int y, int w, int h, ColorModel model,
                          byte pixels[], int off, int scansize) {
        int srcoff = off;
        int dstoff = y * srcW + x;
        for (int yc = 0; yc < h; yc++) {
            for (int xc = 0; xc < w; xc++) {
                raster[dstoff++] = model.getRGB(pixels[srcoff++] & 0xff);
            }
            srcoff += (scansize - w);
            dstoff += (srcW - w);
        }
    }

    public void setPixels(int x, int y, int w, int h, ColorModel model,
                          int pixels[], int off, int scansize) {
        int srcoff = off;
        int dstoff = y * srcW + x;
        if (model == defaultRGB) {
            for (int yc = 0; yc < h; yc++) {
                System.arraycopy(pixels, srcoff, raster, dstoff, w);
                srcoff += scansize;
                dstoff += srcW;
            }
        } else {
            for (int yc = 0; yc < h; yc++) {
                for (int xc = 0; xc < w; xc++) {
                    raster[dstoff++] = model.getRGB(pixels[srcoff++]);
                }
                srcoff += (scansize - w);
                dstoff += (srcW - w);
            }
        }
    }

    public void imageComplete(int status) {
        if (status == IMAGEERROR || status == IMAGEABORTED) {
            consumer.imageComplete(status);
            return;
        }
        int pixels[] = new int[dstW];
        for (int dy = 0; dy < dstH; dy++) {
            itransform(0 - xoffset, dy - yoffset, coord);
            double x1 = coord[0];
            double y1 = coord[1];
            itransform(dstW - xoffset, dy - yoffset, coord);
            double x2 = coord[0];
            double y2 = coord[1];
            double xinc = (x2 - x1) / dstW;
            double yinc = (y2 - y1) / dstW;
            for (int dx = 0; dx < dstW; dx++) {
                int sx = (int) Math.round(x1);
                int sy = (int) Math.round(y1);
                if (sx < 0 || sy < 0 || sx >= srcW || sy >= srcH) {
                    pixels[dx] = 0;
                } else {
                    pixels[dx] = raster[sy * srcW + sx];
                }
                x1 += xinc;
                y1 += yinc;
            }
            consumer.setPixels(0, dy, dstW, 1, defaultRGB, pixels, 0, dstW);
        }
        consumer.imageComplete(status);
    }
}
