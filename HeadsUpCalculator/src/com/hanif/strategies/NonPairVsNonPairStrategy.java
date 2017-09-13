package com.hanif.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hanif.Evaluation;
import com.hanif.HandUtil;
import com.hanif.beans.Hand;
import com.hanif.service.HandEvaluationStrategy;

/**
 * This strategy does not use any statistics instead, uses a defined set of probabilities
 * depending upon card ranks. Note also that for now, suits are NOT being taken into account<p>
 * TODO: incorporate real statistical calculations
 * @author Hanif
 *
 */
public class NonPairVsNonPairStrategy implements HandEvaluationStrategy {

	private Integer firstCardValue;
	private Integer secondCardValue;
	private Integer thirdCardValue;
	private Integer fourthCardValue;

	private Map<Integer, Integer> cardsMap = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> treeMap;
	private List<Integer> sortedKeys;

	public NonPairVsNonPairStrategy(Hand firstHand, Hand secondHand) {
		this.firstCardValue = HandUtil.getFirstCardValueFromHand(firstHand);
		this.secondCardValue = HandUtil.getSecondCardValueFromHand(firstHand);
		this.thirdCardValue = HandUtil.getFirstCardValueFromHand(secondHand);
		this.fourthCardValue = HandUtil.getSecondCardValueFromHand(secondHand);
	}

