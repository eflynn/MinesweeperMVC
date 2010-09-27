package org.foobar.minesweeper.ui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JCounterLabel extends JLabel {

    private static final Font myFont = new Font("Monospaced", Font.BOLD, 18);
    private int number;
    private int initialValue;

    public JCounterLabel() {
        this(0);
    }

    public JCounterLabel(int initialValue) {
        this.initialValue = initialValue;
        setCounter(initialValue);
        setBorder(BorderFactory.createLoweredBevelBorder());
        setFont(myFont);
    }

    public void reset() {
        setCounter(initialValue);
    }

    public void increment() {
        setCounter(number + 1);
    }

    public void decrement() {
        setCounter(number - 1);
    }

    public void setCounter(int number) {
        this.number = number;
        setText(String.format("%03d", number));
    }

    public int getCounter() {
        return number;
    }
}
