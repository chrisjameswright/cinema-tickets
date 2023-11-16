package uk.gov.dwp.uc.pairtest.account;

import uk.gov.dwp.uc.pairtest.exception.AccountException;

/**
 * Determines whether account IDs are valid.
 */
public interface AccountService {

    /**
     * Checks whether an account ID is valid.
     * @param accountId The account ID
     * @throws AccountException If the account is not valid
     */
    void validate(Long accountId) throws AccountException;

}
