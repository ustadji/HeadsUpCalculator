package com.hanif;

import com.hanif.beans.Card;
import com.hanif.beans.Hand;

/**
 * Utility methods for Card/Hands
 * @author Hanif
 *
 */
public class HandUtil {

	public static boolean isHandAPair(Hand hand) {
		Card firstCard = hand.getFirstCard();
		Card secondCard = hand.getSecondCard();
		Integer firstCardValue = getCardValueAsInt(firstCard.getValue());
		Integer secondCardValue = getCardValueAsInt(secondCard.getValue());
		if (firstCardValue == secondCardValue) {
			return true;
		}
		return false;
	}

	private static int getCardValueAsInt(String card) {
		switch (card) {
		case "A":
			return 14;
		case "K":
			return 13;
		case "Q":
			return 12;
		case "J":
			return 11;
		default:
			return Integer.parseInt(card);
		}
	}

	public static int getValueFromCard(Card card) {
		return getCardValueAsInt(card.getValue());
	}

	public static int getFirstCardValueFromHand(Hand hand) {
		Card firstCard = hand.getFirstCard();
		return getValueFromCard(firstCard);
	}

	public static int getSecondCardValueFromHand(Hand hand) {
		Card secondCard = hand.getSecondCard();
		return getValueFromCard(secondCard);
	}

	public static String getFirstCardSuitFromHand(Hand hand) {
		Card firstCard = hand.getFirstCard();
		return firstCard.getSuit();
	}

	public static String getSecondCardSuitFromHand(Hand hand) {
		Card secondCard = hand.getSecondCard();
		return secondCard.getSuit();
	}
}
