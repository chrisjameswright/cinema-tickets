package uk.gov.dwp.uc.pairtest.account;

import uk.gov.dwp.uc.pairtest.exception.AccountException;

public class AccountServiceImpl implements AccountService {

    @Override
    public void validate(Long accountId) throws AccountException {
        if (accountId == null || accountId <= 0) throw new AccountException();
    }
}
