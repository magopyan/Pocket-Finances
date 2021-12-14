package com.example.pocketexpenses.recyclers;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.ViewModel.TransactionViewModel;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.viewholders.TransactionViewHolder;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    private TransactionViewModel oTransactionViewModel;
    private List<Transaction> oListTransactions;
    private List<Account> oListAccounts;
    private List<TransactionSubtype> oListTransactionSubtypes;

    public TransactionsAdapter(TransactionViewModel oTransactionViewModel) {
        this.oTransactionViewModel = oTransactionViewModel;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_viewholder, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction oTransaction = oListTransactions.get(position);

        holder.setTvDate(oTransaction.getDate());
        holder.setTvTransactionSum(String.valueOf(oTransaction.getSum()));
        holder.setTvNote(oTransaction.getNote());

        for(Account oAccount : oListAccounts){
            if(oAccount.getId() == oTransaction.getAccountId())
                holder.setTvAccountName(oAccount.getName());
        }

        for(TransactionSubtype oTransactionSubtype : oListTransactionSubtypes){
            if(oTransactionSubtype.getId() == oTransaction.getTransactionSubtypeId())
                holder.setTvTransactionType(oTransactionSubtype.getName());
        }

        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(v.getContext(), holder.itemView);
                //inflating menu from xml resource
                popup.inflate(R.menu.on_long_click_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                return true;
                            case R.id.delete:
                                oTransactionViewModel.deleteTransaction(oTransaction);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(oListTransactions != null && oListTransactions.size() != 0) {
            return oListTransactions.size();
        }
        else return 0;
    }

    public void setTransactionSubtypesData(List<TransactionSubtype> oListTransactionSubtypes){
        this.oListTransactionSubtypes = oListTransactionSubtypes;
        notifyDataSetChanged();
    }

    public void setAccountsData(List<Account> oListAccounts){
        this.oListAccounts = oListAccounts;
        notifyDataSetChanged();
    }

    public void setTransactionsData(List<Transaction> oListTransactions){
        this.oListTransactions = oListTransactions;
        notifyDataSetChanged();
    }
}
