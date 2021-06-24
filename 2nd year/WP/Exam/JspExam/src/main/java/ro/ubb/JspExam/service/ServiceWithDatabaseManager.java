package ro.ubb.JspExam.service;

import ro.ubb.JspExam.database.DatabaseManager;

public abstract class ServiceWithDatabaseManager {
    protected final DatabaseManager databaseManager = new DatabaseManager();

    public void closeConnections() {
        databaseManager.close();
    }
}
