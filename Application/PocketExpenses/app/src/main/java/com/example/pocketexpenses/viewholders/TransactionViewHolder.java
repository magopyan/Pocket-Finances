package com.example.pocketexpenses.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDate;
    private TextView tvTransactionSum;
    private TextView tvNote;
    private TextView tvAccountName;
    private TextView tvTransactionSubType;
    private ImageView oImageView;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvTransactionSum = itemView.findViewById(R.id.tvAmount);
        tvNote = itemView.findViewById(R.id.tvNote);
        tvAccountName = itemView.findViewById(R.id.tvAccountNameInTransaction);
        tvTransactionSubType = itemView.findViewById(R.id.tvTransactionSubType);
        oImageView = itemView.findViewById(R.id.transactionImageView);
    }

    public void setTvDate(String tvDate) {
        this.tvDate.setText(tvDate);
    }

    public void setTvTransactionSum(String tvTransactionSum) {
        this.tvTransactionSum.setText(tvTransactionSum);
    }

    public void setTvNote(String tvNote) {
        this.tvNote.setText(tvNote);
    }

    public void setTvAccountName(String tvAccountName) {
        this.tvAccountName.setText(tvAccountName);
    }

    public void setTvTransactionType(String tvTransactionType) {
        this.tvTransactionSubType.setText(tvTransactionType);
    }

    public void setImage(int imageId) {
        this.oImageView.setImageResource(imageId);
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }
}
