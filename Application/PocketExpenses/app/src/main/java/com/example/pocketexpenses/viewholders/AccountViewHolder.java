package com.example.pocketexpenses.viewholders;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;


public class AccountViewHolder extends RecyclerView.ViewHolder {

    private TextView tvAccountName;
    private TextView tvAccountType;
    private TextView tvBalance;
    private ImageView oImageView;

    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAccountName = itemView.findViewById(R.id.tvAccountName);
        tvAccountType = itemView.findViewById(R.id.tvAccountType);
        tvBalance = itemView.findViewById(R.id.tvBalance);
        oImageView = itemView.findViewById(R.id.accountImageView);
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

    public void setImage(int imageId) {
        this.oImageView.setImageResource(imageId);
    }

    public TextView getBalanceTextView() {
        return tvBalance;
    }

    public void setTvBalanceColor(int color) {
        tvBalance.setTextColor(color);
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
