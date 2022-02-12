package ab3.impl.GroßmannDuellerZangerl;

import ab3.TuringMachine;

public class Transition {
    private int fromState;
    private int toState;
    private Character[] read;
    private Character[] write;
    private TuringMachine.Movement[] moveDirection;

    public Transition(int fromState, int toState, Character[] read, Character[] write, TuringMachine.Movement[] moveDirection) {
        this.fromState = fromState;
        this.toState = toState;
        this.read = read;
        this.write = write;
        this.moveDirection = moveDirection;
    }

    public int getFromState() {
        return fromState;
    }

    public int getToState() {
        return toState;
    }

    public Character[] getRead() {
        return read;
    }

    public Character[] getWrite() {
        return write;
    }

    public TuringMachine.Movement[] getMoveDirection() {
        return moveDirection;
    }
}
