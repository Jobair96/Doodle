import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

public class SideBarView extends JPanel implements Observer {

    private static final long serialVersionUID = 32319937L;

    private Model model;

    public SideBarView(Model model) {
        
        this.model = model;

    }

    public void update(Observable observable, Object arg) {
    
    }

}
