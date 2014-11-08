package net.codesree.servlet;

import java.io.File;
import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {

  private final String UPLOAD_DIRECTORY = "/home/sree25/uploads";

  public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
    if(ServletFileUpload.isMultipartContent(req)) {
      try {
        List<FileItem> multiparts=new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
        for(FileItem item:multiparts) {
          if(!item.isFormField()) {
            String name=new File(item.getName()).getName();
            item.write(new File(UPLOAD_DIRECTORY+File.separator+name));
          }
        }
        req.setAttribute("message","File Uploaded successfully.");
      } catch(Exception ex) {
        req.setAttribute("message", "File Upload Failed due to " + ex);
      }
    } else {
      req.setAttribute("message","Sorry this Servlet only handles file upload request");
    }
    req.getRequestDispatcher("/result.jsp").forward(req,res);
  }

  public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
    doPost(req,res);
  }

}