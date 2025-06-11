import java.sql.*;

public class Like {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Kolom yang harus diisi: <email> <post_id>");
            return;
        }

        String email = args[0];
        int postId = Integer.parseInt(args[1]);

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:pubsub.sqlite3");

        String sql = "INSERT INTO likes(email, post_id) VALUES(?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, email);
        ps.setInt(2, postId);
        ps.executeUpdate();

        ps.close();
        c.close();
    }
}
