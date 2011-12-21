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

package au.com.polly.plotter;

import au.com.polly.util.graphics.RotateFilter;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

/*
 * Class RotateDemo: To create the image on-the-fly and to rotate it.
 */
public class RotateDemo extends Applet
{

    Font font;
    String title;
    Image image;
    double angle = 0.0;
    TextField titleField;
    RotatorCanvas rotator;
    double radiansPerDegree = Math.PI/180.0;
    Choice fontChoice;
    Scrollbar fontSize;
    Scrollbar angleScroller;
    Label angleLabel, sizeLabel;

    public void init()
	{
	    /*
	     * Defaults
	     */
	    font =  new Font("Helvetica", Font.BOLD, 20);

	    title = "Hello";

	    image = this.createRotatedImage(Color.black);

	    /*
	     * Create UI components.
	     */
	    fontChoice = new Choice();
	    Toolkit tk = Toolkit.getDefaultToolkit();
	    String fontList[] = tk.getFontList();
	    for(int i = 0; i < fontList.length; i++) {
		fontChoice.addItem(fontList[i]);
	    }

	    fontSize = new Scrollbar(Scrollbar.HORIZONTAL, 20, 1, 5, 40);
	    angleScroller = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 361);

	    Label titleLabel = new Label("Text to rotate:");
	    titleField = new TextField(30);
	    titleField.setText(title);
	    sizeLabel = new Label("Font Size: (20)");
	    angleLabel = new Label("Angle: (0)");
	    Label fontLabel = new Label("Font:");

	    rotator = new RotatorCanvas(image);

	    // Set up the UI

	    GridBagLayout gridBag = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();
	    setLayout(gridBag);

	    c.anchor = GridBagConstraints.WEST;
	    addComponent(titleLabel, gridBag, c, 0, 0, 1, 1);

	    c.fill = GridBagConstraints.HORIZONTAL;
	    addComponent(titleField, gridBag, c, 0, 1, 3, 1);

	    addComponent(sizeLabel, gridBag, c, 1, 0, 1, 1);

	    c.fill = GridBagConstraints.HORIZONTAL;
	    addComponent(fontSize, gridBag, c, 1, 1, 3, 1);

	    addComponent(angleLabel, gridBag, c, 2, 0, 1, 1);

	    c.fill = GridBagConstraints.HORIZONTAL;
	    addComponent(angleScroller, gridBag, c, 2, 1, 3, 1);


	    addComponent(fontLabel, gridBag, c, 3, 0, 1, 1);

	    addComponent(fontChoice, gridBag, c, 3, 1, 1, 1);

	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    addComponent(rotator, gridBag, c, 4, 0, 4, 5);

	    validate();

	}


    private Image createRotatedImage(Color c)
	{

	    /*
	     * Get fontmetrics and calculate position.
	     */
	    FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);

	    int width = fm.stringWidth(title);
	    int height = fm.getHeight();
	    int ascent = fm.getMaxAscent();
	    int leading = fm.getLeading();
	    int verMarzin = (size().height - height)/2;

	    int h = verMarzin + ascent + leading;

	    /*
	     * Create the image.
	     */
	    image = this.createImage(width + 8, height);

	    /*
	     * Set graphics attributes and draw the string.
	     */
	    Graphics gr = image.getGraphics();
	    gr.setColor(Color.white);
	    gr.fillRect(0, 0, image.getWidth(this), image.getHeight(this));

	    gr.setFont(font);

	    gr.setColor(c);
	    gr.drawString(title, 4, ascent + leading);

	    /*
	     * Create an imagefilter to rotate the image.
	     */
	    ImageFilter filter = new RotateFilter(angle);

	    /*
	     * Produce the rotated image.
	     */
	    ImageProducer producer = new FilteredImageSource(image.getSource(),
							     filter);

	    /*
	     * Create the rotated image.
	     */
	    image = createImage(producer);

	    return image;
	}


    public boolean action(Event evt, Object arg)
	{
	    title = (titleField.getText().length() > 0) ? titleField.getText() :
		"Hello";

	    angle = angleScroller.getValue();
	    angleLabel.setText("Angle: (" + angle + ")");

	    angle *= radiansPerDegree;

	    font = new Font(fontChoice.getSelectedItem(), Font.BOLD,
			    fontSize.getValue());

	    sizeLabel.setText("Font Size: (" + fontSize.getValue() + ")");

	    image = this.createRotatedImage(Color.black);
	    rotator.setImage(image);
	    rotator.repaint();
	    return true;
	}

    public boolean handleEvent(Event e) {
        if (e.target instanceof Scrollbar) {
	    title = (titleField.getText().length() > 0) ? titleField.getText() :
		"Hello";

	    angle = angleScroller.getValue();
	    angleLabel.setText("Angle: (" + angle + ")");

	    angle *= radiansPerDegree;

	    font = new Font(fontChoice.getSelectedItem(), Font.BOLD,
			    fontSize.getValue());

	    sizeLabel.setText("Font Size: (" + fontSize.getValue() + ")");

	    image = this.createRotatedImage(Color.black);
	    rotator.setImage(image);
	    rotator.repaint();
        }
        return super.handleEvent(e);
    }

    private void addComponent(Component c, GridBagLayout g, GridBagConstraints
			      gc, int row, int column, int width, int height)
	{
	    /*
	     * Set gridx and gridy.
	     */
	    gc.gridx = column;
	    gc.gridy = row;

	    /*
	     * Set gridwidth and gridheight.
	     */
	    gc.gridwidth = width;
	    gc.gridheight = height;

	    g.setConstraints(c, gc);
	    add(c);
	}
}

class RotatorCanvas extends Canvas
{

    Image image;

    public RotatorCanvas(Image image)
	{
	    super();

	    this.image = image;

	}

    public void setImage(Image image)
	{
	    this.image = image;
	}

    public void paint(Graphics g) {
        g.drawRect(1, 1, image.getWidth(this) + 1, image.getHeight(this) + 1);
	g.drawImage(image, 2, 2, this);

    }
}
