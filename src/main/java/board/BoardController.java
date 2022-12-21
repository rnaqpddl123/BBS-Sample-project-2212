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
import db.ReplyDao;

@WebServlet({"/board/list", "/board/search", "/board/write", "/board/update", 
			"/board/detail", "/board/delete","/board/deleteConfirm", "/board/reply"})
public class BoardController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String[] uri = request.getRequestURI().split("/");
		String action = uri[uri.length -1];
		BoardDao dao = new BoardDao();
		ReplyDao replydao = new ReplyDao();
		HttpSession session = request.getSession();
		
		// 어떤게시판인지 활성화시키기용
		session.setAttribute("menu", "board");
		
		
		response.setContentType("text/html; charset=utf-8");	
		String uid = null, title=null, content=null, files=null, sessionUid=null;
		int page = 0, bid=0, isMine=0;
		RequestDispatcher rd = null;
		Board board = null;
		Reply reply = null;
		sessionUid = (String)session.getAttribute("uid");
		
		switch(action) {
		case "list":
			page = (request.getParameter("page")==null) ? 1 : Integer.parseInt(request.getParameter("page"));
			List<Board> list = dao.listBoard("title", "", page);
			
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
			bid = Integer.parseInt((String)request.getParameter("bid"));
			uid = request.getParameter("uid");
			// 조회수증가. 단, 작성지기 읽거나 댓글 작성후에는 제외.
			if (request.getParameter("option")==null && (!uid.equals(sessionUid)))
				dao.increaseViewCount(bid);
			board = dao.getBoardDetail(bid);
			List<Reply> replyList = replydao.gerReplies(bid);
			
			request.setAttribute("board", board);
			request.setAttribute("replyList", replyList);
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
				
				board = new Board(sessionUid, title, content, files);
				dao.insert(board);
				response.sendRedirect("/bbs/board/list");
			}
			break;
		case "reply":
			content = request.getParameter("content");
			bid = Integer.parseInt(request.getParameter("bid"));
			uid = request.getParameter("uid"); 			// 게시글을 작성한사람의 uid
			isMine = uid.equals(sessionUid) ? 1 : 0;	// 게시글 작성자와 댓글 작성자가 같으면1다르면0
			
			reply = new Reply(content, isMine, sessionUid, bid);
			replydao.insert(reply);
			dao.increaseReplyCount(bid);
			// option=DNI는 조회수 늘리지 않기위해서
			response.sendRedirect("/bbs/board/detail?bid=" + bid + "&uid=" + uid + "&option=DNI");
			
			break;
		case "delete":
			bid = Integer.parseInt(request.getParameter("bid"));
			response.sendRedirect("/bbs/board/delete.jsp?bid=" + bid);
			break;
		case "deleteConfirm":
			bid = Integer.parseInt(request.getParameter("bid"));
			dao.deleteBoard(bid);
			response.sendRedirect("/bbs/board/list?page=" + session.getAttribute("currentBoardPage"));
			break;
		case "update":					// 게시글 수정 화면으로 이동
			if (request.getMethod().equals("GET")) {
				bid = Integer.parseInt((String)request.getParameter("bid"));
				board = dao.getBoardDetail(bid);
				
				request.setAttribute("board", board);
				rd = request.getRequestDispatcher("/board/update.jsp?bid=" + bid);
				rd.forward(request, response);
			} else {
				uid = request.getParameter(uid);
				bid = Integer.parseInt((String)request.getParameter("bid"));
				title = request.getParameter("title");
				content = request.getParameter("content");
				files = request.getParameter("files");
					
				board = new Board(title, content, files, bid);
				dao.update(board);
				response.sendRedirect("/bbs/board/detail?bid=" + bid + "&uid=" + uid);
			}
			break;
		default:
			System.out.println(request.getMethod() +"잘못된경로");
			break;
		}
	}
}
