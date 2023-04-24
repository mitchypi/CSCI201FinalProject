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
		
		
		public boolean updateBalance(int user_id, double balance, double total) throws SQLException {
			balance = balance - total;
			Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
	    	PreparedStatement stmt = conn.prepareStatement("UPDATE users SET balance = ? WHERE user_id = ?");
	    	stmt.setDouble(1, balance);
	    	stmt.setInt(2, user_id);
	    	// Execute the update statement
	    	int rowsUpdated = stmt.executeUpdate();
	    	if (rowsUpdated > 0) {
	    		stmt.close();
	    		return true;
	    	}
	    	return false;
		}
		
		public static void deleteCartItems(int user_id, String itemName) throws SQLException {
			Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM carts WHERE user_id = ? AND item_id = (SELECT item_id FROM items WHERE item_name = ?)");
			stmt.setInt(1, user_id);
	    	stmt.setString(2, itemName);
	    	// Execute the statement
	    	stmt.executeQuery();
		}
		
		public static ArrayList<Integer> getCartItems(int user_id) throws SQLException{
			ArrayList<Integer> result = new ArrayList<Integer>();
			int number = 0;
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM carts WHERE userID=?");
			stmt.setInt(1, user_id);
			ResultSet rs = null;
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				number = rs.getInt("balance");
				result.add(number);
			}
			
			return result;
		}
		
		public static String getItemData(ArrayList<Integer> numbers) throws SQLException {
			String result = "";
			Connection conn = DriverManager.getConnection("jdbc:mysql://placeholder", "root", "password");
			
			for(int i = 0; i < numbers.size(); i++) {
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE itemID=?");
				stmt.setInt(1, numbers.get(i));
				ResultSet rs = null;
				rs = stmt.executeQuery();
				
				rs.first();
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				String desc = rs.getString("description");
				
				result += "{\"name\":" + "\"" + name + "\",";
				result += "\"description\":" + "\"" + desc + "\",";
				result += "\"price\":" +  price + "}";
				if(i != numbers.size()-1) {
					result += ",";
				}
				
				stmt.close();
				rs.close();
			}
			return result;
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
		public int registerUser(String username, String password, String email, double balance) throws SQLException {
		    int userID = -3;
		    try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		    Connection conn  =null;
		    try{
		    	conn = DriverManager.getConnection("");
		        String query = "SELECT * FROM User WHERE email=?";
		        try (PreparedStatement ps = conn.prepareStatement(query)) {
		            ps.setString(1, email);
		            try (ResultSet rs = ps.executeQuery()) {
		                if (rs.next()) {
		                    return -1; // email already exists
		                }
		            }
		        }
		        query = "SELECT * FROM User WHERE username=?";
		        try (PreparedStatement ps = conn.prepareStatement(query)) {
		            ps.setString(1, username);
		            try (ResultSet rs = ps.executeQuery()) {
		                if (rs.next()) {
		                    return -2; 
		                }
		            }
		        }
		        query = "INSERT INTO User (username, password, email, balance) VALUES (?, ?, ?, ?)";
		        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
		            ps.setString(1, username);
		            ps.setString(2, password);
		            ps.setString(3, email);
		            ps.setDouble(4, balance);
		            int rowsAffected = ps.executeUpdate();
		            if (rowsAffected == 1) {
		                try (ResultSet rs = ps.getGeneratedKeys()) {
		                    if (rs.next()) {
		                        userID = rs.getInt(1);
		                    }
		                }
		            }
		        }
		    }catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return userID;
		}


		public int loginUser(String username, String password) throws SQLException {
		    int userID = -1;
		    try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
		    try (Connection conn = DriverManager.getConnection("")) {
		        String query = "SELECT * FROM User WHERE username=?";
		        try(PreparedStatement ps = conn.prepareStatement(query)){
			        ps.setString(1, username);
			        try(ResultSet rs = ps.executeQuery()){
				        if (!rs.next()) {
				            rs.close();
				            ps.close();
				            return -1;
				        } 
				        else {
				            String dbPassword = rs.getString("password");
				            userID = rs.getInt("user_id");
				            if (!dbPassword.equals(password)) {
				                rs.close();
				                ps.close();
				                return -2;
				            }
				        }
				        rs.close();
				        ps.close();
			        }
		        }
		    }catch (SQLException e) {
		        e.printStackTrace();
		    } 
		    return userID;
		}

		public static double getBalance(int userID) {
		    try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    double balance = -1;
		    String query = "SELECT balance FROM User WHERE user_id=?";
		    try (Connection conn = DriverManager.getConnection("");
		    		PreparedStatement ps = conn.prepareStatement(query)) {
		    			ps.setInt(1, userID);
		    			try(ResultSet rs = ps.executeQuery()){
	    					if (rs.next()) {
	    						balance = rs.getDouble("balance");
	    					}
		    			}
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return balance;
		}
}
