package br.com.idbservices.transpcheckserver.Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.idbservices.transpcheckserver.Application.TransporteApplication;
import br.com.idbservices.transpcheckserver.Application.UsuarioApplication;
import br.com.idbservices.transpcheckserver.Domain.Entities.TransporteEntity;
import br.com.idbservices.transpcheckserver.Domain.Entities.UsuarioEntity;
import br.com.idbservices.transpcheckserver.Infrastructure.CrossCutting.JsonConcerns;

@WebServlet("/transporte")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
				maxFileSize=1024*1024*10,      // 10MB
				maxRequestSize=1024*1024*50)   // 50MB
public class TransporteService extends HttpServlet {
       
	private static final String SAVE_DIR = "uploadFiles";
	
    public TransporteService() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
        	
        	String idUsuario = (String) request.getParameter("u"); 
        	
        	TransporteEntity transporte = new TransporteApplication().getTransporteAtivoByIdUsuario(idUsuario);
			
			if (transporte != null) {
				response.setStatus(HttpServletResponse.SC_OK);
				JsonConcerns.writeJsonInResponseFromObject(response, transporte);
			} else {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				JsonConcerns.writeJsonInResponseFromObject(response, "Nenhum transporte ativo encontrado");
			}
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			JsonConcerns.writeJsonInResponseFromObject(response, e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {		

		try {
			String idUsuario = (String) request.getParameter("u");
	        // gets absolute path of the web application
	        String appPath = request.getServletContext().getRealPath("");
	        // constructs path of the directory to save uploaded file
	        String savePath = appPath + File.separator + SAVE_DIR;
	         
	        // creates the save directory if it does not exists
	        File fileSaveDir = new File(savePath);
	        if (!fileSaveDir.exists()) {
	            fileSaveDir.mkdir();
	        }
	         
	        for (Part part : request.getParts()) {
	            String fileName = extractFileName(part);
	            // refines the fileName in case it is an absolute path
	            fileName = new File(fileName).getName();
	            part.write(savePath + File.separator + fileName);
	        }
	        
	        JsonConcerns.writeJsonInResponseFromObject(response, "Arquivo enviado com sucesso!");
        
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			JsonConcerns.writeJsonInResponseFromObject(response, e);
		}
	}
		
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
