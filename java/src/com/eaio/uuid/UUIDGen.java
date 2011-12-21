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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.eaio.util.lang.Hex;

/**
 * This class contains methods to generate UUID fields. These methods have been
 * refactored out of {@link com.eaio.uuid.UUID}.
 * <p>
 * Starting with version 2, this implementation tries to obtain the MAC address
 * of the network card. Under Microsoft Windows, the <code>ifconfig</code>
 * command is used which may pop up a command window in Java Virtual Machines
 * prior to 1.4 once this class is initialized. The command window is closed
 * automatically.
 * <p>
 * The MAC address code has been tested extensively in Microsoft Windows,
 * Linux, Solaris 8, HP-UX 11, but should work in MacOS X and BSDs, too.
 * <p>
 * If you use JDK 6 or later, the code in {@link InterfaceAddress} will be used.
 *
 * @see <a href="http://johannburkard.de/software/uuid/">UUID</a>
 * @author <a href="mailto:jb@eaio.de">Johann Burkard</a>
 * @version $Id: UUIDGen.java 2914 2010-04-23 11:35:00Z johann $
 * @see com.eaio.uuid.UUID
 */
public final class UUIDGen {

    /**
     * No instances needed.
     */
    private UUIDGen() {
        super();
    }

    /**
     * The last time value. Used to remove duplicate UUIDs.
     */
    private static long lastTime = Long.MIN_VALUE;
    
    /**
     * The cached MAC address.
     */
    private static String macAddress = null;

    /**
     * The current clock and node value.
     */
    private static long clockSeqAndNode = 0x8000000000000000L;

    static {

        try {
            Class.forName("java.net.InterfaceAddress");
            macAddress = Class.forName(
                    "com.eaio.uuid.UUIDGen$HardwareAddressLookup").newInstance().toString();
        }
        catch (ExceptionInInitializerError err) {
            // Ignored.
        }
        catch (ClassNotFoundException ex) {
            // Ignored.
        }
        catch (LinkageError err) {
            // Ignored.
        }
        catch (IllegalAccessException ex) {
            // Ignored.
        }
        catch (InstantiationException ex) {
            // Ignored.
        }
        catch (SecurityException ex) {
            // Ignored.
        }

        if (macAddress == null) {

            Process p = null;
            BufferedReader in = null;

            try {
                String osname = System.getProperty("os.name", "");

                if (osname.startsWith("Windows")) {
                    p = Runtime.getRuntime().exec(
                            new String[] { "ipconfig", "/all" }, null);
                }
                // Solaris code must appear before the generic code
                else if (osname.startsWith("Solaris")
                        || osname.startsWith("SunOS")) {
                    String hostName = getFirstLineOfCommand(
                            "uname", "-n" );
                    if (hostName != null) {
                        p = Runtime.getRuntime().exec(
                                new String[] { "/usr/sbin/arp", hostName },
                                null);
                    }
                }
                else if (new File("/usr/sbin/lanscan").exists()) {
                    p = Runtime.getRuntime().exec(
                            new String[] { "/usr/sbin/lanscan" }, null);
                }
                else if (new File("/sbin/ifconfig").exists()) {
                    p = Runtime.getRuntime().exec(
                            new String[] { "/sbin/ifconfig", "-a" }, null);
                }

                if (p != null) {
                    in = new BufferedReader(new InputStreamReader(
                            p.getInputStream()), 128);
                    String l = null;
                    while ((l = in.readLine()) != null) {
                        macAddress = MACAddressParser.parse(l);
                        if (macAddress != null
                                && Hex.parseShort(macAddress) != 0xff) {
                            break;
                        }
                    }
                }

            }
            catch (SecurityException ex) {
                // Ignore it.
            }
            catch (IOException ex) {
                // Ignore it.
            }
            finally {
                if (p != null) {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (IOException ex) {
                            // Ignore it.
                        }
                    }
                    try {
                        p.getErrorStream().close();
                    }
                    catch (IOException ex) {
                        // Ignore it.
                    }
                    try {
                        p.getOutputStream().close();
                    }
                    catch (IOException ex) {
                        // Ignore it.
                    }
                    p.destroy();
                }
            }

        }

