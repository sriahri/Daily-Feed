package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.Headlines;

public class CustomFeedAdapter extends RecyclerView.Adapter<CustomNewsViewHolder> {

    private final Context context;
    private final List<Headlines> headlines;

    private final SelectClickListener listener;

    public CustomFeedAdapter(Context context, List<Headlines> headlines, SelectClickListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CustomNewsViewHolder(LayoutInflater.from(context).inflate(R.layout.headlines_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomNewsViewHolder holder, int position) {

        holder.textView_title.setText(headlines.get(position).getTitle());
        holder.textView_source.setText(headlines.get(position).getSource().getName());

        if (headlines.get(position).getUrlToImage() != null) {
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.imageView_newsImage);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNewsClicked(headlines.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
