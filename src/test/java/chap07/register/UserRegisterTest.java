package chap07.register;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRegisterTest {
	private UserRegister userRegister;
	private StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker();

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(stubWeakPasswordChecker);
	}

	@DisplayName("약한 암호면 가입 실패")
	@Test
	void weakPassword() {
		stubWeakPasswordChecker.setWeak(true);    // 암호가 약하다고 응답하도록 설정

		assertThrows(WeakPasswordException.class, () -> {
			userRegister.register("id", "pw", "email");
		});
	}
}
