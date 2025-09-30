import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Complaint extends JFrame {
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    private JPanel panelMain, panelButtons, panelForm, panelTable;
    private JLabel lblTitle, lblID, lblComplaint, lblDate;
    private JTextField txtID, txtDate;
    private JTextArea txtComplaint;
    private JScrollPane scrollComplaint, scrollTable;
    private JButton btnAdd, btnSolve, btnBack;
    private JTable table;

    public Complaint() {
        super("ISP - Complaint Management");
        conn = javaconnect.ConnectDb();
        initComponents();
        showDate();
        loadTable();
    }

    void showDate(){
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
        txtDate.setText(s.format(d));
    }

    private void initComponents() {
        // Panels
        panelMain = new JPanel(new BorderLayout(10, 10));
        panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelButtons = new JPanel(new FlowLayout());
        panelTable = new JPanel(new BorderLayout());

        // Title
        lblTitle = new JLabel("Manage Complaints", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 153, 255));

        // Labels
        lblID = new JLabel("Complaint ID:");
        lblComplaint = new JLabel("Complaint Details:");
        lblDate = new JLabel("Date:");

        // Text Fields
        txtID = new JTextField();
        txtDate = new JTextField();
        txtDate.setEditable(false);

        // Text Area for Complaint
        txtComplaint = new JTextArea(4, 20);
        scrollComplaint = new JScrollPane(txtComplaint);

        // Buttons
        btnAdd = new JButton("Add Complaint");
        btnSolve = new JButton("Mark as Solved");
        btnBack = new JButton("Back");

        // Table
        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Complaint", "Date", "Action"}
        ));
        scrollTable = new JScrollPane(table);

        // Add components to form panel
        panelForm.add(lblID); panelForm.add(txtID);
        panelForm.add(lblComplaint); panelForm.add(scrollComplaint);
        panelForm.add(lblDate); panelForm.add(txtDate);
        panelForm.add(new JLabel()); // Empty cell for spacing
        panelForm.add(new JLabel()); // Empty cell for spacing

        // Add buttons to button panel
        panelButtons.add(btnAdd);
        panelButtons.add(btnSolve);
        panelButtons.add(btnBack);

        // Setup table panel
        panelTable.add(scrollTable, BorderLayout.CENTER);

        // Main layout
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(panelForm, BorderLayout.CENTER);
        leftPanel.add(panelButtons, BorderLayout.SOUTH);

        panelMain.add(lblTitle, BorderLayout.NORTH);
        panelMain.add(leftPanel, BorderLayout.WEST);
        panelMain.add(panelTable, BorderLayout.CENTER);

        add(panelMain);

        // Apply dark theme
        applyTheme();

        // Button actions
        btnAdd.addActionListener(e -> addComplaint());
        btnSolve.addActionListener(e -> markAsSolved());
        btnBack.addActionListener(e -> {
            dispose();
            new Admin().setVisible(true);
        });

        // Table click listener
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
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
        panelTable.setBackground(new Color(45, 45, 45));

        // Labels
        JLabel[] labels = {lblID, lblComplaint, lblDate};
        for (JLabel l : labels) {
            l.setForeground(new Color(245, 245, 245));
            l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        // Text fields
        JTextField[] fields = {txtID, txtDate};
        for (JTextField f : fields) {
            f.setBackground(new Color(55, 55, 55));
            f.setForeground(Color.WHITE);
            f.setCaretColor(Color.WHITE);
            f.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
            f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        // Text area
        txtComplaint.setBackground(new Color(55, 55, 55));
        txtComplaint.setForeground(Color.WHITE);
        txtComplaint.setCaretColor(Color.WHITE);
        txtComplaint.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        txtComplaint.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        scrollComplaint.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        // Buttons
        styleButton(btnAdd, new Color(34, 153, 84));
        styleButton(btnSolve, new Color(52, 152, 219));
        styleButton(btnBack, new Color(192, 57, 43));

        // Table styling
        table.setBackground(new Color(55, 55, 55));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(100, 100, 100));
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(0, 153, 255));
        table.setSelectionForeground(Color.WHITE);

        // Table header
        table.getTableHeader().setBackground(new Color(23, 32, 42));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Scroll pane
        scrollTable.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
    }

    private void loadTable() {
        try {
            String sql = "SELECT ID, Complaint, Date, Action FROM complain";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID"),
                    rs.getString("Complaint"),
                    rs.getString("Date"),
                    rs.getString("Action")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading complaints: " + e.getMessage());
        }
    }

    private void addComplaint() {
        try {
            if (txtID.getText().trim().isEmpty() || txtComplaint.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!");
                return;
            }

            String sql = "INSERT INTO complain (ID, Complaint, Date) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtID.getText().trim());
            pst.setString(2, txtComplaint.getText().trim());
            pst.setString(3, txtDate.getText().trim());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Complaint added successfully!");
            
            // Clear fields
            txtID.setText("");
            txtComplaint.setText("");
            
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding complaint: " + e.getMessage());
        }
    }

    private void markAsSolved() {
        try {
            if (txtID.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a complaint to mark as solved!");
                return;
            }

            String sql = "UPDATE complain SET Action = 'Solved' WHERE ID = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtID.getText().trim());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Complaint marked as solved!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "No complaint found with the given ID!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating complaint: " + e.getMessage());
        }
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow >= 0) {
            txtID.setText(model.getValueAt(selectedRow, 0).toString());
            txtComplaint.setText(model.getValueAt(selectedRow, 1).toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Complaint().setVisible(true));
    }
}