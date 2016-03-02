import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.*;

public class MenuBarView extends JMenuBar implements Observer {
    private static final long serialVersionUID = -9438437L;

    private Model model;

    private JMenu file, view;
    private JMenuItem saveBinary, loadBinary, saveText, loadText, create, exit;
    private JMenuItem fullSize, fitWindow;

    private JTextField filename, directory;

    private JFileChooser fileChooser;
    private FileNameExtensionFilter txtFilter, binaryFilter;

    public MenuBarView(Model model) {
        super();


        // set selected filter
        // chooser.setFileFilter(xmlfilter);

        fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new FileNameExtensionFilter("binary", "bin"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("text", "txt"));

        this.model = model;

        // Build the first heading on the menu bar
        file = new JMenu("File");
        this.add(file);

        // Build the second heading on the menu bar
        view = new JMenu("View");
        this.add(view);

        // Build the options for the 'file' option

        // The option for creating a new doodle
        create = new JMenuItem("Create New Doodle", null); // Null for images. Add Image later!
        file.add(create);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;

                if(!model.getPointList().isEmpty()) {
                    int dialogResult = JOptionPane.showConfirmDialog (null, "You have content on your current Doodle. If you would like to continue without saving, or if you have already saved the Doodle, please click YES. If you would like to save the current Doodle, click NO, then save the Doodle.", "Warning", dialogButton);

                    if(dialogResult == JOptionPane.YES_OPTION){
                        model.resetDoodle();
                    }
                } else {
                    model.resetDoodle();
                }
            }
        });

        saveBinary = new JMenuItem("Save Doodle as Binary", null); 
        file.add(saveBinary);

        saveBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save dialog
                int rVal = fileChooser.showSaveDialog(MenuBarView.this);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(fileChooser.getSelectedFile().getName());
                    directory.setText(fileChooser.getCurrentDirectory().toString());

                    MenuBarView.this.model.saveBinary(directory.getText() + "/" + filename.getText());
                }

                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("");
                    directory.setText("");
                }
            }
        });

        loadBinary = new JMenuItem("Load Doodle from Binary", null); // Null for images. Add Image later!
        file.add(loadBinary);

        loadBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;

                if(!model.getPointList().isEmpty()) {
                    int dialogResult = JOptionPane.showConfirmDialog (null, "You have content on your current Doodle. If you would like to load a new Doodle without saving the current one, or if you have already saved the current Doodle, please click YES. If you would like to save the current Doodle, click NO, then save the Doodle.", "Warning", dialogButton);

                    if(dialogResult == JOptionPane.NO_OPTION) {
                        return;
                    }
                }

                // Open dialog box
                int rVal = fileChooser.showOpenDialog(MenuBarView.this);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(fileChooser.getSelectedFile().getName());
                    directory.setText(fileChooser.getCurrentDirectory().toString());

                    // Now deserialize from file
                    MenuBarView.this.model.loadBinary(directory.getText() + "/" + filename.getText());
                }

                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("");
                    directory.setText("");
                }
            }
        });

        saveText = new JMenuItem("Save Doodle as Text", null); // Null for images. Add Image later!
        file.add(saveText);

        // Add an action listener to open a dialog when save is clicked
        saveText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rVal = fileChooser.showSaveDialog(MenuBarView.this);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(fileChooser.getSelectedFile().getName());
                    directory.setText(fileChooser.getCurrentDirectory().toString());

                    MenuBarView.this.model.saveText(directory.getText() + "/" + filename.getText());
                }

                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("");
                    directory.setText("");
                }
            }
        });

        loadText = new JMenuItem("Load Doodle from Text", null); // Null for images. Add Image later!
        file.add(loadText);

        // Add an action listener to open a dialog when save is clicked
        loadText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;

                if(!model.getPointList().isEmpty()) {
                    int dialogResult = JOptionPane.showConfirmDialog (null, "You have content on your current Doodle. If you would like to load a new Doodle without saving the current one, or if you have already saved the current Doodle, please click YES. If you would like to save the current Doodle, click NO, then save the Doodle.", "Warning", dialogButton);

                    if(dialogResult == JOptionPane.NO_OPTION) {
                        return;
                    }
                }

                int rVal = fileChooser.showOpenDialog(MenuBarView.this);

                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(fileChooser.getSelectedFile().getName());
                    directory.setText(fileChooser.getCurrentDirectory().toString());

                    MenuBarView.this.model.loadText(directory.getText() + "/" + filename.getText());
                }

                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("");
                    directory.setText("");
                }
            }
        });

        exit = new JMenuItem("Exit", null);
        file.add(exit);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!model.getPointList().isEmpty()) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "You have content on your current Doodle. If you would like to exit the application without saving, or if you have already saved the current Doodle, please click YES. If you would like to save the current Doodle, click NO, then save the Doodle.", "Warning", dialogButton);

                    if(dialogResult == JOptionPane.YES_OPTION){
                        model.closeApplication();
                    }
                } else {
                    model.closeApplication();
                }
            }
        });

        // Build the options for the 'view' option
        fullSize = new JMenuItem("Full Size", null); 
        view.add(fullSize);

        fullSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCanvasImageFullSize(true);
            }

        });

        fitWindow = new JMenuItem("Fit Window", null); // Null for images. Add Image later!
        view.add(fitWindow);

        fitWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setCanvasImageFullSize(false);
            }

        });

        this.filename = new JTextField();
        this.directory = new JTextField();
    }

    public void update(Observable observable, Object arg) {

    }
}
