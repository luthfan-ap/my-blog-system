import java.sql.*;

public class Like { 
    public static void main(String a[]) throws Exception {
        String email = a[0];
        int postId = Integer.parseInt(a[1]);

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:centralized.sqlite3");

        String sql = "INSERT INTO likes(email, post_id) VALUES(?,?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);
        ps.setInt(2, postId);
        ps.executeUpdate();

        ps.close();
        c.close();
    }
}