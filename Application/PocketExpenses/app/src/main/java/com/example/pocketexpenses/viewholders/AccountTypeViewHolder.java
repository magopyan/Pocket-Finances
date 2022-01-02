package com.example.pocketexpenses.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;

public class AccountTypeViewHolder extends RecyclerView.ViewHolder {

    private TextView tvAccountTypeName;
    private ImageView oImageView;

    public AccountTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAccountTypeName = itemView.findViewById(R.id.tvAccountTypeName);
        oImageView = itemView.findViewById(R.id.imageViewAccountTypeViewHolder);
    }

    public void setAccountTypeName(String strAccountTypeName) {
        this.tvAccountTypeName.setText(strAccountTypeName);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    public void setImage(int imageId) {
        this.oImageView.setImageResource(imageId);
    }
}
