package fa.nfa;

import java.util.LinkedHashSet;

public class NFAState extends fa.State {

    private boolean isFinal;

/*Contrsuctor
* input: string name
*/
    public NFAState(String inputName){
    }
/*Contrsuctor
* input: string name
* input boolean
*/
    public NFAState(String inputname, boolean bool){

    }

    public boolean isFinal(){
        return this.isFinal();
    }

    public void addTransition(char onSymb, LinkedHashSet<NFAState> destination){

    }

    public void addTransition(char onSymb, NFAState destination){

    }

    public LinkedHashSet<NFAState> getTo(char onSymb){

    }

    public boolean hasTransition(char c){

    }

    public Set<NFAState> getTransition(char c){
        
    }
}