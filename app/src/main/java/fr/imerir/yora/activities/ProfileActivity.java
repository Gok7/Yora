package fr.imerir.yora.activities;

import android.os.Bundle;

import fr.imerir.yora.R;
import fr.imerir.yora.views.MainNavDrawer;

/**
 * Created by student on 16/01/2017.
 */

public class ProfileActivity extends BaseAuthenticatedActivity{
    @Override
    protected void onYoraCreate(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        setNavDrawer(new MainNavDrawer(this));
    }
}
