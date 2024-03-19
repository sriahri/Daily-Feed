package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.Expense;
import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.databinding.ActivityAddExpenseBinding;
import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.databinding.ActivityExpenseBinding;

public class AddExpenseActivity extends AppCompatActivity {

    ActivityAddExpenseBinding binding;
    String type;
    private Expense expense;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        type = getIntent().getStringExtra("type");
        expense = (Expense) getIntent().getSerializableExtra("model");
        if (type == null) {
            type = expense.getType();
            binding.editTextAmount.setText(String.valueOf(expense.getAmount()));
            binding.editTextCategory.setText(expense.getCategory());
            binding.editTextNote.setText(expense.getNote());
        }

        if (type.equalsIgnoreCase("income")) {
            binding.radioButtonIncome.setChecked(true);
        }
        else
            binding.radioButtonExpense.setChecked(true);

        binding.radioButtonIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Income";
            }
        });

        binding.radioButtonExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Expense";
            }
        });

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expense == null) {
                    createExpense();
                }
                else {
                    updateExpense();
                }
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteExpense();
            }
        });

    }

    private void createExpense() {
        String expenseId = UUID.randomUUID().toString();
        String amount = binding.editTextAmount.getText().toString();
        String note = binding.editTextNote.getText().toString();
        String category = binding.editTextCategory.getText().toString();
        boolean incomeChecked = binding.radioButtonIncome.isChecked();
        if (incomeChecked) {
            type = "Income";
        }
        else
            type = "Expense";

        if (amount.trim().length() == 0) {
            binding.editTextAmount.setError("Empty");
            return;
        }
        Expense expense = new Expense(expenseId, note, category, Long.parseLong(amount), type, Calendar.getInstance().getTimeInMillis(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore.getInstance().collection("expenses").document(expenseId)
                .set(expense);
        finish();

    }

    private void updateExpense() {
        String expenseId = expense.getExpenseId();
        String amount = binding.editTextAmount.getText().toString();
        String note = binding.editTextNote.getText().toString();
        String category = binding.editTextCategory.getText().toString();
        boolean incomeChecked = binding.radioButtonIncome.isChecked();
        if (incomeChecked) {
            type = "Income";
        }
        else
            type = "Expense";

        if (amount.trim().length() == 0) {
            binding.editTextAmount.setError("Empty");
            return;
        }
        Expense expenseNew = new Expense(expenseId, note, category, Long.parseLong(amount), type, expense.getTime(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore.getInstance().collection("expenses").document(expenseId)
                .set(expenseNew);
        finish();

    }

    private void deleteExpense() {
        FirebaseFirestore.getInstance()
                .collection("expenses")
                .document(expense.getExpenseId())
                .delete();
        finish();
    }
}