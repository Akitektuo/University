package ro.ubb.truckers.util.network;

import java.io.*;

public class Request {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static final String OK = "200";
    public static final String NOT_FOUND = "404";
    public static final String PRECONDITION_FAILED = "412";
    public static final String INTERNAL_SERVER_ERROR = "500";
    public static final String NOT_IMPLEMENTED = "501";

    private String header;
    private String body;

    public Request() {
        this("");
    }

    public Request(InputStream stream) throws IOException {
        readFrom(stream);
    }

    public Request(String header) {
        this(header, "");
    }

    public Request(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public void readFrom(InputStream stream) throws IOException {
        var br = new BufferedReader(new InputStreamReader(stream));
        header = br.readLine();
        body = br.readLine();
    }

    public void writeTo(OutputStream stream) throws IOException {
        stream.write((header + LINE_SEPARATOR + body + LINE_SEPARATOR).getBytes());
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("Request(header='%s', body='%s')", header, body);
    }

    public static Request fromError(String errorCode) {
        return fromError(errorCode, "");
    }

    public static Request fromError(String errorCode, String message) {
        return new Request(errorCode, message);
    }
}
