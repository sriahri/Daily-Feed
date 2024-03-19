package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.WeatherModel;
import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.databinding.ActivityWeatherBinding;

public class WeatherActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    ArrayList<WeatherModel> weatherModelArrayList;
    WeatherAdapter weatherAdapter;
    LocationManager locationManager;
    int PERMISSION = 1;
    String cityNameInput;
    ActivityWeatherBinding binding;
    private RelativeLayout home;
    private ProgressBar loading;
    private TextView cityName, temperature, condition;
    private TextInputEditText cityEdit;
    private ImageView background, icon, search;
    private RecyclerView weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        home = binding.Home;
        loading = binding.Loading;
        cityName = binding.CityName;
        temperature = binding.temperature;
        condition = binding.condition;
        cityEdit = binding.EditCityName;
        background = binding.backgroudImage;
        icon = binding.icon;
        search = binding.search;
        weather = binding.weather;
        weatherModelArrayList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherModelArrayList);
        weather.setAdapter(weatherAdapter);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeatherActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        cityNameInput = getCityName(location.getLongitude(), location.getLatitude());
        getWeatherDetails(cityNameInput);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = cityEdit.getText().toString();
                if (city.equalsIgnoreCase("")) {
                    Toast.makeText(WeatherActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    cityName.setText(cityNameInput);
                    getWeatherDetails(city);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(WeatherActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(WeatherActivity.this, "Please provide the permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    String getCityName(double longitude, double latitude) {
        String cityName = "";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 10);

            for (Address address : addresses) {
                if (address != null) {
                    String city = address.getLocality();
                    if (city != null && !city.equalsIgnoreCase("")) {
                        cityName = city;
                    }
                } else {
                    Log.d("TAG", "CITY NOT FOUND");
                    Toast.makeText(this, "City not found", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cityName;
    }

    void getWeatherDetails(String city) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=814106c5c4d44b4fb69180108231507&q=" + city + "&days=1&aqi=yes&alerts=yes\n";
        cityName.setText(city);
        RequestQueue requestQueue = Volley.newRequestQueue(WeatherActivity.this);
        Log.i("Info", "Weather output: " + weatherModelArrayList.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.setVisibility(View.GONE);
                home.setVisibility(View.VISIBLE);
                weatherModelArrayList.clear();
                try {

                    String temperatureOutput = response.getJSONObject("current").getString("temp_c");
                    temperature.setText(temperatureOutput.concat("°C"));
                    int isDayTime = response.getJSONObject("current").getInt("is_day");
                    String conditionOutput = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIconOutput = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIconOutput)).into(icon);
                    condition.setText(conditionOutput);
                    Log.i("Info", "Is Day: " + isDayTime);
                    if (isDayTime == 1) {
                        binding.condition.setTextColor(Color.RED);
                        // binding.editcityname(Color.BLACK);
                        Picasso.get().load("http://images.unsplash.com/photo-1530908295418-a12e326966ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80").into(background);
                    } else {
                        binding.condition.setTextColor(Color.YELLOW);
                        // binding.editcityname.setBackgroundColor(Color.WHITE);
                        Picasso.get().load("http://images.unsplash.com/photo-1531306728370-e2ebd9d7bb99?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1887&q=80").into(background);
                    }

                    JSONObject forecasting = response.getJSONObject("forecast");
                    JSONObject forecastDay = forecasting.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray forecastHours = forecastDay.getJSONArray("hour");

                    for (int i = 0; i < forecastHours.length(); i++) {
                        JSONObject hour = forecastHours.getJSONObject(i);
                        String time = hour.getString("time");
                        String temp = hour.getString("temp_c");
                        String img = hour.getJSONObject("condition").getString("icon");
                        String wspeed = hour.getString("wind_kph");
                        weatherModelArrayList.add(new WeatherModel(time, temp, img, wspeed));


                    }

                    weatherAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this, "Please enter valid city", Toast.LENGTH_SHORT).show();
                Log.i("Info", "Error response given: " + error);
            }
        });

        requestQueue.add(jsonObjectRequest);
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
//        if (id == R.id.menu_logout) {
//            Toast.makeText(this, "User logged out", Toast.LENGTH_SHORT).show();
//            mAuth.signOut();
//            startActivity(new Intent(MainActivity.this, Login.class));
////            this.finish();
//            return true;
//        }

//        else if (id == R.id.menu_Workout) {
//            Toast.makeText(this, "Showing workouts", Toast.LENGTH_SHORT).show();
//            // mAuth.signOut();
//            startActivity(new Intent(MainActivity.this, ViewWorkOutsActivity.class));
////            this.finish();
//            return true;
//        }

//        if (id == R.id.menu_reminders) {
//            return true;
//        }
        if (id == R.id.menu_logout) {
            Toast.makeText(this, "User logged out", Toast.LENGTH_SHORT).show();
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(WeatherActivity.this, Login.class));
//            this.finish();
            return true;
        }

        else if (id == R.id.menu_expenses) {
            Toast.makeText(this, "Showing Expense", Toast.LENGTH_SHORT).show();
//            mAuth.signOut();
            startActivity(new Intent(WeatherActivity.this, ExpenseActivity.class));
//            this.finish();
            return true;
        }

        else if (id == R.id.menu_weather) {
            Toast.makeText(this, "Showing weather", Toast.LENGTH_SHORT).show();
            // mAuth.signOut();
            startActivity(new Intent(WeatherActivity.this, WeatherActivity.class));
            // this.finish();
            return true;
        }

        else if (id == R.id.menu_news) {
            Toast.makeText(this, "Showing News", Toast.LENGTH_SHORT).show();
            // mAuth.signOut();
            startActivity(new Intent(WeatherActivity.this, MainActivity.class));
            // this.finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}