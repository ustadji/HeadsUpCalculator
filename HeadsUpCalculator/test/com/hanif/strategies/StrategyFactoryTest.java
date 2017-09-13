package com.hanif.strategies;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.hanif.beans.Card;
import com.hanif.beans.Hand;
import com.hanif.service.HandEvaluationStrategy;

/**
 * These tests ensure that the correct strategy for calculating the odds is being used
 * given a set of hands playing Heads Up.
 * @author Hanif
 *
 */
public class StrategyFactoryTest {

	private StrategyFactory strategyFactory;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testWhenInputsArePairsThenPairVsPairStrategyReturned() {
		Card firstCard = new Card("h", "5");
		Card secondCard = new Card("d", "5");
		Hand hand1 = new Hand(firstCard, secondCard);

		firstCard = new Card("h", "6");
		secondCard = new Card("d", "6");
		Hand hand2 = new Hand(firstCard, secondCard);

		this.strategyFactory = new StrategyFactory(hand1, hand2);
		HandEvaluationStrategy strategy = this.strategyFactory.getStrategy();

		assertThat(strategy, instanceOf(PairVsPairStrategy.class));
	}

	@Test
	public void testWhenInputsAreOnePairAndNonPairThenPairVsNonPairStrategyReturned() {
		Card firstCard = new Card("h", "10");
		Card secondCard = new Card("d", "10");
		Hand hand1 = new Hand(firstCard, secondCard);

		firstCard = new Card("h", "6");
		secondCard = new Card("d", "7");
		Hand hand2 = new Hand(firstCard, secondCard);

		this.strategyFactory = new StrategyFactory(hand1, hand2);
		HandEvaluationStrategy strategy = this.strategyFactory.getStrategy();

		assertThat(strategy, instanceOf(PairVsNonPairStrategy.class));
	}

	@Test
	public void testWhenInputsAreNonPairAndNonPairThenNonPairVsNonPairStrategyReturned() {
		Card firstCard = new Card("h", "J");
		Card secondCard = new Card("d", "10");
		Hand hand1 = new Hand(firstCard, secondCard);

		firstCard = new Card("h", "2");
		secondCard = new Card("d", "A");
		Hand hand2 = new Hand(firstCard, secondCard);

		this.strategyFactory = new StrategyFactory(hand1, hand2);
		HandEvaluationStrategy strategy = this.strategyFactory.getStrategy();

		assertThat(strategy, instanceOf(NonPairVsNonPairStrategy.class));
	}
}
