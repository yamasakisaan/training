package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	/**
	 * クエリ文字列
	 */
	private static final String SELECT_FROM_USERID = "select USER_ID, PASS, EMPID from USERS where USER_ID = ?";

	public User findByUserid(String userid){
		User result = null;

		Connection connection = ConnectionProvider.getConnection();
		if(connection == null){
			return null;
		}

		try(PreparedStatement statement = connection.prepareStatement(SELECT_FROM_USERID)){
			statement.setString(1,userid);

			ResultSet rs = statement.executeQuery();

			if (rs.next()){
				result = processRow(rs);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}

	private User processRow(ResultSet rs)throws SQLException{
		User result = new User();
		result.setUserid(rs.getString("USER_ID"));
		result.setPassword(rs.getString("PASS"));
		return result;
	}

}
