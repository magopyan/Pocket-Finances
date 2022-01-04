package com.example.pocketexpenses.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pocketexpenses.repositories.TransactionTypeRepository;
import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.entities.relationships.TransactionTypeWithSubtypes;

import java.util.List;

public class TransactionTypeViewModel extends AndroidViewModel {
    private TransactionTypeRepository oTransactionTypeRepository;
    private LiveData<List<TransactionDirectionWithTypesAndSubtypes>> oLiveDataListAllTransactionDirectionWithTypesAndSubtypes;
    private LiveData<List<TransactionDirection>> oLiveDataListAllTransactionDirections;
    private LiveData<List<TransactionTypeWithSubtypes>> oLiveDataListAllTransactionTypeWithSubtypes;
    private LiveData<List<TransactionType>> oLiveDataListAllTransactionTypes;
    private LiveData<List<TransactionSubtype>> oLiveDataListAllTransactionSubtype;

    public TransactionTypeViewModel(@NonNull Application application) {
        super(application);
        oTransactionTypeRepository = new TransactionTypeRepository(application);
        oLiveDataListAllTransactionDirectionWithTypesAndSubtypes = oTransactionTypeRepository.getAllTransactionDirectionWithTypesAndSubtypes();
        oLiveDataListAllTransactionDirections = oTransactionTypeRepository.getAllTransactionDirections();
        oLiveDataListAllTransactionTypeWithSubtypes = oTransactionTypeRepository.getAllTransactionTypeWithSubtypes();
        oLiveDataListAllTransactionTypes = oTransactionTypeRepository.getAllTransactionTypes();
        oLiveDataListAllTransactionSubtype = oTransactionTypeRepository.getAllTransactionSubtype();
    }

    public LiveData<TransactionDirectionWithTypesAndSubtypes> getTransactionDirectionWithTypesAndSubtypesByID(int nID){
        return oTransactionTypeRepository.getTransactionDirectionWithTypesAndSubtypesByID(nID);
    }

    public LiveData<TransactionDirection> getTransactionDirectionByID(int nID){
        return oTransactionTypeRepository.getTransactionDirectionByID(nID);
    }

    public int insertTransactionDirection(TransactionDirection oTransactionDirection){
        return oTransactionTypeRepository.insertTransactionDirection(oTransactionDirection);
    }

    public void updateTransactionDirection(TransactionDirection oTransactionDirection){
        oTransactionTypeRepository.updateTransactionDirection(oTransactionDirection);
    }

    public void deleteTransactionDirection(TransactionDirection oTransactionDirection){
        oTransactionTypeRepository.deleteTransactionDirection(oTransactionDirection);
    }

    public void deleteAllTransactionDirections(){
        oTransactionTypeRepository.deleteAllTransactionDirection();
    }

    public LiveData<List<TransactionDirectionWithTypesAndSubtypes>> getAllTransactionDirectionWithTypesAndSubtypes(){
        return oLiveDataListAllTransactionDirectionWithTypesAndSubtypes;
    }

    public LiveData<List<TransactionDirection>> getAllTransactionDirections(){
        return oLiveDataListAllTransactionDirections;
    }

    public LiveData<TransactionTypeWithSubtypes> getTransactionTypeWithSubtypesByID(int nID){
        return oTransactionTypeRepository.getTransactionTypeWithSubtypesByID(nID);
    }

    public LiveData<TransactionType> getTransactionTypeByID(int nID){
        return oTransactionTypeRepository.getTransactionTypeByID(nID);
    }

    public int insertTransactionType(TransactionType oTransactionType){
        return oTransactionTypeRepository.insertTransactionType(oTransactionType);
    }

    public void updateTransactionType(TransactionType oTransactionType){
        oTransactionTypeRepository.updateTransactionType(oTransactionType);
    }

    public void deleteTransactionType(TransactionType oTransactionType){
        oTransactionTypeRepository.deleteTransactionType(oTransactionType);
    }

    public void deleteAllTransactionType(){
        oTransactionTypeRepository.deleteAllTransactionType();
    }

    public LiveData<List<TransactionTypeWithSubtypes>> getAllTransactionTypeWithSubtypes(){
        return oLiveDataListAllTransactionTypeWithSubtypes;
    }

    public LiveData<List<TransactionType>> getAllTransactionTypes(){
        return oLiveDataListAllTransactionTypes;
    }

    public LiveData<TransactionSubtype> getTransactionSubtypeByID(int nID) {
        return oTransactionTypeRepository.getTransactionSubtypeByID(nID);
    }

    public int insertTransactionSubtype(TransactionSubtype oTransactionSubtype){
        return oTransactionTypeRepository.insertTransactionSubtype(oTransactionSubtype);
    }

    public void updateTransactionSubtype(TransactionSubtype oTransactionSubtype){
        oTransactionTypeRepository.updateTransactionSubtype(oTransactionSubtype);
    }

    public void deleteTransactionSubtype(TransactionSubtype oTransactionSubtype){
        oTransactionTypeRepository.deleteTransactionSubtype(oTransactionSubtype);
    }

    public void deleteAllTransactionSubtype(){
        oTransactionTypeRepository.deleteAllTransactionSubtype();
    }

    public LiveData<List<TransactionSubtype>> getAllTransactionSubtypes(){
        return oLiveDataListAllTransactionSubtype;
    }
}
