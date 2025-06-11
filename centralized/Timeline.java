import java.sql.*;

public class Timeline {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Gunakan: java Timeline <email>");
            return;
        }

        String email = args[0];

        // Koneksi ke SQLite
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:centralized.sqlite3");

        // Query untuk mendapatkan daftar email yang diikuti oleh pengguna
        String sqlFollowedUsers = "SELECT email2 FROM follows WHERE email1=?";
        PreparedStatement psFollowed = c.prepareStatement(sqlFollowedUsers);
        psFollowed.setString(1, email);
        ResultSet rsFollowed = psFollowed.executeQuery();

        while (rsFollowed.next()) {
            String followedEmail = rsFollowed.getString(1);

            // Query untuk mendapatkan postingan dari user yang diikuti
            String sqlPosts = "SELECT id, post_date, title, content FROM posts WHERE email=? ORDER BY post_date DESC";
            PreparedStatement psPosts = c.prepareStatement(sqlPosts);
            psPosts.setString(1, followedEmail);
            ResultSet rsPosts = psPosts.executeQuery();

            while (rsPosts.next()) {
                int postId = rsPosts.getInt("id");
                String postDate = rsPosts.getString("post_date");
                String title = rsPosts.getString("title");
                String content = rsPosts.getString("content");

                // Query untuk menghitung jumlah likes pada post ini
                String sqlLikes = "SELECT COUNT(*) FROM likes WHERE post_id=?";
                PreparedStatement psLikes = c.prepareStatement(sqlLikes);
                psLikes.setInt(1, postId);
                ResultSet rsLikes = psLikes.executeQuery();
                int likeCount = rsLikes.next() ? rsLikes.getInt(1) : 0;

                // Cetak hasil
                System.out.println("User   : " + followedEmail);
                System.out.println("Date   : " + postDate);
                System.out.println("Title  : " + title);
                System.out.println("Content: " + content);
                System.out.println("Likes  : " + likeCount);
                System.out.println("--------------------------------");

                // Tutup query likes
                rsLikes.close();
                psLikes.close();
            }

            // Tutup query posts
            rsPosts.close();
            psPosts.close();
        }

        // Tutup koneksi
        rsFollowed.close();
        psFollowed.close();
        c.close();
    }
}
