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
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Home extends JFrame {

    private JTextField user;
    private JTextField user1;
    private JButton logBtn;
    private JButton regBtn;

    public Home() {
        super("Home Page");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Colors (dark theme)
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

        JLabel title = new JLabel("ISP Management System");
        title.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 26));
        title.setForeground(accent);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        topPanel.add(title, c);
        
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(cardDark);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel cardTitle = new JLabel("LOGIN FIRST");
        cardTitle.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 18));
        cardTitle.setForeground(accent);

        JLabel lblUser = new JLabel("User");
        lblUser.setForeground(subText);
        lblUser.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        user = new JTextField(18);
        user.setBackground(bgDark);
        user.setForeground(textCol);
        user.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JLabel lblPass = new JLabel("Password");
        lblPass.setForeground(subText);
        lblPass.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        user1 = new JTextField(18);
        user1.setBackground(bgDark);
        user1.setForeground(textCol);
        user1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        logBtn = new JButton("Log In");
        logBtn.setBackground(btnBg);
        logBtn.setForeground(btnText);
        logBtn.setFocusPainted(false);
        logBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        logBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLoginAction();
            }
        });

        regBtn = new JButton("Register");
        regBtn.setBackground(btnBg);
        regBtn.setForeground(btnText);
        regBtn.setFocusPainted(false);
        regBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register().setVisible(true);
            }
        });

        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets(8, 8, 8, 8);
        cc.gridx = 0; cc.gridy = 0; cc.gridwidth = 2; cc.anchor = GridBagConstraints.CENTER;
        card.add(cardTitle, cc);

        cc.gridwidth = 1;
        cc.gridx = 0; cc.gridy = 1; cc.anchor = GridBagConstraints.EAST;
        card.add(lblUser, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.WEST;
        card.add(user, cc);

        cc.gridx = 0; cc.gridy = 2; cc.anchor = GridBagConstraints.EAST;
        card.add(lblPass, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.WEST;
        card.add(user1, cc);

        cc.gridx = 0; cc.gridy = 3; cc.anchor = GridBagConstraints.CENTER;
        card.add(logBtn, cc);
        cc.gridx = 1; cc.anchor = GridBagConstraints.CENTER;
        card.add(regBtn, cc);

        // Main frame layout
        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        // center with some spacing
        JPanel centerWrap = new JPanel();
        centerWrap.setBackground(bgDark);
        centerWrap.add(card);
        getContentPane().add(centerWrap, BorderLayout.CENTER);

        // small preferred sizes
        topPanel.setPreferredSize(new Dimension(720, 80));
        card.setPreferredSize(new Dimension(420, 220));
    }

    private void doLoginAction() {
        String inputUser = user.getText().trim();
        String inputPass = user1.getText().trim();

        if (inputUser.isEmpty() || inputPass.isEmpty()) {
            JOptionPane.showMessageDialog(Home.this, "Please enter both username and password.");
            return;
        }

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = javaconnect.ConnectDb();
            if (conn != null) {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, inputUser);
                pst.setString(2, inputPass);
                rs = pst.executeQuery();

                if (rs.next()) {
                    //JOptionPane.showMessageDialog(Home.this, "Login Successful!");
                     //Open admin/main dashboard here
                     new Admin().setVisible(true);
                     setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(Home.this, "Wrong Username or Password!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Home.this, "Database error: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }
}