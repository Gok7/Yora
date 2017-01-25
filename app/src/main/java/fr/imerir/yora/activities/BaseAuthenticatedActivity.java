package fr.imerir.yora.activities;

//enforce the log in

import android.content.Intent;
import android.os.Bundle;

public abstract class BaseAuthenticatedActivity extends BaseActivity {

    private Intent intent;

    @Override
    protected final void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        if (!application.getAuth().getUser().isLoggedIn()) {

            if (application.getAuth().hasAuthToken()) {
                intent = new Intent(this, AuthenticationActivity.class);
                intent.putExtra(
                        AuthenticationActivity.EXTRA_RETURN_TO_ACTIVITY,
                        getClass().getName());
                startActivity(intent);
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }

            finish();
            return;
        }

        onYoraCreate(savedState);
    }

    protected abstract void onYoraCreate(Bundle savedState);

}
