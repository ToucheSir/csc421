import java.util.Arrays;

public class StateWaterJugs {    
    final int[] jugs;

    public StateWaterJugs(int... jugs) {
    	this.jugs = jugs;
    }
    
    // It has to be a copy of values not reference because we will 
    // create many states and don't want to overwrite the same array.
    public StateWaterJugs(StateWaterJugs state) {
    	jugs = state.jugs.clone();
    }
    
    @Override
    public boolean equals(Object o) {
        StateWaterJugs state = (StateWaterJugs) o;
        return Arrays.equals(jugs, state.jugs);
    }
    
    @Override
    public int hashCode() {
    	return Arrays.hashCode(jugs);
    }    
    
    @Override
    public String toString() {
    	return Arrays.toString(jugs);
    }
}