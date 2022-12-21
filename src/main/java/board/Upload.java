
package board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/board/uploads")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	    maxFileSize = 1024 * 1024 * 10,      // 10 MB
	    maxRequestSize = 1024 * 1024 * 100   // 100 MB
	)
public class Upload extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tmpPath = "c:/Temp/upload";		// 여기서 설정하는것보다 config파일에서 설정하는게 더좋음
        System.out.println("tmpPath: " + tmpPath);
        File file = new File(tmpPath);
        if(!file.exists()) 
            file.mkdirs();
        
        /* Receive file uploaded to the Servlet from the HTML5 form */
        request.setCharacterEncoding("utf-8");
        String param = request.getParameter("param");
        System.out.println("param"+ param);
        
        String fileName = null;
        
        //하나의 파일을 보낼때
//        Part filePart = request.getPart("file");		// name으로 등록한것
//        if (filePart == null) {
//        	System.out.println("no files uploaded.");
//        } else {
//        	fileName = filePart.getSubmittedFileName();
//        	System.out.println("fileName" + fileName);
//        	
//        	for (Part part : request.getParts()) {		// 파일 업로드
//                part.write(tmpPath + File.separator + fileName);
//            }
//        }
        	
        // 여러개의 파일을 보낼때
        Part filePart = null;
		List<String> fileList = new ArrayList<>();
		for (int i=1; i<=2; i++) {
		    filePart = request.getPart("file" + i);		// name이 file1, file2
		    if (filePart == null)
		    	continue;
		    fileName = filePart.getSubmittedFileName();
		    System.out.println("file" + i + ": " + fileName);
		    if (fileName == null || fileName.equals(""))
		        continue;
		    fileList.add(fileName);
		    
		    for (Part part : request.getParts()) {
		    	System.out.println("여기가 이상" + fileName);
		        part.write(tmpPath + File.separator + fileName);
		    }
		    response.getWriter().print("The file is uploaded sucessfully.");	
		}
	}

}
