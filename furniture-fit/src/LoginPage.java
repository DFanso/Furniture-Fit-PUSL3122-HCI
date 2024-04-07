import javax.swing.*;
import java.awt.*;

public class LoginPage {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }

    public LoginPage() {
        // Create JFrame for login page
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(1080, 800);
        loginFrame.getContentPane().setBackground(new Color(240,233,212));
        loginFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add logo
        ImageIcon logoIcon = new ImageIcon("images/FurnitureFitLogo.png"); // Replace with actual path
        JLabel logoLabel = new JLabel(logoIcon);
        //JLabel logoLabel = new JLabel("Furniture Fit"); // Temporarily using text instead of an image
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24)); // Make the logo bigger
        loginFrame.add(logoLabel, gbc);

        // Email field
        JTextField emailField = new JTextField(15);
        loginFrame.add(createLabel("Email"), gbc);
        loginFrame.add(emailField, gbc);

        // Password field
        JPasswordField passwordField = new JPasswordField(15);
        loginFrame.add(createLabel("Password"), gbc);
        loginFrame.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            // Here you would normally include your login logic
            // After a successful login, show the dialog and open the HomePage
            JOptionPane.showMessageDialog(loginFrame, "Login Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
            String userName = emailField.getText(); // Or however you retrieve the user's name
            loginFrame.dispose(); // Close the login window
            new HomePage(userName); // Open the home page
        });
        loginFrame.add(loginButton, gbc);

        // Position the window in the center of the screen
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private JLabel createLabel(String text) {
        return new JLabel(text);
    }
}
