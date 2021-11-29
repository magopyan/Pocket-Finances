package com.example.pocketexpenses.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pocketexpenses.dao.TransactionDao;
import com.example.pocketexpenses.database.AppDatabase;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.relationships.AccountWithTransactions;
import com.example.pocketexpenses.entities.relationships.TransactionSubtypeWithTransactions;

import java.util.List;

public class TransactionRepository {
    private TransactionDao oTransactionDao;
    private LiveData<List<Transaction>> oLiveDataListAllTransactions;
    private LiveData<List<AccountWithTransactions>> oLiveDataListAllAccountsWithTransactions;
    private LiveData<List<TransactionSubtypeWithTransactions>> oLiveDataListAllTransactionSubtypesWithTransactions;

    public TransactionRepository(Application application) {
        AppDatabase oAppDatabase = AppDatabase.getInstance(application);

        oTransactionDao = oAppDatabase.transactionDao();

        oLiveDataListAllTransactions = oTransactionDao.getAllTransactions();
        oLiveDataListAllAccountsWithTransactions = oTransactionDao.getAllAccountsWithTransactions();
        oLiveDataListAllTransactionSubtypesWithTransactions = oTransactionDao.getAllTransactionSubtypesWithTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return oLiveDataListAllTransactions;
    }

    public LiveData<List<AccountWithTransactions>> getAllAccountsWithTransactions() {
        return oLiveDataListAllAccountsWithTransactions;
    }

    public LiveData<List<TransactionSubtypeWithTransactions>> getAllTransactionSubtypesWithTransactions() {
        return oLiveDataListAllTransactionSubtypesWithTransactions;
    }

    public Transaction getTransactionByID(int nID){
        new getTransactionByIDAsyncTask(oTransactionDao).execute(nID);
        return getTransactionByIDAsyncTask.getTransaction();
    }

    public int insertTransaction(Transaction oTransaction){
        new InsertTransactionAsyncTask(oTransactionDao).execute(oTransaction);
        return InsertTransactionAsyncTask.getTransactionID();
    }

    public void updateTransaction(Transaction oTransaction){
        new UpdateTransactionAsyncTask(oTransactionDao).execute(oTransaction);
    }

    public void deleteTransaction(Transaction oTransaction){
        new DeleteTransactionAsyncTask(oTransactionDao).execute(oTransaction);
    }

    public void deleteAllTransactions(){
        new DeleteAllTransactionsAsyncTask(oTransactionDao).execute();
    }

    public AccountWithTransactions getAccountWithTransactionByID(int nID){
        new getAccountWithTransactionByIDAsyncTask(oTransactionDao).execute(nID);
        return getAccountWithTransactionByIDAsyncTask.getAccountWithTransactions();
    }

    public TransactionSubtypeWithTransactions getTransactionSubtypeWithTransactionsByID(int nID){
        new getTransactionSubtypeWithTransactionsByIDAsyncTask(oTransactionDao).execute(nID);
        return getTransactionSubtypeWithTransactionsByIDAsyncTask.getTransactionSubtypeWithTransactions();
    }

    private static class InsertTransactionAsyncTask extends AsyncTask<Transaction, Void, Long> {
        private TransactionDao oTransactionDao;
        private static int nTransactionID;

        private InsertTransactionAsyncTask(TransactionDao oTransactionDao){
            this.oTransactionDao = oTransactionDao;
        }

        @Override
        protected Long doInBackground(Transaction... transactions) {
            return oTransactionDao.insertTransaction(transactions[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            this.nTransactionID = aLong.intValue();
        }

        public static int getTransactionID() {
            return nTransactionID;
        }
    }

    private static class UpdateTransactionAsyncTask extends AsyncTask<Transaction, Void, Void>{
        private TransactionDao oTransactionDao;

        private UpdateTransactionAsyncTask(TransactionDao oTransactionDao){
            this.oTransactionDao = oTransactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            oTransactionDao.updateTransaction(transactions[0]);
            return null;
        }
    }

    private static class DeleteTransactionAsyncTask extends AsyncTask<Transaction, Void, Void>{
        private TransactionDao oTransactionDao;

        private DeleteTransactionAsyncTask(TransactionDao oTransactionDao){
            this.oTransactionDao = oTransactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            oTransactionDao.deleteTransaction(transactions[0]);
            return null;
        }
    }

    private static class DeleteAllTransactionsAsyncTask extends AsyncTask<Void, Void, Void>{
        private TransactionDao oTransactionDao;

        private DeleteAllTransactionsAsyncTask(TransactionDao oTransactionDao){
            this.oTransactionDao = oTransactionDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            oTransactionDao.deleteAllTransactions();
            return null;
        }
    }

    private static class getTransactionByIDAsyncTask extends AsyncTask<Integer, Void, Transaction>{
        private TransactionDao oTransactionDao;
        private static Transaction oTransaction;

        private getTransactionByIDAsyncTask(TransactionDao oTransactionDao){
            this.oTransactionDao = oTransactionDao;
        }

        @Override
        protected Transaction doInBackground(Integer... integers) {
            return oTransactionDao.getTransactionByID(integers[0]);
        }

        @Override
        protected void onPostExecute(Transaction oTransaction) {
            this.oTransaction = oTransaction;
        }

        public static Transaction getTransaction() {
            return oTransaction;
        }
    }

    private static class getAccountWithTransactionByIDAsyncTask extends AsyncTask<Integer, Void, AccountWithTransactions>{
        private TransactionDao oTransactionDao;
        private static AccountWithTransactions oAccountWithTransactions;

        private getAccountWithTransactionByIDAsyncTask(TransactionDao oTransactionDao){
            this.oTransactionDao = oTransactionDao;
        }

        @Override
        protected AccountWithTransactions doInBackground(Integer... integers) {
            return oTransactionDao.getAccountWithTransactionByID(integers[0]);
        }

        @Override
        protected void onPostExecute(AccountWithTransactions oAccountWithTransactions) {
            this.oAccountWithTransactions = oAccountWithTransactions;
        }

        public static AccountWithTransactions getAccountWithTransactions() {
            return oAccountWithTransactions;
        }
    }

    private static class getTransactionSubtypeWithTransactionsByIDAsyncTask extends AsyncTask<Integer, Void, TransactionSubtypeWithTransactions>{
        private TransactionDao oTransactionDao;
        private static TransactionSubtypeWithTransactions oTransactionSubtypeWithTransactions;

        private getTransactionSubtypeWithTransactionsByIDAsyncTask(TransactionDao oTransactionDao){
            this.oTransactionDao = oTransactionDao;
        }

        @Override
        protected TransactionSubtypeWithTransactions doInBackground(Integer... integers) {
            return oTransactionDao.getTransactionSubtypesWithTransactionsByID(integers[0]);
        }

        @Override
        protected void onPostExecute(TransactionSubtypeWithTransactions oTransactionSubtypeWithTransactions) {
            this.oTransactionSubtypeWithTransactions = oTransactionSubtypeWithTransactions;
        }

        public static TransactionSubtypeWithTransactions getTransactionSubtypeWithTransactions() {
            return oTransactionSubtypeWithTransactions;
        }
    }
}
