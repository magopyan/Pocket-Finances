package com.example.pocketexpenses.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.onclicklisteners.OnAccountClickListener;
import com.example.pocketexpenses.viewholders.AccountViewHolder;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChooseAccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {

    private AccountTypeViewModel oViewModel;

    private OnAccountClickListener listener;

    private List<AccountTypeWithAccounts> oAccountTypesWithAccounts;
    private List<Account> oAccountsList;

    public ChooseAccountAdapter(AccountTypeViewModel oViewModel, OnAccountClickListener listener) {
        this.oViewModel = oViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_viewholder, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = oAccountsList.get(position);
        String accountTypeName = null;
        for(AccountTypeWithAccounts accTypeWithAccounts : oAccountTypesWithAccounts) {
            AccountType accType = accTypeWithAccounts.getAccountType();
            if(accType.getId() == account.getAccountTypeId())
                accountTypeName = accType.getName();
        }
        holder.setTvAccountName(account.getName());
        holder.setTvAccountType(accountTypeName);
        holder.setTvBalance(String.valueOf(account.getBalance()));
        holder.setImage(account.getImageId());

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAccount(account);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(oAccountsList != null && oAccountsList.size() != 0) {
            return oAccountsList.size();
        }
        else return 0;
    }

    public void setData(List<AccountTypeWithAccounts> accTypesWithAccounts){
        oAccountTypesWithAccounts = accTypesWithAccounts;
        oAccountsList = new ArrayList<>();
        for(AccountTypeWithAccounts accTypeWithAccounts : oAccountTypesWithAccounts) {
            oAccountsList.addAll(accTypeWithAccounts.getAccountList());
        }
        notifyDataSetChanged();
    }
}
