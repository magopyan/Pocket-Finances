package com.example.pocketexpenses.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;

public class AccountTypeViewHolder extends RecyclerView.ViewHolder {

    private TextView tvAccountTypeName;

    public AccountTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAccountTypeName = itemView.findViewById(R.id.tvAccountTypeName);
    }

    public void setAccountTypeName(String strAccountTypeName) {
        this.tvAccountTypeName.setText(strAccountTypeName);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
