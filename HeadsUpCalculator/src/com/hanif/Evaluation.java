package com.hanif;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Evaluation {

	@JsonProperty
	private boolean success;

	@JsonProperty
	private String result;

	@JsonProperty
	private Integer firstHandPercentage;

	@JsonProperty
	private Integer secondHandPercentage;


	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getFirstHandPercentage() {
		return firstHandPercentage;
	}

	public void setFirstHandPercentage(Integer firstHandPercentage) {
		this.firstHandPercentage = firstHandPercentage;
	}

	public Integer getSecondHandPercentage() {
		return secondHandPercentage;
	}

	public void setSecondHandPercentage(Integer secondHandPercentage) {
		this.secondHandPercentage = secondHandPercentage;
	}
}
