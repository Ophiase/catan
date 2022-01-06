package gui.menupanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.function.Consumer;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import cli.Utils;
import gui.Assets;
import gui.constants.ASCII;
public class Counter extends JComponent implements CanIncrease, CanDecrease {
    private int lowerLIMIT;
    private int upperLIMIT;
    private int value;
    private char[] text;
    private BufferedImage textImage;
    private Consumer<Integer> updater;

    private final int padding = 3;

    public Counter(String text, int defaultValue, int lowerLIMIT, int upperLIMIT) {
        this(text,defaultValue,lowerLIMIT,upperLIMIT,null);
    }

    public Counter(String text, int defaultValue, int lowerLIMIT, int upperLIMIT, Consumer<Integer> updater) {
        this.lowerLIMIT = lowerLIMIT;
        this.upperLIMIT = upperLIMIT;
        this.value = defaultValue;
        this.updater = updater;

        this.text = Arrays.copyOf(text.toCharArray(), text.length() + padding);
        fillNumber(defaultValue);
    }

    public void reinit(int defaultValue, int lowerLIMIT, int upperLIMIT) {
        this.lowerLIMIT = lowerLIMIT;
        this.upperLIMIT = upperLIMIT;
        this.value = defaultValue;

        fillNumber(defaultValue);
    }

    private void fillNumber(int n) {
        if (updater != null)
            try {
                updater.accept(n);
            } catch (Exception e) { 
                Utils.debug("Cannot accept this change."); return; 
            }
            
        this.value = n;

        String s = ASCII.padLeft(n, padding);
        for (int i = text.length - padding, j = 0; i < text.length; i++, j++)
            text[i] = s.charAt(j);
        
        textImage = ASCII.makeText(String.valueOf(text));
    }

    @Override
    public String toString() {
        return new String(text);
    }

    public void modify(int i) {
        if (value>upperLIMIT || value<lowerLIMIT)
            return;

        fillNumber(value);
        repaint();
    }

    public void plus() {
        if (value+1>upperLIMIT)
            return;

        fillNumber(value+1);
        repaint();
    }
    public void minus() {
        if (value-1<lowerLIMIT)
            return;

        fillNumber(value-1);
        repaint();
    }

    public int getValue() {
        return value;
    }
    
    @Override
    public void paint(Graphics g) {
        ASCII.paintText(g, this, textImage, getWidth(), getHeight());
    }
}
