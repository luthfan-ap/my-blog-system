import java.sql.*;

public class Reader {
    public static void main(String[] args) throws Exception {
        // Koneksi ke SQLite
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:centralized.sqlite3");

        // Query untuk mendapatkan semua postingan, diurutkan dari yang terbaru
        String sqlPosts = "SELECT posts.id, posts.email, posts.post_date, posts.title, posts.content, " +
                "(SELECT COUNT(*) FROM likes WHERE likes.post_id = posts.id) AS like_count " +
                "FROM posts ORDER BY posts.post_date DESC";

        PreparedStatement psPosts = c.prepareStatement(sqlPosts);
        ResultSet rsPosts = psPosts.executeQuery();

        while (rsPosts.next()) {
            int postId = rsPosts.getInt("id");
            String email = rsPosts.getString("email");
            String postDate = rsPosts.getString("post_date");
            String title = rsPosts.getString("title");
            String content = rsPosts.getString("content");
            int likeCount = rsPosts.getInt("like_count");

            // Cetak hasil
            System.out.println("User   : " + email);
            System.out.println("Date   : " + postDate);
            System.out.println("Title  : " + title);
            System.out.println("Content: " + content);
            System.out.println("Likes  : " + likeCount);
            System.out.println("--------------------------------");
        }

        // Tutup koneksi
        rsPosts.close();
        psPosts.close();
        c.close();
    }
}
