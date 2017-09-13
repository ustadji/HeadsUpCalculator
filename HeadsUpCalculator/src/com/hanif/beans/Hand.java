package com.hanif.beans;

/**
 * Java Bean representing a hand which contains 2 {@link Card} objects, representing the first and second card
 * @author Hanif
 *
 */
public class Hand {
	private Card firstCard;
	private Card secondCard;

	public Hand(Card firstCard, Card secondCard) {
		this.firstCard = firstCard;
		this.secondCard = secondCard;
	}

	public Card getFirstCard() {
		return firstCard;
	}

	public void setFirstCard(Card firstCard) {
		this.firstCard = firstCard;
	}

	public Card getSecondCard() {
		return secondCard;
	}

	public void setSecondCard(Card secondCard) {
		this.secondCard = secondCard;
	}

}
