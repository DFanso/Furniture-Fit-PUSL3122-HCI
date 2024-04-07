import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Customer_Projects {

    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Customer_Projects::new);
    }

    public Customer_Projects() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Customer Projects");
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


        // Table Model
        String[] columnNames = {"Customer Name", "Design Name", "Date", "Edit", "Share", "Move", "Delete"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);

        ButtonColumnRenderer buttonRenderer = new ButtonColumnRenderer();
        for (int i = 3; i < table.getColumnCount(); i++) { // Start at column index 3 for "Edit" button
            table.getColumnModel().getColumn(i).setCellRenderer(buttonRenderer);
            table.getColumnModel().getColumn(i).setCellEditor(new ButtonColumnEditor(new JCheckBox()));
        }

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setGridColor(Color.BLACK);
        table.setRowHeight(30);

        // Adding the table to the center
        frame.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with some sample data
        addSampleData(tableModel);


        // Set frame location and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    private void addSampleData(DefaultTableModel tableModel) {
        tableModel.addRow(new Object[]{"Mr. Ranjith", "Design Name", "10/06/2023", "Edit", "Share", "Move", "Delete"});
        tableModel.addRow(new Object[]{"Mr. Mervin", "Design Name", "10/06/2023", "Edit", "Share", "Move", "Delete"});
        tableModel.addRow(new Object[]{"Mr. Mervin", "Design Name", "10/06/2023", "Edit", "Share", "Move", "Delete"});
    }

    static class ButtonColumnRenderer extends JButton implements TableCellRenderer {
        public ButtonColumnRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    static class ButtonColumnEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonColumnEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Here you should implement the logic for button click
                JOptionPane.showMessageDialog(button, label + " clicked");
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
