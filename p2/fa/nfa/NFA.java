package fa.nfa;

import java.util.Set;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.HashSet;
import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface{
    private NFAState startState; //starting state
    private LinkedHashSet<NFAState> states; //holds all the states of the NFA	
	private HashSet<Character> alphabet; //includes all letters used
    
    public NFA(){
		states = new LinkedHashSet<>();
		alphabet = new HashSet<>();
    }
  
    @Override
    public void addStartState(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addState(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addFinalState(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Set<? extends State> getStates() {
        return states;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public State getStartState() {
      return startState;
    }

    @Override
    public Set<Character> getABC() {
     return alphabet;
    }

    @Override
    public DFA getDFA() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
