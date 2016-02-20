import java.awt.*;
import javax.swing.*;

public enum LineType {

    NORMAL {
        @Override
        public Stroke getStroke() {
            return new BasicStroke(2);
        }
    },

    THICKER {
        @Override
        public Stroke getStroke() {
            return new BasicStroke(5);
        }

    },

    THICKEST {
        @Override
        public Stroke getStroke() {
            return new BasicStroke(10);
        }

    };

    public abstract Stroke getStroke();
}
