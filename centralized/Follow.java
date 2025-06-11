import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Follow {

	public static void main(String[] a) throws Exception {
		// mengambil parameter
		// parameter ke 0 adalah email yang memfollow
		// parameter ke 1 - n adalah email yang difollow

		String email1 = a[0];
		String email2 = a[1];

		LocalDateTime now = LocalDateTime.now();

		// Definisikan format tanggal
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Format tanggal ke string
		String formattedDate = now.format(formatter);

		// System.out.println(email)
		// System.out.println(message)

		// melakukan koneksi ke sqlite3
		Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:centralized.sqlite3");

		// insert data ke database
		String sql = "INSERT INTO follows(email1, email2, follow_date) VALUES(?,?,?)";
		PreparedStatement stmt = c.prepareStatement(sql);
		stmt.setString(1,email1);
		stmt.setString(2,email2);
		stmt.setString(3,formattedDate);
		stmt.executeUpdate();

		// close connection
		stmt.close();
		c.close();
	}
}
