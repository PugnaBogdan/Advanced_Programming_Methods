package Repository;
import Model.Exceptions.MyException;
import Model.ProgramState;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements InterfaceRepository{
    private List<ProgramState> programStates = new ArrayList<ProgramState>() {};
    private String logFilePath;


    public Repository(ProgramState prgState, String logFilePath){
        this.programStates.add(prgState);
        this.logFilePath = logFilePath;
    }
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    public ProgramState getProgramStatewithID(int currentID)
    {
        for(ProgramState program : programStates)
            if(program.getID() == currentID)
                return program;
        return null;
    }
    public void setProgramStates(List<ProgramState> newStates){
        programStates = newStates;
    }
    public void addProgramState(ProgramState program)
    {
        programStates.add(program);
    }
    public void logPrgStateExec(ProgramState prgState) throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.write(prgState.toString());
            logFile.close();
        }
        catch (IOException e){
            throw new MyException("IO exception");
        }
    }
}
