package com.example.pocketexpenses.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.pocketexpenses.dao.TransactionTypeDao;
import com.example.pocketexpenses.database.AppDatabase;
import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.entities.relationships.TransactionTypeWithSubtypes;

import java.util.List;

public class TransactionTypeRepository {
    private TransactionTypeDao oTransactionTypeDao;
    private LiveData<List<TransactionDirectionWithTypesAndSubtypes>> oLiveDataListAllTransactionDirectionWithTypesAndSubtypes;
    private LiveData<List<TransactionTypeWithSubtypes>> oLiveDataListAllTransactionTypeWithSubtypes;
    private LiveData<List<TransactionSubtype>> oLiveDataListAllTransactionSubtype;

    public TransactionTypeRepository(Application application) {
        AppDatabase oAppDatabase = AppDatabase.getInstance(application);

        oTransactionTypeDao = oAppDatabase.transactionTypeDao();

        oLiveDataListAllTransactionDirectionWithTypesAndSubtypes = oTransactionTypeDao.getAllTransactionDirectionWithTypesAndSubtypes();
        oLiveDataListAllTransactionTypeWithSubtypes = oTransactionTypeDao.getAllTransactionTypeWithSubtypes();
        oLiveDataListAllTransactionSubtype = oTransactionTypeDao.getAllTransactionSubtype();
    }

    public LiveData<List<TransactionDirectionWithTypesAndSubtypes>> getAllTransactionDirectionWithTypesAndSubtypes() {
        return oLiveDataListAllTransactionDirectionWithTypesAndSubtypes;
    }

    public LiveData<List<TransactionTypeWithSubtypes>> getAllTransactionTypeWithSubtypes() {
        return oLiveDataListAllTransactionTypeWithSubtypes;
    }

    public LiveData<List<TransactionSubtype>> getAllTransactionSubtype() {
        return oLiveDataListAllTransactionSubtype;
    }

    public TransactionDirectionWithTypesAndSubtypes getTransactionDirectionByID(int nID){
        new getTransactionDirectionByIDAsyncTask(oTransactionTypeDao).execute(nID);
        //new getTransactionDirectionByIDAsyncTask(oTransactionTypeDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, nID); -sushto ne raboti
        return getTransactionDirectionByIDAsyncTask.getTransactionDirectionWithTypesAndSubtypes();
    }

    public int insertTransactionDirection(TransactionDirection oTransactionDirection){
        new InsertTransactionDirectionAsyncTask(oTransactionTypeDao).execute(oTransactionDirection);
        return InsertTransactionDirectionAsyncTask.getTransactionDirectionID();
    }

    public void updateTransactionDirection(TransactionDirection oTransactionDirection){
        new UpdateTransactionDirectionAsyncTask(oTransactionTypeDao).execute(oTransactionDirection);
    }

    public void deleteTransactionDirection(TransactionDirection oTransactionDirection){
        new DeleteTransactionDirectionAsyncTask(oTransactionTypeDao).execute(oTransactionDirection);
    }

    public void deleteAllTransactionDirection(){
        new DeleteAllTransactionDirectionAsyncTask(oTransactionTypeDao).execute();
    }

    public TransactionTypeWithSubtypes getTransactionTypeByID(int nID){
        new getTransactionTypeByIDAsyncTask(oTransactionTypeDao).execute(nID);
        return getTransactionTypeByIDAsyncTask.getTransactionTypeWithSubtypes();
    }

    public int insertTransactionType(TransactionType oTransactionType){
        new InsertTransactionTypeAsyncTask(oTransactionTypeDao).execute(oTransactionType);
        return InsertTransactionTypeAsyncTask.getTransactionTypeID();
    }

    public void updateTransactionType(TransactionType oTransactionType){
        new UpdateTransactionTypeAsyncTask(oTransactionTypeDao).execute(oTransactionType);
    }

    public void deleteTransactionType(TransactionType oTransactionType){
        new DeleteTransactionTypeAsyncTask(oTransactionTypeDao).execute(oTransactionType);
    }

    public void deleteAllTransactionType(){
        new DeleteAllTransactionTypeAsyncTask(oTransactionTypeDao).execute();
    }

