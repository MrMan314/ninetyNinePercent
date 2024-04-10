import java.util.ArrayList;
/**
 * This class models a deck of playing cards without jokers
 */
class Deck {
	private ArrayList<Card> cardsInDeck = new ArrayList<Card>(); //stores the cards in the deck in the form of PlayingCard objects
	
	/**
	  * Constructor that creates a new deck of cards using Card objects
	  * pre: none
	  * post: creates a new Deck with the 52 cards that would appear in a standard deck of cards
	  */
	public Deck(){
		for(int i = 1; i <= 13; i++){
			cardsInDeck.add(new PlayingCard(i, "spades"));
			cardsInDeck.add(new PlayingCard(i, "clubs"));
			cardsInDeck.add(new PlayingCard(i, "diamonds"));
			cardsInDeck.add(new PlayingCard(i, "hearts"));
		}
	}
	/**
	  * Method that shuffles the deck of cards
	  * pre: none
	  * post: the order of the cards in the deck is randomized
	  */
	public void shuffle(){
		ArrayList<PlayingCard> shuffledDeck = new ArrayList<PlayingCard>(); //this will store the shuffled deck
		for(int i = 0; i < cardsInDeck.size(); i++){ //loops through each card in the original deck
			int randomIndex = (int)(Math.random()*cardsInDeck.size()); //chooses a random index to retrive a card from
			shuffledDeck.add(cardsInDeck.remove(randomIndex)); //add the randomly chosen card to the shuffled deck while removing the card from the original deck
		}
		cardsInDeck = shuffledDeck; //updates the card deck to the shuffled deck
	}
	/**
	  * Method that draws a card from the deck
	  * pre: none
	  * post: removes the top card from the deck and returns it
	  */
	public Card draw(){
		return cardsInDeck.remove(0); //returns the card at the top of the deck while removing the card 
	}
	
}
