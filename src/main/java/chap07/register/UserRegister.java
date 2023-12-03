package chap07.register;

public class UserRegister {
	private WeakPasswordChecker weakPasswordChecker;

	public UserRegister(WeakPasswordChecker weakPasswordChecker) {
		this.weakPasswordChecker = weakPasswordChecker;
	}

	public void register(String id, String pw, String email) {
		if (weakPasswordChecker.checkPasswordWeak(pw)) {
			throw new WeakPasswordException();
		}
	}
}
