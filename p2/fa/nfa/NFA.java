package fa.nfa;

import java.util.Set;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Iterator;
import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface{
    
    private LinkedHashSet<NFAState> finalStates; //holds all final states
    private NFAState startState; //starting state
    private LinkedHashSet<NFAState> eClosure;// holds the states that can be transtioned with 'e'
    private LinkedHashSet<NFAState> statesSet; //holds all the states of the NFA	
	private HashSet<Character> sigma; //includes all letters used
    
    public NFA(){
		statesSet = new LinkedHashSet<>();
		eClosure = new LinkedHashSet<>();
        sigma = new HashSet<>();
    }
  
    @Override
    public void addStartState(String name) {
        NFAState s = checkIfExists(name);
        if(s == null){
            s = new NFAState(name);
            statesSet.add(s);
        }else{
            System.out.println("Warning: A state with name"+name+"already exists");
        }
        startState = s;
        
        // if(startState != null){
        //     checkState(startState);
        // }else{
        //     addState(name);
        // }
        
    }

    @Override
    public void addState(String name) {
    NFAState s = checkIfExists(name);
    if(s == null){
        s = new NFAState(name);
        statesSet.add(s);
    }else{
        System.out.println("Warning: A state with name"+name+"already exists");
    }
        //    NFAState stateToAdd = new NFAState(name);
    //    checkState(stateToAdd);
    }

    @Override
    public void addFinalState(String name) {
      NFAState f = checkIfExists(name);
      if(f == null){
          f = new NFAState(name);
          finalStates.add(f);
          statesSet.add(f);
      }else{
        System.out.println("Warning: A state with name"+name+"already exists");
      }
        // finalState = new NFAState(name,true);
        // checkState(finalState);
        
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        if(!sigma.contains(onSymb)) {
			sigma.add(onSymb);
		}
		Iterator<NFAState> toItr = statesSet.iterator();
		Iterator<NFAState> fromItr = statesSet.iterator();
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
        return statesSet;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        // Set<NFAState> finalStates = new HashSet<NFAState>();
        // for (NFAState x: states){
        //     if (x.isFinal()){
        //         finalStates.add(x);
        //     }
        // }
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
        // Must implement the breadth first search algorithm.
		Queue<Set<NFAState>> workQueue = new LinkedList<Set<NFAState>>();
		DFA d = new DFA(); // Step 1. https://www.javatpoint.com/automata-conversion-from-nfa-to-dfa
		workQueue.add(eClosure(startState));

		while (!workQueue.isEmpty()) {
			Set<NFAState> currentNode = workQueue.poll(); // current workItem.
			boolean isFinalState = false;

			for (NFAState n : currentNode) {
				if (n.isFinal()) {
					isFinalState = true;
				}
			}

			if (d.getStartState() == null && !isFinalState) {
				d.addStartState(currentNode.toString());
			} else if (d.getStartState() == null && isFinalState) {
				d.addFinalState(currentNode.toString());
				d.addStartState(currentNode.toString());
			}

			for (Character symb : getABC()) {
				Set<NFAState> setOfStateForSymb = new HashSet<NFAState>();
				for (NFAState v : currentNode) {
					if (v.getTrans(symb) != null) {
						for (NFAState t : v.getTrans(symb)) {
							setOfStateForSymb.addAll(eClosure(t));
						}
					}
				}

				boolean dfaHasState = false;

				for (State s : d.getStates()) {
					if (s.getName().equals(setOfStateForSymb.toString())) {
						dfaHasState = true;
					}
				}
				if (setOfStateForSymb.toString() == "[]") {
					if (!dfaHasState) {
						d.addState("[]");
						workQueue.add(setOfStateForSymb);
					}
					d.addTransition(currentNode.toString(), symb, "[]");
				} else if (!dfaHasState) {
					boolean isFinal = false;
					for (NFAState ns : setOfStateForSymb) {
						if (ns.isFinal()) {
							isFinal = true;
						}
					}
					if (isFinal) {
						workQueue.add(setOfStateForSymb);
						d.addFinalState(setOfStateForSymb.toString());
					} else {
						workQueue.add(setOfStateForSymb);
						d.addState(setOfStateForSymb.toString());
					}
				}
				d.addTransition(currentNode.toString(), symb, setOfStateForSymb.toString());
			}
		}
		return d;
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
	 * Check if a state with such name already exists
	 * 
     * This method is taken from DFA class provided
	 * @param name
	 * @return null if no state exist, or DFAState object otherwise.
	 */
	private NFAState checkIfExists(String name) {
		NFAState ret = null;
		for (NFAState s : statesSet) {
			if (s.getName().equals(name)) {
				ret = s;
				break;
			}
		}
		return ret;
	}
	
    // /**
    //  * Checks if the state already exists.
    //  * Adds the state to the list if not already added.
    //  * 
    //  * @param x
    //  */
    // private void checkState(NFAState x){
    //     if(!states.contains(x)){
    //         states.add(x);
    //     }
    // }
    
}
