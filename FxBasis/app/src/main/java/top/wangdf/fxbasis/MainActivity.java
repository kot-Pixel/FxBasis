package top.wangdf.fxbasis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import top.wangdf.fxbasis.net.VestApi;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    private static final String vestCode = "Q11KF9MU";

    private static final String version = "1.0";

    private static final int RC_SIGN_IN = 1001;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VestApi api = new VestApi();
        //请求接口
        //api.makeVestRequest(new VestEntity("Q11KF9MU","1.0", Utils.getDeviceId(MainActivity.this), Utils.getTimeStamp(),"google"));
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("AIzaSyAe0nozTGKBPLdKgs7PIudDJLW-mL52snc")
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    private void attemptLoginGoogle() {
        //初始化gso，google_sign_up_client_id为添加的客户端id
//        GoogleSignInOptions gso = new GoogleSignInOptions
//                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(MainActivity.this.getString(R.string.google_sign_up_client_id))
//                .requestEmail()
//                .build();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i(TAG, "onActivityResult: " + account.getId());
                Log.i(TAG, "onActivityResult: " + account.getDisplayName());
            } catch (ApiException ignored) {
                Log.i(TAG, "onActivityResult: " + ignored.getStatusCode());
            }
        }
    }
}