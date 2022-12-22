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
import misc.JSONUtill;

@WebServlet({"/board/list", "/board/write", "/board/update", 
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
		String uid = null, title=null, content=null, files=null, sessionUid=null, today=null;
		int page = 0, bid=0, isMine=0, totalBoardNum=0, totalPages=0;
		RequestDispatcher rd = null;
		Board board = null;
		Reply reply = null;
		List<Board> list = null;
		List<String> pageList = null;
		sessionUid = (String)session.getAttribute("uid");
		
		switch(action) {
		case "list":
			page = (request.getParameter("p")==null || request.getParameter("p")=="") ? 1 : Integer.parseInt(request.getParameter("p"));
			String field = (request.getParameter("f")==null || request.getParameter("f")=="") ? "b.title" : request.getParameter("f");
			String query = (request.getParameter("q")==null || request.getParameter("q")=="") ? "" : request.getParameter("q");
			list = dao.listBoard(field, query, page);
			
			
			session.setAttribute("currentBoardPage", page);
			request.setAttribute("field", field);
			request.setAttribute("query", query);
			totalBoardNum = dao.getBoardCount("title", "");
			totalPages = (int)Math.ceil(totalBoardNum/10.);
			
			// paenation을위한 작업
			int startPage = (int)Math.ceil((page-0.5)/10. -1 )*10 +1;
			int endPage = Math.min(totalPages, startPage+9);	//둘중 작은숫자
			pageList = new ArrayList<>();
			for (int i=startPage; i<=endPage; i++)
				pageList.add(String.valueOf(i));
			request.setAttribute("pageList", pageList);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("totalPages", totalPages);
			
			
			today = LocalDate.now().toString();
			request.setAttribute("today", today);
			request.setAttribute("boardList", list);
			rd = request.getRequestDispatcher("/board/list.jsp");
			rd.forward(request, response);
			break;
		case "detail":
			bid = Integer.parseInt((String)request.getParameter("bid"));
			uid = request.getParameter("uid");
			// 조회수증가. 단, 작성지기 읽거나 댓글 작성후에는 제외.
			if (request.getParameter("option")==null && (!uid.equals(sessionUid)))
				dao.increaseViewCount(bid);
			board = dao.getBoardDetail(bid);
			
			// 첨부파일 다운로드 하기위해서 
			String jsonFiles = board.getFiles();
			if(!(jsonFiles ==null || jsonFiles.equals(""))){
				JSONUtill json = new JSONUtill();
				List<String> fileList = json.parse(jsonFiles);
				request.setAttribute("fileList", fileList);
			}
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
				/** '/board/multiupload'(FileUpload.java)로부터 전달된 데이터를 읽음 **/
				
				title= (String)request.getAttribute("title");
				content= (String)request.getAttribute("content");
				files = (String)request.getAttribute("files");

				
				board = new Board(sessionUid, title, content, files);
				dao.insert(board);
				response.sendRedirect("/bbs/board/list?p=1&f=&q=");
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
			response.sendRedirect("/bbs/board/list?p=" + session.getAttribute("currentBoardPage") + "&f=&q=");
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
				// TODO: 첨부파일수정
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
