package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import base.Card;
import base.Hand;

/**
 * A container that represents the Dealer. It has a hand (dealer's hand), the
 * minimum bet he will accept, and the card images file. Similar to PlayerPanel.
 * 
 * @author Vance Zuo
 */
public class DealerPanel extends JPanel {

	private Hand hand;
	private int minBet;
	private Image cardImgs;

	private JLabel minBetDisp;

	/**
	 * Initializes a JPanel name "dealer" displaying the minimum wager. Card
	 * graphics are dealt with automatically by paintComponent(g).
	 * 
	 * @param minimumBet smallest money wager allowed
	 * @param cardImages card image map file
	 */
	public DealerPanel(int minimumBet, Image cardImages) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // vertical layout
		setPreferredSize(new Dimension(100, 320));
		setOpaque(false);
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.DARK_GRAY), "Dealer"));

		hand = null;
		minBet = minimumBet;
		cardImgs = cardImages;
		minBetDisp = new JLabel("<HTML><font color=#EDDA74>Minimum Bet: "
				+ "<u>$" + minBet + "</u></font></HTML>");
		minBetDisp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		add(minBetDisp);
	}

	/**
	 * Returns the hand of the dealer, probably for comaparison purposes.
	 * 
	 * @return dealers hand
	 */
	public Hand getHand() {
		return hand;
	}

	/**
	 * Clears the hand and returns it in an ArrayList
	 * 
	 * @return ArrayList containing the cleared hand
	 */
	public ArrayList<Card> clearHand() {
		return hand.clearHand();
	}

	/**
	 * Creates a hand with Card c1 and Card c2
	 * 
	 * @param c1 First card to be added
	 * @param c2 Second card to be added
	 */
	public void startHand(Card c1, Card c2) {
		hand = new Hand(c1, c2);
	}

	/**
	 * Flips the second card in the hand
	 */
	public void flipSecond() {
		hand.get(1).flip();
		repaint();
	}

	/**
	 * Checks if there's an ace in the dealer's hand
	 * 
	 * @return Boolean representing whether an ace is present
	 */
	public boolean checkAce() {
		return hand.get(0).getFace() == Card.ACE;
	}

	/**
	 * Paints the cards stacked top-down in addition to the rest of the
	 * components. The cards are arranged so the user can still see all of the
	 * cards' values.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (hand == null)
			return;
		for (int i = 0; i < hand.length(); i++) {
			drawCard(g, hand.get(i), 10, 80 + 33 * i);
		}
	}

	/**
	 * Paints a card image onto (x,y) of the container. A facedown card will be
	 * drawn accordingly.
	 * 
	 * @param g the graphics context
	 * @param card the card to be printed
	 * @param x the x-position of the printed card in this container
	 * @param y the y-position of the printed card in this container
	 */
	// Based on http://math.hws.edu/eck/cs124/f11/lab11/cards/PokerCard.java
	private void drawCard(Graphics g, Card card, int x, int y) {
		int cx; // top-left x in cards.png
		int cy; // top-left y in cards.png
		final int CARD_WIDTH = 79; // pixel width of one card image
		final int CARD_HEIGHT = 123; // pixel height of one card image
		if (!card.isFaceUp()) {
			cx = 2 * CARD_WIDTH;
			cy = 4 * CARD_HEIGHT;
		} else {
			cx = card.getFace() * CARD_WIDTH;
			switch (card.getSuit()) {
			case Card.DIAMONDS:
				cy = 1 * CARD_HEIGHT;
				break;
			case Card.CLUBS:
				cy = 0 * CARD_HEIGHT;
				break;
			case Card.HEARTS:
				cy = 2 * CARD_HEIGHT;
				break;
			default:
				cy = 3 * CARD_HEIGHT;
				break; // Spades
			}
		}
		// Copies 79x123 box from cards.png to GUI
		g.drawImage(cardImgs, x, y, x + CARD_WIDTH, y + CARD_HEIGHT, cx, cy, cx
				+ CARD_WIDTH, cy + CARD_HEIGHT, this);
	}
}
