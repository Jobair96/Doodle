import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.Image.*;
import java.io.*;
import javax.imageio.*;

public class PlayBackView extends JPanel implements Observer {
    private static final long serialVersionUID = -39291933L;

    private Model model;

    // The following are the gui elements
    private JButton play, start, end;
    private JSlider progress;
    private JPanel row;

    // Timers for increasing and decreasing slider at certian values
    private Timer increaseValue;

    public PlayBackView(Model model) {
        this.model = model;

        this.progress = new JSlider();

        // Setting up the icons
        Icon playIcon = new ImageIcon("Play.png");
        this.play = new JButton(playIcon);

        Icon endIcon = new ImageIcon("End.png");
        this.end = new JButton(endIcon);

        Icon startIcon = new ImageIcon("Start.png");
        this.start = new JButton(startIcon);

        // Put the above into a row
        this.row = new JPanel();
        this.row.setLayout(new BoxLayout(this.row, BoxLayout.X_AXIS));

        this.row.add(play);
        this.row.add(Box.createHorizontalStrut(5));
        this.row.add(progress);
        this.row.add(Box.createHorizontalStrut(5));
        this.row.add(start);
        this.row.add(Box.createHorizontalStrut(5));
        this.row.add(end);

        // Setting some properties of the JSlider
        progress.setMajorTickSpacing(0);
        progress.setPaintTicks(true);
        progress.setPaintLabels(false);
        progress.setValue(0);

        // And add them to this component.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(row);

        // Setup the event to go to the "controller"
        // (this anonymous class is essentially the controller)
        progress.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                JSlider slider = (JSlider) evt.getSource();

                if(slider.getValueIsAdjusting()) {
                    int value = slider.getValue();
                    model.setSliderPosition(value);
                }
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayBackView.this.showEntireAnimation();  
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayBackView.this.goToBeginning();
            }
        });

        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayBackView.this.goToEnd();
            }
        });

        // Initialize timers
        increaseValue = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(progress.getMaximum() != progress.getValue()) {
                    progress.setValue(progress.getValue() + 1); 
                    model.setSliderPosition(progress.getValue());
                } else {
                    ((Timer) e.getSource()).stop(); 
                }
            }
        });
    }

    public void update(Observable observable, Object arg) {
        progress.setMinimum(0);
        progress.setMaximum(model.getPointList().size());
        progress.setValue(model.getSliderPosition());

        if(!model.getPointList().isEmpty()) {
            enableSliderAndButtons(); 
        } else {
            disableSliderAndButtons(); 
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        progress.repaint();
    }

    public void showEntireAnimation() {
        model.setSliderPosition(0);
        increaseValue.start();
    }

    public void goToBeginning() {
        model.setSliderPosition(progress.getMinimum());
    }

    public void goToEnd() {
        model.setSliderPosition(progress.getMaximum());
    }

    private void disableSliderAndButtons() {
        play.setEnabled(false);
        start.setEnabled(false);
        end.setEnabled(false);
        progress.setEnabled(false);
    }

    private void enableSliderAndButtons() {
        play.setEnabled(true);
        start.setEnabled(true);
        end.setEnabled(true);
        progress.setEnabled(true);
    }
}
