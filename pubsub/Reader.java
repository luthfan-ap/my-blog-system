import java.sql.*;

public class Reader {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Gunakan: java Reader <email>");
            return;
        }

        String email = args[0];

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:pubsub.sqlite3");

        String sql = "SELECT title, post_date FROM notifications WHERE email=?";
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String title = rs.getString("title");
            String postDate = rs.getString("post_date");
            System.out.println("New Post: " + title + " | Date: " + postDate);
        }

        rs.close();
        stmt.close();
        c.close();
    }
}
