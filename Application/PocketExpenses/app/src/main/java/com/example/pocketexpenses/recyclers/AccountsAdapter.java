package com.example.pocketexpenses.recyclers;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.ViewModel.AccountTypeViewModel;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.viewholders.AccountViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountViewHolder> {

    private AccountTypeViewModel oViewModel;

    private List<AccountTypeWithAccounts> oAccountTypesWithAccounts;
    private List<Account> oAccountsList;

    public AccountsAdapter(AccountTypeViewModel viewModel) {
        this.oViewModel = viewModel;
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
                                oViewModel.deleteAccount(account);
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
