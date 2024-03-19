package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.Headlines;
import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.News;
import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements SelectClickListener, View.OnClickListener {


    RecyclerView recyclerView;
    CustomFeedAdapter customFeedAdapter;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    private final DataListener<News> listener = new DataListener<News>() {
        @Override
        public void onFetchData(List<Headlines> headlines, String message) {
            if (headlines.isEmpty()) {
                Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            } else {
                showNews(headlines);
                progressDialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "Error occurred while getting details.", Toast.LENGTH_LONG).show();
        }
    };
    Button button_business;
    Button button_sports;
    Button button_technology;
    Button button_entertainment;
    Button button_general;
    Button button_health;
    Button button_science;
    Button button_weather;
    ActivityMainBinding binding;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        searchView = binding.searchViewSearch;
        mAuth = FirebaseAuth.getInstance();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Getting things ready for ".concat(query).concat("..."));
                progressDialog.show();
                ManageRequests requests = new ManageRequests(MainActivity.this);
                requests.getNews(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Getting things ready...");
        progressDialog.show();

        button_business = binding.buttonBusiness;
        button_business.setOnClickListener(this);
        button_entertainment = binding.buttonEntertainment;
        button_entertainment.setOnClickListener(this);
        button_general = binding.buttonGeneral;
        button_general.setOnClickListener(this);
        button_health = binding.buttonHealth;
        button_health.setOnClickListener(this);
        button_science = binding.buttonScience;
        button_science.setOnClickListener(this);
        button_technology = binding.buttonTechnology;
        button_technology.setOnClickListener(this);
        button_sports = binding.buttonSports;
        button_sports.setOnClickListener(this);
        button_weather = binding.buttonWeather;
        button_weather.setOnClickListener(this);

        ManageRequests requests = new ManageRequests(this);
        requests.getNews(listener, "general", null);


    }

    private void showNews(List<Headlines> headlines) {

        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        customFeedAdapter = new CustomFeedAdapter(this, headlines, this);

        recyclerView.setAdapter(customFeedAdapter);

    }

    @Override
    public void onNewsClicked(Headlines headlines) {
        startActivity(new Intent(MainActivity.this, NewsDetailsActivity.class).putExtra("data", headlines));

    }

    @Override
    public void onClick(View view) {

        Button button = (Button) view;
        String category = button.getText().toString().toLowerCase();
        if (category.equalsIgnoreCase("Weather")) {
            progressDialog.setTitle("Getting things ready for ".concat(category).concat("..."));
            progressDialog.show();
            startActivity(new Intent(MainActivity.this, WeatherActivity.class).putExtra("name", "value"));
            progressDialog.dismiss();
        } else {
            progressDialog.setTitle("Getting things ready for ".concat(category).concat("..."));
            progressDialog.show();
            ManageRequests requests = new ManageRequests(this);
            requests.getNews(listener, category, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("Info", "Menu Created:");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            Toast.makeText(this, "User logged out", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
//            this.finish();
            return true;
        }

//        else if (id == R.id.menu_Workout) {
//            Toast.makeText(this, "Showing workouts", Toast.LENGTH_SHORT).show();
//            // mAuth.signOut();
//            startActivity(new Intent(MainActivity.this, ViewWorkOutsActivity.class));
////            this.finish();
//            return true;
//        }

//        else if (id == R.id.menu_reminders) {
//            return true;
//        }

        else if (id == R.id.menu_expenses) {
            Toast.makeText(this, "Showing Expense", Toast.LENGTH_SHORT).show();
//            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
//            this.finish();
            return true;
        }

        else if (id == R.id.menu_weather) {
            Toast.makeText(this, "Showing weather", Toast.LENGTH_SHORT).show();
            // mAuth.signOut();
            startActivity(new Intent(MainActivity.this, WeatherActivity.class));
            // this.finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}