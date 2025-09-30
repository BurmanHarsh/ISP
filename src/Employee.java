import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Employee extends JFrame {
    Connection conn;
    Statement stmt;
    ResultSet rs;

    private JPanel panelMain, panelButtons, panelForm;
    private JLabel lblTitle, lblID, lblName, lblContact, lblJoinDate, lblAddress, lblLeaveDate;
    private JTextField txtID, txtName, txtContact, txtJoinDate, txtAddress, txtLeaveDate;
    private JButton btnAdd, btnDelete, btnUpdate, btnBack;
    private JTable table;
    private JScrollPane scrollPane;

    public Employee() {
        super("ISP - Employee Management");
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
        lblTitle = new JLabel("Manage Employees", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 153, 255));

        // Labels
        lblID = new JLabel("Employee ID:");
        lblName = new JLabel("Name:");
        lblContact = new JLabel("Contact:");
        lblJoinDate = new JLabel("Join Date:");
        lblAddress = new JLabel("Address:");
        lblLeaveDate = new JLabel("Leave Date:");

        // TextFields
        txtID = new JTextField();
        txtName = new JTextField();
        txtContact = new JTextField();
        txtJoinDate = new JTextField();
        txtAddress = new JTextField();
        txtLeaveDate = new JTextField();

        // Buttons
        btnAdd = new JButton("Add Employee");
        btnDelete = new JButton("Delete Employee");
        btnUpdate = new JButton("Update Employee");
        btnBack = new JButton("Back");

        // Table
        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "Contact", "Join Date", "Address", "Leave Date"}
        ));
        scrollPane = new JScrollPane(table);

        // Add to form panel
        panelForm.add(lblID); panelForm.add(txtID);
        panelForm.add(lblName); panelForm.add(txtName);
        panelForm.add(lblContact); panelForm.add(txtContact);
        panelForm.add(lblJoinDate); panelForm.add(txtJoinDate);
        panelForm.add(lblAddress); panelForm.add(txtAddress);
        panelForm.add(lblLeaveDate); panelForm.add(txtLeaveDate);

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
        btnAdd.addActionListener(e -> addEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
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
        JLabel[] labels = {lblID, lblName, lblContact, lblJoinDate, lblAddress, lblLeaveDate};
        for (JLabel l : labels) l.setForeground(new Color(245, 245, 245));

        // Text fields
        JTextField[] fields = {txtID, txtName, txtContact, txtJoinDate, txtAddress, txtLeaveDate};
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
            rs = stmt.executeQuery("SELECT ID, Name, Contact, JoinDate, Address, LeaveDate FROM employee");

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("ID"),
                        rs.getString("Name"),
                        rs.getString("Contact"),
                        rs.getString("JoinDate"),
                        rs.getString("Address"),
                        rs.getString("LeaveDate")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employees: " + e.getMessage());
        }
    }

    private void addEmployee() {
        try {
            String sql = "INSERT INTO employee (ID, Name, Contact, JoinDate, Address, LeaveDate) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtID.getText());
            pst.setString(2, txtName.getText());
            pst.setString(3, txtContact.getText());
            pst.setString(4, txtJoinDate.getText());
            pst.setString(5, txtAddress.getText());
            pst.setString(6, txtLeaveDate.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee added successfully!");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        try {
            String sql = "DELETE FROM employee WHERE ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtID.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        try {
            String sql = "UPDATE employee SET Name=?, Contact=?, JoinDate=?, Address=?, LeaveDate=? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtName.getText());
            pst.setString(2, txtContact.getText());
            pst.setString(3, txtJoinDate.getText());
            pst.setString(4, txtAddress.getText());
            pst.setString(5, txtLeaveDate.getText());
            pst.setString(6, txtID.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Employee().setVisible(true));
    }
}
