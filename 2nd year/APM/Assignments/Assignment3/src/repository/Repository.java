package repository;

import container.List;
import container.ListInterface;
import model.ProgramState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Repository implements RepositoryInterface {
    private final ListInterface<ProgramState> programStates = new List<>();
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    private final String absolutePath = new File("").getAbsolutePath();
    private Date dateSeed;

    @Override
    public void addProgramState(ProgramState programState) {
        programStates.add(programState);
    }

    @Override
    public ProgramState getCurrentProgramState() {
        dateSeed = Calendar.getInstance().getTime();
        return programStates.getLast();
    }

    @Override
    public void logProgramState(ProgramState programState) {
        var fileName = String.format("%s/logs/%s.log", absolutePath, dateFormat.format(dateSeed));

        try {
            new FileWriter(fileName, true)
                    .append(programState.toString())
                    .append("\n")
                    .close();
        } catch (IOException exception) {
            System.out.printf("Error: %s%n", exception.getMessage());
        }
    }


}
