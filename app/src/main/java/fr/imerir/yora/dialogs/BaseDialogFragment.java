package fr.imerir.yora.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;

import com.squareup.otto.Bus;

import fr.imerir.yora.infrastructure.YoraApplication;

public class BaseDialogFragment extends DialogFragment {

    protected YoraApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedState) {

        super.onCreate(savedState);
        application = (YoraApplication) getActivity().getApplication();
        bus = application.getBus();

        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
