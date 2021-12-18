package com.example.pocketexpenses.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;

public class TransactionTypeViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTranTypeName;

    public TransactionTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTranTypeName = itemView.findViewById(R.id.tvTranTypeName);
    }

    public void setTvTranTypeName(String tranTypeName) {
        tvTranTypeName.setText(tranTypeName);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
