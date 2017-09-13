package com.hanif;

public class RequestCommand {

	private String firstCardSuit;
	private String firstCardValue;

	private String secondCardSuit;
	private String secondCardValue;

	private String thirdCardSuit;
	private String thirdCardValue;

	private String fourthCardSuit;
	private String fourthCardValue;

	public String getFirstCardSuit() {
		return firstCardSuit;
	}
	public void setFirstCardSuit(String firstCardSuit) {
		this.firstCardSuit = firstCardSuit;
	}
	public String getFirstCardValue() {
		return firstCardValue;
	}
	public void setFirstCardValue(String firstCardValue) {
		this.firstCardValue = firstCardValue;
	}
	public String getSecondCardSuit() {
		return secondCardSuit;
	}
	public void setSecondCardSuit(String secondCardSuit) {
		this.secondCardSuit = secondCardSuit;
	}
	public String getSecondCardValue() {
		return secondCardValue;
	}
	public void setSecondCardValue(String secondCardValue) {
		this.secondCardValue = secondCardValue;
	}
	public String getThirdCardSuit() {
		return thirdCardSuit;
	}
	public void setThirdCardSuit(String thirdCardSuit) {
		this.thirdCardSuit = thirdCardSuit;
	}
	public String getThirdCardValue() {
		return thirdCardValue;
	}
	public void setThirdCardValue(String thirdCardValue) {
		this.thirdCardValue = thirdCardValue;
	}
	public String getFourthCardSuit() {
		return fourthCardSuit;
	}
	public void setFourthCardSuit(String fourthCardSuit) {
		this.fourthCardSuit = fourthCardSuit;
	}
	public String getFourthCardValue() {
		return fourthCardValue;
	}
	public void setFourthCardValue(String fourthCardValue) {
		this.fourthCardValue = fourthCardValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestCommand [firstCardSuit=");
		builder.append(firstCardSuit);
		builder.append(", firstCardValue=");
		builder.append(firstCardValue);
		builder.append(", secondCardSuit=");
		builder.append(secondCardSuit);
		builder.append(", secondCardValue=");
		builder.append(secondCardValue);
		builder.append(", thirdCardSuit=");
		builder.append(thirdCardSuit);
		builder.append(", thirdCardValue=");
		builder.append(thirdCardValue);
		builder.append(", fourthCardSuit=");
		builder.append(fourthCardSuit);
		builder.append(", fourthCardValue=");
		builder.append(fourthCardValue);
		builder.append("]");
		return builder.toString();
	}

}
