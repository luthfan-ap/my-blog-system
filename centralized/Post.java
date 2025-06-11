import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Post {
    // INPUT : email, title, content, post_date
    public static void main(String a[]) throws Exception {

        // error handling kalau input kurang
        if (a.length < 2) {
            System.out.println("Kolom yang harus diisi: <email> <title>");
            return;
        }

        // bikin variabel untuk masing-masing parameter

        // parameter 1:
        String email = a[0];
        // parameter 2:
        String title = a[1];
        // parameter 3:
        StringBuilder content = new StringBuilder();
        System.out.println("Masukkan content postingan (Enter 2x untuk selesai):");
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            content.append(line).append("\n");
        }
        scanner.close();
        
        // ambil tanggal sekarang
        LocalDateTime now = LocalDateTime.now();

        // bikin formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // formatted date:
        String formattedDate = now.format(formatter);

        // masukin ke database

        // bikin connection:
        Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:centralized.sqlite3");

        // query
        String sql = "INSERT INTO posts(email, title, content, post_date) VALUES(?,?,?,?)";

        // buat masukin valuesnya ke ?
        PreparedStatement ps = c.prepareStatement(sql);

        // masukin valuesnya ke ?,?,?
        ps.setString(1,email);
        ps.setString(2,title);
        ps.setString(3,content.toString());
        ps.setString(4,formattedDate);
        ps.executeUpdate();

        // close Connection
        ps.close();
        c.close();
    }
}