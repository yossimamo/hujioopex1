package oop.ex4.yourplayername_contest;
//Move this code to package with actual yourplayername


import oop.ex4.crosswords.Crossword;
import oop.ex4.crosswords.CrosswordDictionary;
import oop.ex4.crosswords.CrosswordShape;

/**
 * The basic scoring policy is as follows:
 * for each round programs will be sorted according to their quality
 * fastest program will receive 50 points, 2nd place - 49 points,....
 * In case of more than 40 participants, the score for first program
 * per round will start from 100.
 * If several programs returned the same quality, they will be given the same score
 * On some problems where reaching the ultimate goal is possible,
 * the score will be given according to fastest completion time
 * Among them some rounds are very easy and some are very hard
 * There will be 50 rounds all of equal weight
 * 
 * IMPORTANT notes:
 * 	    1) Use Refactor tools to put your program in a single oop.ex4.[yourplayername_contest] package
 *      2) Try avoid using one-time initialization and relying on static variables, 
 *      since for a single creation of your class, the player can be executed many times
 *      3) DO NOT assume that for createCrossword/setup you will be given
 *      your CrosswordShape/CrosswordDictionary implementations! Assume only
 *      CrosswordShape/CrosswordDictionary interface 
 *      If you re-implemented shape and/or dictionary, just copy the supplied
 *      shape and dictionary into your implementations inside createCrossword/setup methods
 *      
 *      The basic way to add your Ex4 to tournament is:
 *      1) Move all to the same package
 *      2) Use code connection lines from CrosswordViewer to 
 *      implement setup & createCrossword
 *      3) Implement getPlayerName and getUserName(s)
 *      4) Check that your program follows tournament rules and satisfy the
 *      requirements of this interface
 *      Obviously you can and encouraged to upgrade your tournament code
 *      in every possible and impossible way.  
 *      Good Luck!
 * @author Dima
 *
 */
public interface CrosswordSolverPlayer {
	/**
	 * Should return a name of player.
	 * It should be some COOL and unique player name which looks good
	 * on Tournament table.
	 * Name should be without special characters and between 3 to 15 letters long 
	 */
	String getPlayerName();
	/**
	 * This function should perform all necessary setup and loading
	 * dictionary. Time of execution of this function will not be 
	 * included in tournament time. However, it should take less than
	 * 20 minutes on CS computers in worst case on med_wndict crossword
	 * 
	 * There will be only a single call to setup per Player object.
	 * Note that after a single call to setup, several calls can be
	 * made to createCrossword() method 
	 * 
	 * @param dict The dictionary
	 */
	void setup(CrosswordDictionary dict);
	

	/**
	 * This is the main tournament function. The score of players on each round
	 * will be calculated based on quality/correctness/real time/ used by this function.
	 * If function throws exception or creates invalid crossword or runs more 
	 * than 1.2 x timeout it considered to be a failure for bonuses and it receives 
	 * 0 rank in that round. It also considered to be failure if it returns 
	 * a trivial crossword (less then 0.1 of the best returned crossword quality)
	 * 
	 * On tasks with complete solution, the score for program which returned partial solution
	 * will be WorstScoreForCompleteSolution*PartialityOfSolution 
	 * 
	 * @param shape - Crossword shape
	 * @param timeout - time in milliseconds
	 * @return the best solution found so far 
	 * 
	 */	
	Crossword createCrossword(CrosswordShape shape, long timeout);
	
	/**
	 * Returns cs username of first student (for giving bonuses)
	 * @return the name of first user
	 */
	String getFirstUserName();
	
	/**
	 * Returns cs username of second student (for giving bonuses)
	 * Returns null if done alone.
	 * @return the name of second user
	 */
	String getSecondUserName();
}
