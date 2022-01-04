package gui.menupanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import gui.Assets;
public class Counter extends JComponent{
    private final int lowerLIMIT;
    private final int upperLIMIT;
    private int value;
    private char[] text;

    public Counter(String text, int defaultValue, int lowerLIMIT, int upperLIMIT) {
        this.lowerLIMIT = lowerLIMIT;
        this.upperLIMIT = upperLIMIT;
        this.value = defaultValue;

        Arrays.copyOf(text.toCharArray(), text.length() + 3);
    }
    
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
    }
}
