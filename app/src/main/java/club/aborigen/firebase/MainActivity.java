package club.aborigen.firebase;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private DataProvider dataProvider;
    private AuthorizationService authorizationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("UWC", "Firebase application started");

        //Database
        dataProvider = new DataProvider();
        TextView bidButton = findViewById(R.id.add_bid);
        bidButton.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null) {
                dataProvider.addBidRecord(user);
                Toast.makeText(this, "Bid added, check in dashboard", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            }
        });

        //Push notification
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task ->
                Log.i("UWC", "FCM token: " + task.getResult())
        );

        //Authorization (move to login activity)
        TextView loginButton = findViewById(R.id.google_login);
        OnCompleteListener<AuthResult> authorizationCallback = task -> {
            if(task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                loginButton.setText(getString(R.string.label_already_logged_in, user.getDisplayName()));
                dataProvider.addUserRecord(user);
            }
        };

        authorizationService = new AuthorizationService(this);

        GoogleSignInAccount lastAccount = authorizationService.getLastAccount(this);
        if(lastAccount != null) {
            authorizationService.processAccount(lastAccount, authorizationCallback);
        } else {
            loginButton.setText(getString(R.string.label_google_login));

            ActivityResultLauncher<Intent> loginResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(), result -> {
                        authorizationService.processAccount(authorizationService.getAccountFromResult(result), authorizationCallback);
                    });
            loginButton.setOnClickListener(view -> loginResultLauncher.launch(authorizationService.getLaunchIntent()));
        }
    }
}