package utils;

public class CodeFormatter {
    private final StringBuilder code = new StringBuilder();

    public CodeFormatter(String firstLine) {
        indent();
        code.append(firstLine);
    }

    public CodeFormatter(String firstLine, int indentation) {
        indent(indentation);
        code.append(firstLine);
    }

    public CodeFormatter addLine(String line) {
        return addLine(line, 1);
    }

    public CodeFormatter addLine(String line, int indentation) {
        newLine();
        indent(indentation);

        code.append(line);

        return this;
    }

    public CodeFormatter indent() {
        return indent(1);
    }

    public CodeFormatter indent(int count) {
        code.append("\t".repeat(Math.max(0, count)));

        return this;
    }

    public CodeFormatter newLine() {
        code.append('\n');

        return this;
    }

    public String build() {
        return code.toString();
    }
}
