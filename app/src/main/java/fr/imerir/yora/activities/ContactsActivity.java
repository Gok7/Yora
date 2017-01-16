package fr.imerir.yora.activities;


import android.os.Bundle;

import fr.imerir.yora.R;
import fr.imerir.yora.views.MainNavDrawer;

public class ContactsActivity extends BaseAuthenticatedActivity {
    @Override
    protected void onYoraCreate(Bundle savedState) {
        setContentView(R.layout.activity_contacts);
        setNavDrawer(new MainNavDrawer(this));

    }
}
