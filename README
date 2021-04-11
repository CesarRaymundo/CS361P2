# Project 2: NFA Machine

* Author(s): Cesar Raymundo(Section 003) & Andrew Gerber(Section 001)
* Class: CS361 
* Semester: Spring 2021

## Overview

This program will take a given input file that contains information related to a regular NFA. Information 
included are the states, transitions, and alphabet. Once the file is passed, it will then build the NFA given the start final and other states then using the input strings to determine if the strings are valid or not.

## Compiling and Using

To compile this program, from the top most directory type:
	javac fa/nfa/NFADriver.java

To run this program, type one of the following:
	java fa.nfa.NFADriver ./tests/p2tc0.txt
	java fa.nfa.NFADriver ./tests/p2tc1.txt
	java fa.nfa.NFADriver ./tests/p2tc2.txt
	java fa.nfa.NFADriver ./tests/p2tc3.txt

## Discussion

This project was a rough one. We started by creating the two needed java classes and implementing the needed stated methods as skeletons so we could see what we needed to do. We felt good with most of the basic methods as they were close to the DFA methods we ran into big trouble with eclosure and the toDFA(). We understood that in the toDFA we needed to recursively build transitions to many different states while allowing them to not replace each other. We started doing this with a stack as a “work” queue and building in the transitions. After we wrote our toDFA and closure we got the program to run, however we found it was only printing the start state. After hours of debugging and chasing bugs we realized we need to rewrite our toDFA. We reached out to many peers to ask for their help and they graciously led us down the right path with logical pseudo code of how they did it. We also researched hot to build a dfa from an nfs online and used our findings as references. We were able to get the toDFA closure working after help from classmates and the program passes all given tests.

## Testing

The way we tested this program was by using the provided test files to make sure that were getting 
the expected output. We probably could've made our own test for certain methods but found the test 
files to be sufficient to find bugs within our code. After we were at a runable place in our code we would run it after fixing a bug to see if it worked

## Extra Credit

No extra credit in this project.

## Sources used

https://www.cs.odu.edu/~zeil/cs390/latest/Public/nfa-jflap/index.html
https://www.geeksforgeeks.org/c-program-to-simulate-nondeterministic-finite-automata-nfa/
Classmate advice
