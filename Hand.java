import java.util.ArrayList;
/**
 * This class models a player's hand of cards
 */
class Hand {
	private ArrayList<Card> playerHand = new ArrayList<Card>(); //this arraylist will store the Card objects currently held in hand
	private int fingerCount = 6;
	/**
	 * Constructor that creates a new empty player hand
	 * pre: none
	 * post: creates a new empty player hand 
	 */
	public Hand(){
	}
	/**
	 * Method that adds a Card to the hand
	 * pre: none
	 * post: adds the Card to the hand
	 */
	public void addCard(Card card){
		playerHand.add(card); //adds the card to the arraylist that stores the cards in hand
	}
	/**
	 * Method that removes a Card from the hand
	 * pre: none
	 * post: removes the Card from the hand
	 */
	public void removeCard(Card card){
		playerHand.remove(card);
	}
	/**
	 * This method returns how many cards are in the hand
	 * pre: none
	 * post: returns the number of cards in the hand
	 */
	public int size(){
		return playerHand.size(); //check the size of the arraylist and return it
	}
	/**
	 * This method returns a list of the full name of every card in hand
	 * pre: none
	 * post: returns a String array with the full names of every card in hand
	 */
	public String[] listCards(){
		String[] allCardNames = new String[playerHand.size()]; //this will store the name of each card
		for(int i = 0; i < playerHand.size(); i++){ //iterate through each card
			allCardNames[i] = playerHand.get(i).getCardName(); //add the card name to the array
		}
		return allCardNames;
	}

	/**
	 * This method returns the number of fingers on the player's hand
	 * pre: none
	 * post: returns an integer containing the number of fingers
	 */

	public int getFingerCount() {
		return fingerCount;
	} 
}
