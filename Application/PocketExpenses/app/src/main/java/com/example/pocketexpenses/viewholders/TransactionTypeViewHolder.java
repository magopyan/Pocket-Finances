package com.example.pocketexpenses.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;

public class TransactionTypeViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTranTypeName;
    private ImageView oImageView;

    public TransactionTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTranTypeName = itemView.findViewById(R.id.tvTranTypeName);
        oImageView = itemView.findViewById(R.id.imageView4);
    }

    public void setTvTranTypeName(String tranTypeName) {
        tvTranTypeName.setText(tranTypeName);
    }

    public void setImage(int imageId) {
        this.oImageView.setImageResource(imageId);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
