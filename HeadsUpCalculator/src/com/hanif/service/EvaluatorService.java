package com.hanif.service;

import com.hanif.Evaluation;
import com.hanif.beans.Hand;

public interface EvaluatorService {

	public Evaluation evaluateHands(Hand firstHand, Hand secondHand);
}
