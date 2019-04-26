package dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Expense;

/**
 * 経費データを扱うDAO
 */

public class ExpenseDAO {

	/**
	 * クエリ文字列
	 */
	private static final String SELECT_ALL_QUERY = "select ID, APPLICATION_DATE, UPDATE_DATE, EMPID, TITLE, AMOUNT, STATUS from EXPENSE";
	private static final String SELECT_BY_ID_QUERY = SELECT_ALL_QUERY + "where 1=1  \n" +
			"and EMPID='0000000001' \n" +
			"and APPLICATION_DATE between '20130501' and '20190501' \n" +
			"and TITLE = '交通費' \n";

	/**
	 * 経費の全件を取得する。
	 *
	 * @return DBに登録されている経費データ全権を収めたリスト。途中でエラーが発生した場合は空のリストを返す。
	 */
	public List<Expense> findAll(){
		List<Expense> result = new ArrayList<>();


		Connection connection = ConnectionProvider.getConnection();
		if(connection == null){
			return result;
		}

		try(Statement statement = connection.createStatement();){
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);

			while(rs.next()){
				result.add(processRow(rs));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;
	}

	/**
	 * 検索結果行をオブジェクトとして構成する。
	 * @param rs 検索結果が収められているResultSet
	 * @return 検索結果行の各データを収めたPostインスタンス
	 * @throws SQLException ResultSetの処理中発生した例外
	 */
	private Expense processRow(ResultSet rs) throws SQLException{
		Expense result = new Expense();
		result.setId(rs.getString("ID"));
		result.setApplicationDate(rs.getString("APPLICATION_DATE"));
		result.setUpdateDate(rs.getString("UPDATE_DATE"));
		result.setEmpId(rs.getString("EMPID"));
		result.setTitle(rs.getString("TITLE"));
		result.setAmount(rs.getInt("AMOUNT"));
		result.setStatus(rs.getString("STATUS"));
		return result;
	}
}
