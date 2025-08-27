import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db")) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS expenses(id INTEGER PRIMARY KEY, description TEXT, amount REAL)");

            while (true) {
                System.out.println("1. Add Expense | 2. View Expenses | 3. Exit");
                int choice = sc.nextInt(); sc.nextLine();
                if (choice == 1) {
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Amount: ");
                    double amt = sc.nextDouble();
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO expenses(description, amount) VALUES(?, ?)");
                    ps.setString(1, desc);
                    ps.setDouble(2, amt);
                    ps.executeUpdate();
                } else if (choice == 2) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM expenses");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " | " + rs.getString("description") + " | " + rs.getDouble("amount"));
                    }
                } else break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
