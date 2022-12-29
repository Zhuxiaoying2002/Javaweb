package com.example.book.controller.book.backend;

import com.example.book.pojo.Book;
import com.example.book.service.BookServiceImpl;
import com.example.book.util.ApiSerialize;
import com.example.book.util.CommonResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/book-save-api")
public class BookSaveApi extends HttpServlet {
    private static final String  UPLOAD_DIRECTORY = "upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Book book = null;
        try {
            book = getBookFromRequest(req);
        }
        catch (Exception e) {
            e.printStackTrace();
            // 出错
            try(Writer writer = resp.getWriter()) {
                resp.setContentType("application/json; charset=UTF-8");
                ApiSerialize.dumpsToJson(writer, CommonResponse.fail(500, "server error in book"));
                return;
            }
        }

        // 写库返回数据
        if (book == null) {
            try(Writer writer = resp.getWriter()) {
                resp.setContentType("application/json; charset=UTF-8");
                ApiSerialize.dumpsToJson(writer, CommonResponse.fail(500, "param error"));
                return;
            }
        }

        //写库
        BookServiceImpl bookService = new BookServiceImpl();
        try {
            bookService.insert(book);
        }
        catch (SQLException e) {
            e.printStackTrace();
            try(Writer writer = resp.getWriter()) {
                resp.setContentType("application/json; charset=UTF-8");
                ApiSerialize.dumpsToJson(writer, CommonResponse.fail(500, "db save error"));
                return;
            }
        }

        // 保存成功返回数据
        try(Writer writer = resp.getWriter()) {
            resp.setContentType("application/json; charset=UTF-8");
            ApiSerialize.dumpsToJson(writer, CommonResponse.success("data save success"));
        }
    }

    private Book getBookFromRequest(HttpServletRequest request) throws Exception {
        // 非文件传输直接返回null
        if (!ServletFileUpload.isMultipartContent(request)) {
            return null;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // 中文处理
        upload.setHeaderEncoding("UTF-8");


        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath =
                request.getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        List<FileItem> formItems = upload.parseRequest(request);
        Book book = new Book();

        if (formItems != null && formItems.size() > 0) {
            for (FileItem item: formItems) {
                if (!item.isFormField()) {  // 处理图片
                    // 非表单字段
                    String fileName = new File(item.getName()).getName();
                    fileName = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."));
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    // 在控制台输出文件的上传路径
                    System.out.println("save-path: " + filePath);
                    // 保存文件到硬盘
                    item.write(storeFile);
                    book.setImgUrl(fileName);
                }
                else {
                    // 表单字段
                    if (item.getFieldName().equals("name")) {
                        book.setName(item.getString("UTF-8"));
                    }
                    else if (item.getFieldName().equals("author")) {
                        book.setAuthor(item.getString("UTF-8"));
                    }
                    else if(item.getFieldName().equals("publisher")) {
                        book.setPublisher(item.getString("UTF-8"));
                    }
                    else if (item.getFieldName().equals("description")) {
                        book.setDescription(item.getString("UTF-8"));
                    }
                    else if (item.getFieldName().equals("price")) {
                        book.setPrice(Double.parseDouble(item.getString("UTF-8")));
                    }
                }
            }
        }

        return book;
    }
}
