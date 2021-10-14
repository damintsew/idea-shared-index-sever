package ru.damintsew.webserver.rest;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import ru.damintsew.webserver.service.FileService;

@WebServlet(
        name = "AnnotationExample",
        description = "Example Servlet Using Annotations",
        urlPatterns = {"/shared-index"}
)
public class SharedIndexServlet extends HttpServlet {

    private final FileService fileService;

    public SharedIndexServlet(FileService fileService) {
        this.fileService = fileService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException { //todo remove this throws

        String requestURI = stripeBaseUrl(request.getRequestURI());
        File file = fileService.getFileByPath(requestURI); //todo !

        response.setHeader(
                "Content-Disposition",
                "attachment;filename=" + file.getName());
        response.setContentLength((int) file.length());
//        response.setContentType("audio/mpeg"); todo!

        try(ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file)) {

            long l = fileInputStream.transferTo(outputStream);
            System.out.println("l = " + l);
        }
    }

    private String stripeBaseUrl(String requestURI) {
        return requestURI.substring("/shared-index".length());
    }
}
