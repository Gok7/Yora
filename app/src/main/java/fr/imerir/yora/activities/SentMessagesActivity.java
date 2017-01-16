package fr.imerir.yora.activities;

import android.os.Bundle;

import fr.imerir.yora.R;
import fr.imerir.yora.views.MainNavDrawer;


public class SentMessagesActivity extends BaseAuthenticatedActivity{

    @Override
    protected void onYoraCreate(Bundle savedState) {

        setContentView(R.layout.activity_sent_messages);
        setNavDrawer(new MainNavDrawer(this));

    }
}
