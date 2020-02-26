package Repository;
import Model.Collection.List.MyIList;
import Model.Exceptions.MyException;
import Model.ProgramState;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface InterfaceRepository {
     void addProgramState(ProgramState program);
     void logPrgStateExec(ProgramState prgState) throws IOException, MyException;
     List<ProgramState> getProgramList();
     void setProgramStates(List<ProgramState> newStates);
     ProgramState getProgramStatewithID(int currentId);
}
