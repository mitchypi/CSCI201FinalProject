import java.sql.*;
import java.util.ArrayList;

	public class JDBCConnector {
	        
		public static void deleteCartItems(int user_id) throws SQLException {
			Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
			PreparedStatement pstmt = conn.prepareStatement("DELETE * FROM carts WHERE user_id=?");
	    	pstmt.setInt(1, user_id);
	    	pstmt.executeQuery();
	    	pstmt.close();
		}
		
		public int getBalance(int user_id) throws SQLException {
			int result = -1;
			Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
			PreparedStatement ps = conn.prepareStatement("SELECT balance from users WHERE userID=?");
			ps.setInt(1, user_id);

		    ResultSet rs = ps.executeQuery();
		    if (rs.next()) {
		        result = rs.getInt("balance");
		    }
			return result;
		}
		
		public boolean updateBalance(int user_id, int balance, int total) throws SQLException {
			balance = balance - total;
			Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
	    	PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance = ? WHERE user_id = ?");
	    	stmt.setInt(1, balance);
	    	stmt.setInt(2, user_id);
	    	// Execute the update statement
	    	int rowsUpdated = stmt.executeUpdate();
	    	if (rowsUpdated > 0) {
	    		stmt.close();
	    		return true;
	    	}
	    	return false;
		}
		
		public static void deleteCartItems(int user_id, Stirng itemName) {
			Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM carts WHERE user_id = ? AND item_id = (SELECT item_id FROM items WHERE item_name = ?)");
			stmt.setInt(1, user_id);
	    	stmt.setString(2, itemName);
	    	// Execute the statement
	    	stmt.executeQuery();
		}

		public static boolean insertRegistration(String username, String password, String email, double balance) {
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
		    	
		        conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");

		        // check if a row with the same username or email already exists
		        String checkSql = "SELECT * FROM users WHERE username = ? OR email = ?";
		        pstmt = conn.prepareStatement(checkSql);
		        pstmt.setString(1, username);
		        pstmt.setString(2, email);
		        rs = pstmt.executeQuery();
		        if (rs.next()) {
		            System.out.println("A user with the same username or email already exists.");
		            return false;
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
		    return true;
		}

	    public static boolean login(String username, String password) throws SQLException{
	    	try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
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

		public static ArrayList<Restaurant> getRestaurants(){
			ArrayList <Restaurant> restaurants = new ArrayList<Restaurant>();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
			try{
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TrojanOrdering", "root", "wasdwasd");
				String sql = "SELECT * FROM Restaurants";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()){
					Restaurant r = new Restaurant(rs.getInt("restaurant_id"), rs.getString("name"), rs.getString("address"), rs.getString("imgurl"));
					restaurants.add(r);
				}
				
				
			}
			catch (SQLException sqle) {
		        System.out.println("SQLException in getRest: ");
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
		            System.out.println("SQLException in getRest: ");
		            sqle.printStackTrace();
		        }
		    }
			return restaurants;

		}
}
