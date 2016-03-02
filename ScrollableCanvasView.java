import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScrollableCanvasView extends JScrollPane implements Observer {
    private static final long serialVersionUID = 32319937L;

    private DrawableCanvasView drawableCanvasView;
    private Model model;

    public ScrollableCanvasView(Model model, DrawableCanvasView drawableCanvasView) {
        super(drawableCanvasView);

        this.drawableCanvasView = drawableCanvasView; 
        this.model = model;
    }

    public void update(Observable observable, Object arg) {
        if(model.isCanvasImageFullSize()) {
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        } else {
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            this.setSize(this.getWidth(), this.getHeight());
        }

        revalidate();
        repaint();
    }
}
