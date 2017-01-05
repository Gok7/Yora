package fr.imerir.yora.activities;

//enforce the log in

import android.content.Intent;
import android.os.Bundle;

public abstract class BaseAuthenticatedActivity extends BaseActivity {

    @Override
    protected final void onCreate(Bundle savedState){
        super.onCreate(savedState);

        if(!application.getAuth().getUser().isLoggedIn()){

            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        onYoraCreate(savedState);
    }

    protected abstract void onYoraCreate(Bundle savedState);

}
