import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MaterialsLibraryPage extends JFrame {

    public MaterialsLibraryPage() {
        setTitle("Material Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 800);
        getContentPane().setBackground(new Color(240, 233, 212));


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
                dispose();
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
                dispose();
                new ProfilePage();
            }
        });

        navBarPanel.add(homeIconLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(logoLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(userIconLabel);

        add(navBarPanel, BorderLayout.NORTH);

        // Central Panel
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(2, 2, 20, 20));
        centralPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Material Library Items
        String[] materialItems = {"Wood", "Metal", "Glass", "Leather"};
        String[] materialDescriptions = {
                "From classic oak to modern bamboo, explore our diverse collection of wood materials to bring natural warmth and character to your designs.",
                "Discover the sleek and industrial appeal of metal materials, perfect for adding a contemporary edge to your furniture creations.",
                "Elevate your designs with the timeless elegance and transparency of glass materials, ideal for crafting sophisticated and luminous pieces.",
                "Indulge in the richness and luxury of leather materials, offering a range of colors and textures to enhance the comfort and style of your furniture."
        };

        for (int i = 0; i < materialItems.length; i++) {
            JPanel itemPanel = createMaterialItemPanel(materialItems[i], materialDescriptions[i]);
            centralPanel.add(itemPanel);
        }

        add(centralPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }



    private JPanel createMaterialItemPanel(String title, String description) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(255, 255, 255));
        panel.add(descriptionArea, BorderLayout.CENTER);

        JButton viewButton = new JButton("View");
        panel.add(viewButton, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MaterialsLibraryPage();
            }
        });
    }
}