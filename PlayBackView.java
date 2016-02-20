import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class PlayBackView extends JPanel implements Observer {

    private static final long serialVersionUID = -39291933L;

    private Model model;

    // The following are the gui elements
    private JButton play, start, end;
    private JSlider progress;
    private JLabel time;
    private JPanel row;

    private Timer increaseValue, decreaseValue, decreaseThenIncreaseValue;

    public PlayBackView(Model model) {
        this.model = model;

        this.play = new JButton("Play");
        this.start = new JButton("Start");
        this.end = new JButton("End");
        this.progress = new JSlider();

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
        progress.setMajorTickSpacing(50);
        progress.setPaintTicks(true);
        progress.setPaintLabels(true);
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

        decreaseValue = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                if(progress.getMinimum() != progress.getValue()) {
                    progress.setValue(progress.getValue() - 1); 
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
        decreaseValue.start();
    }

    public void goToEnd() {
        increaseValue.start();
    }

}
