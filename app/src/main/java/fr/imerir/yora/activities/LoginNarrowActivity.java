package fr.imerir.yora.activities;


import android.os.Bundle;

import fr.imerir.yora.R;
import fr.imerir.yora.fragments.LoginFragment;

public class LoginNarrowActivity extends BaseActivity implements LoginFragment.Callbacks{

    @Override
    protected void onCreate(Bundle savedState){

        super.onCreate(savedState);

        setContentView(R.layout.activity_login_narrow);

    }

    @Override
    public void onLoggedIn() {
        setResult(RESULT_OK);
        finish();
    }
}
