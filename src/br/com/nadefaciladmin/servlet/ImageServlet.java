/**
 * @author Leoh
 */
package br.com.nadefaciladmin.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("file");
        File file = new File(System.getProperty("user.home") + File.separator + "ImagesUploaded" + File.separator + fileName);
        if (file.exists() && !file.isDirectory()) {
	        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
	        // Get image contents.
	        byte[] bytes = new byte[in.available()];
	        in.read(bytes);	
	        in.close();
	        //Write image contents to response.
	        response.getOutputStream().write(bytes);
        }
    }

}