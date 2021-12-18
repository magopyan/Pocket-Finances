package com.example.pocketexpenses.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.AccountViewholderBinding;


public class AccountViewHolder extends RecyclerView.ViewHolder {

    private TextView tvAccountName;
    private TextView tvAccountType;
    private TextView tvBalance;

    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAccountName = itemView.findViewById(R.id.tvAccountName);
        tvAccountType = itemView.findViewById(R.id.tvAccountType);
        tvBalance = itemView.findViewById(R.id.tvTranTypeName);
    }

    public void setTvAccountName(String tvAccountName) {
        this.tvAccountName.setText(tvAccountName);
    }

    public void setTvAccountType(String tvAccountType) {
        this.tvAccountType.setText(tvAccountType);
    }

    public void setTvBalance(String tvBalance) {
        this.tvBalance.setText(tvBalance);
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
