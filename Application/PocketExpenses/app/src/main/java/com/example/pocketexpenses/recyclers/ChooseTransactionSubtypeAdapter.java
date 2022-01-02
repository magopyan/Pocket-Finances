package com.example.pocketexpenses.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.entities.relationships.TransactionTypeWithSubtypes;
import com.example.pocketexpenses.onclicklisteners.OnTransactionSubtypeClickListener;
import com.example.pocketexpenses.viewholders.TransactionSubtypeViewHolder;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChooseTransactionSubtypeAdapter extends RecyclerView.Adapter<TransactionSubtypeViewHolder> {

    private TransactionTypeViewModel oViewModel;
    private OnTransactionSubtypeClickListener listener;
    private int tranTypeId;

    private List<TransactionDirectionWithTypesAndSubtypes> oTranDirWithTypesAndSubtypes;
    private List<TransactionDirection> oTranDirList;
    private List<TransactionSubtype> oTransactionSubtypes;

    public ChooseTransactionSubtypeAdapter(TransactionTypeViewModel oViewModel, OnTransactionSubtypeClickListener listener, int tranTypeId) {
        this.oViewModel = oViewModel;
        this.listener = listener;
        this.tranTypeId = tranTypeId;
    }


    @NonNull
    @Override
    public TransactionSubtypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_subtype_viewholder, parent, false);
        return new TransactionSubtypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionSubtypeViewHolder holder, int position) {
        TransactionSubtype transactionSubtype = oTransactionSubtypes.get(position);
        holder.setTvTranSubtypeName(transactionSubtype.getName());
        holder.setImage(transactionSubtype.getImageId());

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickTransactionSubtype(transactionSubtype);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(oTransactionSubtypes != null && oTransactionSubtypes.size() != 0) {
            return oTransactionSubtypes.size();
        }
        else return 0;
    }

    // Get all Subtypes and loop to find the ones with Type Foreign Key matching
//    public void setData(List<TransactionSubtype> oTransactionSubtypes) {
//        for(TransactionSubtype subtype : oTransactionSubtypes) {
//            if(subtype.getTransactionTypeId() == tranTypeId) {
//                this.oTransactionSubtypes.add(subtype);
//            }
//        }
//    }

    // Get from double nested relationship
    public void setDataForExpense(List<TransactionDirectionWithTypesAndSubtypes> oTranDirWithTypesAndSubtypes) {
        this.oTranDirWithTypesAndSubtypes = oTranDirWithTypesAndSubtypes;
        oTransactionSubtypes = new ArrayList<>();
        for(TransactionDirectionWithTypesAndSubtypes tranDir : oTranDirWithTypesAndSubtypes) {
            if(tranDir.getTransactionDirection().getId() == 2) { // Expense
                for(TransactionTypeWithSubtypes tranTypeWithSubtype : tranDir.getTypeWithSubtypesList()) {
                    if(tranTypeWithSubtype.getTransactionType().getId() == tranTypeId) {
                        oTransactionSubtypes.addAll(tranTypeWithSubtype.getSubtypesList());
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setDataForIncome(List<TransactionDirectionWithTypesAndSubtypes> oTranDirWithTypesAndSubtypes) {
        this.oTranDirWithTypesAndSubtypes = oTranDirWithTypesAndSubtypes;
        oTransactionSubtypes = new ArrayList<>();
        for(TransactionDirectionWithTypesAndSubtypes tranDir : oTranDirWithTypesAndSubtypes) {
            if(tranDir.getTransactionDirection().getId() == 1) { // Income
                List<TransactionTypeWithSubtypes> tranTypesWithSubtypes = tranDir.getTypeWithSubtypesList();
                for(TransactionTypeWithSubtypes tranTypeWithSubtype : tranTypesWithSubtypes) {
                    TransactionTypeWithSubtypes transactionTypeWithSubtypes = tranTypeWithSubtype;
                    oTransactionSubtypes.addAll(tranTypeWithSubtype.getSubtypesList());
                }
            }
        }
        notifyDataSetChanged();
    }
}
