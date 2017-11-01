package com.hanif.strategies;

import com.hanif.Evaluation;
import com.hanif.HandUtil;
import com.hanif.beans.Hand;
import com.hanif.service.HandEvaluationStrategy;

/**
 * This strategy does not use any statistics instead, uses a defined set of probabilities
 * depending upon card ranks. <p>
 * TODO: incorporate real statistical calculations
 * @author Hanif
 *
 */
public class PairVsNonPairStrategy implements HandEvaluationStrategy {

	private Integer firstCardValue, secondCardValue, thirdCardValue, fourthCardValue;
	private String firstCardSuit, secondCardSuit, thirdCardSuit, fourthCardSuit;

	public PairVsNonPairStrategy(Hand firstHand, Hand secondHand) {
		this.firstCardValue = HandUtil.getFirstCardValueFromHand(firstHand);
		this.firstCardSuit = HandUtil.getFirstCardSuitFromHand(firstHand);

		this.secondCardValue = HandUtil.getSecondCardValueFromHand(firstHand);
		this.secondCardSuit = HandUtil.getSecondCardSuitFromHand(firstHand);

		this.thirdCardValue = HandUtil.getFirstCardValueFromHand(secondHand);
		this.thirdCardSuit = HandUtil.getFirstCardSuitFromHand(secondHand);

		this.fourthCardValue = HandUtil.getSecondCardValueFromHand(secondHand);
		this.fourthCardSuit = HandUtil.getSecondCardSuitFromHand(secondHand);
	}

	@Override
	public Evaluation evaluateHand() {
		Evaluation evaluation = new Evaluation();
		Boolean pairIsFirstHand = false;
		Boolean nonPairIsSuited = false;
		Integer pairRank;
		Integer nonPairRankCard1;
		Integer nonPairRankCard2;
		if (firstCardValue == secondCardValue) {
			pairIsFirstHand = true;
			pairRank = firstCardValue;
			nonPairRankCard1 = thirdCardValue;
			nonPairRankCard2 = fourthCardValue;
		} else {
			pairRank = thirdCardValue;
			nonPairRankCard1 = firstCardValue;
			nonPairRankCard2 = secondCardValue;
		}

		if (pairIsFirstHand) {
			if (this.thirdCardSuit.equals(this.fourthCardSuit)) {
				nonPairIsSuited = true;
			}
		} else {
			if (this.firstCardSuit.equals(this.secondCardSuit)) {
				nonPairIsSuited = true;
			}
		}

		/**
		 * Pair vs Overcards
		 */
		if ((nonPairRankCard1 > pairRank) && (nonPairRankCard2 > pairRank)) {
			if (pairIsFirstHand) {
				if (nonPairIsSuited) {
					evaluation.setFirstHandPercentage(51);
					evaluation.setSecondHandPercentage(49);
					evaluation.setResult("Here is the real coin flip situation. A fifty-fifty proposition. The higher suited cards would have an edge against a lower pair.");
				} else {
					evaluation.setFirstHandPercentage(55);
					evaluation.setSecondHandPercentage(45);
					evaluation.setResult("This is the classic coin flip hand that you'll see many times late in tournaments with one player being all-in. The term coin flip indicates an even money situation which is really a 55 to 45 percent situation, as the pair is a slight favourite.");
				}
			} else {
				if (nonPairIsSuited) {
					evaluation.setFirstHandPercentage(49);
					evaluation.setSecondHandPercentage(51);
					evaluation.setResult("Here is the real coin flip situation. A fifty-fifty proposition. The higher suited cards would have an edge against a lower pair.");
				} else {
					evaluation.setFirstHandPercentage(45);
					evaluation.setSecondHandPercentage(55);
					evaluation.setResult("This is the classic coin flip hand that you'll see many times late in tournaments with one player being all-in. The term coin flip indicates an even money situation which is really a 55 to 45 percent situation, as the pair is a slight favourite.");
				}
			}

		/**
		 * Pair vs Undercards
		 */
		} else if ((nonPairRankCard1 < pairRank) && (nonPairRankCard2 < pairRank)) {
			if (pairIsFirstHand) {
				if (nonPairIsSuited){
					evaluation.setFirstHandPercentage(78);
					evaluation.setSecondHandPercentage(22);
					evaluation.setResult("You see this match-up late in tournaments when a player is getting desperate and pushes all-in with suited cards. The pair is a strong favourite to win.");
				} else {
					evaluation.setFirstHandPercentage(85);
					evaluation.setSecondHandPercentage(15);
					evaluation.setResult("In this situation the pair is normally about a 5-to-1 favourite and can vary depending on whether the two undercards are suited and/or connectors.");
				}
			} else {
				if (nonPairIsSuited) {
					evaluation.setFirstHandPercentage(22);
					evaluation.setSecondHandPercentage(78);
					evaluation.setResult("You see this match-up late in tournaments when a player is getting desperate and pushes all-in with suited cards. The pair is a strong favourite to win.");
				} else {
					evaluation.setFirstHandPercentage(15);
					evaluation.setSecondHandPercentage(85);
					evaluation.setResult("In this situation the pair is normally about a 5-to-1 favourite and can vary depending on whether the two undercards are suited and/or connectors.");
				}

			}

		/**
		 * Pair vs (Overcard and an undercard)
		 */
		} else if (((nonPairRankCard1 < pairRank) && (nonPairRankCard2 > pairRank)) || ((nonPairRankCard1 > pairRank) && (nonPairRankCard2 < pairRank))) {
			if (pairIsFirstHand) {
				evaluation.setFirstHandPercentage(70);
				evaluation.setSecondHandPercentage(30);
			} else {
				evaluation.setFirstHandPercentage(30);
				evaluation.setSecondHandPercentage(70);
			}
			evaluation.setResult("The pair is about a 70 percent favourite. Another example of this holding would be J-J against A-9. The underdog non-paired hand has three outs while the favourite has redraws.");
		/**
		 * Pair vs. (Overcard and one of that pair)
		 */
		} else if (((nonPairRankCard1 > pairRank) && (nonPairRankCard2 == pairRank)) || ((nonPairRankCard1 == pairRank) && (nonPairRankCard2 > pairRank))) {
			if (pairIsFirstHand) {
				evaluation.setFirstHandPercentage(70);
				evaluation.setSecondHandPercentage(30);
			} else {
				evaluation.setFirstHandPercentage(30);
				evaluation.setSecondHandPercentage(70);
			}

			evaluation.setResult("The classic example of this situation is the confrontation between a pair of cowboys and big slick. The A-K has three outs and it becomes a 70-30 percent situation or a 2.3-to-1 dog for the cowboys.");
			/**
			 * Pair vs. (Undercard and one of that pair)
			 */
		} else if (((nonPairRankCard1 < pairRank) && (nonPairRankCard2 == pairRank)) || ((nonPairRankCard1 == pairRank) && (nonPairRankCard2 < pairRank))) {
			if (pairIsFirstHand) {
				evaluation.setFirstHandPercentage(90);
				evaluation.setSecondHandPercentage(10);
			} else {
				evaluation.setFirstHandPercentage(10);
				evaluation.setSecondHandPercentage(90);
			}
			evaluation.setResult("The non pair has to hit its undercard twice or make a straight or flush to prevail. The pair is better than a 90 percent favourite or slightly better than 10-to-1 odds. I'll take those odds anytime.");
		}

		return evaluation;
	}
}