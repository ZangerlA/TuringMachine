package ab3.impl.Nachnamen;

import ab3.TuringMachine;

import java.util.List;
import java.util.Set;

public class TM implements TuringMachine {


    @Override
    public void reset() {

    }

    @Override
    public int getCurrentState() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setAlphabet(Set<Character> alphabet) throws IllegalArgumentException {

    }

    @Override
    public Set<Character> getAlphabet() {
        return null;
    }

    @Override
    public void addTransition(int fromState, Character read, int toState, Character write, Movement move) throws IllegalArgumentException {

    }

    @Override
    public void addTransition(int fromState, Character[] read, int toState, Character[] write, Movement[] move) throws IllegalArgumentException {

    }

    @Override
    public int getNumberOfStates() {
        return 0;
    }

    @Override
    public int getNumberOfTapes() {
        return 0;
    }

    @Override
    public void setNumberOfStates(int numStates) throws IllegalArgumentException {

    }

    @Override
    public void setNumberOfTapes(int numTapes) throws IllegalArgumentException {

    }

    @Override
    public void setHaltingState(int haltingState) throws IllegalArgumentException {

    }

    @Override
    public void setInitialState(int initialState) throws IllegalArgumentException {

    }

    @Override
    public void setInput(String content) {

    }

    @Override
    public void doNextStep() throws IllegalStateException {

    }

    @Override
    public boolean isInHaltingState() {
        return false;
    }

    @Override
    public boolean isInErrorState() {
        return false;
    }

    @Override
    public List<TapeContent> getTapeContents() {
        return null;
    }

    @Override
    public TapeContent getTapeContent(int tape) {
        return null;
    }
}
