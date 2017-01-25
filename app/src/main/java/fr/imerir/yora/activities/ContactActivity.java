package fr.imerir.yora.activities;

import android.os.Bundle;

import fr.imerir.yora.R;


public class ContactActivity extends BaseAuthenticatedActivity {
    public static final String EXTRA_USER_DETAILS = "EXTRA_USER_DETAILS";

    @Override
    protected void onYoraCreate(Bundle savedState) {
        setContentView(R.layout.activity_contact);
    }
}
