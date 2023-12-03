package chap07.register;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class UserRegisterMockOvercaseTest {
	private UserRegister userRegister;
	private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
	private UserRepository mockRepository = Mockito.mock(UserRepository.class);
	private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

	@BeforeEach
	void setUp() {
		userRegister = new UserRegister(
			mockPasswordChecker,
			mockRepository,
			mockEmailNotifier);
	}

	@Test
	void noDupId_RegisterSuccess() {
		userRegister.register("id", "pw", "email");

		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		BDDMockito.then(mockRepository).should().save(captor.capture());

		User savedUser = captor.getValue();
		assertEquals("id", savedUser.getId());
		assertEquals("email", savedUser.getEmail());
	}
}
