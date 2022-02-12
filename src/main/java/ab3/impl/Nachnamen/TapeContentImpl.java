package ab3.impl.Nachnamen;

import ab3.TuringMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TapeContentImpl {

    private List<Character> leftOfHead;
    private Character belowHead;
    private List<Character> rightOfHead;

    public TapeContentImpl(List<Character> leftOfHead, Character belowHead, List<Character> rightOfHead) {
        this.leftOfHead = leftOfHead;
        this.belowHead = belowHead;
        this.rightOfHead = rightOfHead;
    }

    public List<Character> getLeftOfHead() {
        return leftOfHead;
    }

    public Character[] getLeftOfHeadAsArray() {
        ArrayList<Character> newLeftOfHead = new ArrayList<>(this.leftOfHead);
        for (Character character : this.leftOfHead) {
            if (character == null) {
                newLeftOfHead.remove(character);
            } else {
                break;
            }
        }
        Character[] leftOfheadArray = new Character[newLeftOfHead.size()];
        return newLeftOfHead.toArray(leftOfheadArray);
    }

    public Character getBelowHead() {
        return belowHead;
    }

    public List<Character> getRightOfHead() {
        return rightOfHead;
    }

    public Character[] getRightOfHeadAsArray() {
        ArrayList<Character> newRightOfHead = new ArrayList<>(this.rightOfHead);
        for (int i = this.rightOfHead.size() - 1; i >= 0; i--) {
            Character character = this.rightOfHead.get(i);
            if (character == null) {
                newRightOfHead.remove(character);
            } else {
                break;
            }
        }
        Character[] rightOfHeadArray = new Character[newRightOfHead.size()];
        return newRightOfHead.toArray(rightOfHeadArray);
    }

    public void setLeftOfHead(List<Character> leftOfHead) {
        this.leftOfHead = leftOfHead;
    }

    public void setBelowHead(Character belowHead) {
        this.belowHead = belowHead;
    }

    public void setRightOfHead(List<Character> rightOfHead) {
        this.rightOfHead = rightOfHead;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TapeContentImpl tc) {
            if (belowHead != tc.belowHead) return false;
            if (!Arrays.equals(leftOfHead.toArray(), tc.leftOfHead.toArray())) return false;
            return Arrays.equals(rightOfHead.toArray(), tc.rightOfHead.toArray());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "BEGIN:>" + printArray(getLeftOfHeadAsArray()) + "'" + belowHead + "'" + printArray(getRightOfHeadAsArray()) + "<:END";
    }

    private String printArray(Character[] arr)
    {
        StringBuilder s = new StringBuilder();
        for(Character c:arr) {
            if(c != null) s.append(c);
            else s.append(' ');
        }
        return s.toString();
    }

    public TuringMachine.TapeContent toTapeContent() {
        return new TuringMachine.TapeContent(getLeftOfHeadAsArray(), this.belowHead, getRightOfHeadAsArray());
    }

}
