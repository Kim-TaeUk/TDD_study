package chap03;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {

    @Test
    void 만원_납부시_한달_뒤_만료일이_됨() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 4, 1)
        );
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 5, 5))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 6, 6)
        );
    }

    @Test
    void 납부일과_만료일의_한달_뒤_일자가_같지_않음() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 2, 28)
        );
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 1, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020, 2, 29)
        );
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 5, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 6, 30)
        );
    }

    private void assertExpiryDate(
            PayData payData,
            LocalDate expectedExpiryDate
    ) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate, realExpiryDate);
    }
}
