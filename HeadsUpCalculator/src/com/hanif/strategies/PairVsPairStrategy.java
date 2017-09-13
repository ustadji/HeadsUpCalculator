package com.hanif.strategies;

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
public class PairVsPairStrategy implements HandEvaluationStrategy {

	Integer firstCardValue;
	Integer secondCardValue;
	Integer thirdCardValue;
	Integer fourthCardValue;

	public PairVsPairStrategy(Hand firstHand, Hand secondHand) {
		this.firstCardValue = HandUtil.getFirstCardValueFromHand(firstHand);
		this.secondCardValue = HandUtil.getSecondCardValueFromHand(firstHand);
		this.thirdCardValue = HandUtil.getFirstCardValueFromHand(secondHand);
		this.fourthCardValue = HandUtil.getSecondCardValueFromHand(secondHand);
	}

	@Override
	public Evaluation evaluateHand() {
		Evaluation evaluation = new Evaluation();
		evaluation.setResult("The higher pair is an 80 percent favourite. We can get very technical and highlight the fact that if the underpair didn’t have any clean suits and/or the maximum number of straight outs then the high pair’s equity would increases by one or two percent.");
		if (firstCardValue > thirdCardValue) {
			evaluation.setFirstHandPercentage(80);
			evaluation.setSecondHandPercentage(20);
		} else if (thirdCardValue > firstCardValue) {
			evaluation.setFirstHandPercentage(20);
			evaluation.setSecondHandPercentage(80);
		} else {
			evaluation.setFirstHandPercentage(50);
			evaluation.setSecondHandPercentage(50);
			evaluation.setResult("Same hand, same percentage");
		}

		return evaluation;
	}
}
