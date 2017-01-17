package fr.imerir.yora.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.imerir.yora.R;
import fr.imerir.yora.dialogs.ChangePasswordDialog;
import fr.imerir.yora.infrastructure.User;
import fr.imerir.yora.views.MainNavDrawer;


public class ProfileActivity extends BaseAuthenticatedActivity implements View.OnClickListener {

    private static final int REQUEST_SELECT_IMAGE = 100;

    private static final int STATE_VIEWING = 1;
    private static final int STATE_EDITING = 2;

    private static final String BUNDLE_STATE = "BUNDLE_STATE";

    private int currentState;
    private EditText displayNameText;
    private EditText emailText;
    private View changeAvatarButton;
    private ActionMode editProfileActionMode;//keeping track of contextual action bar
    private ImageView avatarView;
    private View avatarProgressFrame;
    private File tempOutputFile;

    @Override
    protected void onYoraCreate(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        setNavDrawer(new MainNavDrawer(this));

        if (!isTablet) {
            View textFields = findViewById(R.id.activity_profile_textFields);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textFields.getLayoutParams();
            params.setMargins(0, params.getMarginStart(), 0, 0);
            params.removeRule(RelativeLayout.END_OF);
            params.addRule(RelativeLayout.BELOW, R.id.activity_profile_changeAvatar);
            textFields.setLayoutParams(params);
        }

        avatarView = (ImageView) findViewById(R.id.activity_profile_avatar);
        avatarProgressFrame = findViewById(R.id.activity_profile_avatarProgressFrame);
        changeAvatarButton = findViewById(R.id.activity_profile_changeAvatar);
        displayNameText = (EditText) findViewById(R.id.activity_profile_displayName);
        emailText = (EditText) findViewById(R.id.activity_profile_email);
        tempOutputFile = new File(getExternalCacheDir(), "temp-image.jpg");

        avatarView.setOnClickListener(this);
        changeAvatarButton.setOnClickListener(this);
        avatarProgressFrame.setVisibility(View.GONE);

        User user = application.getAuth().getUser();
        getSupportActionBar().setTitle(user.getDisplayName());

        if (savedState == null) {
            displayNameText.setText(user.getDisplayName());
            emailText.setText(user.getEmail());
            changeState(STATE_VIEWING);
        } else {
            changeState(savedState.getInt(BUNDLE_STATE));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {

        super.onSaveInstanceState(savedState);
        savedState.putInt(BUNDLE_STATE, currentState);
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        if (viewId == R.id.activity_profile_changeAvatar || viewId == R.id.activity_profile_avatar) {

            changeAvatar();
        }
    }

    private void changeAvatar() {

        List<Intent> otherImageCaptureIntents = new ArrayList<>();
        List<ResolveInfo> otherImageCaptureActivities = getPackageManager()
                .queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);

        for (ResolveInfo info : otherImageCaptureActivities) {

            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempOutputFile));
            otherImageCaptureIntents.add(captureIntent);
        }

        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        selectImageIntent.setType("image/*");

        Intent chooser = Intent.createChooser(selectImageIntent, "Chooser avatar");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, otherImageCaptureIntents.toArray
                (new Parcelable[otherImageCaptureActivities.size()]));

        startActivityForResult(chooser, REQUEST_SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if requesting an image or trying to capture image fails, delete temp file and return
        if (resultCode != RESULT_OK) {
            tempOutputFile.delete();
            return;
        }

        //two conditions : number 1 : user selected an image from device
        //number 2 : user took an image
        if (requestCode == REQUEST_SELECT_IMAGE) {
            Uri outputFile;
            Uri tempFileUri = Uri.fromFile(tempOutputFile);

            //detect if user selected image or took a picture
            if (data != null &&
                    (data.getAction() != null ||
                            !data.getAction().equals(MediaStore.ACTION_IMAGE_CAPTURE)))
                outputFile = data.getData();    //user selected image

            else
                outputFile = tempFileUri;       //user took a picture

            //crop image and start new soundcloud activity
            new Crop(outputFile).asSquare().output(tempFileUri).start(this);
        } else if (requestCode == Crop.REQUEST_CROP) {

            //todo : send tempsFileUri to server as new avatar

            avatarView.setImageResource(0);
            avatarView.setImageURI(Uri.fromFile(tempOutputFile));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//when user select something on actionbar

        int itemId = item.getItemId();

        if (itemId == R.id.activity_profile_menuEdit) {
            changeState(STATE_EDITING);
            return true;
        } else if (itemId == R.id.activity_profile_menuChangePassword) {
            //when we hit back key transaction will be undone
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null);

            ChangePasswordDialog dialog = new ChangePasswordDialog();
            dialog.show(transaction, null);
            return true;//indicate that we handled the options menu to the OS
        }
        return false;
    }

    private void changeState(int state) {

        if (state == currentState) {
            return;
        }

        currentState = state;
        if (state == STATE_VIEWING) {
            displayNameText.setEnabled(false);
            emailText.setEnabled(false);
            changeAvatarButton.setVisibility(View.VISIBLE);

            if (editProfileActionMode != null) {
                editProfileActionMode.finish();
                editProfileActionMode = null;
            }
        } else if (state == STATE_EDITING) {

            displayNameText.setEnabled(true);
            emailText.setEnabled(true);
            changeAvatarButton.setVisibility(View.GONE);

            editProfileActionMode = toolbar.startActionMode(new EditProfileActionCallback());

        } else {
            throw new IllegalArgumentException("Invalid state" + state);
        }
    }

    private class EditProfileActionCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

            getMenuInflater().inflate(R.menu.activity_profile_edit, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {

            int itemId = item.getItemId();

            if (itemId == R.id.activity_profile_edit_menuDone) {
                //todo : send request to update display name and email
                User user = application.getAuth().getUser();
                user.setDisplayName(displayNameText.getText().toString());
                user.setEmail(emailText.getText().toString());

                changeState(STATE_VIEWING);
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

            if (currentState != STATE_VIEWING) {
                changeState(STATE_VIEWING);
            }
        }
    }
}

