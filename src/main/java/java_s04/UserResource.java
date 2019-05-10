package java_s04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.User;
import dao.UserDao;

@Path("users")
public class UserResource {
	private final UserDao userdao = new UserDao();

	@POST
	@Path("{userid}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findByUserid(@PathParam("userid") String userid,@PathParam("password") String password
			,@Context final HttpServletRequest request)throws WebApplicationException{
		//, HttpServletResponse response
		System.out.println("ok");
		User user = userdao.findByUserid(userid);

		if(password.equals(user.getPassword())){
			System.out.println("ログイン成功");

			HttpSession session = request.getSession();
			session.setAttribute("userid", userid);
			session.setAttribute("password", password);

			//Object Userid = session.getAttribute("userid");
			//Object Password = session.getAttribute("password");

//			if(Userid = null){
//				session.setAttribute(userid, null);
//				session.setAttribute(password, null);
//			}


			return user;
		}


		return user;
	}
}