	@Override
	public Evaluation evaluateHand() {
		Evaluation evaluation = new Evaluation();

		/**
		 * sort the ranks in order. The second card from the top is second
		 * highest. Also keep track of which hand the card belongs to
		 */
		this.cardsMap.put(this.firstCardValue, 1);
		this.cardsMap.put(this.secondCardValue, 2);
		this.cardsMap.put(this.thirdCardValue, 3);
		this.cardsMap.put(this.fourthCardValue, 4);

		/**
		 * Sort the map by placing it into a TreeMap which implicitly sorts the
		 * keys
		 */
		this.treeMap = new TreeMap<Integer, Integer>(cardsMap);

		this.sortedKeys = new ArrayList<Integer>(treeMap.keySet());

		/**
		 * Two high cards vs. Two undercards
		 */
		if (this.firstCardOfFirstHandIsHigherThanSecondHand() && this.secondCardOfFirstHandIsHigherThanSecondHand()) {
			evaluation.setFirstHandPercentage(65);
			evaluation.setSecondHandPercentage(35);
			evaluation.setResult(
					"The two higher cards are usually a 65% favourite to win, but it can vary depending on whether any of the cards are suited and/or connectors.");
			return evaluation;
		} else if (this.firstCardOfSecondHandIsHigherThanFirstHand()
				&& this.secondCardOfSecondHandIsHigherThanFirstHand()) {
			evaluation.setFirstHandPercentage(35);
			evaluation.setSecondHandPercentage(65);
			evaluation.setResult(
					"The two higher cards are usually a 65% favourite to win, but it can vary depending on whether any of the cards are suited and/or connectors.");
			return evaluation;
		}

		/**
		 * High card, low card vs. Two middle cards
		 */
		if ((this.firstCardOfFirstHandIsHigherThanSecondHand() && this.secondCardOfFirstHandIsLowerThanSecondHand())
				|| (this.firstCardOfFirstHandIsLowerThanSecondHand()
						&& this.secondCardOfFirstHandIsHigherThanSecondHand())) {
			evaluation.setFirstHandPercentage(57);
			evaluation.setSecondHandPercentage(43);
			evaluation.setResult(
					"In this match-up the high card gives it the edge. But it’s only a marginal winner, approximately 57% to the hand containing the high card.");
			return evaluation;
		} else if ((this.firstCardOfSecondHandIsHigherThanFirstHand()
				&& this.secondCardOfSecondHandIsLowerThanFirstHand())
				|| (this.firstCardOfSecondHandIsLowerThanFirstHand()
						&& this.secondCardOfSecondHandIsHigherThanFirstHand())) {
			evaluation.setFirstHandPercentage(43);
			evaluation.setSecondHandPercentage(57);
			evaluation.setResult(
					"In this match-up the high card gives it the edge. But it’s only a marginal winner, approximately 57% to the hand containing the high card.");
			return evaluation;
		}

		/**
		 * High card, middle card vs. Second highest, low card
		 */
		if (this.firstHandHasHighestCard() && this.firstHandHasMiddleCard()) {
			if (this.secondHandHasSecondHighestCard() && this.secondHandHasLowestCard()) {
				evaluation.setFirstHandPercentage(62);
				evaluation.setSecondHandPercentage(38);
				// evaluation.setResult("");
			}
		} else if (this.secondHandHasHighestCard() && this.secondHandHasMiddleCard()) {
			if (this.firstHandHasSecondHighestCard() && this.firstHandHasLowestCard()) {
				evaluation.setFirstHandPercentage(38);
				evaluation.setSecondHandPercentage(62);
			}
		}

		/**
		 * High card, same card vs. Same card, low card
		 */
		if (this.firstHandHasHighestCard() && this.OneCardOfFirstHandPairsWithOneCardOfSecondHand()) {
			if (this.secondHandHasLowestCard()) {
				evaluation.setFirstHandPercentage(73);
				evaluation.setSecondHandPercentage(27);
				evaluation.setResult(
						"In this case the higher hand is in a very strong position. If we discount any flush or straight possibilities, it only leaves the other player with three outs.");
				return evaluation;
			}
		} else if (this.secondHandHasHighestCard() && this.OneCardOfSecondHandPairsWithOneCardOfFirstHand()) {
			if (this.firstHandHasLowestCard()) {
				evaluation.setFirstHandPercentage(27);
				evaluation.setSecondHandPercentage(73);
				evaluation.setResult(
						"In this case the higher hand is in a very strong position. If we discount any flush or straight possibilities, it only leaves the other player with three outs.");
				return evaluation;
			}
		}

		/**
		 * High card, high kicker vs. same high card pair, low kicker
		 */
		if (this.firstHandHasHighestCardThatPairsWithOneCardOfSecondHand() && this.secondHandHasLowestCard()) {
			evaluation.setFirstHandPercentage(74);
			evaluation.setSecondHandPercentage(26);
			evaluation.setResult("The high kicker gives this hand a fairly big edge.");
			return evaluation;
		} else if (this.secondHandHasHighestCardThatPairsWithOneCardOfFirstHand() && this.firstHandHasLowestCard()) {
			evaluation.setFirstHandPercentage(26);
			evaluation.setSecondHandPercentage(74);
			evaluation.setResult("The high kicker gives this hand a fairly big edge.");
			return evaluation;
		}

		/**
		 * Same cards
		 */
		if (this.firstCardOfFirstHandEqualsFirstCardOfSecondHand()
				&& this.secondCardOfFirstHandEqualsSecondCardOfSecondHand()) {
			evaluation.setFirstHandPercentage(50);
			evaluation.setSecondHandPercentage(50);
			evaluation.setResult("Same hand, same percentage");
			return evaluation;
		} else if (this.firstCardOfFirstHandEqualsSecondCardOfSecondHand()
				&& this.secondCardOfFirstHandEqualsFirstCardOfSecondHand()) {
			evaluation.setFirstHandPercentage(50);
			evaluation.setSecondHandPercentage(50);
			evaluation.setResult("Same hand, same percentage");
			return evaluation;
		}

		return evaluation;
	}


	/**
	 * Helper methods
	 */
	/**
	 * Determine which hand has high card
	 *
	 * @return
	 */
	private boolean firstHandHasHighestCard() {
		if (this.firstCardOfFirstHandIsHigherThanSecondHand() || this.secondCardOfFirstHandIsHigherThanSecondHand()) {
			return true;
		}

		return false;
	}

	private boolean secondHandHasHighestCard() {
		if (this.firstCardOfSecondHandIsHigherThanFirstHand() || this.secondCardOfSecondHandIsHigherThanFirstHand()) {
			return true;
		}

		return false;
	}

	private boolean firstHandHasLowestCard() {
		if (this.firstCardOfFirstHandIsLowerThanSecondHand() || this.secondCardOfFirstHandIsLowerThanSecondHand()) {
			return true;
		}

		return false;
	}

