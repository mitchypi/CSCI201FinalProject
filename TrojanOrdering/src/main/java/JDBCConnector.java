
public class JDBCConnector {
	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
	public class JDBCConnector {
	        
	    

		public static void insertRegistration(String username, String password, String email, double balance) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    try {
		    	
		        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/a4", "root", "wasdwasd");

		        // check if a row with the same username or email already exists
		        String checkSql = "SELECT * FROM users WHERE username = ? OR email = ?";
		        pstmt = conn.prepareStatement(checkSql);
		        pstmt.setString(1, username);
		        pstmt.setString(2, email);
		        rs = pstmt.executeQuery();
		        if (rs.next()) {
		            System.out.println("A user with the same username or email already exists.");
		            return;
		        }

		        // insert a new row
		        String insertSql = "INSERT INTO users (username, password, email, balance) VALUES (?, ?, ?, ?)";
		        pstmt = conn.prepareStatement(insertSql);
		        pstmt.setString(1, username);
		        pstmt.setString(2, password);
		        pstmt.setString(3, email);
		        pstmt.setDouble(4, balance);
		        pstmt.executeUpdate();
		    } catch (SQLException sqle) {
		        System.out.println("SQLException in insertRegistration: ");
		        sqle.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) {
		                rs.close();
		            }
		            if (pstmt != null) {
		                pstmt.close();
		            }
		            if (conn != null) {
		                conn.close();
		            }
		        } catch (SQLException sqle) {
		            System.out.println("SQLException in insertRegistration: ");
		            sqle.printStackTrace();
		        }
		    }
		}

	    public static boolean login(String username, String password) throws SQLException{
	    	try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/a4", "root", "wasdwasd");
	        ResultSet rs = null;
	        boolean isValid = false;
	        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
	        stmt.setString(1, username);
	        stmt.setString(2, password);
	        rs = stmt.executeQuery();
	        if(rs.next()){
	        	System.out.println("valid");
	            isValid = true;
	            return isValid;
	        }
	        System.out.println("invalid");
	        return isValid;

	    }
}
