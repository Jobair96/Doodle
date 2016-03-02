import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;

public class ColorPaletteView extends JPanel implements Observer {
    private static final long serialVersionUID = 297438297L;

    private Model model;

    private JColorChooser colorChooser;

    // The following are buttons for the sidebar
    private JButton color_RED;
    private JButton color_BLUE;
    private JButton color_GREEN;
    private JButton color_YELLOW;
    private JButton color_BLACK;
    private JButton color_PURPLE;
    private JButton color_ORANGE;
    private JButton color_PINK;

    // Button for popping up JColorChooser
    private JButton moreColorChooser;

    private JPanel coloredButtonPanel;

    private JPanel activeColor;

    public ColorPaletteView(Model model) {
        this.model = model;
        this.colorChooser = new JColorChooser();
        this.colorChooser.setColor(new Color(0, 0, 0));

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(new JLabel("Pick a Color:"));

        // Add some padding
        this.add(Box.createVerticalStrut(5));

        // Now, we setup the colors on the side
        JPanel coloredButtonPanel = new JPanel();
        coloredButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));

        color_RED = new JButton();
        color_RED.setBackground(Color.RED);
        color_RED.setOpaque(true);
        color_RED.setBorderPainted(false);
        color_RED.setPreferredSize(new Dimension(50, 50));

        color_RED.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setColor(Color.RED);
            }

        });

        color_BLUE = new JButton();
        color_BLUE.setBackground(Color.BLUE);
        color_BLUE.setOpaque(true);
        color_BLUE.setBorderPainted(false);
        color_BLUE.setPreferredSize(new Dimension(50, 50));

        color_BLUE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setColor(Color.BLUE);
            }

        });

        color_GREEN = new JButton();
        color_GREEN.setBackground(Color.GREEN);
        color_GREEN.setOpaque(true);
        color_GREEN.setBorderPainted(false);
        color_GREEN.setPreferredSize(new Dimension(50, 50));

        color_GREEN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setColor(Color.GREEN);
            }

        });

        color_YELLOW = new JButton();
        color_YELLOW.setBackground(Color.YELLOW);
        color_YELLOW.setOpaque(true);
        color_YELLOW.setBorderPainted(false);
        color_YELLOW.setPreferredSize(new Dimension(50, 50));

        color_YELLOW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setColor(Color.YELLOW);
            }

        });

        color_BLACK = new JButton();
        color_BLACK.setBackground(Color.BLACK);
        color_BLACK.setOpaque(true);
        color_BLACK.setBorderPainted(false);
        color_BLACK.setPreferredSize(new Dimension(50, 50));

        color_BLACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setColor(Color.BLACK);
            }

        });

        color_PURPLE = new JButton();
        color_PURPLE.setBackground(Color.MAGENTA);
        color_PURPLE.setOpaque(true);
        color_PURPLE.setBorderPainted(false);
        color_PURPLE.setPreferredSize(new Dimension(50, 50));

        color_PURPLE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setColor(Color.MAGENTA);
            }

        });

        color_ORANGE = new JButton();
        color_ORANGE.setBackground(Color.ORANGE);
        color_ORANGE.setOpaque(true);
        color_ORANGE.setBorderPainted(false);
        color_ORANGE.setPreferredSize(new Dimension(50, 50));

        color_ORANGE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setColor(Color.ORANGE);
            }

        });

        color_PINK = new JButton();
        color_PINK.setBackground(Color.PINK);
        color_PINK.setOpaque(true);
        color_PINK.setBorderPainted(false);
        color_PINK.setPreferredSize(new Dimension(50, 50));

        color_PINK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setColor(Color.PINK);
            }

        });

        // Add all the buttons to the panel
        coloredButtonPanel.add(color_RED);
        coloredButtonPanel.add(color_BLUE);
        coloredButtonPanel.add(color_GREEN);
        coloredButtonPanel.add(color_YELLOW);
        coloredButtonPanel.add(color_BLACK);
        coloredButtonPanel.add(color_PURPLE);
        coloredButtonPanel.add(color_ORANGE);
        coloredButtonPanel.add(color_PINK);

        // Now add the color panel
        this.add(coloredButtonPanel);

        // Add some padding
        this.add(Box.createVerticalStrut(5));

        moreColorChooser = new JButton("More");

        moreColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color initialColor = model.getColor();
                Color newColor = colorChooser.showDialog(null, "Change Stroke Color", initialColor);

                if (newColor != null) {
                    model.setColor(newColor);
                }
            }
        });

        this.add(moreColorChooser);

        // Add some padding
        this.add(Box.createVerticalStrut(10));

        this.add(new JLabel("Active Color:"));

        // Add some padding
        this.add(Box.createVerticalStrut(10));

        activeColor = new JPanel();
        activeColor.setOpaque(true);
        activeColor.setBackground(Color.BLACK);

        this.add(activeColor);
    }

    public void update(Observable observable, Object arg) {
        activeColor.setBackground(model.getColor());
    }
}
