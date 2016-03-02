import java.awt.*;
import javax.swing.*;
import java.io.*;

public class PolyPoint implements Serializable {
    private static final long serialVersionUID = -94292389L;

    private int oldX, oldY, currentX, currentY;

    // Current color selected to draw with
    private Color color;

    // Stroke type
    private LineType lineType;

    // To check if this is the last point in the line
    private boolean lineEnd = false;

    public String toString() {
        String representation = "";

        representation += "PolyPoint:";
        representation += " oldX: " + oldX;
        representation += " oldY: " + oldY;
        representation += " currentX: " + currentX;
        representation += " currentY: " + currentY;
        representation += " color: " + Integer.toString(color.getRGB());
        representation += " LineType: " + lineType;
        representation += " LineEnd: " + lineEnd;
        representation += "\n";

        return representation;
    }

    // Constructor
    public PolyPoint (
            Color color, LineType lineType, boolean lineEnd, 
            int oldX, int oldY, int currentX, int currentY
        ) {
        this.color = color;
        this.lineType = lineType;
        this.lineEnd = lineEnd;
        this.oldX = oldX;
        this.oldY = oldY;
        this.currentX = currentX;
        this.currentY = currentY;
    }

    // Getters and setters

    public Color getColor() {
        return this.color;
    }

    public int getOldX() {
        return this.oldX; 
    }

    public void setOldX(int oldX) {
        this.oldX = oldX; 
    }

    public int getOldY() {
        return this.oldY; 
    }

    public void setOldY(int oldY) {
        this.oldY = oldY; 
    }

    public int getCurrentX() {
        return this.currentX; 
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return this.currentY; 
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public LineType getLineType() {
        return this.lineType;
    }
}
