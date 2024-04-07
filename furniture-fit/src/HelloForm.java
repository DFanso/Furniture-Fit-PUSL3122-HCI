import javax.swing.*;
import java.awt.*;


public class HelloForm extends JFrame {

    private JPanel Nav;
    private JLabel logo;
    private JPanel Main;
    private JLabel user_Icon;

    public HelloForm() {
        setContentPane(Main);
        setTitle("Hello Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));

        ImageIcon logoIcon = new ImageIcon("images/FurnitureFitLogo.png");
        // Resize the ImageIcon
        Image image = logoIcon.getImage(); // Convert ImageIcon to Image
        Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // Scale it to 100x100 (or any size you prefer)
        logoIcon = new ImageIcon(newimg);  // Convert it back to ImageIcon

        // Set the resized icon to the label
        logo.setIcon(logoIcon);

        ImageIcon userIcon = new ImageIcon("images/user.png");
        // Resize the ImageIcon
        Image userImage = userIcon.getImage(); // Convert ImageIcon to Image
        Image newUserImage = userImage.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // Scale it to 100x100 (or any size you prefer)
        userIcon = new ImageIcon(newUserImage);  // Convert it back to ImageIcon

        // Set the resized icon to the label
        user_Icon.setIcon(userIcon);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HelloForm().setVisible(true);
            }
        });
    }


}
