import javax.swing.*;
import java.awt.*;

public class SignUpPage {

    private JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpPage::new);
    }

    public SignUpPage() {
        // Create JFrame for sign-up page
        frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 800);
        frame.getContentPane().setBackground(new Color(240,233,212));
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add logo
        ImageIcon logoIcon = new ImageIcon("images/FurnitureFitLogo.png"); // Replace with actual path
        JLabel logoLabel = new JLabel(logoIcon);
        //JLabel logoLabel = new JLabel("Furniture Fit"); // Temporarily using text instead of an image
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24)); // Make the logo bigger
        frame.add(logoLabel, gbc);

        // Designer Name field
        JTextField designerNameField = new JTextField(15);
        frame.add(createLabel("Designer Name"), gbc);
        frame.add(designerNameField, gbc);

        // Email field
        JTextField emailField = new JTextField(15);
        frame.add(createLabel("Email"), gbc);
        frame.add(emailField, gbc);

        // Phone field
        JTextField phoneField = new JTextField(15);
        frame.add(createLabel("Phone"), gbc);
        frame.add(phoneField, gbc);

        // Password field
        JPasswordField passwordField = new JPasswordField(15);
        frame.add(createLabel("Password"), gbc);
        frame.add(passwordField, gbc);

        // Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            // Here you would normally include your sign-up logic
            // and after a successful sign-up you show the dialog
            JOptionPane.showMessageDialog(frame, "Sign Up Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        frame.add(signUpButton, gbc);

        // Back to Login button
        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the sign-up window
            new LoginPage(); // Open the login window
        });
        frame.add(backButton, gbc);

        // Position the window in the center of the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }
}
