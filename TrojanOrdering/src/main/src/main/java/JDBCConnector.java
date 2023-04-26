import java.sql.*;

public class JDBCConnector {
	
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
	
	public double getBalance(int userID) {
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
