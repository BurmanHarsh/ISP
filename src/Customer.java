import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Customer extends JFrame {
    Connection conn;
    Statement stmt;
    ResultSet rs;

    private JPanel panelMain, panelButtons, panelForm;
    private JLabel lblTitle, lblID, lblName, lblEmail, lblPhone, lblPlan, lblAddress;
    private JTextField txtID, txtName, txtEmail, txtPhone, txtPlan, txtAddress;
    private JButton btnAdd, btnDelete, btnUpdate, btnBack;
    private JTable table;
    private JScrollPane scrollPane;

    public Customer() {
        super("ISP - Customer Management");
        conn = javaconnect.ConnectDb();
        initComponents();
        loadTable();
    }

    private void initComponents() {
        // Panels
        panelMain = new JPanel(new BorderLayout());
        panelButtons = new JPanel(new FlowLayout());
        panelForm = new JPanel(new GridLayout(6, 2, 10, 10));

        // Title
        lblTitle = new JLabel("Manage Customers", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 153, 255));

        // Labels
        lblID = new JLabel("Customer ID:");
        lblName = new JLabel("Name:");
        lblEmail = new JLabel("Email:");
        lblPhone = new JLabel("Phone:");
        lblPlan = new JLabel("Plan ID:");
        lblAddress = new JLabel("Address:");

        // TextFields
        txtID = new JTextField();
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtPlan = new JTextField();
        txtAddress = new JTextField();

        // Buttons
        btnAdd = new JButton("Add Customer");
        btnDelete = new JButton("Delete Customer");
        btnUpdate = new JButton("Update Customer");
        btnBack = new JButton("Back");

        // Table
        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "Email", "Phone", "Plan ID", "Address"}
        ));
        scrollPane = new JScrollPane(table);

        // Add to form panel
        panelForm.add(lblID); panelForm.add(txtID);
        panelForm.add(lblName); panelForm.add(txtName);
        panelForm.add(lblEmail); panelForm.add(txtEmail);
        panelForm.add(lblPhone); panelForm.add(txtPhone);
        panelForm.add(lblPlan); panelForm.add(txtPlan);
        panelForm.add(lblAddress); panelForm.add(txtAddress);

        // Add to buttons panel
        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnBack);

        // Add to main panel
        panelMain.add(lblTitle, BorderLayout.NORTH);
        panelMain.add(panelForm, BorderLayout.CENTER);
        panelMain.add(panelButtons, BorderLayout.SOUTH);
        panelMain.add(scrollPane, BorderLayout.EAST);

        add(panelMain);

        // Dark theme
        applyTheme();

        // Button actions
        btnAdd.addActionListener(e -> addCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
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
        panelMain.setBackground(new Color(33, 33, 33));
        panelForm.setBackground(new Color(45, 45, 45));
        panelButtons.setBackground(new Color(45, 45, 45));

        // Labels
        JLabel[] labels = {lblID, lblName, lblEmail, lblPhone, lblPlan, lblAddress};
        for (JLabel l : labels) l.setForeground(new Color(245, 245, 245));

        // Text fields
        JTextField[] fields = {txtID, txtName, txtEmail, txtPhone, txtPlan, txtAddress};
        for (JTextField f : fields) {
            f.setBackground(new Color(55, 55, 55));
            f.setForeground(Color.WHITE);
            f.setCaretColor(Color.WHITE);
            f.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        }

        // Buttons
        styleButton(btnAdd, new Color(34, 153, 84));
        styleButton(btnDelete, new Color(192, 57, 43));
        styleButton(btnUpdate, new Color(241, 196, 15));
        styleButton(btnBack, new Color(52, 152, 219));

        // Table
        table.setBackground(new Color(55, 55, 55));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(100, 100, 100));
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        table.getTableHeader().setBackground(new Color(23, 32, 42));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void loadTable() {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM customer");

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("PlanID"),
                        rs.getString("Address")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage());
        }
    }

    private void addCustomer() {
        try {
            String sql = "INSERT INTO customer (ID, Name, Email, Phone, PlanID, Address) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtID.getText());
            pst.setString(2, txtName.getText());
            pst.setString(3, txtEmail.getText());
            pst.setString(4, txtPhone.getText());
            pst.setString(5, txtPlan.getText());
            pst.setString(6, txtAddress.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer added successfully!");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage());
        }
    }

    private void deleteCustomer() {
        try {
            String sql = "DELETE FROM customer WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtID.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting customer: " + e.getMessage());
        }
    }

    private void updateCustomer() {
        try {
            String sql = "UPDATE customer SET Name=?, Email=?, Phone=?, PlanID=?, Address=? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtName.getText());
            pst.setString(2, txtEmail.getText());
            pst.setString(3, txtPhone.getText());
            pst.setString(4, txtPlan.getText());
            pst.setString(5, txtAddress.getText());
            pst.setString(6, txtID.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer updated successfully!");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating customer: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Customer().setVisible(true));
    }
}
