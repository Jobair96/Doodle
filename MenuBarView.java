import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MenuBarView extends JPanel implements Observer {

    private static final long serialVersionUID = -9438437L;

    private Model model;

    private JMenuBar menuBar;
    private JMenu file, view;
    private JMenuItem save, load, create, exit;
    private JMenuItem fullSize, fitWindow;

    //private static JFileChooser chooser;

    private JTextField filename, directory;

    public JMenuBar getMenuBar() {

        return menuBar;

    }

    public MenuBarView(Model model) {

        this.model = model;

        menuBar = new JMenuBar();

        // Build the first heading on the menu bar
        file = new JMenu("File");
        menuBar.add(file);

        // Build the second heading on the menu bar
        view = new JMenu("View");
        menuBar.add(view);

        // Build the options for the 'file' option
        save = new JMenuItem("Save Doodle", null); // Null for images. Add Image later!
        file.add(save);

        // Add an action listener to open a dialog when save is clicked
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // Save dialog
                int rVal = fileChooser.showSaveDialog(MenuBarView.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(fileChooser.getSelectedFile().getName());
                    directory.setText(fileChooser.getCurrentDirectory().toString());

                    // Now serialize to the file
                    try {
                        FileOutputStream outputFileStream = new FileOutputStream(directory.getText() + "/" + filename.getText());
                        ObjectOutputStream outputStream = new ObjectOutputStream(outputFileStream);

                        outputStream.writeObject(MenuBarView.this.model);
                        outputStream.close();
                        outputFileStream.close();

                    } catch(IOException exception) {
                        exception.printStackTrace();
                    }
                }

                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("You pressed cancel");
                    directory.setText("");
                }
            }
        });

        load = new JMenuItem("Load Doodle", null); // Null for images. Add Image later!
        file.add(load);

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // Open dialog box
                int rVal = fileChooser.showOpenDialog(MenuBarView.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(fileChooser.getSelectedFile().getName());
                    directory.setText(fileChooser.getCurrentDirectory().toString());

                    // Now deserialize from file
                    try {
                        FileInputStream inputFileStream = new FileInputStream(directory.getText() + "/" + filename.getText());
                        ObjectInputStream inputStream = new ObjectInputStream(inputFileStream);

                        MenuBarView.this.model = (Model) inputStream.readObject();
                        inputStream.close();
                        inputFileStream.close();

                    } catch(Exception exception) {
                        exception.printStackTrace();
                    }
                }

                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("You pressed cancel");
                    directory.setText("");
                }
            }
        });

        create = new JMenuItem("Create New Doodle", null); // Null for images. Add Image later!
        file.add(create);

        exit = new JMenuItem("Exit", null); // Null for images. Add Image later!
        file.add(exit);

        // Build the options for the 'view' option
        fullSize = new JMenuItem("Full Size", null); // Null for images. Add Image later!
        view.add(fullSize);

        fitWindow = new JMenuItem("Fit Window", null); // Null for images. Add Image later!
        view.add(fitWindow);

        this.filename = new JTextField();
        this.directory = new JTextField();
    }

    public void update(Observable observable, Object arg) {

    }

    /*
       public void serializeStaticState(ObjectOutputStream os) throws IOException {
       os.writeObject(model);
       }

       public void deserializeStaticState(ObjectInputStream os) throws IOException {
       model = (Model) os.readObject();
       }*/

}
