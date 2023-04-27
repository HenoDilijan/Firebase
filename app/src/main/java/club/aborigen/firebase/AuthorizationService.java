package club.aborigen.firebase;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthorizationService {
    GoogleSignInClient googleSignInClient;

    public AuthorizationService(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }
    public Intent getLaunchIntent() {
        return googleSignInClient.getSignInIntent();
    }

    public GoogleSignInAccount getLastAccount(Context context) {
        return GoogleSignIn.getLastSignedInAccount(context);
    }

    public GoogleSignInAccount getAccountFromResult(ActivityResult result) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
        try {
            return task.getResult(ApiException.class);
        } catch (ApiException e) {
            return null;
        }
    }

    public void processAccount(GoogleSignInAccount account, OnCompleteListener<AuthResult> callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(callback);
    }
}
