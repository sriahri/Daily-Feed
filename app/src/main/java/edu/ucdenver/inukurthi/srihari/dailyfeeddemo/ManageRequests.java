package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import android.content.Context;
import android.widget.Toast;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ManageRequests {

    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/").addConverterFactory(GsonConverterFactory.create()).build();

//    public ManageRequests() {
//        retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/").addConverterFactory(GsonConverterFactory.create()).build();
//
//    }


    public ManageRequests(Context context) {
        this.context = context;
//        retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/").addConverterFactory(GsonConverterFactory.create()).build();
    }

    public void getNews(DataListener listener, String category, String queryWord) {
        CallAPI callAPI = retrofit.create(CallAPI.class);
        Call<News> call = callAPI.callHeadlines("us", category, queryWord, context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Error while loading...", Toast.LENGTH_LONG);
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    listener.onError("Error while loading. Request Failed.");
                }
            });
        } catch (Exception ex) {

        }
    }

    public interface CallAPI {
        @GET("top-headlines")
        Call<News> callHeadlines(@Query("country") String country, @Query("category") String category, @Query("q") String queryWord, @Query("apiKey") String apiKey);
    }
}
