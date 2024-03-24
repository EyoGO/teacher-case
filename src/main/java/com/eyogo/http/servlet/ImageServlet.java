package com.eyogo.http.servlet;

import com.eyogo.http.dto.GetUserDto;
import com.eyogo.http.service.ImageService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

@WebServlet("/images" + "/*")
@MultipartConfig
public class ImageServlet extends HttpServlet {

    private final ImageService imageService = ImageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part file = request.getPart("file");
        String upload = imageService.upload("/data/user_" + ((GetUserDto) request.getSession().getAttribute("user")).getId() +
                                            File.separator + file.getSubmittedFileName(), file.getInputStream()); //TODO 1 user still can insert 2 images with the same name
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("location", upload);

        String okResponse = new Gson().toJson(jsonObject);
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(okResponse);
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();

        int indexOfImages = requestURI.indexOf("/images");
        String imagePath = requestURI.substring(indexOfImages + "/images".length());

        imageService.get(imagePath)
                .ifPresentOrElse(image -> {
                    resp.setContentType("application/octet-stream");
                    writeImage(image, resp);
                }, () -> resp.setStatus(404));
    }

    @SneakyThrows
    private void writeImage(InputStream image, HttpServletResponse resp) {
        try(image; OutputStream outputStream = resp.getOutputStream()) {
            int currentByte;
            while ((currentByte = image.read()) != -1) {
                outputStream.write(currentByte);
            }
        }
    }
}
