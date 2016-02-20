import java.util.Observable;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

public class Model extends Observable implements Serializable {

    private boolean lineEnd = false;

    // Mouse coordinates
    private int oldX, oldY, currentX, currentY;

    // Current color selected to draw with
    private Color color;

    // Stroke type
    private LineType lineType;

    private ArrayList<PolyPoint> pointList;

    private int sliderPosition;

    // Constructor
    public Model () {

        this.color = new Color(0, 0, 0);
        this.lineType = LineType.NORMAL;

        this.pointList = new ArrayList<PolyPoint>();
        this.sliderPosition = 0;

    }

    // Getters and setters

    public Color getColor() {

        return this.color;

    }

    public void setColor(Color color) {

        this.color = color; 

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

    public void setLineType(LineType lineType) {

        this.lineType = lineType; 

    }

    public void setLineEnd(boolean lineEnd) {

        this.lineEnd = lineEnd; 

    }

    public int getSliderPosition() {

        return sliderPosition; 

    }

    public void setSliderPosition(int sliderPosition) {
        int oldSliderPosition = this.sliderPosition;
        this.sliderPosition = sliderPosition;

        /*
           if(oldSliderPosition < this.sliderPosition) {
           this.undo(sliderPosition, this.sliderPosition);
           } 
           */

        setChanged();
        notifyObservers();
    }

    public ArrayList<PolyPoint> getPointList() {

        return pointList; 

    }

    public void undo(int start, int end) {


        pointList.subList(start, end).clear();


        setChanged();
        notifyObservers();

    }

    public void draw() {

        // Logic so that if the slider is not at the end, we delete
        // everything from current slider position to end, then add the
        // points accordingly
        int currentSliderPosition = sliderPosition;

        if(currentSliderPosition != pointList.size()) {
            pointList.subList(currentSliderPosition, pointList.size()).clear();
        }


        PolyPoint polyPoint = new PolyPoint(
                this.color, this.lineType, this.lineEnd, 
                this.oldX, this.oldY, this.currentX, this.currentY
                );

        pointList.add(polyPoint);

        sliderPosition = pointList.size();

        setChanged();
        notifyObservers();
        //                                         // store current coords x,y as olds x,y
        oldX = currentX;
        oldY = currentY;

    }

    private void writeObject(java.io.ObjectOutputStream stream)
        throws IOException {

        System.out.println(countObservers());
        stream.defaultWriteObject();
        System.out.println(countObservers());
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {

        System.out.println(countObservers());
        stream.defaultReadObject();
        System.out.println(countObservers());

        this.setChanged();
        this.notifyObservers();
    }

}
