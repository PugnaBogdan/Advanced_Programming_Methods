package Controller;

import Model.Collection.Stack.MyIStack;
import Model.Exceptions.MyException;
import Model.Statement.IStatement;
import Model.Value.Value;
import Model.ProgramState;
import Model.Value.ReferenceValue;
import Repository.InterfaceRepository;
import Repository.Repository;


import java.io.IOException;
import java.util.List;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private InterfaceRepository repository;
    private ExecutorService executor;
    public Controller(InterfaceRepository repo){
        repository=repo;
    }
    Map<Integer, Value> safeGarbageCollector(List<Integer> addresses, Map<Integer,Value> heap)
    {
        return heap.entrySet().stream().filter(e->addresses.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    private List<Integer> getSymbolTableAddresses(Collection<Value> symbolTableValues) {
        return symbolTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddr();})
                .collect(Collectors.toList());
    }
    List<Integer> getAllSymbolTableAdresses()
    {
        return repository.getProgramList().stream()
                .map(p -> getSymbolTableAddresses(p.getSymbolTable().getValues().values()))
                .reduce(Stream.of(0).collect(Collectors.toList()),
                        (acc, item) -> Stream.concat(acc.stream(), item.stream())
                                .collect(Collectors.toList()));
    }

    public InterfaceRepository getRepo()
    {
        return repository;
    }

    List<Integer> extractAllValidAddresses()
    {
        Set<Map.Entry<Integer, Value>> heapEntrySet = repository.getProgramList().get(0).getHeapTable().getValues().entrySet();
        LinkedList<Integer> indirectAddressesList = new LinkedList<>(getAllSymbolTableAdresses());
        boolean doneExtracting = false;
        while (!doneExtracting) {
            doneExtracting = true;
            List <Integer> currentIndirectPhaseAddresses = heapEntrySet.stream()
                    .filter(entry -> indirectAddressesList.contains(entry.getKey()))
                    .filter(entry -> entry.getValue() instanceof ReferenceValue)
                    .map(entry -> {ReferenceValue v = (ReferenceValue) entry.getValue();
                        return v.getAddr();})
                    .filter(entry -> !indirectAddressesList.contains(entry))
                    .collect(Collectors.toList());

            if (!currentIndirectPhaseAddresses.isEmpty()) {
                doneExtracting = false;
                indirectAddressesList.addAll(currentIndirectPhaseAddresses);
            }
        }
        return indirectAddressesList;
    }

    void callGarbageCollector()
    {
        repository.getProgramList().get(0).getHeapTable().setValues((HashMap)safeGarbageCollector(extractAllValidAddresses(),repository.getProgramList().get(0).getHeapTable().getValues()));
    }

//    private Map<Integer, Value>safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value>
//            heap){
//        return heap.entrySet().stream()
//                .filter(e->symTableAddr.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}
//
//    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap){
//
//        return Stream.concat(symTableValues.stream()
//                .filter(v-> v instanceof ReferenceValue)
//                .map(v-> {
//                    ReferenceValue v1 = (ReferenceValue)v; return v1.getAddr();}),
//
//                heap.stream().filter(v-> v instanceof ReferenceValue)
//                        .map(v-> {
//                            ReferenceValue v1 = (ReferenceValue)v; return v1.getAddr();})
//        ).collect(Collectors.toList());
//    }


//    private void oneStep() throws MyException {
//        ProgramState state = getCurrentState();
//        MyIStack<IStatement> stack=state.getStack();
//
//        if(stack.isEmpty()) {
//            throw new MyException("ProgramState stack is empty" +"\n");
//        }
//        IStatement crtStmt = stack.pop();
//        crtStmt.execute(state);
//    }
//    public void allStep() throws IOException, MyException {
//        ProgramState state = getCurrentState();
//        repository.logPrgStateExec(state);
//        while (!state.getStack().isEmpty()){
//            state.oneStep();
//            repository.logPrgStateExec(state);
//            state.getHeapTable().setValues((HashMap<Integer, Value>) this.safeGarbageCollector(this.getAddrFromSymTable(state.getSymbolTable().getValues().values(), state.getSymbolTable().getValues().values()), state.getHeapTable().getValues()));
//        }
//    }


    public void oneStep() throws MyException
    {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> list = removeCompletedPrg(repository.getProgramList());
        if (!list.isEmpty()) {
            callGarbageCollector();
            oneStepForAllProgram(list);
        }
        executor.shutdownNow();
        repository.setProgramStates(list);
    }

    public void removeAfterOneStep()
    {
        repository.setProgramStates(removeCompletedPrg(repository.getProgramList()));
    }

    private List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgState){
        return inPrgState.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }



    public void allStep() throws MyException, IOException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> list = removeCompletedPrg(repository.getProgramList());
        list.forEach(program -> {try {repository.logPrgStateExec(program);} catch(MyException | IOException ignored) {}});
        while (!list.isEmpty()) {
            callGarbageCollector();
            oneStepForAllProgram(list);
            list = removeCompletedPrg(repository.getProgramList());
        }
        repository.getProgramList().forEach(program -> {try {repository.logPrgStateExec(program);} catch(MyException | IOException ignored) {}});
        executor.shutdownNow();
        repository.setProgramStates(list);
    }

    public void oneStepForAllProgram(List<ProgramState> activePrograms) throws MyException{

        List<Callable<ProgramState>> callList = activePrograms.stream().map((ProgramState program) -> (Callable<ProgramState>)(() -> {return program.oneStep();}))
                .collect(Collectors.toList());
        List<ProgramState> newList = null;
        try {
            newList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException ignored) {
                            return null;
                        }
                    })
                    .filter(state -> state != null)
                    .collect(Collectors.toList());
        } catch (InterruptedException except) {
            throw new MyException(except.getMessage());
        }
        activePrograms.addAll(newList);

        activePrograms.forEach(program -> {try {repository.logPrgStateExec(program);} catch(MyException ignored) {} catch (IOException e) {
            e.printStackTrace();
        }
        });
        repository.setProgramStates(activePrograms);

    }



}
