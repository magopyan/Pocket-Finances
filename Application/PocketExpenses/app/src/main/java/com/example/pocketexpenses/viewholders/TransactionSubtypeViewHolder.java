package com.example.pocketexpenses.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;

public class TransactionSubtypeViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTranSubtypeName;
    private ImageView oImageView;

    public TransactionSubtypeViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTranSubtypeName = itemView.findViewById(R.id.tvTranSubtypeName);
        oImageView = itemView.findViewById(R.id.imageView5);
    }

    public void setTvTranSubtypeName(String tranSubtypeName) {
        tvTranSubtypeName.setText(tranSubtypeName);
    }

    public void setImage(int imageId) {
        this.oImageView.setImageResource(imageId);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
