package java_s04;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import dao.ExpenseDAO;

public class ExpenseResource {
	private final ExpenseDAO dao = new ExpenseDAO();

	/**
	 * 一覧用に経費情報を全件取得する。
	 * @return 経費情報のリストをJSON形式で返す。
	 */
	@GET
	@Produces()
	public List<Expense> findAll(){
		return dao.findAll();
	}
}
