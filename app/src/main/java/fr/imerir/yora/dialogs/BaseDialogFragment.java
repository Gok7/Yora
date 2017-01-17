package fr.imerir.yora.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;

import fr.imerir.yora.infrastructure.YoraApplication;

public class BaseDialogFragment extends DialogFragment {

    protected YoraApplication application;

    @Override
    public void onCreate(Bundle savedState) {

        super.onCreate(savedState);
        application = (YoraApplication) getActivity().getApplication();
    }
}
