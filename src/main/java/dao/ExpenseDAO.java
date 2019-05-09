package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
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
	private static final String SELECT_ALL_QUERY = "select ID, APPLICATION_DATE, UPDATE_DATE, EMPID, TITLE, AMOUNT, STATUS, PAYEE, MODIFIED_BY, REJECT_REASON from EXPENSE";
	private static final String SELECT_BY_ID_QUERY = "select ID, APPLICATION_DATE, UPDATE_DATE, EMPID, TITLE, AMOUNT, STATUS, PAYEE, MODIFIED_BY,REJECT_REASON from EXPENSE where ID = ?";
	private static final String UPDATE_QUERY = "update EXPENSE set ID=?, APPLICATION_DATE=?, UPDATE_DATE=?, EMPID=?, TITLE=?, \n" +
			"	AMOUNT=?, STATUS=?, PAYEE=?, MODIFIED_BY=?, REJECT_REASON=? \n" +
			"where ID=? \n" ;
	private static final String INSERT_QUERY = "insert into EXPENSE \n" +
			"(ID, APPLICATION_DATE, UPDATE_DATE, EMPID, TITLE, AMOUNT, STATUS, PAYEE, \n" +
			"	MODIFIED_BY, REJECT_REASON) \n" +
			"values(?,?,?,?,?,?,?,?,?,?) \n" ;
	private static final String DELETE_QUERY = "delete from EXPENSE where ID=? " ;

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
	 * ID指定の検索を実施する。
	 *
	 * @param id 検索対象のID
	 * @return 検索できた場合は検索結果データを収めたExpenseインスタンスを返す。検索に失敗した場合はnullが返る。
	 */
	public Expense findById(String id){
		Expense result = null;

		Connection connection = ConnectionProvider.getConnection();
		if(connection == null){
			return result;
		}

		try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)){
			statement.setString(1,id);

			ResultSet rs = statement.executeQuery();

			if(rs.next()){
				result = processRow(rs);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return result;

	}

	/**
	 *
	 * @param expense 更新対象オブジェクト
	 * @return 更新に成功したらtrue、失敗したらfalse
	 */
	public Expense update(Expense expense){
		Connection connection = ConnectionProvider.getConnection();
		if(connection == null){
			return expense;
		}

		try(PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)){
			setParameter(statement, expense, true);
			statement.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return expense;
	}

	/**
	 * DBに新規追加
	 * @param expense
	 * @return
	 */
	public Expense create(Expense expense){
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return expense;
		}

		try(PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "ID" });){
			setParameter(statement,expense,false);
			statement.executeUpdate();

			//INSERTできたらKEY取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			String id = rs.getString(1);
			expense.setId(id);
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			ConnectionProvider.close(connection);
		}
		return expense;
	}

	/*public boolean remove(String id){
		Connection connection = ConnectionProvider.getConnection();
		if(connection == null){
			return false;
		}

		int count = 0;
		try(PreparedStatement = connection.preparedStatement(DELETE_QUERY)){
			statement.setString(1,id);
		}
	}*/

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

		result.setPayee(rs.getString("PAYEE"));
		result.setModifiedBy(rs.getString("MODIFIED_BY"));
		result.setReasonOfReject(rs.getString("REJECT_REASON"));
		return result;
	}

	/**
	 * オブジェクトからSQLにパラメータを展開する。
	 * @param statement パラメータ展開対象のSQL
	 * @param expense パラメータに対して実際の値を供給するオブジェクト
	 * @param forUpdate 更新に使われるならtrueを、新規追加に使われるならfalseを指定する
	 * @throws SQLException パラメータ展開時に何らかの問題が発生した場合に送出される
	 */
	private void setParameter(PreparedStatement statement, Expense expense, boolean forUpdate) throws SQLException{
		int count=1;

		statement.setString(count++, expense.getId());
		statement.setString(count++, expense.getApplicationDate());
		statement.setString(count++, expense.getUpdateDate());
		//statement.setInt(count++, expense.getGender().ordinal());
		statement.setString(count++, expense.getEmpId());
		statement.setString(count++, expense.getTitle());
		statement.setInt(count++, expense.getAmount());
		statement.setString(count++, expense.getStatus());
		statement.setString(count++, expense.getPayee());
		statement.setString(count++, expense.getModifiedBy());
		statement.setString(count++, expense.getReasonOfReject());
		//statement.setInt(count++, expense.getExpense().getId());
		if(forUpdate){
			statement.setString(count++, expense.getId());
		}
	}

}
