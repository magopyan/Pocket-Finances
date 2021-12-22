package com.example.pocketexpenses.recyclers;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.activities.AccountsEditActivity;
import com.example.pocketexpenses.activities.AccountsInputActivity;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.viewholders.AccountViewHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
                PopupMenu popup = new PopupMenu(v.getContext(), holder.itemView);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                Intent intent = new Intent(v.getContext(), AccountsEditActivity.class);
                                intent.putExtra("topBarTitle", "Add Account");
                                intent.putExtra("AccountItem", account);
                                Activity tempActivity = new Activity();
                                tempActivity.startActivity(intent);
                                //tempActivity.startActivityForResult(intent, REQUEST_EDIT_CODE);
                                return true;
                            case R.id.delete:
                                oViewModel.deleteAccount(account);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.inflate(R.menu.on_long_click_menu);

                // Zaobikolen nachin da se pokazvat ikonki za vseki item, koeto po princip e nevuzmojno
                Object menuHelper;
                Class[] argTypes;
                try {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    menuHelper = fMenuHelper.get(popup);
                    argTypes = new Class[]{boolean.class};
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
