package fa.nfa;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface {

	private LinkedHashSet<NFAState> finalStates; // holds all final states
	private NFAState startState; // starting state
	private LinkedHashSet<NFAState> statesSet; // holds all the states of the NFA
	private HashSet<Character> sigma; // includes all letters used
	
	

	public NFA() {
		finalStates = new LinkedHashSet<>();
		statesSet = new LinkedHashSet<>();
		sigma = new HashSet<>();
	}

	@Override
	public void addStartState(String name) {
		NFAState s = checkIfExists(name);
		if (s == null) {
			s = new NFAState(name);
			statesSet.add(s);
		} else {
			System.out.println("Warning: A state with name" + name + "already exists");
		}
		startState = s;

	}

	@Override
	public void addState(String name) {
		NFAState s = checkIfExists(name);
		if (s == null) {
			s = new NFAState(name);
			statesSet.add(s);
		} else {
			System.out.println("Warning: A state with name" + name + "already exists");
		}

	}

	@Override
	public void addFinalState(String name) {
		NFAState f = checkIfExists(name);
		if (f == null) {
			f = new NFAState(name, true);
			finalStates.add(f);
			statesSet.add(f);
		} else {
			System.out.println("Warning: A state with name" + name + "already exists");
		}
	}

	@Override
	public void addTransition(String fromState, char onSymb, String toState) {
		NFAState fromS = checkIfExists(fromState);
		NFAState toS = checkIfExists(toState);
		if (fromS == null || toS == null) {
			System.exit(2);
		}
		fromS.addTransition(onSymb, toS);
		if (!sigma.contains(onSymb) && onSymb != 'e') {
			sigma.add(onSymb);
		}

	}

	@Override
	public Set<? extends State> getStates() {
		return statesSet;
	}

	@Override
	public Set<? extends State> getFinalStates() {
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
					if (v.getTransition(symb) != null) {
						for (NFAState t : v.getTransition(symb)) {
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
		return from.getTransition(onSymb);
	}

	@Override
	public Set<NFAState> eClosure(NFAState s) {
		//Creating new set for empty transitions
		HashSet<NFAState> eStatesSet = new HashSet<>();
		return DFS(s, eStatesSet);
	}

	/**
	 * Depth- first search algorithm that adds the epsilion enclosure
	 * to eStatesSet
	 * 
	 * @param s - the state with epsilion enClosure
	 * @param list - contains the list with the states
	 * @return the states with episilon enClosure
	 */
	private HashSet<NFAState> DFS(NFAState s, HashSet<NFAState> list) {
		//Adding the state to a list
		list.add(s);

		Set<NFAState> eStatesSet = s.getTransition('e');
		if (eStatesSet != null) {
			// iterate through out eStatesSet
			for (NFAState itEstates : eStatesSet) {
				// making sure the state is not already in the list
				if (!(list.contains(itEstates)))
					// calling recursively to process all the states
					DFS(itEstates, list);
			}
		}
		return list;
	}

	/**
	 * Check if a state with such name already exists
	 * 
	 * This method is taken from DFA class provided
	 * 
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

}
