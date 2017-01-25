package fr.imerir.yora.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import fr.imerir.yora.R;
import fr.imerir.yora.activities.AddContactActivity;
import fr.imerir.yora.activities.BaseActivity;
import fr.imerir.yora.activities.ContactActivity;
import fr.imerir.yora.services.Contacts;
import fr.imerir.yora.services.entities.UserDetails;
import fr.imerir.yora.views.UserDetailsAdapter;

public class ContactsFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private UserDetailsAdapter adapter;
    private View progressFrame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        adapter = new UserDetailsAdapter((BaseActivity) getActivity());
        progressFrame = view.findViewById(R.id.fragment_contacts_progressFrame);
        ListView listView = (ListView) view.findViewById(R.id.fragment_contacts_list);
        listView.setEmptyView(view.findViewById(R.id.fragment_contacts_emptyList));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        bus.post(new Contacts.GetContactsRequest(false));

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        UserDetails details = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ContactActivity.class);
        intent.putExtra(ContactActivity.EXTRA_USER_DETAILS, details);
        startActivity(intent);
    }

    @Subscribe
    public void onContactsResponse(final Contacts.GetContactsResponse response) {
        scheduler.invokeOnResume(Contacts.GetContactsResponse.class, new Runnable() {
            @Override
            public void run() {
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
                adapter.addAll(response.Contacts);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_contacts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.fragment_contacts_menu_addContact) {
            startActivity(new Intent(getActivity(), AddContactActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
