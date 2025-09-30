import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Register extends JFrame {

    private static final String LOG_JSON_PATH = "newjson.json";
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField confirmPasswordField;
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
        // Colors (dark theme - matching Home class)
        java.awt.Color bgDark = new java.awt.Color(25, 25, 25);
        java.awt.Color cardDark = new java.awt.Color(40, 40, 40);
        java.awt.Color accent = new java.awt.Color(0, 153, 255);
        java.awt.Color textCol = new java.awt.Color(230, 230, 230);
        java.awt.Color subText = new java.awt.Color(180, 180, 180);
        java.awt.Color btnBg = new java.awt.Color(0, 153, 255);
        java.awt.Color btnText = java.awt.Color.WHITE;
        
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(bgDark);
        topPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("User Registration");
        title.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 26));
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
        cardTitle.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 18));
        cardTitle.setForeground(accent);

        // Username Field
        JLabel lblUser = new JLabel("Username *");
        lblUser.setForeground(subText);
        lblUser.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        usernameField = new JTextField(20);
        setupTextField(usernameField, bgDark, textCol, accent);

        // Password Field
        JLabel lblPass = new JLabel("Password *");
        lblPass.setForeground(subText);
        lblPass.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        passwordField = new JTextField(20);
        setupTextField(passwordField, bgDark, textCol, accent);

        // Confirm Password Field
        JLabel lblConfirmPass = new JLabel("Confirm Password *");
        lblConfirmPass.setForeground(subText);
        lblConfirmPass.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        confirmPasswordField = new JTextField(20);
        setupTextField(confirmPasswordField, bgDark, textCol, accent);

        // Email Field
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(subText);
        lblEmail.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        emailField = new JTextField(20);
        setupTextField(emailField, bgDark, textCol, accent);

        // Phone Field
        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setForeground(subText);
        lblPhone.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        phoneField = new JTextField(20);
        setupTextField(phoneField, bgDark, textCol, accent);

        // Buttons
        registerBtn = new JButton("Register");
        setupButton(registerBtn, btnBg, btnText);
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doRegistration();
            }
        });

        backBtn = new JButton("Back to Login");
        setupButton(backBtn, new Color(100, 100, 100), btnText);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToLogin();
            }
        });

        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets(6, 6, 6, 6);
        cc.fill = GridBagConstraints.HORIZONTAL;
        
        // Card Title
        cc.gridx = 0; cc.gridy = 0; cc.gridwidth = 2; cc.anchor = GridBagConstraints.CENTER;
        card.add(cardTitle, cc);

        // Username Row
        cc.gridwidth = 1;
        cc.gridx = 0; cc.gridy = 1; cc.anchor = GridBagConstraints.EAST;
        card.add(lblUser, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.WEST;
        card.add(usernameField, cc);

        // Password Row
        cc.gridx = 0; cc.gridy = 2; cc.anchor = GridBagConstraints.EAST;
        card.add(lblPass, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.WEST;
        card.add(passwordField, cc);

        // Confirm Password Row
        cc.gridx = 0; cc.gridy = 3; cc.anchor = GridBagConstraints.EAST;
        card.add(lblConfirmPass, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.WEST;
        card.add(confirmPasswordField, cc);

        // Email Row
        cc.gridx = 0; cc.gridy = 4; cc.anchor = GridBagConstraints.EAST;
        card.add(lblEmail, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.WEST;
        card.add(emailField, cc);

        // Phone Row
        cc.gridx = 0; cc.gridy = 5; cc.anchor = GridBagConstraints.EAST;
        card.add(lblPhone, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.WEST;
        card.add(phoneField, cc);

        // Buttons Row
        cc.gridx = 0; cc.gridy = 6; cc.anchor = GridBagConstraints.CENTER;
        card.add(registerBtn, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.CENTER;
        card.add(backBtn, cc);

        // Main frame layout
        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        
        // center with some spacing
        JPanel centerWrap = new JPanel();
        centerWrap.setBackground(bgDark);
        centerWrap.add(card);
        getContentPane().add(centerWrap, BorderLayout.CENTER);

        // preferred sizes
        topPanel.setPreferredSize(new Dimension(600, 80));
        card.setPreferredSize(new Dimension(450, 350));
    }

    private void setupTextField(JTextField field, Color bg, Color fg, Color border) {
        field.setBackground(bg);
        field.setForeground(fg);
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
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validation
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

        try {
            // Read existing users
            JsonObject root;
            JsonArray users;
            
            try (FileReader fr = new FileReader(LOG_JSON_PATH)) {
                root = JsonParser.parseReader(fr).getAsJsonObject();
                users = root.getAsJsonArray("users");
            } catch (Exception e) {
                // If file doesn't exist or is empty, create new structure
                root = new JsonObject();
                users = new JsonArray();
                root.add("users", users);
            }

            // Check if username already exists
            for (int i = 0; i < users.size(); i++) {
                JsonObject user = users.get(i).getAsJsonObject();
                if (user.get("username").getAsString().equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists! Please choose a different one.");
                    return;
                }
            }

            // Create new user object
            JsonObject newUser = new JsonObject();
            newUser.addProperty("username", username);
            newUser.addProperty("password", password);
            newUser.addProperty("email", email.isEmpty() ? "" : email);
            newUser.addProperty("phone", phone.isEmpty() ? "" : phone);
            newUser.addProperty("registrationDate", java.time.LocalDate.now().toString());

            // Add new user to array
            users.add(newUser);

            // Write back to file
            try (FileWriter fw = new FileWriter(LOG_JSON_PATH)) {
                fw.write(root.toString());
                fw.flush();
            }

            JOptionPane.showMessageDialog(this, "Registration successful! You can now login.");
            clearForm();
            goBackToLogin();

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during registration: " + ex.getMessage());
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
        // The Home frame will be shown again when needed
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
    }
}
