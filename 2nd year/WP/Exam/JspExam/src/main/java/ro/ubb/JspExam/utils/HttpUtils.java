package ro.ubb.JspExam.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpUtils {
    private final static Gson jsonFormatter = new Gson();

    public static <T> void printJsonResponse(HttpServletResponse response, T object) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.print(jsonFormatter.toJson(object));
        writer.flush();
    }
}
