package services;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileChooser {

    public String FileSelect(JFrame parent) {
        String path = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file

            File selectedFile = fileChooser.getSelectedFile();

            path = selectedFile.getAbsolutePath();
            // System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }

        return path;
    }
}
