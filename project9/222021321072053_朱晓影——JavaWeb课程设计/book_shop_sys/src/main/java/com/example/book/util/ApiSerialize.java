package com.example.book.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 *  定义接口序列化器工具
 */
public class ApiSerialize {
    public static void dumpsToJson(Writer writer, Object obj) throws IOException {
        String json = null;
        try {
             json = new ObjectMapper()
                    .writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("json序列化异常");
        }

        if (json == null) {
            writer.write("{\"code\": 500, \"message\": \"server error\", \"data\": null}");
        }
        else {
            writer.write(json);
        }
    }

    public static void dumpsToJson2(ServletOutputStream writer, Object obj) throws IOException {
        String json = null;
        try {
            json = new ObjectMapper()
                    .writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("json序列化异常");
        }

        if (json == null) {
            String temp = "{\"code\": 500, \"message\": \"server error\", \"data\": null}";
            writer.write(temp.getBytes(StandardCharsets.UTF_8));
        }
        else {
            writer.write(json.getBytes(StandardCharsets.UTF_8));
        }
    }
}
