package com.example.nicholasskjellum_project05;

import androidx.appcompat.app.AppCompatActivity;

import android.net.TrafficStats;
import android.os.Bundle;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Button btnplus, btnminus;

    EditText txtdate, txtamount, txtreason;
    TextView total;
    TableLayout history;
    double balance = 0.00;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnplus = findViewById(R.id.addBut);
        btnminus = findViewById(R.id.subBut);
        txtamount = findViewById(R.id.amountIn);
        txtdate = findViewById(R.id.dateIn);
        txtreason = findViewById(R.id.reasonIn);
        total = findViewById(R.id.currentBal);
        history = findViewById(R.id.history);


        db = openOrCreateDatabase("store.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE If Not Exists Transactions (Date TEXT, Amount double, Reason TEXT)");


        String sql = "select * from Transactions";
        Log.i("SQL", sql);

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() >0) {
            cursor.moveToFirst();
            do {

                TableRow tr = new TableRow(MainActivity.this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tr.setLayoutParams(lp);
                TransactionHistory th = new TransactionHistory();

                th.date = cursor.getString(0);
                th.amount = Double.parseDouble(cursor.getString(1));
                th.reason = cursor.getString(2);
                TextView tv = new TextView(MainActivity.super.getBaseContext());
                TextView tv1 = new TextView(MainActivity.super.getBaseContext());
                TextView tv2 = new TextView(MainActivity.super.getBaseContext());
                tv.setText(th.date);
                String amount = Double.toString(th.amount);
                tv1.setText(amount);
                balance = balance + th.amount;
                String bal01 = Double.toString(balance);
                total.setText(bal01);
                tv2.setText(th.reason);
                tr.addView(tv);
                tr.addView(tv1);
                tr.addView(tv2);
                history.addView(tr);


            }
            while (cursor.moveToNext());
        }

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransactionHistory transaction = new TransactionHistory();
                TableRow row = new TableRow(MainActivity.super.getBaseContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);


                String date = "";
                double amount;

                if (txtdate.getText().toString().isEmpty()) {
                    date = "No date given.";
                }
                else {
                    date = txtdate.getText().toString();
                }
               transaction.date = date;

                if(txtamount.getText().toString().isEmpty()) {
                    amount = 0.00;
                }
                else {

                    amount = Double.parseDouble(txtamount.getText().toString());

                }
                transaction.amount = amount;
                String reason = txtreason.getText().toString();
                transaction.reason = reason;
                TextView tv = new TextView(MainActivity.super.getBaseContext());
                tv.setText(date);
                TextView tv2 = new TextView(MainActivity.super.getBaseContext());
                tv2.setText(Double.toString(amount));
                TextView tv3 = new TextView(MainActivity.super.getBaseContext());
                tv3.setText(reason);
                row.addView(tv);
                row.addView(tv2);
                row.addView(tv3);
                balance = balance + amount;
                String bal01 = Double.toString(balance);
                total.setText(bal01);
                String sql = "INSERT INTO Transactions VALUES " + transaction.toSQL();
                Log.i("SQL", sql);
                db.execSQL(sql);
                history.addView(row);

            }
        });

        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransactionHistory transaction = new TransactionHistory();
                TableRow row = new TableRow(MainActivity.super.getBaseContext());
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);

                String date = "";
                double amount;

                if (txtdate.getText().toString().isEmpty()) {
                    date = "No date given.";
                }
                else {
                    date = txtdate.getText().toString();
                }
                transaction.date = date;

                if(txtamount.getText().toString().isEmpty()) {
                    amount = 0.00;
                }
                else {

                    amount = -(Double.parseDouble(txtamount.getText().toString()));

                }
                transaction.amount = amount;
                String reason = txtreason.getText().toString();
                transaction.reason = reason;
                TextView tv = new TextView(MainActivity.super.getBaseContext());
                tv.setText(date);
                TextView tv2 = new TextView(MainActivity.super.getBaseContext());
                tv2.setText(Double.toString(amount));
                TextView tv3 = new TextView(MainActivity.super.getBaseContext());
                tv3.setText(reason);
                row.addView(tv);
                row.addView(tv2);
                row.addView(tv3);
                balance = balance + amount;
                String bal01 = Double.toString(balance);
                total.setText(bal01);
                String sql = "INSERT INTO Transactions VALUES " + transaction.toSQL();
                Log.i("SQL", sql);
                db.execSQL(sql);
                history.addView(row);

            }
        });
    }
}
