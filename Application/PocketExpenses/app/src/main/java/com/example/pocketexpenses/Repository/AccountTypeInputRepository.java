package com.example.pocketexpenses.Repository;

import androidx.lifecycle.MutableLiveData;
import com.example.pocketexpenses.entities.AccountType;

public class AccountTypeInputRepository {

    // Singleton code
    ///////////////////////////////////////////////////////////
    private static AccountTypeInputRepository instance = null;

    private AccountTypeInputRepository() {}

    public static AccountTypeInputRepository getInstance() {
        if(instance == null) {
            instance = new AccountTypeInputRepository();
        }
        return instance;
    }

    // Repository code
    ///////////////////////////////////////////////////////////
    private MutableLiveData<AccountType> oAccountType;

    public MutableLiveData<AccountType> getAccountType() {
        if (oAccountType == null) {
            oAccountType = new MutableLiveData<>();
        }
        return oAccountType;
    }
    public void setAccountType(AccountType oAccountTypeArg) {
        oAccountType.setValue(oAccountTypeArg);
    }
}
