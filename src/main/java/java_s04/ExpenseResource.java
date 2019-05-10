package java_s04;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

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

	/**
	 * 指定した経費情報を登録する
	 * @param id
	 * @param form 経費情報を収めたオブジェクト
	 * @return DB上のIDが振られた経費情報
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Expense create(@PathParam("id") String id,
			final FormDataMultiPart form)throws WebApplicationException {
		Expense expense = new Expense();

		expense.setId(null);
		expense.setApplicationDate(form.getField("applicationDate").getValue());
		expense.setUpdateDate(form.getField("updateDate").getValue());
		expense.setEmpId(form.getField("empId").getValue());
		expense.setTitle(form.getField("title").getValue());
		expense.setAmount(Integer.parseInt(form.getField("amount").getValue()));
		expense.setStatus(form.getField("status").getValue());
		expense.setPayee(form.getField("payee").getValue());
		expense.setModifiedBy(form.getField("modifiedBy").getValue());
		expense.setReasonOfReject(form.getField("reasonOfReject").getValue());

		return dao.create(expense);
	}


	/**
	 * 指定した情報でDBを更新する
	 * @param id
	 * @param form 更新情報を含めた経費情報
	 * @return
	 * @throws WebApplicationException 入力データチェックに失敗した場合に送出される。
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Expense update(@PathParam("id") String id,
			final FormDataMultiPart form)throws WebApplicationException{
		Expense expense = new Expense();

		expense.setId(id);
		expense.setApplicationDate(form.getField("applicationDate").getValue());
		expense.setUpdateDate(form.getField("updateDate").getValue());
		expense.setEmpId(form.getField("empId").getValue());
		expense.setTitle(form.getField("title").getValue());
		expense.setAmount(Integer.parseInt(form.getField("amount").getValue()));
		expense.setStatus(form.getField("status").getValue());
		expense.setPayee(form.getField("payee").getValue());
		expense.setModifiedBy(form.getField("modifiedBy").getValue());
		expense.setReasonOfReject(form.getField("reasonOfReject").getValue());


		return dao.update(expense);
	}

	/**
	 * 指定したIDの経費情報を削除する
	 * @param id
	 */
	@DELETE
	@Path("{id}")
	public void remove(@PathParam("id") String id,@Context final HttpServletRequest request
			,@Context final HttpServletResponse response){
		//Expense expense = new dao.findById(id);
		//HttpSession session = session.getAttribute(id);

		HttpSession session = request.getSession(false);
		boolean check = authUser(id);

		//Object status = session.getAttribute(id);

		if(check){
			dao.remove(id);
		}else{
			session.setAttribute("id",null);
			try {
				response.sendRedirect("/java_s04/Login.html");
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		//dao.remove(id);
	}

	protected boolean authUser(String id){
		if(id == null || id.length() == 0){
			return false;
		}else{
		return true;
		}
	}
}
