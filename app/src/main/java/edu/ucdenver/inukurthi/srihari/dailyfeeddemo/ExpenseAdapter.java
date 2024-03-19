package edu.ucdenver.inukurthi.srihari.dailyfeeddemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ucdenver.inukurthi.srihari.dailyfeeddemo.Model.Expense;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {


    private Context context;
    private List<Expense> expenseList;
    private OnItemsClicked onItemsClicked;

    CardView cardView;

    public ExpenseAdapter(Context context, OnItemsClicked onItemsClicked) {
        this.context = context;
        this.expenseList = new ArrayList<>();
        this.onItemsClicked = onItemsClicked;
    }

    public void add(Expense expense) {
        expenseList.add(expense);
        notifyDataSetChanged();
    }

    public void clear() {
        expenseList.clear();
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row_item, parent, false);
        cardView = view.findViewById(R.id.cardView_expense_item);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.textView_description.setText(expense.getNote());
        holder.textView_amount_item.setText(String.valueOf(expense.getAmount()));
        holder.textView_category.setText(expense.getCategory());
        Date date = new Date(expense.getTime());
        DateFormat df = new SimpleDateFormat("MM:dd:yy:");
        holder.textView_date.setText(df.format(date));

        if (expense.getType().equalsIgnoreCase("Income")) {
            cardView.setBackgroundColor(Color.GREEN);
        }
        else {
            cardView.setBackgroundColor(Color.RED);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemsClicked.onClick(expense);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView textView_description;
        private TextView textView_category;
        private TextView textView_date;
        private TextView textView_amount_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_description = itemView.findViewById(R.id.textView_description);
            textView_category = itemView.findViewById(R.id.textView_category);
            textView_date = itemView.findViewById(R.id.textView_date);
            textView_amount_item = itemView.findViewById(R.id.textView_amount_item);
        }
    }
}
