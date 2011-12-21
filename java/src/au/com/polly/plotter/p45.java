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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * Graph a data set.
 *
 * authored by dave young, out of frustration on the lack of available graphing software.
 */
public class p45 extends JFrame implements ActionListener
{
    private JLabel label;
    private TestCanvas canvas;
    private static String labelPrefix = "Number of button clicks: ";
    private int numClicks = 0;
    AxisConfiguration xAxisConfig = null;
    AxisConfiguration yAxisConfig = null;
    Axis<Long> xAxis = null;
    Axis<Double> yAxis = null;

    public p45() {
        super("HelloSwing");

        JButton button = new JButton("I'm a Swing button!");
        button.setMnemonic('i');
        button.addActionListener(this);
        button.getAccessibleContext().setAccessibleDescription(
            "When you click this button, the label is updated "
              + "to display the total number of button clicks."
        );

        label = new JLabel(labelPrefix + "0    ");
        canvas = new TestCanvas();
        canvas.setBounds( 0, 0, 600, 600 );

        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        pane.setLayout( new GridBagLayout());
        pane.add(button);
        pane.add( label );
        pane.add( canvas );

        setContentPane(pane);
    }

    public void actionPerformed(ActionEvent e) {
        numClicks++;
        label.setText(labelPrefix + numClicks);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't use the cross-platform "
                             + "look and feel: " + e);
        }

        JFrame frame = new p45();

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(l);

        frame.pack();
        frame.setVisible(true);
    }


}
