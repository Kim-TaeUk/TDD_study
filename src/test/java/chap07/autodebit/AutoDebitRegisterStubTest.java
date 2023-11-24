package chap07.autodebit;

import static chap07.autodebit.CardValidity.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutoDebitRegisterStubTest {
	private AutoDebitRegister register;
	private StubCardNumberValidator stubValidator;
	private JpaAutoDebitInfoRepository stubRepository;

	@BeforeEach
	void setUp() {
		stubValidator = new StubCardNumberValidator();
		stubRepository = new JpaAutoDebitInfoRepository();
		register = new AutoDebitRegister(stubValidator, stubRepository);
	}

	@Test
	void invalidCard() {
		stubValidator.setInvalidNo("111122223333");

		AutoDebitReq req = new AutoDebitReq("user1", "111122223333");
		RegisterResult result = register.register(req);

		assertEquals(INVALID, result.getValidity());
	}

	@Test
	void theftCard() {
		stubValidator.setTheftNo("1234567890123456");

		AutoDebitReq req = new AutoDebitReq("user1", "1234567890123456");
		RegisterResult result = register.register(req);

		assertEquals(THEFT, result.getValidity());
	}
}
