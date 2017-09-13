package com.hanif.service;

import org.springframework.stereotype.Service;

import com.hanif.Evaluation;
import com.hanif.beans.Hand;
import com.hanif.strategies.StrategyFactory;

@Service
public class HeadsUpEvaluatorServiceImpl implements EvaluatorService {

	private StrategyFactory strategyFactory;
	private HandEvaluationStrategy handEvaluationStrategy;

	@Override
	public Evaluation evaluateHands(Hand firstHand, Hand secondHand) {
		this.strategyFactory = new StrategyFactory(firstHand, secondHand);
		this.handEvaluationStrategy = this.strategyFactory.getStrategy();

		Evaluation evaluation = this.handEvaluationStrategy.evaluateHand();
		evaluation.setSuccess(true);
		return evaluation;
	}
}
