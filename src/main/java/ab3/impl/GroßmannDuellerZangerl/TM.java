package ab3.impl.GroßmannDuellerZangerl;

import ab3.TuringMachine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TM implements TuringMachine {

    private Set<Character> inputAlphabet;
    private Set<Character> tapeAlphabet;
    private Set<Transition> transitions;
    private ArrayList<TapeContentImpl> tapes;
    private int numStates;
    private int numTapes;
    private int initialState;
    private int currentState;
    private int haltingState;
    private boolean isTrapped;

    public TM() {
        inputAlphabet = new HashSet<>();
        tapeAlphabet = new HashSet<>();
        transitions = new HashSet<>();
        tapes = new ArrayList<>();
    }

    @Override
    public void reset() {
        this.currentState = initialState;
        for (TapeContentImpl t : tapes) {
            t = new TapeContentImpl(new ArrayList<>(), null, new ArrayList<>());
        }
    }

    @Override
    public int getCurrentState() throws IllegalStateException {
        if (isInErrorState()) {
            throw new IllegalStateException("CurrentState is in TrapState");
        }
        return currentState;
    }

    @Override
    public void setAlphabet(Set<Character> alphabet) throws IllegalArgumentException {
        if (alphabet.contains(null)) {
            throw new IllegalArgumentException("inputAlphabet should not contain null");
        }

        inputAlphabet = alphabet;
        tapeAlphabet = alphabet;
        tapeAlphabet.add(null);
    }

    @Override
    public Set<Character> getAlphabet() {
        return inputAlphabet;
    }

    @Override
    public void addTransition(int fromState, Character read, int toState, Character write, Movement move) throws IllegalArgumentException {
        if (fromState == haltingState) {
            throw new IllegalArgumentException("Halting state can't have an outgoing transition");
        }
        if (!isDeterministic(fromState, new Character[]{read})) {
            throw new IllegalArgumentException("Transition is not deterministic");
        }
        if (!tapeAlphabet.contains(read) || !tapeAlphabet.contains(write)) {
            throw new IllegalArgumentException("Symbol not part of tapeAlphabet");
        }
        if (fromState < 0 || fromState >= numStates || toState < 0 || toState >= numStates) {
            throw new IllegalArgumentException("State doesn't exist");
        }
        if (numTapes > 1) {
            throw new IllegalArgumentException("TM has more than 1 tape");
        }

        transitions.add(new Transition(fromState, toState, new Character[]{read}, new Character[]{write}, new Movement[]{move}));
    }

    @Override
    public void addTransition(int fromState, Character[] read, int toState, Character[] write, Movement[] move) throws IllegalArgumentException {
        if (fromState == haltingState) {
            throw new IllegalArgumentException("Halting state can't have an outgoing transition");
        }
        if (!isDeterministic(fromState, read)) {
            throw new IllegalArgumentException("Transition is not deterministic");
        }
        if (!isInTapeAlphabet(read, write)) {
            throw new IllegalArgumentException("Symbol not part of tapeAlphabet");
        }
        if (fromState < 0 || fromState >= numStates || toState < 0 || toState >= numStates) {
            throw new IllegalArgumentException("State doesn't exist");
        }

        transitions.add(new Transition(fromState, toState, read, write, move));
    }

    @Override
    public int getNumberOfStates() {
        return numStates;
    }

    @Override
    public int getNumberOfTapes() {
        return numTapes;
    }

    @Override
    public void setNumberOfStates(int numStates) throws IllegalArgumentException {
        if (numStates < 1) {
            throw new IllegalArgumentException("Number of states can't be 0 or less");
        }
        this.numStates = numStates;
    }

    @Override
    public void setNumberOfTapes(int numTapes) throws IllegalArgumentException {
        if (numTapes < 1) {
            throw new IllegalArgumentException("Number of tapes can´t be 0 or less");
        }
        this.numTapes = numTapes;
        for (int i = 0; i < numTapes; i++) {
            this.tapes.add(i, new TapeContentImpl(new ArrayList<>(), null, new ArrayList<>()));
        }
    }

    @Override
    public void setHaltingState(int haltingState) throws IllegalArgumentException {
        if (haltingState < 0 || haltingState >= numStates) {
            throw new IllegalArgumentException("haltingState is outside of boundaries");
        }
        this.haltingState = haltingState;
    }

    @Override
    public void setInitialState(int initialState) throws IllegalArgumentException {
        if (initialState < 0 || initialState >= numStates) {
            throw new IllegalArgumentException("initialState is outside of boundaries");
        }
        this.initialState = initialState;
        this.currentState = initialState;
    }

    @Override
    public void setInput(String content) {
        Character headCharacter;
        ArrayList<Character> rightSideChars;

        if (content.equals("")) {
            headCharacter = null;
            rightSideChars = new ArrayList<>();

        } else if (content.length() == 1) {
            headCharacter = content.charAt(0);
            rightSideChars = new ArrayList<>();
        } else {
            String rightSide = content.substring(1);
            headCharacter = content.charAt(0);
            rightSideChars = stringToCharacterList(rightSide);
        }

        tapes.set(0, new TapeContentImpl(new ArrayList<>(), headCharacter, rightSideChars));
    }

    @Override
    public void doNextStep() throws IllegalStateException {
        if (currentState == haltingState || isInErrorState()) {
            throw new IllegalStateException("TM already in haltingState or errorState");
        }
        ArrayList<TapeContentImpl> tapeContents = this.tapes;
        List<Transition> transitions = this.transitions.stream().filter(transition -> {
            boolean temp = transition.getFromState() == currentState;
            for (int i = 0; i < transition.getRead().length; i++) {
                if (!(transition.getRead()[i] == tapes.get(i).getBelowHead())) temp = false;
            }
            return temp;
        }).toList();

        if (transitions.size() == 0) {
            isTrapped = true;
        } else {
            transitions.forEach(transition -> {
                for (int i = 0; i < tapeContents.size(); i++) {
                    TapeContentImpl tape = tapeContents.get(i);
                    System.out.println(transition.getFromState() + "--" + transition.getRead()[i] + "/" + transition.getWrite()[i] + "/" + transition.getMoveDirection()[i] + "->" + transition.getToState());
                    this.currentState = transition.getToState();
                    if (transition.getMoveDirection()[i] == Movement.Right) {
                        tape.getLeftOfHead().add(transition.getWrite()[i]);
                        Character belowHead;
                        if (tape.getRightOfHead().size() == 0) {
                            belowHead = null;
                        } else {
                            belowHead = tape.getRightOfHead().get(0);
                            tape.getRightOfHead().remove(0);
                        }
                        tape.setBelowHead(belowHead);
                    } else if (transition.getMoveDirection()[i] == Movement.Left) {
                        tape.getRightOfHead().add(0, transition.getWrite()[i]);
                        Character belowHead;
                        if (tape.getLeftOfHead().size() == 0) {
                            belowHead = null;
                        } else {
                            belowHead = tape.getLeftOfHead().get(tape.getLeftOfHead().size() - 1);
                            tape.getLeftOfHead().remove(tape.getLeftOfHead().size() - 1);
                        }
                        tape.setBelowHead(belowHead);
                    } else if (transition.getMoveDirection()[i] == Movement.Stay) {
                        tape.setBelowHead(transition.getWrite()[i]);
                    }
                }
            });
        }

    }

    @Override
    public boolean isInHaltingState() {
        return currentState == haltingState;
    }

    @Override
    public boolean isInErrorState() {
        return isTrapped;
    }

    @Override
    public List<TapeContent> getTapeContents() {
        if (isInErrorState()) {
            return null;
        }
        List<TapeContent> tapeContents = new ArrayList<>();
        tapes.forEach(tape -> {
            tapeContents.add(tape.toTapeContent());
        });
        return tapeContents;
    }

    @Override
    public TapeContent getTapeContent(int tape) {
        if (isInErrorState()) {
            return null;
        }
        return tapes.get(tape).toTapeContent();
    }

    public boolean isDeterministic(int fromState, Character[] read) {
        for (Transition t : transitions) {
            if (t.getFromState() == fromState) {
                if (t.getRead().equals(read)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isInTapeAlphabet(Character[] read, Character[] write) {
        for (Character c : read) {
            if (!tapeAlphabet.contains(c)) {
                return false;
            }
        }
        for (Character c : write) {
            if (!tapeAlphabet.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Character> stringToCharacterList(String string) {
        ArrayList<Character> result;
        char[] temp = string.toCharArray();
        result = new ArrayList<>();
        for (char c : temp) {
            result.add(c);
        }
        return result;
    }
}
