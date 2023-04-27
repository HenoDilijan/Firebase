package club.aborigen.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataProvider {
    DatabaseReference database;
    public DataProvider() {
        database = FirebaseDatabase.getInstance().getReference("Shake");

        Log.i("UWC", "Reading from " + database.getKey());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("UWC", "Value is: " + dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("UWC", "Failed to read value.", error.toException());
            }
        };
        database.addValueEventListener(postListener);
        database.setValue("Hamburger");
    }
}

