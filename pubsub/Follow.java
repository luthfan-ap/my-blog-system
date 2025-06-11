import java.sql.*;

public class Follow {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Kolom yang harus diisi: <email1> <email2>");
            return;
        }

        String email1 = args[0];
        String email2 = args[1];

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:pubsub.sqlite3");

        String sql = "INSERT INTO follows(email1, email2) VALUES(?, ?)";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, email1);
        stmt.setString(2, email2);
        stmt.executeUpdate();

        stmt.close();
        c.close();
    }
}
