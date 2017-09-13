package com.hanif.strategies;

import com.hanif.HandUtil;
import com.hanif.beans.Hand;
import com.hanif.service.HandEvaluationStrategy;

/**
 * Factory to return the appropriate instance of {@link HandEvaluationStrategy} given 2 hands
 * @author Hanif
 *
 */
public class StrategyFactory {

	private Hand firstHand;
	private Hand secondHand;

	public StrategyFactory(Hand firstHand, Hand secondHand) {
		this.firstHand = firstHand;
		this.secondHand = secondHand;
	}

	public HandEvaluationStrategy getStrategy() {

		if (HandUtil.isHandAPair(this.firstHand) && HandUtil.isHandAPair(this.secondHand)) {
			HandEvaluationStrategy strategy = new PairVsPairStrategy(this.firstHand, this.secondHand);
			return strategy;
		}

		if (HandUtil.isHandAPair(this.firstHand) || HandUtil.isHandAPair(this.secondHand)) {
			HandEvaluationStrategy strategy = new PairVsNonPairStrategy(this.firstHand, this.secondHand);
			return strategy;
		}

		HandEvaluationStrategy strategy = new NonPairVsNonPairStrategy(this.firstHand, this.secondHand);

		return strategy;
	}
}