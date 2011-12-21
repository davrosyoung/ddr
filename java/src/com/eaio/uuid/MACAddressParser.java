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
package com.eaio.uuid;

/**
 * The MAC address parser attempts to find the following patterns:
 * <ul>
 * <li>.{1,2}:.{1,2}:.{1,2}:.{1,2}:.{1,2}:.{1,2}</li>
 * <li>.{1,2}-.{1,2}-.{1,2}-.{1,2}-.{1,2}-.{1,2}</li>
 * </ul>
 *
 * @see <a href="http://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="mailto:jb@eaio.com">Johann Burkard</a>
 * @version $Id: MACAddressParser.java 1888 2009-03-15 12:43:24Z johann $
 */
class MACAddressParser {

    /**
     * No instances needed.
     */
    private MACAddressParser() {
        super();
    }

    /**
     * Attempts to find a pattern in the given String.
     *
     * @param in the String, may not be <code>null</code>
     * @return the substring that matches this pattern or <code>null</code>
     */
    static String parse(String in) {

        String out = in;

        // lanscan

        int hexStart = out.indexOf("0x");
        if (hexStart != -1 && out.indexOf("ETHER") != -1) {
            int hexEnd = out.indexOf(' ', hexStart);
            if (hexEnd > hexStart + 2) {
                out = out.substring(hexStart, hexEnd);
            }
        }

        else {

            int octets = 0;
            int lastIndex, old, end;

            if (out.indexOf('-') > -1) {
                out = out.replace('-', ':');
            }

            lastIndex = out.lastIndexOf(':');

            if (lastIndex > out.length() - 2) {
                out = null;
            }
            else {

                end = Math.min(out.length(), lastIndex + 3);

                ++octets;
                old = lastIndex;
                while (octets != 5 && lastIndex != -1 && lastIndex > 1) {
                    lastIndex = out.lastIndexOf(':', --lastIndex);
                    if (old - lastIndex == 3 || old - lastIndex == 2) {
                        ++octets;
                        old = lastIndex;
                    }
                }

                if (octets == 5 && lastIndex > 1) {
                    out = out.substring(lastIndex - 2, end).trim();
                }
                else {
                    out = null;
                }

            }

        }

        if (out != null && out.startsWith("0x")) {
            out = out.substring(2);
        }

        return out;
    }

}
