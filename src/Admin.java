import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Admin extends JFrame {
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    // UI Components
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private JTextField dateField;
    private JTextField timeField;
    private JButton btnPlan;
    private JButton btnCustomer;
    private JButton btnComplain;
    private JButton btnEmp;
    private JButton btnLogout;

    // Colors (matching your login page)
    private final Color bgDark = new Color(25, 25, 25);
    private final Color cardDark = new Color(40, 40, 40);
    private final Color accent = new Color(0, 153, 255);
    private final Color textCol = new Color(230, 230, 230);
    private final Color subText = new Color(180, 180, 180);
    private final Color btnBg = new Color(0, 153, 255);
    private final Color btnText = Color.WHITE;
    private final Color dangerBtn = new Color(220, 53, 69);

    public Admin() {
        super("Admin Panel");
        initComponents();
        
        // Initialize database connection
        conn = javaconnect.ConnectDb();
        showDate();
        showTime();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(bgDark);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        createTopPanel();
        createCenterPanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
    }

    private void createTopPanel() {
        topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(bgDark);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        topPanel.setPreferredSize(new Dimension(800, 120));

        // Title
        titleLabel = new JLabel("ADMIN PANEL");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        titleLabel.setForeground(accent);

        // Date and Time labels
        dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        dateLabel.setForeground(subText);

        timeLabel = new JLabel("Time:");
        timeLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        timeLabel.setForeground(subText);

        // Date and Time fields
        dateField = new JTextField(12);
        setupDisplayField(dateField);
        dateField.setForeground(new Color(144, 238, 144)); // Light green

        timeField = new JTextField(12);
        setupDisplayField(timeField);
        timeField.setForeground(new Color(255, 215, 0)); // Gold

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(titleLabel, gbc);

        // Date and Time
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        
        // Date
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(dateLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(dateField, gbc);
        
        // Time
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(timeLabel, gbc);
        
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(timeField, gbc);
    }

    private void createCenterPanel() {
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(bgDark);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Create buttons
        btnPlan = createButton("CREATE PLAN", btnBg);
        btnCustomer = createButton("CUSTOMER", btnBg);
        btnEmp = createButton("EMPLOYEE", btnBg);
        btnComplain = createButton("COMPLAINT", btnBg);
        btnLogout = createButton("LOG OUT", dangerBtn);

        // Add action listeners
        btnPlan.addActionListener(e -> openCreatePlan());
        btnCustomer.addActionListener(e -> openCustomer());
        btnEmp.addActionListener(e -> openEmployee());
        btnComplain.addActionListener(e -> openComplaint());
        btnLogout.addActionListener(e -> logout());

        // Layout for buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 20;
        gbc.ipady = 15;

        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(btnPlan, gbc);

        // Row 2
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        centerPanel.add(btnEmp, gbc);
        
        gbc.gridx = 1;
        centerPanel.add(btnCustomer, gbc);

        // Row 3
        gbc.gridy = 2;
        gbc.gridx = 0;
        centerPanel.add(btnComplain, gbc);
        
        gbc.gridx = 1;
        centerPanel.add(btnLogout, gbc);
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(btnText);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accent, 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setPreferredSize(new Dimension(180, 50));
        return button;
    }

    private void setupDisplayField(JTextField field) {
        field.setEditable(false);
        field.setBackground(cardDark);
        field.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accent, 1),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        field.setOpaque(true);
    }

    void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        dateField.setText(s.format(d));
    }

    void showTime() {
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
                timeField.setText(s.format(d));
            }
        }).start();
    }

    private void openCreatePlan() {
        setVisible(false);
        CreatePlan ob = new CreatePlan();
        ob.setVisible(true);
    }

    private void openEmployee() {
        setVisible(false);
        Employee ob = new Employee();
        ob.setVisible(true);
    }

    private void openCustomer() {
        setVisible(false);
        Customer ob = new Customer();
        ob.setVisible(true);
    }

    private void openComplaint() {
        setVisible(false);
        Complaint ob = new Complaint();
        ob.setVisible(true);
    }

    private void logout() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            javax.swing.JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            setVisible(false);
            new Home().setVisible(true);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }
}
