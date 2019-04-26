package java_s04;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.Expense;
import dao.ExpenseDAO;

@Path("expenses")
public class ExpenseResource {
	private final ExpenseDAO dao = new ExpenseDAO();

	/**
	 * 一覧用に経費情報を全件取得する。
	 * @return 経費情報のリストをJSON形式で返す。
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Expense> findAll(){
		return dao.findAll();
	}
}
