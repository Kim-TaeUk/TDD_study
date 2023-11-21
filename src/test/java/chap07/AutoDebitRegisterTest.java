package chap07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoDebitRegisterTest {
    private AutoDebitRegister register;

    @BeforeEach
    void setUp() {
        CardNumberValidator validator = new CardNumberValidator();
        AutoDebitInfoRepository repository = new JpaAutoDebitInfoRepository();
        register = new AutoDebitRegister(validator, repository);
    }

    @Test
    void validCard() {
        // 업체에서 받은 테스트용 유효한 카드 번호 사용
        AutoDebitReq req = new AutoDebitReq("uesr1", "1234123412341234");
        RegisterResult registerResult = this.register.register(req);
        assertEquals(VALID, result.getValidity());
    }

    @Test
    void theftCard() {
        // 업체에서 받은 도난 테스트용 유효한 카드 번호 사용
        AutoDebitReq req = new AutoDebitReq("uesr1", "1234567890123456");
        RegisterResult registerResult = this.register.register(req);
        assertEquals(THEFT, result.getValidity());
    }
}
