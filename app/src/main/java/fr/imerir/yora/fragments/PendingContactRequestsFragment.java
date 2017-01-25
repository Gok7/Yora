package fr.imerir.yora.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import fr.imerir.yora.R;
import fr.imerir.yora.activities.BaseActivity;
import fr.imerir.yora.services.Contacts;
import fr.imerir.yora.views.ContactRequestsAdapter;

public class PendingContactRequestsFragment extends BaseFragment {
    private View progressFrame;
    private ContactRequestsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_contact_requests, container, false);
        progressFrame = view.findViewById(R.id.fragment_pending_contact_request_progressFrame);
        adapter = new ContactRequestsAdapter((BaseActivity) getActivity());

        ListView listView = (ListView) view.findViewById(R.id.fragment_pending_contact_request_list);
        listView.setAdapter(adapter);

        bus.post(new Contacts.GetContactRequestsRequest(true));

        return view;
    }

    @Subscribe
    public void onGetContactsRequest(Contacts.GetContactRequestResponse response) {
        response.showErrorToast(getActivity());

        progressFrame.animate()
                .alpha(0)
                .setDuration(250)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        progressFrame.setVisibility(View.GONE);
                    }
                })
                .start();

        adapter.clear();
        adapter.addAll(response.requests);
    }
}
