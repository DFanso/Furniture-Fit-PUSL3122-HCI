import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FurnitureCatalogue {

    private JFrame frame;

    public FurnitureCatalogue() {
        frame = new JFrame("Furniture Catalogue");
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
        homeIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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
        userIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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

        // Catalogue Panel
        JPanel cataloguePanel = new JPanel();
        cataloguePanel.setLayout(new GridLayout(2, 4, 10, 10));
        cataloguePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Furniture Items
        String[] items = {"Teak Ceylon Authentic table", "Teak Ceylon Authentic table", "Teak Ceylon Authentic table", "Teak Ceylon Authentic table",
                "Teak Ceylon Authentic table", "Teak Ceylon Authentic table", "Teak Ceylon Authentic table", "Teak Ceylon Authentic table"};
        String[] prices = {"4 x 5", "4 x 5", "4 x 5", "4 x 5", "4 x 5", "4 x 5", "4 x 5", "4 x 5"};
        String[] imagePaths = {"table1.png", "table2.png", "table3.png", "table4.png", "table5.png", "table6.png", "table7.png", "table8.png"};

        for (int i = 0; i < items.length; i++) {
            ImageIcon itemIcon = new ImageIcon("images/" + imagePaths[i]);
            Image itemImage = itemIcon.getImage();
            Image newItemImage = itemImage.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            itemIcon = new ImageIcon(newItemImage);

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBackground(Color.WHITE);

            JLabel imageLabel = new JLabel(itemIcon);
            JLabel nameLabel = new JLabel(items[i]);
            JLabel priceLabel = new JLabel(prices[i]);

            itemPanel.add(imageLabel);
            itemPanel.add(nameLabel);
            itemPanel.add(priceLabel);

            cataloguePanel.add(itemPanel);
        }

        frame.add(cataloguePanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}