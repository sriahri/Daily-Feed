package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.Expense;
import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.databinding.ActivityExpenseBinding;

public class ExpenseActivity extends AppCompatActivity implements OnItemsClicked{

    ActivityExpenseBinding binding;
    private ExpenseAdapter expenseAdapter;
    Intent intent;

    private double expenseAmount;
    private double incomeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = ActivityExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        expenseAdapter = new ExpenseAdapter(this, this);

        binding.recyclerViewExpenses.setAdapter(expenseAdapter);
        binding.recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(this));
        getData();
        expenseAdapter.notifyDataSetChanged();
        binding.textViewIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseActivity.this, AddExpenseActivity.class).putExtra("type", "Income"));
            }
        });

        binding.textViewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseActivity.this, AddExpenseActivity.class).putExtra("type", "Expense"));
            }
        });

        expenseAdapter.notifyDataSetChanged();
    }

    private void getData() {
        FirebaseFirestore.
                getInstance().
                collection("expenses").
                whereEqualTo("uId", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        expenseAdapter.clear();
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();


                        for(DocumentSnapshot ds: list) {
                            Expense expense = ds.toObject(Expense.class);
                            Log.i("Info", "Expense details: " + expense.toString());

                            if (expense.getType().equalsIgnoreCase("income")) {
                                incomeAmount += expense.getAmount();
                            }
                            else
                                expenseAmount += expense.getAmount();
                            expenseAdapter.add(expense);
                            expenseAdapter.notifyDataSetChanged();
                        }
                        setGraph();

                        expenseAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void setGraph() {
        List<PieEntry> pieEntriesList = new ArrayList<>();
        List<Integer> colorsList = new ArrayList<>();

        if (incomeAmount != 0) {
            pieEntriesList.add(new PieEntry((float) incomeAmount, "Income"));
            colorsList.add(getResources().getColor(R.color.green));
        }

        if (expenseAmount != 0) {
            pieEntriesList.add(new PieEntry((float) expenseAmount, "Expense"));
            colorsList.add(getResources().getColor(R.color.orangish));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntriesList, String.valueOf(incomeAmount-expenseAmount));
        pieDataSet.setColors(colorsList);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.black));
        PieData pieData = new PieData(pieDataSet);

        binding.pieChartGraph.setData(pieData);
        binding.pieChartGraph.invalidate();
    }
    @Override
    public void onClick(Expense expense) {
        startActivity(new Intent(ExpenseActivity.this, AddExpenseActivity.class).putExtra("model", expense));
        expenseAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        incomeAmount = 0;
        expenseAmount = 0;
        getData();
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
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ExpenseActivity.this, Login.class));
//            this.finish();
            return true;
        }

        else if (id == R.id.menu_expenses) {
            Toast.makeText(this, "Showing Expense", Toast.LENGTH_SHORT).show();
//            mAuth.signOut();
            startActivity(new Intent(ExpenseActivity.this, ExpenseActivity.class));
//            this.finish();
            return true;
        }

        else if (id == R.id.menu_weather) {
            Toast.makeText(this, "Showing weather", Toast.LENGTH_SHORT).show();
            // mAuth.signOut();
            startActivity(new Intent(ExpenseActivity.this, WeatherActivity.class));
            // this.finish();
            return true;
        }

        else if (id == R.id.menu_news) {
            Toast.makeText(this, "Showing News", Toast.LENGTH_SHORT).show();
            // mAuth.signOut();
            startActivity(new Intent(ExpenseActivity.this, MainActivity.class));
            // this.finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}