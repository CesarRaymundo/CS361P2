package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
//import java.util.LinkedHashSet;

//@author andrew gerber

public class NFAState extends fa.State {


    //private vcariables
    private boolean finalBool;//to hold if final

    //Map of transitions on a character fir the given state
    private HashMap<Character , HashSet<NFAState>> transitionMap;//based on fa.State and DFAState



/*Contrsuctor
* input: string name
*/
    public NFAState(String inputName){
        this.name = inputName;
        transitionMap = new HashMap<Character, HashSet<NFAState>>();
        finalBool = false;
    }
    /*Contrsuctor
    * input: string name
    * input boolean
    * Use: if needed to maike a state that you know is final rather than change to final
    */
    public NFAState(String inputname, boolean bool){
        this.name = inputname;
        transitionMap = new HashMap<Character, HashSet<NFAState>>();
        finalBool = bool;
    }

    public boolean isFinal(){
        return this.finalBool;
    }
    /**
	 * Method to add a transition to the DFA
	 * @param onSymb - symbol need to make transition to toState
	 * @param toState - state that will be transitioned to
	 */
    //BASED ON DFA
    public void addTransition(char onSymb, NFAState toState){
       if(!transitionMap.containsKey(onSymb)){
           transitionMap.put(onSymb, new HashSet<NFAState>());
       }

       transitionMap.get(onSymb).add(toState);
        
    }
    /**
	 * Method to add a transition to the DFA
     *  IF there the transiton doesnt exist make transition else make branch transiton
	 * @param onSymb - symbol need to make transition to toState
	 * @param toState - state that will be transitioned to
	 */
    
    // public void addTransition(char onSymb, NFAState toState){
        
    //     if(transitionMap.get(onSymb) == null){//conditon for it to be empty
    //         //make single hashset
    //         LinkedHashSet<NFAState> firstTrans = new LinkedHashSet<NFAState>();
            
    //         firstTrans.add(toState);
    //         transitionMap.put(onSymb, firstTrans);
    //     }
    //     else{//conditon for it to not empty
    //         //make branch linked hash
    //         LinkedHashSet<NFAState> multiTrans = new LinkedHashSet<NFAState>();
    //         multiTrans.add(toState);
    //         transitionMap.put(onSymb, multiTrans);
    //     }
    // }
    public String getNameNFA(){
        return this.name;
    }
    //returns transtion from current state given on symbol
    public HashMap<Character , HashSet<NFAState>> getTrans(){
        return transitionMap;
    }
    //returns transtion from current state given on symbol
    public Set<NFAState> getTransition(char onSymb){
        Set<NFAState> tmp = transitionMap.get(onSymb);
        if(tmp == null){
            return new HashSet<NFAState>();
        }

        return transitionMap.get(onSymb);
    }

    
}