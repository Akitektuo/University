package ro.ubb.JspExam.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultConsumer {
    void call(ResultSet result) throws SQLException;
}
