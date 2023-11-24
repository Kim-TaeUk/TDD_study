package chap07.autodebit;

import static chap07.autodebit.CardValidity.*;

public class RegisterResult {
	private CardValidity validity;

	public RegisterResult(CardValidity validity) {
		this.validity = validity;
	}

	public static RegisterResult error(CardValidity validity) {
		return new RegisterResult(validity);
	}

	public static RegisterResult success() {
		return new RegisterResult(VALID);
	}

	public CardValidity getValidity() {
		return validity;
	}
}
