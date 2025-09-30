import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;

public class javaconnect {
    
    // Fixed method name (was ConnecrDb instead of ConnectDb)
    public static Connection ConnectDb() {
         Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:isp_database.db");
            initializeDatabase(conn);
            System.out.println("SQLite connection established!");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "SQLite Connection Failed: " + e.getMessage());
            return null;
        }
    }
    
    private static void initializeDatabase(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            
            // Create users table
            String createUsersTable = 
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "email TEXT, " +
                "phone TEXT, " +
                "registration_date TEXT NOT NULL, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.execute(createUsersTable);
            
            // Insert default admin user if not exists
            String checkAdmin = "SELECT COUNT(*) as count FROM users WHERE username = 'admin'";
            ResultSet rs = stmt.executeQuery(checkAdmin);
            if (rs.getInt("count") == 0) {
                String insertAdmin = 
                    "INSERT INTO users (username, password, email, phone, registration_date) " +
                    "VALUES ('admin', 'admin123', 'admin@isp.com', '1234567890', date('now'))";
                stmt.execute(insertAdmin);
                System.out.println("Default admin user created");
            }
            
            // Create plans table
            String createPlansTable = 
                "CREATE TABLE IF NOT EXISTS plans (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "speed TEXT NOT NULL, " +
                "price REAL NOT NULL, " +
                "description TEXT" +
                ")";
            stmt.execute(createPlansTable);
            
            // Insert sample plans if not exists
            String checkPlans = "SELECT COUNT(*) as count FROM plans";
            ResultSet rsPlans = stmt.executeQuery(checkPlans);
            if (rsPlans.getInt("count") == 0) {
                String[] samplePlans = {
                    "INSERT INTO plans (name, speed, price, description) VALUES ('Basic', '50 Mbps', 1000.0, 'Basic internet plan for light users')",
                    "INSERT INTO plans (name, speed, price, description) VALUES ('Standard', '100 Mbps', 2000.0, 'Standard plan for families')",
                    "INSERT INTO plans (name, speed, price, description) VALUES ('Premium', '200 Mbps', 3500.0, 'Premium plan for heavy users and gaming')"
                };
                for (String plan : samplePlans) {
                    stmt.execute(plan);
                }
                System.out.println("Sample plans created");
            }
            
            // Create employee table
            String createEmployeeTable =
                "CREATE TABLE IF NOT EXISTS employee (" +
                "ID TEXT PRIMARY KEY, " +
                "Name TEXT NOT NULL, " +
                "Contact TEXT UNIQUE NOT NULL, " +
                "JoinDate TEXT NOT NULL, " +
                "Address TEXT, " +
                "LeaveDate TEXT" +
                ")";
            stmt.execute(createEmployeeTable);
            
            // Create customers table
            String createCustomersTable =
                "CREATE TABLE IF NOT EXISTS customer (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "phone TEXT, " +
                "address TEXT, " +
                "plan_id INTEGER, " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(plan_id) REFERENCES plans(id)" +
                ")";
            stmt.execute(createCustomersTable);
            
            // Create complaints table
            String createComplaintsTable =
                "CREATE TABLE IF NOT EXISTS complain (" +
                "ID TEXT PRIMARY KEY, " +
                "Complaint TEXT NOT NULL, " +
                "Date TEXT NOT NULL, " +
                "Action TEXT DEFAULT 'Pending'" +
                ")";
            stmt.execute(createComplaintsTable);
            
            // Insert sample complaints if table is empty
            String checkComplaints = "SELECT COUNT(*) as count FROM complain";
            ResultSet rsComplaints = stmt.executeQuery(checkComplaints);
            if (rsComplaints.getInt("count") == 0) {
                String[] sampleComplaints = {
                    "INSERT INTO complain (ID, Complaint, Date, Action) VALUES ('COMP001', 'Slow internet speed during peak hours', date('now'), 'Pending')",
                    "INSERT INTO complain (ID, Complaint, Date, Action) VALUES ('COMP002', 'No internet connection since morning', date('now'), 'Solved')",
                    "INSERT INTO complain (ID, Complaint, Date, Action) VALUES ('COMP003', 'Frequent disconnections while working', date('now'), 'Pending')"
                };
                for (String complaint : sampleComplaints) {
                    stmt.execute(complaint);
                }
                System.out.println("Sample complaints created");
            }
            
            // Create payments table (optional but useful for ISP)
            String createPaymentsTable =
                "CREATE TABLE IF NOT EXISTS payments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_id INTEGER, " +
                "plan_id INTEGER, " +
                "amount REAL NOT NULL, " +
                "payment_date TEXT NOT NULL, " +
                "status TEXT DEFAULT 'Pending', " +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY(customer_id) REFERENCES customer(id), " +
                "FOREIGN KEY(plan_id) REFERENCES plans(id)" +
                ")";
            stmt.execute(createPaymentsTable);
            
            stmt.close();
            System.out.println("Database initialization completed successfully!");
            
        } catch (Exception e) {
            System.out.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}