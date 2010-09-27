package org.foobar.minesweeper.ui;

import java.awt.Point;
import javax.swing.ImageIcon;

public final class Utilities {

    /**
     * Convert a Point to a Pair.
     * @param b JBoard
     * @param p Point
     * @return Pair
     */
    public static CoordinatePair toPair(int cellsize, Point p) {
        return new CoordinatePair(p.y / cellsize + 1, p.x / cellsize + 1);
    }
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    public static ImageIcon createImageIcon(Class<?> cl, String path) {
        java.net.URL imgURL = cl.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
