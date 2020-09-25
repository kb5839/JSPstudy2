package kr.or.ddit.servlet06;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import kr.or.ddit.filter.wrapper.FileUploadRequestWrapper;
import kr.or.ddit.filter.wrapper.PartWrapper;
import kr.or.ddit.mvc.annotation.CommandHandler;
import kr.or.ddit.mvc.annotation.HttpMethod;
import kr.or.ddit.mvc.annotation.URIMapping;

@CommandHandler
public class FileUploadController {
	@URIMapping(value="/fileUpload.do", method=HttpMethod.POST)
	public String doPost(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		uploader, uplodeFile
		String uploader = req.getParameter("uploader");
//		String uploadFile = req.getParameter("uploadFile");
		if(req instanceof FileUploadRequestWrapper) {
			String folerURL = "/images";
			String folerPath = req.getServletContext().getRealPath(folerURL);
			File folder = new File(folerPath);
			
			List<PartWrapper> list = ((FileUploadRequestWrapper) req).getPartWrappers("uploadFile");
			if(list!=null) {
				List<String> saveURLs = new ArrayList<>();
				session.setAttribute("saveURLs", saveURLs);
				for(PartWrapper wrapper : list) {
					wrapper.saveToRealPath(folder);
					
					System.out.printf("uploader : %s, uploadFile : %s, uploadedURL : %s\n", uploader, 
									wrapper.getOriginalFilename(), folerURL+"/"+wrapper.getSaveName());
					saveURLs.add(folerURL+"/"+wrapper.getSaveName());
				} // for end
			}// (list!=null) end	
		}
			
		return "redirect:/13/fileUploadForm.jsp";
	}
}