    public TransactionSubtype getTransactionSubtypeByID(int nID) {
        new getTransactionSubtypeByIDAsyncTask(oTransactionTypeDao).execute(nID);
        return getTransactionSubtypeByIDAsyncTask.getTransactionSubtype();
    }

//    public TransactionSubtype getTransactionSubtypeByID(int nID) {  - I tova ne raboti
//
//        final TransactionSubtype[] subtype = {null};
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                subtype[0] = oTransactionTypeDao.getSubtypeById(nID);
//                Log.i("Returned subtype: ", subtype[0].toString());
//            }
//        });
//        return subtype[0];
//    }

    public int insertTransactionSubtype(TransactionSubtype oTransactionSubtype){
        new InsertTransactionSubtypeAsyncTask(oTransactionTypeDao).execute(oTransactionSubtype);
        return InsertTransactionSubtypeAsyncTask.getTransactionSubtypeID();
    }

    public void updateTransactionSubtype(TransactionSubtype oTransactionSubtype){
        new UpdateTransactionSubtypeAsyncTask(oTransactionTypeDao).execute(oTransactionSubtype);
    }

    public void deleteTransactionSubtype(TransactionSubtype oTransactionSubtype){
        new DeleteTransactionSubtypeAsyncTask(oTransactionTypeDao).execute(oTransactionSubtype);
    }

    public void deleteAllTransactionSubtype(){
        new DeleteAllTransactionSubtypeAsyncTask(oTransactionTypeDao).execute();
    }

    private static class getTransactionDirectionByIDAsyncTask extends AsyncTask<Integer, Void, TransactionDirectionWithTypesAndSubtypes> {
        private TransactionTypeDao oTransactionTypeDao;
        private static TransactionDirectionWithTypesAndSubtypes oTransactionDirectionWithTypesAndSubtypes;

        private getTransactionDirectionByIDAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected TransactionDirectionWithTypesAndSubtypes doInBackground(Integer... integers) {
            Log.i("doInBackground() executed", "doInBackground() executed");
            return oTransactionTypeDao.getDirectionById(integers[0]);
        }

        @Override
        protected void onPostExecute(TransactionDirectionWithTypesAndSubtypes oTransactionDirectionWithTypesAndSubtypes) {
            this.oTransactionDirectionWithTypesAndSubtypes = oTransactionDirectionWithTypesAndSubtypes;
        }

        public static TransactionDirectionWithTypesAndSubtypes getTransactionDirectionWithTypesAndSubtypes() {
            return oTransactionDirectionWithTypesAndSubtypes;
        }
    }

    private static class InsertTransactionDirectionAsyncTask extends AsyncTask<TransactionDirection, Void, Long>{
        private TransactionTypeDao oTransactionTypeDao;
        private static int nTransactionDirectionID;

        private InsertTransactionDirectionAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Long doInBackground(TransactionDirection... transactionDirections) {
            return oTransactionTypeDao.insertTransactionDirection(transactionDirections[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            this.nTransactionDirectionID = aLong.intValue();
        }

        public static int getTransactionDirectionID() {
            return nTransactionDirectionID;
        }
    }

    private static class UpdateTransactionDirectionAsyncTask extends AsyncTask<TransactionDirection, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private UpdateTransactionDirectionAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(TransactionDirection... transactionDirections) {
            oTransactionTypeDao.updateTransactionDirection(transactionDirections[0]);
            return null;
        }
    }

    private static class DeleteTransactionDirectionAsyncTask extends AsyncTask<TransactionDirection, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private DeleteTransactionDirectionAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(TransactionDirection... transactionDirections) {
            oTransactionTypeDao.deleteTransactionDirection(transactionDirections[0]);
            return null;
        }
    }

