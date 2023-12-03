package chap07.register;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class UserRegisterMockTest {
	private UserRegister userRegister;
	private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
	private MemoryUserRepository fakeRepository = new MemoryUserRepository();
	private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);
	/*
	stub과 spy를 mock 객체 대신 사용!
	 */

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(
			mockPasswordChecker,
			fakeRepository,
			mockEmailNotifier);
	}

	@DisplayName("약한 암호면 가입 실패")
	@Test
	void weakPassword() {
		BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);
		/*
		mock 객체 이용하여 stub을 대신함!

		특정 메서드를 호출하면(BDDMockito.given())
		해당 메서드 호출에 대한 반환값 지정(willReturn())
		 */

		assertThrows(WeakPasswordException.class, () -> {
			userRegister.register("id", "pw", "email");
		});
	}

	@DisplayName("회원 가입 시 암호 검사 수행")
	@Test
	void checkPassword() {
		userRegister.register("id", "pw", "email");

		/*
		mock 객체에 대한 BDD 스타일의 검증을 시작하는데,
		mock 객체의 메서드 호출이 발생했는지 검증(should())하는데
		checkPasswordWeak(BDDMockito.anyString())
		-> checkPasswordWeak 메서드가 어떠한 문자 파라미터로든 호출되어야 함을 나타냄

		즉, register 메서드 호출 시 checkPasswordWeak 메서드가 mock 객체 mockPasswordChecker에서
		호출되었는지 검증하는 테스트임
		 */
		BDDMockito.then(mockPasswordChecker)
			.should()
			.checkPasswordWeak(BDDMockito.anyString());
	}

	@DisplayName("가입하면 메일 전송")
	@Test
	void whenRegisterThenSendMail() {
		userRegister.register("id", "pw", "email@email.com");

		/*
		Argument: Mockito 기능 중 하나
		메서드 호출 시 전달된 인자 값 캡처!

		mock 객체의 sendRegisterEmail 메서드 호출을 기대함
		ArgumentCaptor 사용하여 captor.capture()로 sendRegisterEmail 메서드 호출 시 전달된 실제 인자 캡처함
		 */
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		BDDMockito.then(mockEmailNotifier)
			.should().sendRegisterEmail(captor.capture());

		String realEmail = captor.getValue();
		assertEquals("email@email.com", realEmail);
	}
}
