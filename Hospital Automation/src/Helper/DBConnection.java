package Helper;
import java.sql.*;

public class DBConnection {
	Connection connection = null;
	
	public DBConnection() {}
	
	public Connection connDB() {
		try {
			this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/hospital?user=root&password=777777ee");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return connection;
	}
	
}
