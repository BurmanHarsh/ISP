//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import javax.swing.Timer;
//import java.awt.Color;
//
//public class Admin extends javax.swing.JFrame {
//Connection conn;
//PreparedStatement pst;
//ResultSet rs;
//    public Admin() {
//        super("Admin Panel");
//        initComponents();
//        
//        conn=javaconnect.ConnecrDb();
//        showDate();
//        showTime();
//        
//
//    
//    Color bgDark   = new Color(45, 45, 45);    
//    Color bgPanel  = new Color(30, 30, 30);    
//    Color btnBg    = new Color(70, 130, 180);  
//    Color btnText  = Color.WHITE;              
//    Color labelCol = new Color(200, 200, 200); 
//
//    jPanel1.setBackground(bgDark);
//    jPanel2.setBackground(bgPanel);
//    jLabel1.setForeground(new Color(0, 204, 255));
//    jLabel2.setForeground(labelCol);
//    jLabel3.setForeground(labelCol);
//    dateLab.setBackground(bgPanel);
//    dateLab.setForeground(Color.GREEN);        
//    timeLab.setBackground(bgPanel);
//    timeLab.setForeground(Color.ORANGE); 
//    btnPlan.setBackground(btnBg);
//    btnPlan.setForeground(btnText);
//
//    btnCustomer.setBackground(btnBg);
//    btnCustomer.setForeground(btnText);
//
//    btnComplain.setBackground(new Color(178, 34, 34));
//    btnComplain.setForeground(Color.WHITE);
//
//    btnEmp.setBackground(btnBg);
//    btnEmp.setForeground(btnText);
//
//    btnLogout.setBackground(new Color(139, 0, 0));
//    btnLogout.setForeground(Color.WHITE);
//}
//
//    
//
//     void showDate(){
//        Date d = new Date();
//        SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
//        dateLab.setText(s.format(d));
//    }
//    
//    void showTime(){
//        new Timer(0, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Date d = new Date();
//        SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
//        timeLab.setText(s.format(d));
//            }
//        }).start();
//   }
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
//    private void initComponents() {
//
//        jPanel1 = new javax.swing.JPanel();
//        jLabel1 = new javax.swing.JLabel();
//        timeLab = new javax.swing.JTextField();
//        dateLab = new javax.swing.JTextField();
//        jLabel2 = new javax.swing.JLabel();
//        jLabel3 = new javax.swing.JLabel();
//        jPanel2 = new javax.swing.JPanel();
//        btnPlan = new javax.swing.JButton();
//        btnCustomer = new javax.swing.JButton();
//        btnComplain = new javax.swing.JButton();
//        btnEmp = new javax.swing.JButton();
//        btnLogout = new javax.swing.JButton();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setResizable(false);
//
//        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
//
//        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
//        jLabel1.setText("ADMIN PANEL");
//
//        timeLab.setEditable(false);
//        timeLab.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
//
//        dateLab.setEditable(false);
//        dateLab.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
//        dateLab.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                dateLabActionPerformed(evt);
//            }
//        });
//
//        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
//        jLabel2.setForeground(new java.awt.Color(51, 0, 153));
//        jLabel2.setText("Date");
//
//        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
//        jLabel3.setForeground(new java.awt.Color(51, 0, 153));
//        jLabel3.setText("Time");
//
//        jPanel2.setBackground(new java.awt.Color(255, 51, 51));
//        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
//
//        btnPlan.setBackground(new java.awt.Color(51, 255, 51));
//        btnPlan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
//        btnPlan.setForeground(new java.awt.Color(0, 153, 0));
//        btnPlan.setText("CREATE PLAN");
//        btnPlan.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnPlanActionPerformed(evt);
//            }
//        });
//
//        btnCustomer.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
//        btnCustomer.setText("CUSTOMER");
//        btnCustomer.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnCustomerActionPerformed(evt);
//            }
//        });
//
//        btnComplain.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
//        btnComplain.setText("COMPLAINT");
//        btnComplain.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnComplainActionPerformed(evt);
//            }
//        });
//
//        btnEmp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
//        btnEmp.setText("EMPLOYEE");
//        btnEmp.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnEmpActionPerformed(evt);
//            }
//        });
//
//        btnLogout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
//        btnLogout.setText("LOG OUT");
//        btnLogout.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnLogoutActionPerformed(evt);
//            }
//        });
//
//        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
//        jPanel2.setLayout(jPanel2Layout);
//        jPanel2Layout.setHorizontalGroup(
//            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel2Layout.createSequentialGroup()
//                .addContainerGap()
//                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(jPanel2Layout.createSequentialGroup()
//                        .addGap(140, 140, 140)
//                        .addComponent(btnPlan))
//                    .addGroup(jPanel2Layout.createSequentialGroup()
//                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(149, 149, 149)
//                        .addComponent(btnComplain, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addGroup(jPanel2Layout.createSequentialGroup()
//                        .addComponent(btnEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(149, 149, 149)
//                        .addComponent(btnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                .addContainerGap())
//        );
//        jPanel2Layout.setVerticalGroup(
//            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel2Layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(btnPlan)
//                .addGap(18, 18, 18)
//                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(btnEmp)
//                    .addComponent(btnCustomer))
//                .addGap(29, 29, 29)
//                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(btnComplain)
//                    .addComponent(btnLogout))
//                .addContainerGap())
//        );
//
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel1Layout.createSequentialGroup()
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                    .addComponent(jLabel3))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                    .addComponent(dateLab)
//                    .addComponent(timeLab, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addGap(54, 54, 54))
//            .addGroup(jPanel1Layout.createSequentialGroup()
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(jPanel1Layout.createSequentialGroup()
//                        .addGap(309, 309, 309)
//                        .addComponent(jLabel1))
//                    .addGroup(jPanel1Layout.createSequentialGroup()
//                        .addGap(169, 169, 169)
//                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                .addContainerGap(188, Short.MAX_VALUE))
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel1Layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(jLabel1)
//                .addGap(47, 47, 47)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(dateLab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel2))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(timeLab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(jLabel3))
//                .addGap(34, 34, 34)
//                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(194, Short.MAX_VALUE))
//        );
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );
//
//        pack();
//    }// </editor-fold>                        
//
//    private void btnPlanActionPerformed(java.awt.event.ActionEvent evt) {                                        
//        setVisible(false);
//        CreatePlan ob=new CreatePlan();
//        ob.setVisible(true);
//    }                                       
//
//    private void btnEmpActionPerformed(java.awt.event.ActionEvent evt) {                                       
//        setVisible(false);
//        Employee ob=new Employee();
//        ob.setVisible(true);
//    }                                      
//
//    private void btnCustomerActionPerformed(java.awt.event.ActionEvent evt) {                                            
//        setVisible(false);
//        Customer ob=new Customer();
//        ob.setVisible(true);
//    }                                           
//
//    private void btnComplainActionPerformed(java.awt.event.ActionEvent evt) {                                            
//        setVisible(false);
//        Complaint ob=new Complaint();
//        ob.setVisible(true);
//    }                                           
//
//    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {                                          
//        setVisible(false);
//        Home ob=new Home();
//        ob.setVisible(true);
//    }                                         
//
//    private void dateLabActionPerformed(java.awt.event.ActionEvent evt) {                                        
//        // TODO add your handling code here:
//    }                                       
//
//    public static void main(String args[]) {
// 
//            java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Admin().setVisible(true);
//            }
//        });
//    }
//
//    // Variables declaration - do not modify                     
//    private javax.swing.JButton btnComplain;
//    private javax.swing.JButton btnCustomer;
//    private javax.swing.JButton btnEmp;
//    private javax.swing.JButton btnLogout;
//    private javax.swing.JButton btnPlan;
//    private javax.swing.JTextField dateLab;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
//    private javax.swing.JPanel jPanel1;
//    private javax.swing.JPanel jPanel2;
//    private javax.swing.JTextField timeLab;
//    // End of variables declaration                   
//}
//
