import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {

    private JFrame frame;

    public HomePage(String userName) {
        frame = new JFrame("Home");
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
        homeIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        homeIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.dispose(); // Close the current frame
                new HomePage("Leo Gavin"); // Create a new instance of HomePage
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
        userIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        userIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.dispose(); // Close the current frame
                new ProfilePage();
            }
        });

        // Add components to nav bar panel
        navBarPanel.add(homeIconLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(logoLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(userIconLabel);

        frame.add(navBarPanel, BorderLayout.NORTH);

        // Central Panel
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome " + userName + " !");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centralPanel.add(welcomeLabel);

        centralPanel.add(Box.createVerticalStrut(20));

        // Menu Buttons with Icons
        String[] menuItems = {"Create Designs", "Manage Designs", "Furniture Catalogue", "Customer Projects", "Help and Tutorials", "Customer Feedback/ Suggestions", "Materials Library", "Inventory and Stock Page"};
        String[] iconPaths = {"create_designs.png", "manage_ico.png", "catalogue_icon.png", "projects_icon.png", "projects_icon.png", "projects_icon.png", "projects_icon.png", "projects_icon.png"}; // icon file names
        for (int i = 0; i < menuItems.length; i++) {
            ImageIcon buttonIcon = new ImageIcon("images/" + iconPaths[i]);
            Image buttonImage = buttonIcon.getImage();
            Image newButtonImage = buttonImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            buttonIcon = new ImageIcon(newButtonImage);

            JButton button = new JButton(menuItems[i], buttonIcon);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setHorizontalTextPosition(JButton.RIGHT);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            centralPanel.add(button);
            centralPanel.add(Box.createVerticalStrut(10));
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (menuItems[finalI]) {
                        case "Create Designs":
                            // Open the CreateDesignsPage
                            frame.dispose();
                            new DesignPage();
                            break;
                        case "Manage Designs":
                            // Open the ManageDesignsPage
                            frame.dispose();
                            new Manage_Design();
                            break;
                        case "Furniture Catalogue":
                            // Open the FurnitureCataloguePage
                            frame.dispose();
                            new FurnitureCatalogue();
                            break;
                        case "Customer Projects":
                            // Open the CustomerProjectsPage
                            frame.dispose();
                            new Customer_Projects();
                            break;
                        case "Help and Tutorials":
                            // Open the HelpTutorialsPage
                            frame.dispose();
                            new HelpTutorialsPage();
                            break;
                        case "Customer Feedback/ Suggestions":
                            // Open the FeedbackSuggestionsPage
                            frame.dispose();
                            new FeedbackSuggestionsPage();
                            break;
                        case "Materials Library":
                            // Open the MaterialsLibraryPage
                            frame.dispose();
                            new MaterialsLibraryPage();
                            break;
                        case "Inventory and Stock Page":
                            // Open the InventoryStockPage
                            frame.dispose();
                            new StockPage();
                            break;
                    }
                }
            });

        }

        // Add central panel to frame
        frame.add(centralPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Other methods
    // ...
}
