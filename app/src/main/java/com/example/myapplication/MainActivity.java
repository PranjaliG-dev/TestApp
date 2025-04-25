package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("speed");
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning Alert");
        builder.setMessage("Speed Limit Exceed");
        builder.setCancelable(false); // Prevents dismissing by tapping outside dialog

// Add a positive button with a click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the positive button click
                dialog.dismiss();
            }
        });

// Optionally, add a negative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the negative button click
                dialog.cancel();
            }
        });

        myRef.setValue("0");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("200")){
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Toast.makeText(MainActivity.this, ""+value, Toast.LENGTH_SHORT).show();
                }

                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Failed to read value."+error.toException(), Toast.LENGTH_SHORT).show();
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    public void showDia(View view) {
        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}