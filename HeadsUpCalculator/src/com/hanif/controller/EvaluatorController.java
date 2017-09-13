package com.hanif.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanif.Evaluation;
import com.hanif.RequestCommand;
import com.hanif.beans.Card;
import com.hanif.beans.Hand;
import com.hanif.service.EvaluatorService;

@Controller
@RequestMapping("/EvaluatorController")
public class EvaluatorController {

	@Autowired
	private Validator requestValidator;

	/**
	 * Jackson/Json annotated class which assist with marshaling of response payload
	 */
	private Evaluation evaluation;

	private EvaluatorService evaluatorService;

	private Hand firstHand, secondHand;

	/**
	 * Set custom validator
	 * @param binder
	 */
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(requestValidator);
	}

	@Autowired
	public EvaluatorController(EvaluatorService evaluatorService) {
		this.evaluatorService = evaluatorService;
	}

	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public @ResponseBody Evaluation evaluate(@Validated RequestCommand requestCommand, BindingResult validationResults) {

		this.evaluation = new Evaluation();
		this.evaluation.setSuccess(true);
		if (validationResults.hasErrors()) {
			List<ObjectError> objectErrors = validationResults.getAllErrors();
			String errorString = "";
			for (ObjectError error : objectErrors) {
				errorString += error.getDefaultMessage() + ";";
			}
			this.evaluation.setResult(errorString);
			this.evaluation.setSuccess(false);
			return this.evaluation;
		}

		initializeHandsFromRequest(requestCommand);

		this.evaluation = this.evaluatorService.evaluateHands(this.firstHand, this.secondHand);
		this.evaluation.setSuccess(true);
		return this.evaluation;
	}

	private void initializeHandsFromRequest(RequestCommand requestCommand) {
		Card firstCard = new Card(requestCommand.getFirstCardSuit(), requestCommand.getFirstCardValue());
		Card secondCard = new Card(requestCommand.getSecondCardSuit(), requestCommand.getSecondCardValue());
		Card thirdCard = new Card(requestCommand.getThirdCardSuit(), requestCommand.getThirdCardValue());
		Card fourthCard = new Card(requestCommand.getFourthCardSuit(), requestCommand.getFourthCardValue());

		this.firstHand = new Hand(firstCard, secondCard);
		this.secondHand = new Hand(thirdCard, fourthCard);
	}
}
