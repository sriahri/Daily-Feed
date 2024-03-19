package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomNewsViewHolder extends RecyclerView.ViewHolder {

    TextView textView_title;
    TextView textView_source;
    ImageView imageView_newsImage;

    CardView cardView;

    public CustomNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_source = itemView.findViewById(R.id.textView_source);
        imageView_newsImage = itemView.findViewById(R.id.imageView_newsImage);
        cardView = itemView.findViewById(R.id.container_main);
    }

    public TextView getTextView_title() {
        return textView_title;
    }

    public void setTextView_title(TextView textView_title) {
        this.textView_title = textView_title;
    }

    public TextView getTextView_source() {
        return textView_source;
    }

    public void setTextView_source(TextView textView_source) {
        this.textView_source = textView_source;
    }

    public ImageView getImageView_newsImage() {
        return imageView_newsImage;
    }

    public void setImageView_newsImage(ImageView imageView_newsImage) {
        this.imageView_newsImage = imageView_newsImage;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }
}
