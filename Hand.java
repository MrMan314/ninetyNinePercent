import java.util.ArrayList;
/**
 * This class models a player's hand of cards
 */
class Hand {
	private ArrayList<Card> playerHand = new ArrayList<Card>(); //this arraylist will store the Card objects currently held in hand
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
	  * This method sums the total value of all the cards in the hand for blackjack, where aces can be 1's or 11's, and returns the highest possible sum under 22
	  * pre: none
	  * post: returns the highest possible sum of all the card values in the hand under 22
	  */
	public int sumOfHandBlackjack(){
		int sum = 0; //this will store the sum of all the card values
		boolean containsAce = false; //will store if the hand contains an ace or not
		for(Card card : playerHand){ //iterates through each Card in playerHand
			if(card.getValue() > 10) sum += 10; //adds 10 if the card is a face card
			else sum += card.getValue(); //adds the number value of the card to the sum
			if(card.getValue() == 1) containsAce = true; //updates containsAce if the Card is 1
		}
		if(containsAce && sum <= 11) sum += 10; //11-1 = 10, so add ten if it won't bust the hand and there is an ace in the hand. There cannot be two aces that are 11 in a hand, so this operation is only performed at most once
		return sum; 
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
}
