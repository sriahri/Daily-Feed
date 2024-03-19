package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.Headlines;
import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.databinding.ActivityNewsDetailsBinding;

public class NewsDetailsActivity extends AppCompatActivity {

    Headlines headlines;
    TextView textView_details_title;
    ImageView imageView_details_news;
    TextView textView_details_author;
    TextView textView_details_time;
    TextView textView_details_details;
    TextView textView_details_content;
    ActivityNewsDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailsBinding.inflate(LayoutInflater.from(getApplicationContext()));
        setContentView(binding.getRoot());

        textView_details_title = binding.textViewDetailsTitle;
        imageView_details_news = binding.imageViewDetailsNews;
        textView_details_author = binding.textViewDetailsAuthor;
        textView_details_time = binding.textViewDetailsTime;
        textView_details_details = binding.textViewDetailsDetails;
        textView_details_content = binding.textViewDetailsContent;

        headlines = (Headlines) getIntent().getSerializableExtra("data");

        textView_details_title.setText(headlines.getTitle());
        textView_details_author.setText(headlines.getAuthor());
        textView_details_time.setText(headlines.getPublishedAt());
        textView_details_details.setText(headlines.getDescription());
        textView_details_content.setText(headlines.getContent());

        Picasso.get().load(headlines.getUrlToImage()).into(imageView_details_news);
    }
}