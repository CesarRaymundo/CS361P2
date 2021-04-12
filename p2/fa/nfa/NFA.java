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
	/**
	 * adds a starting state if it does not already exisit
	 */
	@Override
	public void addStartState(String name) {
		NFAState s = checkIfExists(name);
		if (s == null) {
			s = new NFAState(name);
			statesSet.add(s);
		} else {
			System.out.println(name + "Do already be a thing doe");
		}
		startState = s;

	}
	/**
	 * adds a state if it does not already exisit
	 */
	@Override
	public void addState(String name) {
		NFAState s = checkIfExists(name);
		if (s == null) {
			s = new NFAState(name);
			statesSet.add(s);
		} else {
			System.out.println(name + "Do already be a thing doe");
		}

	}
	/**
	 * adds a final state if it does not already exisit
	 */
	@Override
	public void addFinalState(String name) {
		NFAState f = checkIfExists(name);
		if (f == null) {
			f = new NFAState(name, true);
			finalStates.add(f);
			statesSet.add(f);
		} else {
			System.out.println(name + "Do already be a thing doe");
		}
	}
	/**
	 * add a transiiton fromt he from state to the to state with the onsymb
	 * based on dfa
	 */
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
	/**
	 * returns all the states used in the nfa
	 */
	@Override
	public Set<? extends State> getStates() {
		return statesSet;
	}
	/**
	 * returns the final states as a set
	 */
	@Override
	public Set<? extends State> getFinalStates() {
		return finalStates;
	}
	/**
	 * returns the the starting state
	 * based on dfa
	 */
	@Override
	public State getStartState() {
		return startState;
	}

	@Override
	/**
	 * returns the alphabet used in the nfa
	 * based on dfa
	 */
	public Set<Character> getABC() {
		return sigma;
	}

	/**
	 * 
	 * @return equivalent DFA
	 */
	@Override
	public DFA getDFA() {
		
		Queue<Set<NFAState>> stack = new LinkedList<Set<NFAState>>();
		DFA convertedNFA = new DFA(); 
		stack.add(eClosure(startState));
		while (!stack.isEmpty()) {
			Set<NFAState> currentState = stack.poll();
			boolean isFinalState = false;
			for (NFAState tmp : currentState) {
				if (tmp.isFinal()) {
					isFinalState = true;
				}}
			if (convertedNFA.getStartState() == null && !isFinalState) {
				convertedNFA.addStartState(currentState.toString());
			} else if (convertedNFA.getStartState() == null && isFinalState) {
				convertedNFA.addFinalState(currentState.toString());
				convertedNFA.addStartState(currentState.toString());
			}
			for (Character onSymb : getABC()) {
				Set<NFAState> toSymbolds = new HashSet<NFAState>();
				for (NFAState node : currentState) {
					if (node.getTransition(onSymb) != null) {
						for (NFAState tmp : node.getTransition(onSymb)) {
							toSymbolds.addAll(eClosure(tmp));
						}}}
				boolean dfaHasStatebool = false;
				for (State states : convertedNFA.getStates()) {
					if (states.getName().equals(toSymbolds.toString())) {
						dfaHasStatebool = true;
					}}
				if (toSymbolds.toString() == "[]") {
					if (!dfaHasStatebool) {
						convertedNFA.addState("[]");
						stack.add(toSymbolds);
					}
					convertedNFA.addTransition(currentState.toString(), onSymb, "[]");
				} else if (!dfaHasStatebool) {
					boolean ifFinal = false;
					for (NFAState testFinal : toSymbolds) {
						if (testFinal.isFinal()) {
							ifFinal = true;
						}}
					if (ifFinal) {
						stack.add(toSymbolds);
						convertedNFA.addFinalState(toSymbolds.toString());
					} else {
						stack.add(toSymbolds);
						convertedNFA.addState(toSymbolds.toString());
					}}
			convertedNFA.addTransition(currentState.toString(), onSymb, toSymbolds.toString());
			}}
		return convertedNFA;
	}
	/**
	 * Return delta entries
	 * @param from - the source state
	 * @param onSymb - the label of the transition
	 * @return a set of sink states
	 */
	@Override
	public Set<NFAState> getToState(NFAState from, char onSymb) {
		return from.getTransition(onSymb);
	}
	/**
	 * Traverses all epsilon transitions and determine
	 * what states can be reached from s through e
	 * @param s
	 * @return set of states that can be reached from s on epsilon trans.
	 */
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
	 * @return null if no state exist, or NFAState object otherwise.
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
