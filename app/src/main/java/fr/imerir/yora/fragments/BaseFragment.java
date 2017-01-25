package fr.imerir.yora.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.squareup.otto.Bus;

import fr.imerir.yora.infrastructure.ActionScheduler;
import fr.imerir.yora.infrastructure.YoraApplication;

public abstract class BaseFragment extends Fragment {

    protected YoraApplication application;
    protected Bus bus;
    protected ActionScheduler scheduler;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        application = (YoraApplication) getActivity().getApplication();
        scheduler = new ActionScheduler(application);
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scheduler.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        scheduler.onPause();
    }

}