    private static class DeleteAllTransactionDirectionAsyncTask extends AsyncTask<Void, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private DeleteAllTransactionDirectionAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            oTransactionTypeDao.deleteAllTransactionDirections();
            return null;
        }
    }

    private static class getTransactionTypeByIDAsyncTask extends AsyncTask<Integer, Void, TransactionTypeWithSubtypes> {
        private TransactionTypeDao oTransactionTypeDao;
        private static TransactionTypeWithSubtypes oTransactionTypeWithSubtypes;

        private getTransactionTypeByIDAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected TransactionTypeWithSubtypes doInBackground(Integer... integers) {
            return oTransactionTypeDao.getTypeById(integers[0]);
        }

        @Override
        protected void onPostExecute(TransactionTypeWithSubtypes oTransactionTypeWithSubtypes) {
            this.oTransactionTypeWithSubtypes = oTransactionTypeWithSubtypes;
        }

        public static TransactionTypeWithSubtypes getTransactionTypeWithSubtypes() {
            return oTransactionTypeWithSubtypes;
        }
    }

    private static class InsertTransactionTypeAsyncTask extends AsyncTask<TransactionType, Void, Long>{
        private TransactionTypeDao oTransactionTypeDao;
        private static int nTransactionTypeID;

        private InsertTransactionTypeAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Long doInBackground(TransactionType... transactionTypes) {
            return oTransactionTypeDao.insertTransactionType(transactionTypes[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            this.nTransactionTypeID = aLong.intValue();
        }

        public static int getTransactionTypeID() {
            return nTransactionTypeID;
        }
    }

    private static class UpdateTransactionTypeAsyncTask extends AsyncTask<TransactionType, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private UpdateTransactionTypeAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(TransactionType... transactionTypes) {
            oTransactionTypeDao.updateTransactionType(transactionTypes[0]);
            return null;
        }
    }

    private static class DeleteTransactionTypeAsyncTask extends AsyncTask<TransactionType, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private DeleteTransactionTypeAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(TransactionType... transactionTypes) {
            oTransactionTypeDao.deleteTransactionType(transactionTypes[0]);
            return null;
        }
    }

    private static class DeleteAllTransactionTypeAsyncTask extends AsyncTask<Void, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private DeleteAllTransactionTypeAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            oTransactionTypeDao.deleteAllTransactionTypes();
            return null;
        }
    }

    private static class getTransactionSubtypeByIDAsyncTask extends AsyncTask<Integer, Void, TransactionSubtype> {
        private TransactionTypeDao oTransactionTypeDao;
        private static TransactionSubtype oTransactionSubtype;

        private getTransactionSubtypeByIDAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected TransactionSubtype doInBackground(Integer... integers) {
            Log.i("doInBackground() executed", "doInBackground() start");
            return oTransactionTypeDao.getSubtypeById(integers[0]);
        }

        @Override
        protected void onPostExecute(TransactionSubtype oTransactionSubtype) {
            Log.i("doInBackground() executed", "doInBackground() finish");
            this.oTransactionSubtype = oTransactionSubtype;
        }

        public static TransactionSubtype getTransactionSubtype() {
            return oTransactionSubtype;
        }
    }

    private static class InsertTransactionSubtypeAsyncTask extends AsyncTask<TransactionSubtype, Void, Long>{
        private TransactionTypeDao oTransactionTypeDao;
        private static int nTransactionSubtypeID;

        private InsertTransactionSubtypeAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Long doInBackground(TransactionSubtype... transactionSubtypes) {
            return oTransactionTypeDao.insertTransactionSubtype(transactionSubtypes[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            this.nTransactionSubtypeID = aLong.intValue();
        }

        public static int getTransactionSubtypeID() {
            return nTransactionSubtypeID;
        }
    }

    private static class UpdateTransactionSubtypeAsyncTask extends AsyncTask<TransactionSubtype, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private UpdateTransactionSubtypeAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(TransactionSubtype... transactionSubtypes) {
            oTransactionTypeDao.updateTransactionSubtype(transactionSubtypes[0]);
            return null;
        }
    }

    private static class DeleteTransactionSubtypeAsyncTask extends AsyncTask<TransactionSubtype, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private DeleteTransactionSubtypeAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(TransactionSubtype... transactionSubtypes) {
            oTransactionTypeDao.deleteTransactionSubtype(transactionSubtypes[0]);
            return null;
        }
    }

    private static class DeleteAllTransactionSubtypeAsyncTask extends AsyncTask<Void, Void, Void>{
        private TransactionTypeDao oTransactionTypeDao;

        private DeleteAllTransactionSubtypeAsyncTask(TransactionTypeDao oTransactionTypeDao){
            this.oTransactionTypeDao = oTransactionTypeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            oTransactionTypeDao.deleteAllTransactionSubtypes();
            return null;
        }
    }
}
