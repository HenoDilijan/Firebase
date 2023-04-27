package club.aborigen.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class DataProvider {
    DatabaseReference customers;
    DatabaseReference bids;
    public DataProvider() {
        customers = FirebaseDatabase.getInstance().getReference("Customers");
        bids = FirebaseDatabase.getInstance().getReference("Bids");

        Log.i("UWC", "Reading from " + customers.getKey());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("UWC", "Customers snapshot: " + dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("UWC", "Failed to customers: ", error.toException());
            }
        };
        customers.addValueEventListener(postListener);

        //Testavo user
        User user = new User("Martiros Petrov", "martiros@gmail.com");
        customers.child("fakeid").setValue(user);
    }

    public void addUserRecord(FirebaseUser fu) {
        User user = new User(fu.getDisplayName(), fu.getEmail());
        customers.child(fu.getUid()).setValue(user);
    }

    public void addBidRecord(FirebaseUser fu) {
        String bidId = UUID.randomUUID().toString();
        Bid bid = new Bid(fu.getUid(), "Gazar", 12, 240);
        bids.child(bidId).setValue(bid);
    }
}

