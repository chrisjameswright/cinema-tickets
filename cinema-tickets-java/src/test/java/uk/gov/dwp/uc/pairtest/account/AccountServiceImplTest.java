package uk.gov.dwp.uc.pairtest.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.exception.AccountException;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    private final AccountServiceImpl service = new AccountServiceImpl();

    @Test
    @DisplayName("succeeds for valid account number")
    void validAccountId() {
        assertDoesNotThrow(() -> {
            service.validate(1L);
        });
    }

    @Test
    @DisplayName("fails for null account number")
    void nullAccountId() {
        AccountException ex = assertThrowsExactly(AccountException.class, () -> {
            service.validate(null);
        });

        assertEquals("Invalid account number.", ex.getMessage());
    }

    @Test
    @DisplayName("fails for negative account number")
    void negativeAccountId() {
        AccountException ex = assertThrowsExactly(AccountException.class, () -> {
            service.validate(-1L);
        });

        assertEquals("Invalid account number.", ex.getMessage());
    }

    @Test
    @DisplayName("fails for zero account number")
    void zeroAccountId() {
        AccountException ex = assertThrowsExactly(AccountException.class, () -> {
            service.validate(0L);
        });

        assertEquals("Invalid account number.", ex.getMessage());
    }

}