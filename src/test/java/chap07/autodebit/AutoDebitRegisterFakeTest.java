package chap07.autodebit;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutoDebitRegisterFakeTest {
	private AutoDebitRegister register;
	private StubCardNumberValidator stubValidator;
	private MemoryAutoDebitInfoRepository repository;

	@BeforeEach
	void setUp() {
		stubValidator = new StubCardNumberValidator();
		repository = new MemoryAutoDebitInfoRepository();
		register = new AutoDebitRegister(stubValidator, repository);
	}

	@Test
	void alreadyRegistered_InfoUpdated() {
		repository.save(
			new AutoDebitInfo("user1", "111222333444", LocalDateTime.now())
		);

		AutoDebitReq req = new AutoDebitReq("user1", "123456789012");
		RegisterResult result = this.register.register(req);

		AutoDebitInfo saved = repository.findOne("user1");
		assertEquals("123456789012", saved.getCardNumber());
	}

	@Test
	void notYetRegistered_newInfoRegistered() {
		AutoDebitReq req = new AutoDebitReq("user1", "1234123412342134");
		RegisterResult result = this.register.register(req);

		AutoDebitInfo saved = repository.findOne("user1");
		assertEquals("1234123412341234", saved.getCardNumber());
	}
}
