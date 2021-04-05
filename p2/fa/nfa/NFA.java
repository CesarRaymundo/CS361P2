package fa.nfa;

import java.util.Set;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.HashSet;
import java.util.Iterator;
import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface{
    
    private NFAState finalState; //final state
    private NFAState startState; //starting state
    private LinkedHashSet<NFAState> eClosure;// holds the states that can be transtioned with 'e'
    private LinkedHashSet<NFAState> states; //holds all the states of the NFA	
	private HashSet<Character> sigma; //includes all letters used
    
    public NFA(){
		states = new LinkedHashSet<>();
		sigma = new HashSet<>();
    }
  
    @Override
    public void addStartState(String name) {
        startState = new NFAState(name);
        if(startState != null){
            checkState(startState);
        }else{
            addState(name);
        }
        
    }

    @Override
    public void addState(String name) {
       NFAState stateToAdd = new NFAState(name);
       checkState(stateToAdd);
    }

    @Override
    public void addFinalState(String name) {
        finalState = new NFAState(name,true);
        checkState(finalState);
        
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        if(!sigma.contains(onSymb)) {
			sigma.add(onSymb);
		}
		Iterator<NFAState> toItr = states.iterator();
		Iterator<NFAState> fromItr = states.iterator();
		NFAState fromS = fromItr.next();
		while(fromItr.hasNext() 
				&& !fromS.getNameNFA().equals(fromState)) {
			fromS = fromItr.next();
		}

		NFAState toS = toItr.next();
		while(toItr.hasNext() 
				&& !toS.getNameNFA().equals(toState)) {
			toS = toItr.next();
		}
		fromS.addTransition(onSymb, toS);
		
        if(!sigma.contains(onSymb) && onSymb != 'e'){
            sigma.add(onSymb);
        }
	}  
    
	/**
	 * Construct the textual representation of the DFA, for example
	 * A simple two state DFA
	 * Q = { a b }
	 * Sigma = { 0 1 }
	 * delta =
	 *		0	1	
	 *	a	a	b	
	 *	b	a	b	
	 * q0 = a
	 * F = { b }
	 * 
	 * The order of the states and the alphabet is the order
	 * in which they were instantiated in the DFA.
	 * @return String representation of the DFA
	 */
	//public String toString() {
		// String formatedString = "";
		
		// //printing the whole set of states
		// formatedString += "Q = { ";
		// Object[] statesArray  = states.toArray();
		// Iterator<NFAState> its = states.iterator();

		// while(its.hasNext()) {
		// 	formatedString += its.next().getNameNFA() + " ";
		// }
		// formatedString += "} \n";
		
		// //printing alphabet
		// formatedString += "Sigma = { ";
		// Object[] sigmaArray = sigma.toArray();
		// for (int i = 0; i < sigmaArray.length; i++) {
		// 	formatedString += sigmaArray[i] + " ";
		// }
		// formatedString += "} \n";
		
		
		
		
		// //printing delta
		// formatedString += "delta =  \n\t\t";
		// //for every character in alphabet
		// for (int i = 0; i < sigmaArray.length; i++) { 
		// 	formatedString += sigmaArray[i] + " \t";
			
		// }
		// formatedString += "\n\t";
		
		
		// for (int j = 0; j < statesArray.length; j++) { //for every state
		// 	formatedString += ((NFAState)statesArray[j]).getNameNFA() + "\t";
		// 	for (int i = 0; i < sigmaArray.length; i++) {
		// 		//adds the resulting state of the transition to the output string
		// 		formatedString += getToState((NFASState)statesArray[j], (Character)alphabetArray[i]).getNameDFA(); 
		// 		formatedString += " \t";
		// 	}
			
			
		// 	//get transition given state and character
			
		// 	formatedString += "\n\t"; //go onto next state
		// }
		
		
		
		
		
		
		// formatedString += "\n";
		
		// //printing start state
		// formatedString += "q0 = " + startState.getNameDFA() + "\n";
		
		// //printing final state
		// formatedString += "F = { ";

		
		// Iterator<NFAtate> itf = finalStates.iterator();
		// while(itf.hasNext()) {
		// 	formatedString += itf.next().getNameDFA() + " ";
		// }
		
		// formatedString += "} \n";
		
		
		
		// return formatedString;
//	}
    @Override
    public Set<? extends State> getStates() {
        return states;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        Set<NFAState> finalStates = new HashSet<NFAState>();
        for (NFAState x: states){
            if (x.isFinal()){
                finalStates.add(x);
            }
        }
        return finalStates;
    }

    @Override
    public State getStartState() {
      return startState;
    }

    @Override
    public Set<Character> getABC() {
     return sigma;
    }

    @Override
    public DFA getDFA() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.getTrans(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
          if(!s.getTrans('e').equals(new HashSet<NFAState>())) {
        	for (NFAState x : s.getTrans('e')) {
                if (!this.eClosure.contains(x)) {
                    this.eClosure.add(x);
                    eClosure(x);
                }
            }
        } else {
        	this.eClosure.add(s);
        }
		return this.eClosure;
    }
    

    /**
     * Checks if the state already exists.
     * Adds the state to the list if not already added.
     * 
     * @param x
     */
    private void checkState(NFAState x){
        if(!states.contains(x)){
            states.add(x);
        }
    }
    
}