        if (macAddress != null) {
            clockSeqAndNode |= Hex.parseLong(macAddress);
        }
        else {
            try {
                byte[] local = InetAddress.getLocalHost().getAddress();
                clockSeqAndNode |= (local[0] << 24) & 0xFF000000L;
                clockSeqAndNode |= (local[1] << 16) & 0xFF0000;
                clockSeqAndNode |= (local[2] << 8) & 0xFF00;
                clockSeqAndNode |= local[3] & 0xFF;
            }
            catch (UnknownHostException ex) {
                clockSeqAndNode |= (long) (Math.random() * 0x7FFFFFFF);
            }
        }

        // Skip the clock sequence generation process and use random instead.

        clockSeqAndNode |= (long) (Math.random() * 0x3FFF) << 48;

    }

    /**
     * Returns the current clockSeqAndNode value.
     *
     * @return the clockSeqAndNode value
     * @see UUID#getClockSeqAndNode()
     */
    public static long getClockSeqAndNode() {
        return clockSeqAndNode;
    }

    /**
     * Generates a new time field. Each time field is unique and larger than the
     * previously generated time field.
     *
     * @return a new time value
     * @see UUID#getTime()
     */
    public static long newTime() {
        return createTime(System.currentTimeMillis());
    }
    
    /**
     * Creates a new time field from the given timestamp. Note that even identical
     * values of <code>currentTimeMillis</code> will produce different time fields.
     * 
     * @param currentTimeMillis the timestamp
     * @return a new time value
     * @see UUID#getTime()
     */
    public static synchronized long createTime(long currentTimeMillis) {

        long time;

        // UTC time

        long timeMillis = (currentTimeMillis * 10000) + 0x01B21DD213814000L;

        if (timeMillis > lastTime) {
            lastTime = timeMillis;
        }
        else {
            timeMillis = ++lastTime;
        }

        // time low

        time = timeMillis << 32;

        // time mid

        time |= (timeMillis & 0xFFFF00000000L) >> 16;

        // time hi and version

        time |= 0x1000 | ((timeMillis >> 48) & 0x0FFF); // version 1

        return time;

    }
    
    /**
     * Returns the MAC address. Not guaranteed to return anything.
     * 
     * @return the MAC address, may be <code>null</code>
     */
    public static String getMACAddress() {
        return macAddress;
    }

    /**
     * Returns the first line of the shell command.
     *
     * @param commands the commands to run
     * @return the first line of the command
     * @throws IOException
     */
    static String getFirstLineOfCommand(String... commands) throws IOException {

        Process p = null;
        BufferedReader reader = null;

        try {
            p = Runtime.getRuntime().exec(commands);
            reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()), 128);

            return reader.readLine();
        }
        finally {
            if (p != null) {
                if (reader != null) {
                    try {
                        reader.close();
                    }
                    catch (IOException ex) {
                        // Ignore it.
                    }
                }
                try {
                    p.getErrorStream().close();
                }
                catch (IOException ex) {
                    // Ignore it.
                }
                try {
                    p.getOutputStream().close();
                }
                catch (IOException ex) {
                    // Ignore it.
                }
                p.destroy();
            }
        }

    }

    /**
     * Scans MAC addresses for good ones.
     */
    static class HardwareAddressLookup {

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            String out = null;
            try {
                Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
                if (ifs != null) {
                    while (ifs.hasMoreElements()) {
                        NetworkInterface iface = ifs.nextElement();
                        byte[] hardware = iface.getHardwareAddress();
                        if (hardware != null && hardware.length == 6
                                && hardware[1] != (byte) 0xff) {
                            out = Hex.append(new StringBuilder(36), hardware).toString();
                            break;
                        }
                    }
                }
            }
            catch (SocketException ex) {
                // Ignore it.
            }
            return out;
        }

    }

}
