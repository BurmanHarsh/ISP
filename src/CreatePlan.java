import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;


public class CreatePlan extends JFrame {
    Connection conn;
    Statement stmt;
    ResultSet rs;

    private JPanel jPanel1, jPanel2, jPanel3;
    private JLabel jLabel1, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7;
    private JTextField jTextField1, jTextField2, jTextField3, jTextField4, jTextField5;
    private JButton btnAddPlan, btnDel, btnBack, btnBuy;
    private JTable jTable1;
    private JScrollPane jScrollPane1;

    public CreatePlan() {
        super("ISP - Create Plan");
        conn = javaconnect.ConnectDb();
        initComponents();
        loadPlans();
    }

    private void initComponents() {
        // Panels
        jPanel1 = new JPanel(new BorderLayout());
        jPanel2 = new JPanel();
        jPanel3 = new JPanel(new GridLayout(6, 2, 10, 10));

        // Labels
        jLabel1 = new JLabel("Manage ISP Plans", SwingConstants.CENTER);
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 28));

        jLabel3 = new JLabel("Plan Name:");
        jLabel4 = new JLabel("Speed (Mbps):");
        jLabel5 = new JLabel("Price (₹):");
        jLabel6 = new JLabel("Description:");
        jLabel7 = new JLabel("Plan ID (for delete):");

        // TextFields
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();
        jTextField4 = new JTextField();
        jTextField5 = new JTextField();

        // Buttons
        btnAddPlan = new JButton("Add Plan");
        btnDel = new JButton("Delete Plan");
        btnBack = new JButton("Back");
        btnBuy = new JButton("Buy Selected Plan");

        // Table
        jTable1 = new JTable();
        jTable1.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Name", "Speed", "Price", "Description"}
        ));
        jScrollPane1 = new JScrollPane(jTable1);

        // Layout
        jPanel2.setLayout(new FlowLayout());
        jPanel2.add(btnAddPlan);
        jPanel2.add(btnDel);
        jPanel2.add(btnBack);
        jPanel2.add(btnBuy);

        jPanel3.add(jLabel3); jPanel3.add(jTextField1);
        jPanel3.add(jLabel4); jPanel3.add(jTextField2);
        jPanel3.add(jLabel5); jPanel3.add(jTextField3);
        jPanel3.add(jLabel6); jPanel3.add(jTextField4);
        jPanel3.add(jLabel7); jPanel3.add(jTextField5);

        jPanel1.add(jLabel1, BorderLayout.NORTH);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.SOUTH);
        jPanel1.add(jScrollPane1, BorderLayout.EAST);

        add(jPanel1);

        // Apply Dark Theme Styling
        applyTheme();

        // Button Actions
        btnAddPlan.addActionListener(e -> addPlan());
        btnDel.addActionListener(e -> deletePlan());
        btnBuy.addActionListener(e -> buyPlan());
        btnBack.addActionListener(e -> {
            dispose();
            new Admin().setVisible(true);
        });

        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void applyTheme() {
        // Panels
        jPanel1.setBackground(new Color(33, 33, 33));
        jPanel2.setBackground(new Color(45, 45, 45));
        jPanel3.setBackground(new Color(45, 45, 45));

        // Labels
        JLabel[] labels = {jLabel1, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7};
        for (JLabel l : labels) l.setForeground(new Color(245, 245, 245));
        jLabel1.setForeground(new Color(0, 153, 255));

        // Text fields
        JTextField[] fields = {jTextField1, jTextField2, jTextField3, jTextField4, jTextField5};
        for (JTextField f : fields) {
            f.setBackground(new Color(55, 55, 55));
            f.setForeground(Color.WHITE);
            f.setCaretColor(Color.WHITE);
            f.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        }

        // Buttons
        styleButton(btnAddPlan, new Color(34, 153, 84));
        styleButton(btnDel, new Color(192, 57, 43));
        styleButton(btnBack, new Color(52, 152, 219));
        styleButton(btnBuy, new Color(241, 196, 15));

        // Table styling
        jTable1.setBackground(new Color(55, 55, 55));
        jTable1.setForeground(Color.WHITE);
        jTable1.setGridColor(new Color(100, 100, 100));
        jTable1.setRowHeight(25);
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jTable1.getTableHeader().setBackground(new Color(23, 32, 42));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void loadPlans() {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM plans");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("speed"),
                    rs.getDouble("price"),
                    rs.getString("description")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading plans: " + e.getMessage());
        }
    }

    private void addPlan() {
        try {
            String name = jTextField1.getText();
            String speed = jTextField2.getText();
            double price = Double.parseDouble(jTextField3.getText());
            String desc = jTextField4.getText();

            String sql = "INSERT INTO plans (name, speed, price, description) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, speed);
            pst.setDouble(3, price);
            pst.setString(4, desc);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Plan added successfully!");
            loadPlans();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding plan: " + e.getMessage());
        }
    }

    private void deletePlan() {
        try {
            int id = Integer.parseInt(jTextField5.getText());
            String sql = "DELETE FROM plans WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Plan deleted successfully!");
            loadPlans();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting plan: " + e.getMessage());
        }
    }

    private void buyPlan() {
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a plan to buy!");
        return;
    }

    String name = jTable1.getValueAt(selectedRow, 1).toString();
    String price = jTable1.getValueAt(selectedRow, 3).toString();

    try {
        // Try to load QR image with better error handling
        ImageIcon qrIcon = null;
        String[] possiblePaths = {
            "payment_qr.jpg",
            "src/payment_qr.jpg",
            "images/payment_qr.jpg",
            "resources/payment_qr.jpg"
        };
        
        for (String path : possiblePaths) {
            java.io.File file = new java.io.File(path);
            if (file.exists()) {
                qrIcon = new ImageIcon(path);
                break;
            }
        }
        
        JLabel qrLabel;
        if (qrIcon != null) {
            // Scale the image
            Image img = qrIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            qrIcon = new ImageIcon(img);
            
            qrLabel = new JLabel(
                "<html><div style='text-align: center;'>Scan this QR to pay for <b>" + name + "</b><br/>Amount: ₹" + price + "</div></html>",
                qrIcon,
                JLabel.CENTER
            );
            qrLabel.setHorizontalTextPosition(JLabel.CENTER);
            qrLabel.setVerticalTextPosition(JLabel.BOTTOM);
        } else {
            // Show text-only version if QR image not found
            qrLabel = new JLabel(
                "<html><div style='text-align: center;'>QR Code Image Not Found<br/>" +
                "Plan: <b>" + name + "</b><br/>" +
                "Amount: ₹" + price + "<br/>" +
                "Please contact administrator for payment details.</div></html>",
                JLabel.CENTER
            );
        }

        // Create custom dialog for better display
        JOptionPane.showMessageDialog(
            this, 
            qrLabel, 
            "Payment QR - " + name, 
            JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error loading payment details: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreatePlan().setVisible(true));
    }
}
