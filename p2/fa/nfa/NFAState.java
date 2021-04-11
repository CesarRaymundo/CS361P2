package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements a NFAAState, which holds each transition for a state,
 * as well as each NFAState's name.
 * 
 * @author Andrew Gerber & Cesar Raymundo
 *
 */
public class NFAState extends fa.State {

    // private variables
    private boolean finalBool;// to hold if final

    // Map of transitions on a character fir the given state
    private HashMap<Character, HashSet<NFAState>> transitionMap;// based on fa.State and DFAState

    /**
     * Constructor
     * 
     * @param inputName - name of the state
     */
    public NFAState(String inputName) {
        this.name = inputName;
        transitionMap = new HashMap<Character, HashSet<NFAState>>();
        finalBool = false;
    }

    /**
     * Contrsuctor
     *
     * @param inputName - name of state
     * @param bool      - if needed to maike a state that you know is final rather
     *                  than change to final
     */
    public NFAState(String inputname, boolean bool) {
        this.name = inputname;
        transitionMap = new HashMap<Character, HashSet<NFAState>>();
        finalBool = bool;
    }

    public boolean isFinal() {
        return this.finalBool;
    }

    /**
     * Method to add a transition to the NFA
     * 
     * @param onSymb  - symbol need to make transition to toState
     * @param toState - state that will be transitioned to
     */
    // BASED ON DFA
    public void addTransition(char onSymb, NFAState toState) {
        if (transitionMap.containsKey(onSymb) == false) {
            HashSet<NFAState> temp = new HashSet<NFAState>();
            transitionMap.put(onSymb, temp);
        }
        transitionMap.get(onSymb).add(toState);

    }

    public String getNameNFA() {
        return this.name;
    }

    /**
     * Returns the hashmap containing the transitions.
     * 
     * @return
     */
    public Set<NFAState> getTrans(char onSymb) {
        return transitionMap.get(onSymb);
    }

    /**
     * Retrieves the current state that transitions to on the given symbol
     * 
     * @param symb - the alphabet symbol
     * @return the new state
     */
    public Set<NFAState> getTransition(char onSymb) {
        Set<NFAState> tmp = transitionMap.get(onSymb);
        if (tmp == null) {
            return new HashSet<NFAState>();
        }

        return transitionMap.get(onSymb);
    }

}
