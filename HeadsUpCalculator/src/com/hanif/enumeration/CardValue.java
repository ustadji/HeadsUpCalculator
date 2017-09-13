package com.hanif.enumeration;

public enum CardValue {
	A(14),
	K(13),
	Q(12),
	J(11);


	Integer value;

	private CardValue(Integer value) {
		this.value = value;
	}

	public static CardValue fromString(String text) {
		if (text != null) {
			for (CardValue s : CardValue.values()) {
				if (text.equalsIgnoreCase(s.name())) {
					return s;
				}
			}
		}
		return null;
	}
}
