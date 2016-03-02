import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

public class Main{

    public static void main(String[] args){	
        JFrame frame = new JFrame("Doodle");
        Container container = frame.getContentPane();

        // create Model and initialize it
        Model model = new Model();

        // create View, tell it about model (and controller)
        DrawableCanvasView drawableCanvasView = new DrawableCanvasView(model);
        PlayBackView playBackView = new PlayBackView(model);
        MenuBarView menuBarView = new MenuBarView(model);
        ColorPaletteView colorPaletteView = new ColorPaletteView(model);
        StrokeThicknessView strokeThicknessView = new StrokeThicknessView(model);
        ScrollableCanvasView scrollableCanvasView = new ScrollableCanvasView(model, drawableCanvasView);

        // tell Model about views 
        model.addObserver(drawableCanvasView);
        model.addObserver(playBackView);
        model.addObserver(menuBarView);
        model.addObserver(colorPaletteView);
        model.addObserver(strokeThicknessView);
        model.addObserver(scrollableCanvasView);

        // Setting some container properties
        container.setBackground(Color.GRAY);

        // Sidebar itself is composed of different views using a boxlayout
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.PAGE_AXIS));
        sidebar.add(colorPaletteView);
        sidebar.add(strokeThicknessView);

        // Add the views to the container
        container.add(sidebar, BorderLayout.WEST);
        container.add(scrollableCanvasView, BorderLayout.CENTER);
        container.add(playBackView, BorderLayout.SOUTH);

        // Setting the frame options
        frame.setJMenuBar(menuBarView);
        frame.setMinimumSize(new Dimension(500,500));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH); // To set maximum screen size as default
        frame.setVisible(true);

        // We do the following so this can get the CTRL-C Keystroke
        drawableCanvasView.setFocusable(true);
        drawableCanvasView.requestFocus();
    } 
}
