package chap07.register;

public class UserRegister {
	private WeakPasswordChecker weakPasswordChecker;
	private UserRepository userRepository;
	private EmailNotifier emailNotifier;

	public UserRegister(
		WeakPasswordChecker weakPasswordChecker,
		UserRepository userRepository,
		EmailNotifier emailNotifier) {
		this.weakPasswordChecker = weakPasswordChecker;
		this.userRepository = userRepository;
		this.emailNotifier = emailNotifier;
	}

	public void register(String id, String pw, String email) {
		if (weakPasswordChecker.checkPasswordWeak(pw)) {
			throw new WeakPasswordException();
		}

		User user = userRepository.findById(id);
		if (user != null) {
			throw new DupIdException();
		}
		userRepository.save(new User(id, pw, email));

		emailNotifier.sendRegisterEmail(email);
	}
}
