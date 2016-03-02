import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.beans.*;
import java.awt.datatransfer.*;
import javax.imageio.*;
import java.awt.image.*;

public class Model extends Observable {
    private static final long serialVersionUID = -2919219219L;

    private boolean lineEnd = false;

    private boolean canvasImageFullSize = false;

    private double width, height;

    // Mouse coordinates
    private int oldX, oldY, currentX, currentY;

    // Current color selected to draw with
    private Color color;

    // Stroke type
    private LineType lineType;

    private ArrayList<PolyPoint> pointList;

    private int sliderPosition;

    private FileOutputStream fileOutputStream;
    private File file;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    private ObjectOutputStream objectOutputStream;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;

    private boolean isZero(double value, double threshold) {
        return value >= -threshold && value <= threshold;
    }

    // Constructor
    public Model () {

        this.color = new Color(0, 0, 0);
        this.lineType = LineType.NORMAL;

        this.pointList = new ArrayList<PolyPoint>();
        this.sliderPosition = 0;

        this.width = 0.0d;
        this.height = 0.0d;
    }


    public Color getColor() {

        return this.color;

    }

    public void setColor(Color color) {

        this.color = color; 

        this.setChanged();
        this.notifyObservers();
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
        this.notifyObservers();
    }

    public ArrayList<PolyPoint> getPointList() {

        return pointList; 

    }

    public void setPointlist(ArrayList<PolyPoint> pointList) {

        this.pointList = pointList;

        setChanged();
        this.notifyObservers();

    }


    public void undo(int start, int end) {


        pointList.subList(start, end).clear();


        setChanged();
        this.notifyObservers();

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
        this.notifyObservers();
        //                                         // store current coords x,y as olds x,y
        oldX = currentX;
        oldY = currentY;

    }

    public void rescalePoints(double newWidth, double newHeight) {

        if(isZero(width, 0.000001) || isZero(height, 0.000001)) {
            this.width = newWidth;
            this.height = newHeight;

            return;
        }

        double scaleWidth = newWidth / width;
        double scaleHeight = newHeight / height;

        if(!canvasImageFullSize) {
            for(PolyPoint point : pointList) {
                Double oldX = ((point.getOldX() * scaleWidth));
                Double oldY = ((point.getOldY() * scaleHeight));
                Double currentX = ((point.getCurrentX() * scaleWidth));
                Double currentY = ((point.getCurrentY() * scaleHeight));

                point.setOldX(oldX.intValue());
                point.setOldY(oldY.intValue());
                point.setCurrentX(currentX.intValue());
                point.setCurrentY(currentY.intValue());
            }
        }

        this.width = newWidth;
        this.height = newHeight;

        setChanged();
        this.notifyObservers();
    }

