package fr.imerir.yora.activities;

import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import fr.imerir.yora.R;
import fr.imerir.yora.views.CameraPreview;

public class NewMessageActivity extends BaseAuthenticatedActivity {
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    private static final String TAG = "NewMessageActivity";
    private static final String STATE_CAMERA_INDEX = "STATE_CAMERA_INDEX";
    static Camera camera;
    private Camera.CameraInfo cameraInfo;
    private int currentCameraIndex;
    private CameraPreview cameraPreview;

    @Override
    protected void onYoraCreate(Bundle savedState) {
        setContentView(R.layout.activity_new_message);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (savedState != null) {//activity recreated
            currentCameraIndex = savedState.getInt(STATE_CAMERA_INDEX);
        } else {
            currentCameraIndex = 0;
        }

        cameraPreview = new CameraPreview(this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_new_message_frame);
        frameLayout.addView(cameraPreview, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        establishCamera();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (camera != null) { //if this activity paused, release the camera and set the preview to blank
            cameraPreview.setCamera(null, null);
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CAMERA_INDEX, currentCameraIndex); // handle rotation of device screen
    }

    private void establishCamera() {
        if (camera != null) {
            cameraPreview.setCamera(null, null);
            camera.release();
            camera = null;
        }

        try {
            camera = Camera.open(currentCameraIndex);//exclusive lock on camera ressources
        } catch (Exception e) {
            Log.e(TAG, "Could not open camera " + currentCameraIndex, e);
            Toast.makeText(this, "Error establishing camera", Toast.LENGTH_LONG).show();
            return;
        }

        cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(currentCameraIndex, cameraInfo);
        cameraPreview.setCamera(camera, cameraInfo);

        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            Toast.makeText(this, "Using back camera", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Using front camera", Toast.LENGTH_SHORT).show();
        }
    }
}
