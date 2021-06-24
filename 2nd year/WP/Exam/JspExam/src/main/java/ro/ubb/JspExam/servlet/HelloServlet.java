package ro.ubb.JspExam.servlet;

import ro.ubb.JspExam.service.EmployeeService;
import ro.ubb.JspExam.utils.HttpUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/api/hello-servlet")
public class HelloServlet extends ServletWithService<EmployeeService> {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpUtils.printJsonResponse(response, new ArrayList<>(List.of(service.getData())));
    }

    @Override
    protected EmployeeService initializeService() {
        return new EmployeeService();
    }
}