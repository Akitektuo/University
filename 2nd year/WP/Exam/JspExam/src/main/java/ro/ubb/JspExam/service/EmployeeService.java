package ro.ubb.JspExam.service;

import ro.ubb.JspExam.entity.Employee;

import java.util.concurrent.atomic.AtomicReference;

public class EmployeeService extends ServiceWithDatabaseManager {
    public Employee getData() {
        var name = new AtomicReference<>("Alex");

        databaseManager.query("SELECT * FROM test", result -> name.set(result.getString(1)));

        return new Employee(1, name.get(), ".Net", 1000);
    }

}
