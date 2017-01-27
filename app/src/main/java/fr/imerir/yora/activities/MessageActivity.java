package fr.imerir.yora.activities;

import android.os.Bundle;

public class MessageActivity extends BaseAuthenticatedActivity {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final int REQUEST_IMAGE_DELETED = 100; //request code int the case user deleted image
    public static final String RESULT_EXTRA_MESSAGE_ID = "RESULT_EXTRA_MESSAGE_ID"; //what image was deleted

    @Override
    protected void onYoraCreate(Bundle savedState) {

    }
}
