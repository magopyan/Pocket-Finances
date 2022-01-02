package com.example.pocketexpenses.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.onclicklisteners.OnAccountTypeClickListener;
import com.example.pocketexpenses.viewholders.AccountTypeViewHolder;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;

import java.util.List;

public class ChooseAccountTypeAdapter extends RecyclerView.Adapter<AccountTypeViewHolder> {

    private AccountTypeViewModel oViewModel;
    private OnAccountTypeClickListener listener;

    private List<AccountType> oAccountTypes;

    public ChooseAccountTypeAdapter(AccountTypeViewModel oViewModel, OnAccountTypeClickListener listener) {
        this.oViewModel = oViewModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccountTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_type_viewholder, parent, false);
        return new AccountTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountTypeViewHolder holder, int position) {
        AccountType oAccountType = oAccountTypes.get(position);
        holder.setAccountTypeName(oAccountType.getName());
        holder.setImage(oAccountType.getImageId());

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAccountType(oAccountType);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(oAccountTypes != null && oAccountTypes.size() != 0) {
            return oAccountTypes.size();
        }
        else return 0;
    }

    public void setData(List<AccountType> oAccountTypes) {
        this.oAccountTypes = oAccountTypes;
        notifyDataSetChanged();
    }
}
