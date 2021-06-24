package ro.ubb.JspExam.servlet;

import ro.ubb.JspExam.service.ServiceWithDatabaseManager;

import javax.servlet.http.HttpServlet;

public abstract class ServletWithService<T extends ServiceWithDatabaseManager> extends HttpServlet {
    protected T service;

    @Override
    public void init() {
        service = initializeService();
    }

    @Override
    public void destroy() {
        service.closeConnections();
    }

    protected abstract T initializeService();
}
