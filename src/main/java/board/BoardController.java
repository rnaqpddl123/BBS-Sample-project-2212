package board;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.BoardDao;

@WebServlet({"/board/list", "/board/search", "/board/write", "/board/update", 
			"/board/detail", "/board/delete","/board/deleteConfirm"})
public class BoardController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String[] uri = request.getRequestURI().split("/");
		String action = uri[uri.length -1];
		BoardDao dao = new BoardDao();
//		ReplyDao rdao = new ReplyDao();
		
		HttpSession session = request.getSession();
		// 어떤게시판인지 활성화시키기용
		session.setAttribute("menu", "board");
		
		
		response.setContentType("text/html; charset=utf-8");	
		String uid = null, title=null, content=null, files=null;
		int page = 0, bid=0;
		RequestDispatcher rd = null;
		Board b = null;
		
		switch(action) {
		case "list":
//			page = Integer.parseInt(request.getParameter("page"));
			page = (request.getParameter("page")==null) ? 1 : Integer.parseInt(request.getParameter("page"));
			List<Board> list = dao.listusers("title", "", page);
			
			session.setAttribute("currentBoardPage", page);
			int totalBoardNum = dao.getBoardCount();
			int totalPages = (int)Math.ceil(totalBoardNum/10.);
			List<String> pageList = new ArrayList<>();
			for (int i=1; i<=totalPages; i++)
				pageList.add(String.valueOf(i));
			request.setAttribute("pageList", pageList);
			
			String today = LocalDate.now().toString();
			request.setAttribute("today", today);
			request.setAttribute("boardList", list);
			rd = request.getRequestDispatcher("/board/list.jsp");
			rd.forward(request, response);
			break;
		case "search":
			break;
		case "detail":
			bid = Integer.parseInt(request.getParameter("bid"));
			b = dao.getBoardDetail(bid);
			request.setAttribute("board", b);
			rd = request.getRequestDispatcher("/board/detail.jsp");
			rd.forward(request, response);
			break;
		case "write":
			if (request.getMethod().equals("GET")) {
				response.sendRedirect("/bbs/board/write.jsp");
			} else {
				title = request.getParameter("title");
				content = request.getParameter("content");
				files = request.getParameter("files");
				uid = (String)session.getAttribute("uid");
				
				b = new Board(uid, title, content, files);
				dao.insert(b);
				response.sendRedirect("/bbs/board/list");
			}
			break;
		case "update":
			break;
		case "delete":
			break;
		case "deleteConfirm":
			break;
		default:
			System.out.println(request.getMethod() +"잘못된경로");
			break;
		}
	}
}
