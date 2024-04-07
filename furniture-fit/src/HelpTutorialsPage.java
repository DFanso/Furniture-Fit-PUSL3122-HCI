import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HelpTutorialsPage {
private JFrame frame;

public HelpTutorialsPage() {
    frame = new JFrame("Help and Tutorials");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1080, 800);
    frame.getContentPane().setBackground(new Color(240, 233, 212));

    // Navigation Bar Panel
    JPanel navBarPanel = new JPanel();
    navBarPanel.setBackground(new Color(214, 196, 153));
    navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
    navBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    // Home Icon Label
    ImageIcon homeIcon = new ImageIcon("images/home.png");
    Image homeImage = homeIcon.getImage();
    Image newHomeImage = homeImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    homeIcon = new ImageIcon(newHomeImage);
    JLabel homeIconLabel = new JLabel(homeIcon);
    homeIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    homeIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    homeIconLabel.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
            frame.dispose();
            new HomePage("Leo Gavin");
        }
    });

    // Logo Label
    ImageIcon logoIcon = new ImageIcon("images/FurnitureFitLogo.png");
    Image image = logoIcon.getImage();
    Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    logoIcon = new ImageIcon(newImg);
    JLabel logoLabel = new JLabel(logoIcon);
    logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // User Icon Label
    ImageIcon userIcon = new ImageIcon("images/user.png");
    Image userImage = userIcon.getImage();
    Image newUserImage = userImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    userIcon = new ImageIcon(newUserImage);
    JLabel userIconLabel = new JLabel(userIcon);
    userIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    userIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    userIconLabel.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
            frame.dispose();
            new ProfilePage();
        }
    });

    navBarPanel.add(homeIconLabel);
    navBarPanel.add(Box.createHorizontalGlue());
    navBarPanel.add(logoLabel);
    navBarPanel.add(Box.createHorizontalGlue());
    navBarPanel.add(userIconLabel);

    frame.add(navBarPanel, BorderLayout.NORTH);

    // Help and Tutorials Content Panel
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // FAQ Section
    JLabel faqLabel = new JLabel("FAQ");
    faqLabel.setFont(new Font("Serif", Font.BOLD, 18));
    faqLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPanel.add(faqLabel);
    contentPanel.add(Box.createVerticalStrut(10));

    String[] faqQuestions = {
            "How do I create custom furniture?",
            "How can I track my order status?",
            "What payment methods are accepted?"
    };

    for (String question : faqQuestions) {
        JLabel questionLabel = new JLabel(question);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(questionLabel);
    }

    contentPanel.add(Box.createVerticalStrut(20));

    // Contact Section
    JLabel contactLabel = new JLabel("Contact");
    contactLabel.setFont(new Font("Serif", Font.BOLD, 18));
    contactLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPanel.add(contactLabel);
    contentPanel.add(Box.createVerticalStrut(10));

    String[] contactInfo = {
            "Email us: support@furniturefit.com",
            "Call us: +1 123-456-7890"
    };

    for (String info : contactInfo) {
        JLabel infoLabel = new JLabel(info);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(infoLabel);
    }

    contentPanel.add(Box.createVerticalStrut(20));

    // Tutorials Section
    JLabel tutorialsLabel = new JLabel("Tutorials");
    tutorialsLabel.setFont(new Font("Serif", Font.BOLD, 18));
    tutorialsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    contentPanel.add(tutorialsLabel);
    contentPanel.add(Box.createVerticalStrut(10));

    String[] tutorials = {
            "How to place an order",
            "How to customize your furniture",
            "How to track your delivery"
    };

    for (String tutorial : tutorials) {
        JLabel tutorialLabel = new JLabel(tutorial);
        tutorialLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tutorialLabel);
    }

    frame.add(contentPanel, BorderLayout.CENTER);

    frame.setVisible(true);
}
}