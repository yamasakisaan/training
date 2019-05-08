package java_s04;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

	/**
	 * ID指定で経費情報を取得する。
	 *
	 * @param id 検索対象のID
	 * @return 取得した経費情報をJSON形式で返す。
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Expense findById(@PathParam("id") String id){
		return dao.findById(id);
	}
}
