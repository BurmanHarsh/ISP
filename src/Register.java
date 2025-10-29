
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Register extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JTextField phoneField;
    private JButton registerBtn;
    private JButton backBtn;

    public Register() {
        super("User Registration");
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Colors (dark theme)
        Color bgDark = new Color(25, 25, 25);
        Color cardDark = new Color(40, 40, 40);
        Color accent = new Color(0, 153, 255);
        Color textCol = new Color(230, 230, 230);
        Color subText = new Color(180, 180, 180);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(bgDark);
        topPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("User Registration");
        title.setFont(new Font("Tahoma", Font.BOLD, 26));
        title.setForeground(accent);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.CENTER;
        topPanel.add(title, c);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(cardDark);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel cardTitle = new JLabel("CREATE NEW ACCOUNT");
        cardTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        cardTitle.setForeground(accent);

        JLabel lblUser = new JLabel("Username *");
        JLabel lblPass = new JLabel("Password *");
        JLabel lblConfirmPass = new JLabel("Confirm Password *");
        JLabel lblEmail = new JLabel("Email");
        JLabel lblPhone = new JLabel("Phone");
        for (JLabel label : new JLabel[]{lblUser, lblPass, lblConfirmPass, lblEmail, lblPhone}) {
            label.setForeground(subText);
            label.setFont(new Font("Arial", Font.BOLD, 12));
        }

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);

        setupTextField(usernameField, bgDark, textCol, accent);
        setupTextField(passwordField, bgDark, textCol, accent);
        setupTextField(confirmPasswordField, bgDark, textCol, accent);
        setupTextField(emailField, bgDark, textCol, accent);
        setupTextField(phoneField, bgDark, textCol, accent);

        registerBtn = new JButton("Register");
        setupButton(registerBtn, accent, Color.WHITE);
        registerBtn.addActionListener(e -> doRegistration());

        backBtn = new JButton("Back to Login");
        setupButton(backBtn, new Color(100, 100, 100), Color.WHITE);
        backBtn.addActionListener(e -> goBackToLogin());

        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets(6, 6, 6, 6);
        cc.fill = GridBagConstraints.HORIZONTAL;

        cc.gridx = 0; cc.gridy = 0; cc.gridwidth = 2; cc.anchor = GridBagConstraints.CENTER;
        card.add(cardTitle, cc);

        cc.gridwidth = 1;
        cc.gridx = 0; cc.gridy = 1; card.add(lblUser, cc);
        cc.gridx = 1; card.add(usernameField, cc);

        cc.gridx = 0; cc.gridy = 2; card.add(lblPass, cc);
        cc.gridx = 1; card.add(passwordField, cc);

        cc.gridx = 0; cc.gridy = 3; card.add(lblConfirmPass, cc);
        cc.gridx = 1; card.add(confirmPasswordField, cc);

        cc.gridx = 0; cc.gridy = 4; card.add(lblEmail, cc);
        cc.gridx = 1; card.add(emailField, cc);

        cc.gridx = 0; cc.gridy = 5; card.add(lblPhone, cc);
        cc.gridx = 1; card.add(phoneField, cc);

        cc.gridx = 0; cc.gridy = 6; card.add(registerBtn, cc);
        cc.gridx = 1; card.add(backBtn, cc);

        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(topPanel, BorderLayout.NORTH);

        JPanel centerWrap = new JPanel();
        centerWrap.setBackground(bgDark);
        centerWrap.add(card);
        getContentPane().add(centerWrap, BorderLayout.CENTER);

        topPanel.setPreferredSize(new Dimension(600, 80));
        card.setPreferredSize(new Dimension(450, 350));
    }

    private void setupTextField(JTextField field, Color bg, Color fg, Color border) {
        field.setBackground(bg);
        field.setForeground(fg);
        field.setCaretColor(fg);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private void setupButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void doRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields (*).");
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters long.");
            return;
        }

        try (Connection conn = javaconnect.ConnectDb()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!");
                return;
            }

            // Check username
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkQuery);
            psCheck.setString(1, username);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
                return;
            }

            // Insert new user
            String insertQuery = "INSERT INTO users (username, password, email, phone, registration_date) VALUES (?, ?, ?, ?, date('now'))";
            PreparedStatement psInsert = conn.prepareStatement(insertQuery);
            psInsert.setString(1, username);
            psInsert.setString(2, password);
            psInsert.setString(3, email);
            psInsert.setString(4, phone);
            psInsert.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful!");
            clearForm();
            goBackToLogin();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during registration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearForm() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    private void goBackToLogin() {
        this.dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Register().setVisible(true));
    }
}