    public void saveBinary(String fullPathToFile) {
        try {
            try {
                String extension = "";

                int i = fullPathToFile.lastIndexOf('.');

                if (i > 0) {
                    extension = fullPathToFile.substring(i+1);

                    if(extension.equals("bin")) {
                        fileOutputStream = new FileOutputStream(fullPathToFile);
                    }
                } else {
                    fileOutputStream = new FileOutputStream(fullPathToFile + ".bin");
                }

                objectOutputStream = new ObjectOutputStream(fileOutputStream);

                objectOutputStream.writeObject(lineEnd);
                objectOutputStream.writeObject(canvasImageFullSize);
                objectOutputStream.writeObject(width);
                objectOutputStream.writeObject(height);
                objectOutputStream.writeObject(oldX);
                objectOutputStream.writeObject(oldY);
                objectOutputStream.writeObject(currentX);
                objectOutputStream.writeObject(currentY);
                objectOutputStream.writeObject(color);
                objectOutputStream.writeObject(lineType);
                objectOutputStream.writeObject(pointList);
                objectOutputStream.writeObject(sliderPosition);

            } finally {
                objectOutputStream.close();
                fileOutputStream.close();
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public void loadBinary(String fullPathToFile) {
        try {
            try {
                fileInputStream = new FileInputStream(fullPathToFile);
                objectInputStream = new ObjectInputStream(fileInputStream);

                this.lineEnd = (boolean) objectInputStream.readObject();
                this.canvasImageFullSize = (boolean) objectInputStream.readObject();
                this.width = (double) objectInputStream.readObject();
                this.height = (double) objectInputStream.readObject();
                this.oldX = (int) objectInputStream.readObject();
                this.oldY = (int) objectInputStream.readObject();
                this.currentX = (int) objectInputStream.readObject();
                this.currentY = (int) objectInputStream.readObject();
                this.color = (Color) objectInputStream.readObject();
                this.lineType = (LineType) objectInputStream.readObject();
                this.pointList = (ArrayList<PolyPoint>) objectInputStream.readObject();
                this.sliderPosition = (int) objectInputStream.readObject();

                setChanged();
                this.notifyObservers();

            } finally {
                fileInputStream.close();
                objectInputStream.close();
            }

        } catch(Exception exception) {

        }

    }

    public void saveText(String fullPathToFile) {
        try {
            try {
                String extension = "";

                int i = fullPathToFile.lastIndexOf('.');

                if (i > 0) {
                    extension = fullPathToFile.substring(i+1);

                    if(extension.equals("txt")) {
                        file = new File(fullPathToFile);
                    }
                } else {
                    file = new File(fullPathToFile + ".txt");
                }

                fileWriter = new FileWriter(file.getAbsoluteFile());
                bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(this.toString());
            } finally {
                bufferedWriter.close();
                fileWriter.close();
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public void loadText(String fullPathToFile) {
        try {
            try {

                pointList.clear();
                sliderPosition = 0;

                Scanner scanner = new Scanner(new File(fullPathToFile));

                String lineCounter;
                int length = 0;

                while (scanner.hasNext()){
                    lineCounter = scanner.nextLine();
                    length++;
                }

                String[] array = new String[length];

                scanner = new Scanner(new File(fullPathToFile));

                for (int i = 0; i < length; i++) {
                    array[i] = scanner.nextLine();
                }

                scanner.close();

                for(int j = 0; j < array.length; ++j) {


                    // Parse every line of polypoint
                    String line = array[j]; 
                    String[] tokens = line.split(" ");

                    for(int i = 0; i < tokens.length; ++i) {
                        if(tokens[0].equals("[PolyPoint:")) {
                            int oldX = Integer.parseInt(tokens[2]);
                            int oldY = Integer.parseInt(tokens[4]);
                            int currentX = Integer.parseInt(tokens[6]);
                            int currentY = Integer.parseInt(tokens[8]);
                            Color c = new Color(Integer.parseInt(tokens[10]));

                            String lineTypeAsString = tokens[12];
                            LineType lineType = LineType.NORMAL;

                            if(lineTypeAsString.equals("NORMAL")) {
                                lineType = LineType.NORMAL; 
                            } else if(lineTypeAsString.equals("THICKER")) {
                                lineType = LineType.THICKER; 
                            } else if(lineTypeAsString.equals("THICKEST")) {
                                lineType = LineType.THICKEST; 
                            }

                            boolean lineEnd = Boolean.parseBoolean(tokens[14]);

                            PolyPoint polyPoint = new PolyPoint(
                                    c, lineType, lineEnd, 
                                    oldX, oldY, currentX, currentY
                                    );

                            pointList.add(polyPoint);

                            break;
                        } else if(tokens[0].equals(",")) {
                            int oldX = Integer.parseInt(tokens[3]);
                            int oldY = Integer.parseInt(tokens[5]);
                            int currentX = Integer.parseInt(tokens[7]);
                            int currentY = Integer.parseInt(tokens[9]);
                            Color c = new Color(Integer.parseInt(tokens[11]));

                            String lineTypeAsString = tokens[13];
                            LineType lineType = LineType.NORMAL;

                            if(lineTypeAsString.equals("NORMAL")) {
                                lineType = LineType.NORMAL; 
                            } else if(lineTypeAsString.equals("THICKER")) {
                                lineType = LineType.THICKER; 
                            } else if(lineTypeAsString.equals("THICKEST")) {
                                lineType = LineType.THICKEST; 
                            }

                            boolean lineEnd = Boolean.parseBoolean(tokens[15]);

                            PolyPoint polyPoint = new PolyPoint(
                                    c, lineType, lineEnd, 
                                    oldX, oldY, currentX, currentY
                                    );

                            pointList.add(polyPoint);

                            break;
                        } 
                        else if(tokens[0].equals("sliderPosition:")) {
                            this.sliderPosition = Integer.parseInt(tokens[1]);
                            break;
                        }
                        else if(tokens[0].equals("lineType:")) {
                            String lineTypeAsString = tokens[1];
                            LineType lineType = LineType.NORMAL;

                            if(lineTypeAsString.equals("NORMAL")) {
                                this.lineType = LineType.NORMAL; 
                            } else if(lineTypeAsString.equals("THICKER")) {
                                this.lineType = LineType.THICKER; 
                            } else if(lineTypeAsString.equals("THICKEST")) {
                                this.lineType = LineType.THICKEST; 
                            }

                            break;
                        }
                        else if(tokens[0].equals("color:")) {
                            this.color = new Color(Integer.parseInt(tokens[1]));
                            break;
                        }
                        else if(tokens[0].equals("currentX:")) {
                            this.currentX = Integer.parseInt(tokens[1]);
                            break;
                        }
                        else if(tokens[0].equals("currentY:")) {
                            this.currentY = Integer.parseInt(tokens[1]);
                            break;
                        }
                        else if(tokens[0].equals("oldX:")) {
                            this.oldX = Integer.parseInt(tokens[1]);
                            break;
                        }
                        else if(tokens[0].equals("oldY:")) {
                            this.oldY = Integer.parseInt(tokens[1]);
                            break;
                        }
                        else if(tokens[0].equals("height:")) {
                            this.height = Double.parseDouble(tokens[1]);
                            break;
                        }
                        else if(tokens[0].equals("width:")) {
                            this.width = Double.parseDouble(tokens[1]);
                            break;
                        }
                        else if(tokens[0].equals("canvasImageFullSize:")) {
                            this.canvasImageFullSize = Boolean.parseBoolean(tokens[1]);
                            break;
                        }
                        else if(tokens[0].equals("lineEnd:")) {
                            this.lineEnd = Boolean.parseBoolean(tokens[1]);
                            break;
                        }
                    }
                }
            } finally {
                setChanged();
                this.notifyObservers();

                fileInputStream.close();
                objectInputStream.close();
            }

        } catch(Exception exception) {

        }

    }

    public String toString() {
        return pointList 
            + "\n" + "sliderPosition: " + sliderPosition
            + "\n" + "lineType: " + lineType
            + "\n" + "color: " + color
            + "\n" + "currentY: " + currentY
            + "\n" + "currentX: " + currentX
            + "\n" + "oldY: " + oldY
            + "\n" + "oldX: " + oldX
            + "\n" + "height: " + height
            + "\n" + "width: " + width
            + "\n" + "canvasImageFullSize: " + canvasImageFullSize
            + "\n" + "lineEnd: " + lineEnd;
    }

    public void setCanvasImageFullSize(boolean canvasImageFullSize) {
        this.canvasImageFullSize = canvasImageFullSize; 

        setChanged();
        this.notifyObservers();
    }

    public boolean isCanvasImageFullSize() {
        return canvasImageFullSize; 
    }

    public class TransferableImage implements Transferable {

        Image image;

        public TransferableImage(Image image) {
            this.image = image;
        }

        public Object getTransferData( DataFlavor flavor ) 
            throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && image != null ) {
                return image;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
    }

    public void lostOwnership( Clipboard clip, Transferable trans ) {

    }

    public void resetDoodle() {
        this.color = new Color(0, 0, 0);
        this.lineType = LineType.NORMAL;

        this.pointList.clear();
        this.setSliderPosition(0);

        this.width = 0.0d;
        this.height = 0.0d;

    }

    public void closeApplication() {
        System.exit(0); 
    }

    public LineType getLineType() {
        return this.lineType; 
    }

}