	private boolean secondHandHasLowestCard() {
		if (this.firstCardOfSecondHandIsLowerThanFirstHand() || this.secondCardOfSecondHandIsLowerThanFirstHand()) {
			return true;
		}

		return false;
	}

	/**
	 * Determine which hand has middle card - where middle card is between both
	 * cards of other hand
	 *
	 * @return
	 */
	private boolean firstHandHasMiddleCard() {
		if (this.firstCardOfFirstHandIsBetweenSecondHand() || this.secondCardOfFirstHandIsBetweenSecondHand()) {
			return true;
		}

		return false;
	}

	private boolean secondHandHasMiddleCard() {
		if (this.firstCardOfSecondHandIsBetweenFirstHand() || this.secondCardOfSecondHandIsBetweenFirstHand()) {
			return true;
		}

		return false;
	}

	private boolean firstHandHasSecondHighestCard() {
		/**
		 * if the map contains less than 4 entries, then we don't have 4 unique
		 * cards so simply return false
		 */
		if (cardsMap.size() < 4) {
			return false;
		}

		Integer secondHighestCard = sortedKeys.get(2);

		if (cardsMap.get(secondHighestCard) == 1 || cardsMap.get(secondHighestCard) == 2) {
			return true;
		}
		return false;
	}

	private boolean secondHandHasSecondHighestCard() {
		/**
		 * if the map contains less than 4 entries, then we don't have 4 unique
		 * cards so simply return false
		 */
		if (cardsMap.size() < 4) {
			return false;
		}

		/**
		 * The second highest card will sit in the 3rd position since the array
		 * is sorted in ascending order
		 */
		Integer secondHighestCard = sortedKeys.get(2);

		if (cardsMap.get(secondHighestCard) == 3 || cardsMap.get(secondHighestCard) == 4) {
			return true;
		}
		return false;
	}

	private boolean firstCardOfSecondHandIsBetweenFirstHand() {
		if ((thirdCardValue > firstCardValue) && (thirdCardValue < secondCardValue)) {
			return true;
		}
		if ((thirdCardValue < firstCardValue) && (thirdCardValue > secondCardValue)) {
			return true;
		}
		return false;
	}

	private boolean secondCardOfSecondHandIsBetweenFirstHand() {
		if ((fourthCardValue > firstCardValue) && (fourthCardValue < secondCardValue)) {
			return true;
		}
		if ((fourthCardValue < firstCardValue) && (fourthCardValue > secondCardValue)) {
			return true;
		}
		return false;
	}

	private boolean firstCardOfFirstHandIsBetweenSecondHand() {
		if ((firstCardValue > thirdCardValue) && (firstCardValue < fourthCardValue)) {
			return true;
		}
		if ((firstCardValue < thirdCardValue) && (firstCardValue > fourthCardValue)) {
			return true;
		}
		return false;
	}

	private boolean secondCardOfFirstHandIsBetweenSecondHand() {
		if ((secondCardValue > thirdCardValue) && (secondCardValue < fourthCardValue)) {
			return true;
		}
		if ((secondCardValue < thirdCardValue) && (secondCardValue > fourthCardValue)) {
			return true;
		}
		return false;
	}

	private boolean OneCardOfFirstHandIsLowerThanSecondHand() {
		if ((firstCardValue < thirdCardValue) && (firstCardValue < fourthCardValue)) {
			if ((secondCardValue > thirdCardValue) && (secondCardValue > fourthCardValue)) {
				return true;
			}
		}
		if ((firstCardValue > thirdCardValue) && (firstCardValue > fourthCardValue)) {
			if ((secondCardValue < thirdCardValue) && (secondCardValue < fourthCardValue)) {
				return true;
			}
		}

		return false;
	}

	private boolean firstCardOfFirstHandIsHigherThanSecondHand() {
		if ((firstCardValue > thirdCardValue) && (firstCardValue > fourthCardValue)) {
			return true;
		}
		return false;
	}

	private boolean firstCardOfFirstHandIsLowerThanSecondHand() {
		if ((firstCardValue < thirdCardValue) && (firstCardValue < fourthCardValue)) {
			return true;
		}
		return false;
	}

	private boolean secondCardOfFirstHandIsHigherThanSecondHand() {
		if ((secondCardValue > thirdCardValue) && (secondCardValue > fourthCardValue)) {
			return true;
		}
		return false;
	}

