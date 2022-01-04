package com.example.pocketexpenses.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pocketexpenses.dao.AccountTypeDao;
import com.example.pocketexpenses.database.AppDatabase;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;

import java.util.List;
import java.util.concurrent.Executors;

public class AccountTypeRepository {

    private AccountTypeDao oAccountTypeDao;
    private LiveData<List<Account>> oLiveDataListAllAccounts;
    private LiveData<List<AccountType>> oLiveDataListAllAccountTypes;
    private LiveData<List<AccountTypeWithAccounts>> oLiveDataListAccountTypesWithAccounts;
    private LiveData<List<AccountType>> oLiveDataAccountTypeById;

    public AccountTypeRepository(Application application) {
        AppDatabase oAppDatabase = AppDatabase.getInstance(application);

        oAccountTypeDao = oAppDatabase.accountTypeDao();

        oLiveDataListAllAccounts = oAccountTypeDao.getAllAccounts();
        oLiveDataListAllAccountTypes = oAccountTypeDao.getAllAccountTypes();
        oLiveDataListAccountTypesWithAccounts = oAccountTypeDao.getAllAccountTypesWithAccounts();
    }

    public LiveData<List<AccountTypeWithAccounts>> getAllAccountTypesWithAccounts() {
        return oLiveDataListAccountTypesWithAccounts;
    }

    public Account getAccountByID(int nID){
        new getAccountByIDAsyncTask(oAccountTypeDao).execute(nID);
        return getAccountByIDAsyncTask.getAccount();
        //return oAccountTypeDao.getAccountByID(nID);
    }

    public int insertAccount(Account oAccount){
        new InsertAccountAsyncTask(oAccountTypeDao).execute(oAccount);
        return InsertAccountAsyncTask.getAccountID();
    }

    public void updateAccount(Account oAccount){
        new UpdateAccountAsyncTask(oAccountTypeDao).execute(oAccount);
    }

    public void deleteAccount(Account oAccount){
        new DeleteAccountAsyncTask(oAccountTypeDao).execute(oAccount);
    }

    public void deleteAllAccounts(){
        new DeleteAllAccountsAsyncTask(oAccountTypeDao).execute();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return oLiveDataListAllAccounts;
    }

    public LiveData<List<AccountType>> getAccountTypeByID(int nID){

       return oAccountTypeDao.getAccountTypeByID(nID);
//        new getAccountTypeByIDAsyncTask(oAccountTypeDao).execute(nID);
//        return getAccountTypeByIDAsyncTask.getAccountType();
    }

    public int insertAccountType(AccountType oAccountType){
        new InsertAccountTypeAsyncTask(oAccountTypeDao).execute(oAccountType);
        return InsertAccountTypeAsyncTask.getAccountTypeID();
    }

    public void updateAccountType(AccountType oAccountType){
        new UpdateAccountTypeAsyncTask(oAccountTypeDao).execute(oAccountType);
    }

    public void deleteAccountType(AccountType oAccountType){
        new DeleteAccountTypeAsyncTask(oAccountTypeDao).execute(oAccountType);
    }

    public void deleteAllAccountTypes(){
        new DeleteAllAccountTypesAsyncTask(oAccountTypeDao).execute();
    }

    public LiveData<List<AccountType>> getAllAccountTypes() {
        return oLiveDataListAllAccountTypes;
    }

    private static class getAccountByIDAsyncTask extends AsyncTask<Integer, Void, Account> {
        private AccountTypeDao oAccountTypeDao;
        private static Account oAccount;

        private getAccountByIDAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Account doInBackground(Integer... integers) {
            return oAccountTypeDao.getAccountByID(integers[0]);
        }

        @Override
        protected void onPostExecute(Account oAccount) {
            this.oAccount = oAccount;
        }

        public static Account getAccount() {
            return oAccount;
        }
    }

    private static class InsertAccountAsyncTask extends AsyncTask<Account, Void, Long>{
        private AccountTypeDao oAccountTypeDao;
        private static int nAccountID;

        private InsertAccountAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Long doInBackground(Account... accounts) {
            return oAccountTypeDao.insertAccount(accounts[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            this.nAccountID = aLong.intValue();
        }

        public static int getAccountID() {
            return nAccountID;
        }
    }

    private static class UpdateAccountAsyncTask extends AsyncTask<Account, Void, Void>{
        private AccountTypeDao oAccountTypeDao;

        private UpdateAccountAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            oAccountTypeDao.updateAccount(accounts[0]);
            return null;
        }
    }

    private static class DeleteAccountAsyncTask extends AsyncTask<Account, Void, Void>{
        private AccountTypeDao oAccountTypeDao;

        private DeleteAccountAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            oAccountTypeDao.deleteAccount(accounts[0]);
            return null;
        }
    }

    private static class DeleteAllAccountsAsyncTask extends AsyncTask<Void, Void, Void>{
        private AccountTypeDao oAccountTypeDao;

        private DeleteAllAccountsAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            oAccountTypeDao.deleteAllAccounts();
            return null;
        }
    }

    private static class getAccountTypeByIDAsyncTask extends AsyncTask<Integer, Void, LiveData<List<AccountType>>> {
        private AccountTypeDao oAccountTypeDao;
        private static LiveData<List<AccountType>> oAccountType;

        private getAccountTypeByIDAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected LiveData<List<AccountType>> doInBackground(Integer... integers) {
            return oAccountTypeDao.getAccountTypeByID(integers[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<AccountType>> oAccountType) {
            this.oAccountType = oAccountType;
        }

        public static LiveData<List<AccountType>> getAccountType() {
            if(oAccountType != null)
                return oAccountType;
            return null;
        }
    }

    private static class InsertAccountTypeAsyncTask extends AsyncTask<AccountType, Void, Long>{
        private AccountTypeDao oAccountTypeDao;
        private static int nAccountTypeID;

        private InsertAccountTypeAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Long doInBackground(AccountType... accountTypes) {
            return oAccountTypeDao.insertAccountType(accountTypes[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            this.nAccountTypeID = aLong.intValue();
        }

        public static int getAccountTypeID() {
            return nAccountTypeID;
        }
    }

    private static class UpdateAccountTypeAsyncTask extends AsyncTask<AccountType, Void, Void>{
        private AccountTypeDao oAccountTypeDao;

        private UpdateAccountTypeAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Void doInBackground(AccountType... accountTypes) {
            oAccountTypeDao.updateAccountType(accountTypes[0]);
            return null;
        }
    }

    private static class DeleteAccountTypeAsyncTask extends AsyncTask<AccountType, Void, Void>{
        private AccountTypeDao oAccountTypeDao;

        private DeleteAccountTypeAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Void doInBackground(AccountType... accountTypes) {
            oAccountTypeDao.deleteAccountType(accountTypes[0]);
            return null;
        }
    }

    private static class DeleteAllAccountTypesAsyncTask extends AsyncTask<Void, Void, Void>{
        private AccountTypeDao oAccountTypeDao;

        private DeleteAllAccountTypesAsyncTask(AccountTypeDao oAccountTypeDao){
            this.oAccountTypeDao = oAccountTypeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            oAccountTypeDao.deleteAllAccountTypes();
            return null;
        }
    }
}
