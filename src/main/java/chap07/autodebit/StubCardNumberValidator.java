package chap07.autodebit;

import static chap07.autodebit.CardValidity.*;

public class StubCardNumberValidator extends CardNumberValidator {
	private String invalidNo;
	private String theftNo;

	public void setInvalidNo(String invalidNo) {
		this.invalidNo = invalidNo;
	}

	public void setTheftNo(String theftNo) {
		this.theftNo = theftNo;
	}

	@Override
	public CardValidity validate(String cardNumber) {
		if (invalidNo != null && invalidNo.equals(cardNumber)) {
			return INVALID;
		}
		if (theftNo != null && theftNo.equals(cardNumber)) {
			return THEFT;
		}
		return VALID;
	}
}
