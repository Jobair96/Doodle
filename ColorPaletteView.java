import java.util.Observable;
import java.util.Observer;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class ColorPaletteView extends JPanel implements Observer {
    private static final long serialVersionUID = 297438297L;

    private Model model;

    private JColorChooser colorChooser;

    public ColorPaletteView(Model model) {
        this.model = model;
        this.colorChooser = new JColorChooser();
        this.colorChooser.setColor(new Color(0, 0, 0));

        // Add color chooser to JPanel
        this.add(colorChooser);

        // Setup the event to go to the "controller"
        // (this anonymous class is essentially the controller)
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                Color color = ColorPaletteView.this.colorChooser.getColor();
                model.setColor(color);
            }
        });
    }

    public void update(Observable observable, Object arg) {

    }
}