	private boolean secondCardOfSecondHandIsHigherThanFirstHand() {
		if ((fourthCardValue > firstCardValue) && (fourthCardValue > secondCardValue)) {
			return true;
		}
		return false;
	}

	private boolean secondCardOfSecondHandIsLowerThanFirstHand() {
		if ((fourthCardValue < firstCardValue) && (fourthCardValue < secondCardValue)) {
			return true;
		}
		return false;
	}

	private boolean firstCardOfSecondHandIsHigherThanFirstHand() {
		if ((thirdCardValue > firstCardValue) && (thirdCardValue > secondCardValue)) {
			return true;
		}
		return false;
	}

	private boolean firstCardOfSecondHandIsLowerThanFirstHand() {
		if ((thirdCardValue < firstCardValue) && (thirdCardValue < secondCardValue)) {
			return true;
		}
		return false;
	}

	private boolean secondCardOfFirstHandIsLowerThanSecondHand() {
		if ((secondCardValue < thirdCardValue) && (secondCardValue < fourthCardValue)) {
			return true;
		}
		return false;
	}

	private boolean OneCardOfFirstHandPairsWithOneCardOfSecondHand() {
		if ((firstCardValue == thirdCardValue) || (firstCardValue == fourthCardValue)) {
			return true;
		}
		if ((secondCardValue == thirdCardValue) || (secondCardValue == fourthCardValue)) {
			return true;
		}
		return false;
	}

	private boolean OneCardOfSecondHandPairsWithOneCardOfFirstHand() {
		if ((thirdCardValue == firstCardValue) || (thirdCardValue == secondCardValue)) {
			return true;
		}
		if ((fourthCardValue == firstCardValue) || (fourthCardValue == secondCardValue)) {
			return true;
		}
		return false;
	}

	private boolean firstHandHasHighestCardThatPairsWithOneCardOfSecondHand() {
		if (firstCardValue == thirdCardValue) {
			if ((firstCardValue > secondCardValue) && (firstCardValue > fourthCardValue)) {
				return true;
			}
		}
		if (firstCardValue == fourthCardValue) {
			if ((firstCardValue > secondCardValue) && (firstCardValue > thirdCardValue)) {
				return true;
			}
		}
		if (secondCardValue == thirdCardValue) {
			if ((secondCardValue > firstCardValue) && (secondCardValue > fourthCardValue)) {
				return true;
			}
		}
		if (secondCardValue == fourthCardValue) {
			if ((secondCardValue > firstCardValue) && (secondCardValue > thirdCardValue)) {
				return true;
			}
		}

		return false;
	}

	private boolean secondHandHasHighestCardThatPairsWithOneCardOfFirstHand() {
		if (thirdCardValue == firstCardValue) {
			if ((thirdCardValue > fourthCardValue) && (thirdCardValue > secondCardValue)) {
				return true;
			}
		}
		if (thirdCardValue == secondCardValue) {
			if ((thirdCardValue > firstCardValue) && (thirdCardValue > fourthCardValue)) {
				return true;
			}
		}
		if (fourthCardValue == firstCardValue) {
			if ((fourthCardValue > secondCardValue) && (fourthCardValue > thirdCardValue)) {
				return true;
			}
		}
		if (fourthCardValue == secondCardValue) {
			if ((fourthCardValue > firstCardValue) && (fourthCardValue > thirdCardValue)) {
				return true;
			}
		}

		return false;
	}

	private boolean firstCardOfFirstHandEqualsFirstCardOfSecondHand() {
		if (firstCardValue == thirdCardValue) {
			return true;
		}
		return false;
	}

	private boolean firstCardOfFirstHandEqualsSecondCardOfSecondHand() {
		if (firstCardValue == fourthCardValue) {
			return true;
		}
		return false;
	}

	private boolean secondCardOfFirstHandEqualsFirstCardOfSecondHand() {
		if (secondCardValue == thirdCardValue) {
			return true;
		}
		return false;
	}

	private boolean secondCardOfFirstHandEqualsSecondCardOfSecondHand() {
		if (secondCardValue == fourthCardValue) {
			return true;
		}
		return false;
	}

}
