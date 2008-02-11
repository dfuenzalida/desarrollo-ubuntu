package swing;

import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.*;

public class SwingTest {
    public static void main(String[] args) {

        // Get the native look and feel class name
        String nativeLF = UIManager.getSystemLookAndFeelClassName();
        
        // Install the look and feel
        try {
            UIManager.setLookAndFeel(nativeLF);
        } catch (Exception e){ e.printStackTrace(); }

        ResourceBundle resourceBundle = ResourceBundle.getBundle("FileSplitter");
        String message  = resourceBundle.getString("hello.world");
        Object[] params = new Object[]{ new Date(), ""};
        String greeting = MessageFormat.format(message, params);

        JButton button = new JButton(greeting);
        JFrame frame = new JFrame();

        // Get default close operation
        int op = frame.getDefaultCloseOperation(); // HIDE_ON_CLOSE

        // Set to exit on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add button to the frame
        frame.getContentPane().add(button, BorderLayout.CENTER);

        // Set initial size
        int frameWidth = 300;
        int frameHeight = 300;
        frame.setSize(frameWidth, frameHeight);

        // Show the frame
        frame.setVisible(true);
    }
}

