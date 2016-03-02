import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class StrokeThicknessView extends JPanel implements Observer {
    private static final long serialVersionUID = -28938293833L;

    private Model model;

    private JComboBox comboBox;

    public StrokeThicknessView(Model model) {

        comboBox = new JComboBox(LineType.values());
        comboBox.setRenderer(new LineRenderer());
        comboBox.setSelectedItem(0);

        this.add(comboBox);

        this.model = model;

        // Setup the event to go to the "controller"
        // (this anonymous class is essentially the controller)
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    model.setLineType((LineType) e.getItem());
                }
            }
        });
    }

    public void update(Observable observable, Object arg) {
        LineType lineType = model.getLineType();

        if(lineType == LineType.NORMAL) {
            comboBox.setSelectedItem(LineType.NORMAL);
        } else if(lineType == LineType.THICKER) {
            comboBox.setSelectedItem(LineType.THICKER);
        } else if(lineType == LineType.THICKEST) {
            comboBox.setSelectedItem(LineType.THICKEST);
        }
    }

    private class LineRenderer extends JPanel implements ListCellRenderer {
        private static final long serialVersionUID = 4893482901L;

        private LineType value;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            if (value != null) {
                g2d.setStroke(value.getStroke());
                g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            }
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof LineType) {
                setLineType((LineType) value);
            } else {
                setLineType(null);
            }

            return this;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50, 20);
        }

        private void setLineType(LineType value) {
            this.value = value;
        }
    }
}
