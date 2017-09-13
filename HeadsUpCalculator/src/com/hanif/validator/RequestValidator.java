package com.hanif.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hanif.RequestCommand;

@Component
public class RequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return RequestCommand.class.equals(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RequestCommand requestCommand = (RequestCommand) target;
		String firstCardValue =  requestCommand.getFirstCardValue();
		String secondCardValue = requestCommand.getSecondCardValue();
		String thirdCardValue = requestCommand.getThirdCardValue();
		String fourthCardValue = requestCommand.getFourthCardValue();

		/**
		 * For now, we just test if the fields are populated or not
		 */
		if (StringUtils.isEmpty(firstCardValue)) {
			errors.reject("First card cannot be empty", "First card cannot be empty");
		}
		if (StringUtils.isEmpty(secondCardValue)) {
			errors.reject("Second card cannot be empty", "Second card cannot be empty");
		}
		if (StringUtils.isEmpty(thirdCardValue)) {
			errors.reject("Third card cannot be empty", "Third card cannot be empty");
		}
		if (StringUtils.isEmpty(fourthCardValue)) {
			errors.reject("Fourth card cannot be empty", "Fourth card cannot be empty");
		}
	}

}
