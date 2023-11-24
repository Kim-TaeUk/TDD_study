package chap07.autodebit;

import static chap07.autodebit.CardValidity.*;

import java.time.LocalDateTime;

public class AutoDebitRegister {
	private CardNumberValidator validator;
	private AutoDebitInfoRepository repository;

	public AutoDebitRegister(CardNumberValidator validator, AutoDebitInfoRepository repository) {
		this.validator = validator;
		this.repository = repository;
	}

	private RegisterResult register(AutoDebitReq req) {
		CardValidity validity = validator.validate(req.getCardNumber());
		if (validity != VALID) {
			return RegisterResult.error(validity);
		}

		AutoDebitInfo info = repository.findOne(req.getUserId());
		if (info != null) {
			info.changeCardNumber(req.getCardNumber());
		} else {
			AutoDebitInfo newInfo = new AutoDebitInfo(req.getUserId(), req.getCardNumber(), LocalDateTime.now());
			repository.save(newInfo);
		}

		return RegisterResult.success();
	}
}
