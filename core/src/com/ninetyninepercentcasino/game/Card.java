import com.badlogic.gdx.graphics.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
/**
 * This class models a playing card
 */
class Card {

	private int suit; //this will store the suit of the card as a string
	private int numberValue; //this will store the number value of the card (Ex. 11 is a jack)
	private final static String[] cardNames = {"ace", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king"}; //cardNames[i-1] stores the word name of the card with number value i
	private final static String[] cardSuits = {"diamonds", "spades", "clubs", "hearts"};
	/**
	 * Constructor that creates a new card with a given suit and number
	 * pre: suit is "diamonds", "clubs", "hearts", or "spades"
	 * post: creates a new card object assigned to the given numberValue and suit
	 */
	public Card(int numberValue, int suit) {
		this.suit = suit;
		this.numberValue = numberValue;
	}
	/**
	 * Accessor method that returns the number value of a card
	 * pre: none
	 * post: returns the number value of the card
	 */
	public int getValue() {
		return numberValue;
	}
	/**
	 * Accessor method that returns the suit of a card
	 * pre: none
	 * post: returns the suit of the card as a string
	 */
	public int getSuit() {
		return suit;
	} 
	/**
	 * Accessor method that returns the full name of the card as a word
	 * pre: none
	 * post: returns the full name of the card, including suit and number value
	 */
	public String getCardName() {
		return String.format("%s of %s", cardNames[numberValue - 1], cardSuits[suit]);
	}
}
