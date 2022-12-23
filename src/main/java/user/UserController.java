package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;



/**
 * Servlet implementation class UserServiceController
 * session에서 uname저장안하고 uid만 저장하게 바꾸고 update에서 request로 필요한정보 보내게 바꿈
 */
@WebServlet({ "/user/list", "/user/login", "/user/logout",
			"/user/register", "/user/update", "/user/delete", "/user/deleteConfirm"
		})
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8"); 	filter에서 걸렀기때문에 생략가능
		String[] uri = request.getRequestURI().split("/");
		String action = uri[uri.length -1];
		UserDao dao = new UserDao();
		HttpSession session = request.getSession();
		// 어떤게시판인지 활성화시키기용
		session.setAttribute("menu", "user");
		
		response.setContentType("text/html; charset=utf-8");		
		RequestDispatcher rd = null;
		int page = 0;
		String uid = null, pwd = null, pwd2=null, pwd3=null, uname = null, email = null;
		User u =null;
		
		switch(action) {
		case "list":
			page = (request.getParameter("page")==null) ? 1 : Integer.parseInt(request.getParameter("page"));
			
			List<User> list = dao.listUsers(page);
			// pagenation을위한 준비
			session.setAttribute("currentUserPage", page);
			int totalUsers = dao.getUserCount();
			int totalPages = (int)Math.ceil(totalUsers/10.);
			List<String> pageList = new ArrayList<>();
			for (int i=1; i<=totalPages; i++)
				pageList.add(String.valueOf(i));
			request.setAttribute("pageList", pageList);
			
			request.setAttribute("userList", list);
			rd = request.getRequestDispatcher("/WEB-INF/view/user/list.jsp");
			rd.forward(request, response);
			break;
		case "login":
			if (request.getMethod().equals("GET")) {
				session.setAttribute("menu", "login");
				rd = request.getRequestDispatcher("/WEB-INF/view/user/login.jsp");
				rd.forward(request, response);
			}
			else {
				uid = request.getParameter("uid");
				pwd = request.getParameter("pwd");
				u = dao.getUserinfo(uid);
				if (u.getUid() != null) {	// uid가 존재
					
					if (BCrypt.checkpw(pwd, u.getPwd())) {		// 비밀번호 같은지 비교(암호화해서)
						// 로그인을 했을때 session에 정보 저장
						session.setAttribute("uid", u.getUid());
						session.setAttribute("uname", u.getUname());
						
						// alert창에 메세지띄우고 화면이동시키기위해서 필요한경로와 메세지전달
						request.setAttribute("msg", uid + "님 환영합니다");
						request.setAttribute("url", "/bbs/board/list?p=1");
						rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
						rd.forward(request, response);
					}
					else {		
						// 비밀번호가 틀림, 로그인페이지로 다시이동
						request.setAttribute("msg", "잘못된 패스워드입니다. 다시 입력하세요");
						request.setAttribute("url", "/bbs/user/login");
						rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
						rd.forward(request, response);
					}
				} else { 			// uid가 없음
					// 회원 가입 페이지로 안내
					request.setAttribute("msg", "회원가입페이지로 이동합니다.");
					request.setAttribute("url", "/bbs/user/login");
					rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
					rd.forward(request, response);
				}
			}
			break;
		case "logout":
			// session 정보제거(로그아웃)
			session.invalidate();
			
			request.setAttribute("msg", "로그아웃 되었습니다.");
			request.setAttribute("url", "/bbs/user/list");
			rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
			rd.forward(request, response);		
			break;
		case "register" :
			if (request.getMethod().equals("GET")) {
				System.out.println("확인용");
				rd = request.getRequestDispatcher("/WEB-INF/view/user/register.jsp");
				rd.forward(request, response);
			}
			else {
				uid = request.getParameter("uid").strip();
				pwd = request.getParameter("pwd").strip();
				pwd2 = request.getParameter("pwd2").strip();
				uname = request.getParameter("uname").strip();
				email = request.getParameter("email").strip();			
				u = dao.getUserinfo(uid);
				
				if (u.getUid() != null) {			// 기존 id가 중복인경우
					request.setAttribute("msg", "중복 id가 존재합니다.");
					request.setAttribute("url", "/bbs/user/register");
					rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
					rd.forward(request, response);
				}
				else {								// id가 중복이 아닌경우
					if (pwd.equals(pwd2)) {			// 비밀번호확인이 맞는경우
						u = new User(uid, pwd, uname, email);
						dao.registerUser(u);
						request.setAttribute("msg", "회원가입이 완료되었습니다. 로그인해주세요.");
						request.setAttribute("url", "/bbs/user/list?p=1");
						rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
						rd.forward(request, response);
					}		
					else {							// 비밀번호확인이 틀린경우
						request.setAttribute("msg", "패스워드 입력이 잘못되었습니다.");
						request.setAttribute("url", "/bbs/user/register");
						rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
						rd.forward(request, response);
					}
				}	
			}
			break;
		// 비밀번호 제외하고 변경하나 만들고 비밀번호 포함하게 변경
		case "update" :			
			// controller의 get과 post방식에서 둘다 update를 사용했기때문에 get인지 post인지 구분해준다.
			if (request.getMethod().equals("GET")) {
				uid = (String)session.getAttribute("uid");
				u = dao.getUserinfo(uid);
				request.setAttribute("uname", u.getUname());
				request.setAttribute("email", u.getEmail());
				request.setAttribute("regDate", u.getRegDate());
				rd = request.getRequestDispatcher("/WEB-INF/view/user/update.jsp");
				rd.forward(request, response);				
			} else {
				uid = request.getParameter("uid");
				pwd = request.getParameter("pwd").strip();
				pwd2 = request.getParameter("pwd2").strip();
				pwd3 = request.getParameter("pwd3").strip();
				uname = request.getParameter("uname").strip();
				email = request.getParameter("email").strip();
				u = dao.getUserinfo(uid);
				// 비밀번호도 같이변경할거냐 아니면 비밀번호는 변경 안할거냐 만들기
				if (pwd == null || pwd.equals("")) {		// 비밀번호는 변경 x
					u = new User(uid,uname,email);
					dao.updateUser(u);
					session.setAttribute("uname", uname);
					response.sendRedirect("/bbs/user/list?p=" + session.getAttribute("currentUserPage"));		
				} else if (pwd2.equals(pwd3) && BCrypt.checkpw(pwd, u.getPwd())) {	// 비밀번호 일치하는지 확인
					dao.updateUserWithPassword(new User(uid,pwd2,uname,email));
					request.setAttribute("msg", u.getUid() + "님 정보가 수정되었습니다.");
					request.setAttribute("url", "/bbs/user/list");
					rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
					rd.forward(request, response);
				} else if (!BCrypt.checkpw(pwd, u.getPwd())) {	//현재 비밀번호가 틀렸을경우
					request.setAttribute("msg", "현재 비밀번호가 틀리셧습니다. 다시한번 확인해주세요.");
					request.setAttribute("url", "/bbs/user/update");
					rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
					rd.forward(request, response);	
				} else {
					request.setAttribute("msg", "변경하실 비밀번호가 일치하지 않습니다. 다시한번확인해주세요.");
					request.setAttribute("url", "/bbs/user/update");
					rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
					rd.forward(request, response);
				}
			}
			break;
		case "delete" :
			uid = request.getParameter("uid");
			rd = request.getRequestDispatcher("/WEB-INF/view/user/delete.jsp?uid=" + uid);
			rd.forward(request, response);
			break;
		case "deleteConfirm" :
			uid = request.getParameter("uid");
			dao.deleteUser(uid);
			request.setAttribute("msg", uid + "님의 데이터가 삭제되었습니다.");
			request.setAttribute("url", "/bbs/user/list?p=" + session.getAttribute("currentUserPage"));
			rd = request.getRequestDispatcher("/WEB-INF/view/user/alertMsg.jsp");
			rd.forward(request, response);
			break;
		default :
			System.out.println("잘못된경로");
			break;
		}
	}

}
