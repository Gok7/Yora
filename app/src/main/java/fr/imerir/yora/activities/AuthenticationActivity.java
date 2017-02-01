package fr.imerir.yora.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import fr.imerir.yora.R;
import fr.imerir.yora.fragments.RegisterGcmFragment;
import fr.imerir.yora.infrastructure.Auth;
import fr.imerir.yora.services.Account;

public class AuthenticationActivity extends BaseActivity implements RegisterGcmFragment.GcmRegistrationCallback {

    public static final String EXTRA_RETURN_TO_ACTIVITY = "EXTRA_RETURN_TO_ACTIVITY";
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_authentication);

        auth = application.getAuth();

        if (!auth.hasAuthToken()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        bus.post(new Account.LoginWithLocalTokenRequest(auth.getAuthToken()));
    }

    @Subscribe
    public void onLoginWithLocalToken(Account.LoginWithLocalTokenResponse response) {

        //maybe network is down, maybe authtoken is invalid, maybe useraccount deleted.
        if (!response.didSucceed()) {
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show();
            auth.setAuthToken(null);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        RegisterGcmFragment.get(this, false, getFragmentManager());
    }

    @Override
    public void gcmFinished() {

        Intent intent;
        String returnTo = getIntent().getStringExtra(EXTRA_RETURN_TO_ACTIVITY);
        if (returnTo != null) {
            try {
                intent = new Intent(this, Class.forName(returnTo));
            } catch (Exception ignored) {

                intent = new Intent(this, MainActivity.class);
            }
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();

    }
}
