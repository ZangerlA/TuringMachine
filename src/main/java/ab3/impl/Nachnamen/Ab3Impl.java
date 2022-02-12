package ab3.impl.Nachnamen;

import java.util.Set;

import ab3.Ab3;
import ab3.TuringMachine;

public class Ab3Impl implements Ab3 {

    @Override
    public TuringMachine getEmptyTM() {
	    return new TM();
    }
}
