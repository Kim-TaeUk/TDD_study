package chap07.register;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRegisterTest {
	private UserRegister userRegister;
	private StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker();
	private MemoryUserRepository fakeRepository = new MemoryUserRepository();
	private SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(stubWeakPasswordChecker, fakeRepository, spyEmailNotifier);
	}

	@DisplayName("약한 암호면 가입 실패")
	@Test
	void weakPassword() {
		// 암호가 약하다고 응답하도록 설정
		stubWeakPasswordChecker.setWeak(true);

		assertThrows(WeakPasswordException.class, () -> {
			userRegister.register("id", "pw", "email");
		});
	}

	@DisplayName("이미 같은 ID가 존재하면 가입 실패")
	@Test
	void dupIdExists() {
		// 이미 같은 ID 존재하는 상황 만들기
		fakeRepository.save(new User("id", "pw1", "email@email.com"));
		assertThrows(DupIdException.class, () -> {
			userRegister.register("id", "pw2", "email");
		});
	}

	@DisplayName("같은 ID가 없으면 가입 성공")
	@Test
	void noDupId_RegisterSuccess() {
		userRegister.register("id", "pw", "email");

		// 가입 결과 확인
		User savedUser = fakeRepository.findById("id");
		assertEquals("id", savedUser.getId());
		assertEquals("email", savedUser.getEmail());
	}

	@DisplayName("가입하면 메일 전송")
	@Test
	void whenRegisterThenSendMail() {
		userRegister.register("id", "pw", "email@email.com");

		assertTrue(spyEmailNotifier.isCalled());
		assertEquals(
			"email@email.com",
			spyEmailNotifier.getEmail()
		);
	}
}
