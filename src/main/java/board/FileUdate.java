package board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import db.BoardDao;
import misc.JSONUtill;

@WebServlet("/board/fileUdate")
public class FileUdate extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tmpPath = "c:/Temp/upload";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		/** 업로드된 파일을 저장할 저장소 */
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(tmpPath));	 // 저장할 위치를 File객체로 생성
		factory.setSizeThreshold(10 * 1024 * 1024); 		 // MaxMemorySize 10MB
		
		/** 파일변환 -> 리스트에 담기 */
		ServletFileUpload fu = new ServletFileUpload(factory);
		fu.setSizeMax(1024 * 1024 * 100); // maxRequestSize 전체 파일 용량   100MB
		fu.setFileSizeMax(1024 * 1024 * 10); // maxFileSize 파일 한개당 용량 10MB
		
		// TODO: 해야될 작업 : write와 비슷하나 기존 파일을 불러와서 remove해주고 add해주어야함
		BoardDao dao = new BoardDao();
//		System.out.println((String)request.getAttribute("bid"));
//		System.out.println((String)request.getAttribute("uid"));
//		int bid = Integer.parseInt((String)request.getAttribute("bid"));
//		Board board = dao.getBoardDetail(bid);
//		String jsonFiles = board.getFiles();
//		if(!(jsonFiles ==null || jsonFiles.equals(""))){
//			JSONUtill json = new JSONUtill();
//			fileList = json.parse(jsonFiles);
//		}
		

		try {
			List<FileItem> items = fu.parseRequest(request);
			List<String> fileList = new ArrayList<>();
			int bid = Integer.parseInt(items.get(0).getString("UTF-8"));
			System.out.println("bid_ : " + bid);
			Board board = dao.getBoardDetail(bid);
			String jsonFiles = board.getFiles();
			if(!(jsonFiles ==null || jsonFiles.equals(""))){
				JSONUtill json = new JSONUtill();
				fileList = json.parse(jsonFiles);
			}
			
			/** 파일 저장 */
			for (FileItem i : items) {
				// 첨부 파일일 때
				if (!i.isFormField() && i.getSize() > 0) {
					String fileName = i.getName();
					if (i.getFieldName()=="removeFiles") {		//remove체크박스 누르면 제거
						System.out.println("제거하는부분");
						fileList.remove(fileName);
					} else {
						System.out.println("추가하는부분");
						File uploadFile = new File(tmpPath + File.separator + fileName);
						i.write(uploadFile); // 임시 파일을 파일로 씀
						fileList.add(fileName);
					}
				}
				// 다른 타입 request일 때 (title,content,uid같은 일반 파라메터)
				else if (i.isFormField()) {
					System.out.println("타입 : "+i.getContentType());
					request.setAttribute(i.getFieldName(), i.getString("UTF-8"));
					System.out.println("이름 " + i.getFieldName() + " 내용: "+i.getString("UTF-8"));
				}
			}
			JSONUtill json = new JSONUtill();
			String jsonList = json.stringfy(fileList);
			System.out.println(jsonList);
			request.setAttribute("files", jsonList);
			RequestDispatcher rd = request.getRequestDispatcher("/board/update");
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}