import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.datatransfer.*;


public class DrawableCanvasView extends JPanel implements Observer, ClipboardOwner {
    private static final long serialVersionUID = 32319937L;

    private Model model;

    private Image image; // Image that we are drawing on
    private Graphics2D g2; // Graphics object that we use to draw

    public DrawableCanvasView(Model model) {
        // We do this to show the lines AS they are being drawn
        setDoubleBuffered(false);

        this.model = model;
        this.setBackground(Color.WHITE);

        // Setup the event to go to the "controllers"
        // (these anonymous classes are essentially the controller)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.setOldX(e.getX());
                model.setOldY(e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                model.setLineEnd(true);

                model.setCurrentX(e.getX());
                model.setCurrentY(e.getY());

                model.draw();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                model.setCurrentX(e.getX());
                model.setCurrentY(e.getY());

                model.draw();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();

                // Get new size
                Dimension newSize = c.getSize();

                model.rescalePoints(newSize.getWidth(), newSize.getHeight());
            }
        });

        // Listen for CTRL-C Keystroke
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    try {
                        BufferedImage image = new Robot().createScreenCapture(new Rectangle(DrawableCanvasView.this.getLocationOnScreen().x, DrawableCanvasView.this.getLocationOnScreen().y, DrawableCanvasView.this.getWidth(), DrawableCanvasView.this.getHeight()));
                        Model.TransferableImage trans = DrawableCanvasView.this.model.new TransferableImage(image);
                        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
                        c.setContents(trans, DrawableCanvasView.this);

                    } catch(Exception exception) {
                        exception.printStackTrace(); 
                    }
                }
            }
        });
    }

    public void update(Observable observable, Object arg) {
        if(model.isCanvasImageFullSize()) {
            this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        } else {
            this.setPreferredSize(null); 
        }

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // image to draw on
        image = createImage(this.getSize().width, this.getSize().height);
        g2 = (Graphics2D) image.getGraphics();

        // enable antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // clear draw area
        clear();

        // Get List of coordinates to draw on, then using the graphics object to draw them
        ArrayList<PolyPoint> pointList = model.getPointList();

        // Draw up only until whatever position slider is currently at
        for(int i = 0; i < model.getSliderPosition(); ++i) {
            PolyPoint point = pointList.get(i);

            g2.setColor(point.getColor());
            g2.setStroke(point.getLineType().getStroke());
            g2.drawLine(point.getOldX(), point.getOldY(), point.getCurrentX(), point.getCurrentY());
        }

        g.drawImage(image, 0, 0, null);
    }

    public void clear() {
        g2.setPaint(Color.white);

        // draw white on entire draw area to clear
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);

        revalidate();
        repaint();
    }

    public void lostOwnership( Clipboard clip, Transferable trans ) {

    }
}



