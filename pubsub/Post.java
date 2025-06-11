import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Post {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Kolom yang harus diisi: <email> <title>");
            return;
        }

        String email = args[0];
        String title = args[1];
        StringBuilder content = new StringBuilder();
        
        System.out.println("Masukkan content postingan (Enter 2x untuk selesai):");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            content.append(line).append("\n");
        }
        scanner.close();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:pubsub.sqlite3");
        
        String sqlPost = "INSERT INTO posts(email, title, content, post_date) VALUES(?,?,?,?)";
        PreparedStatement psPost = c.prepareStatement(sqlPost);
        psPost.setString(1, email);
        psPost.setString(2, title);
        psPost.setString(3, content.toString());
        psPost.setString(4, formattedDate);
        psPost.executeUpdate();
        psPost.close();
        
        // Memicu notifikasi untuk subscriber
        String sqlNotify = "INSERT INTO notifications(email, title, post_date) SELECT email1, ?, ? FROM follows WHERE email2 = ?";
        PreparedStatement psNotify = c.prepareStatement(sqlNotify);
        psNotify.setString(1, title);
        psNotify.setString(2, formattedDate);
        psNotify.setString(3, email);
        psNotify.executeUpdate();
        psNotify.close();
        
        c.close();
    }
}