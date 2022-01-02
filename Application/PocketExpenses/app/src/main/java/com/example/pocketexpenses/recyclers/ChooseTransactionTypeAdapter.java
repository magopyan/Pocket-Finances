package com.example.pocketexpenses.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.entities.relationships.TransactionTypeWithSubtypes;
import com.example.pocketexpenses.onclicklisteners.OnTransactionTypeClickListener;
import com.example.pocketexpenses.viewholders.AccountViewHolder;
import com.example.pocketexpenses.viewholders.TransactionTypeViewHolder;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChooseTransactionTypeAdapter extends RecyclerView.Adapter<TransactionTypeViewHolder> {

    private TransactionTypeViewModel oViewModel;
    private OnTransactionTypeClickListener listener;

    private List<TransactionDirectionWithTypesAndSubtypes> oTranDirWithTypesAndSubtypes;
    private List<TransactionType> oTransactionTypes;

    public ChooseTransactionTypeAdapter(TransactionTypeViewModel oViewModel, OnTransactionTypeClickListener listener) {
        this.oViewModel = oViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_type_viewholder, parent, false);
        return new TransactionTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionTypeViewHolder holder, int position) {
        TransactionType transactionType = oTransactionTypes.get(position);
        holder.setTvTranTypeName(transactionType.getName());
        holder.setImage(transactionType.getImageId());

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickTransactionType(transactionType);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(oTransactionTypes != null && oTransactionTypes.size() != 0) {
            return oTransactionTypes.size();
        }
        else return 0;
    }

    public void setData(List<TransactionDirectionWithTypesAndSubtypes> oTranDirWithTypesAndSubtypes){
        this.oTranDirWithTypesAndSubtypes = oTranDirWithTypesAndSubtypes;
        oTransactionTypes = new ArrayList<>();
        for(TransactionDirectionWithTypesAndSubtypes tranDir : oTranDirWithTypesAndSubtypes) {
            if(tranDir.getTransactionDirection().getId() == 2) { // Expense
                List<TransactionTypeWithSubtypes> oTranTypesList = tranDir.getTypeWithSubtypesList();
                for(TransactionTypeWithSubtypes tranTypeWithSubtype : oTranTypesList) {
                    oTransactionTypes.add(tranTypeWithSubtype.getTransactionType());
                }
            }
        }
        notifyDataSetChanged();
    }
}
