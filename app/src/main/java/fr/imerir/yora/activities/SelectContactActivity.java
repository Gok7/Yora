package fr.imerir.yora.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import fr.imerir.yora.R;
import fr.imerir.yora.services.Contacts;
import fr.imerir.yora.services.entities.UserDetails;
import fr.imerir.yora.views.UserDetailsAdapter;


public class SelectContactActivity extends BaseAuthenticatedActivity implements AdapterView.OnItemClickListener {

    public static final String RESULT_CONTACT = "RESULT_CONTACT";

    private static final int REQUEST_ADD_CONTACT = 1;
    private UserDetailsAdapter adapter;
    private View progressFrame;


    @Override
    protected void onYoraCreate(Bundle savedState) {
        setContentView(R.layout.activity_select_contact);
        getSupportActionBar().setTitle("Select Contact");

        adapter = new UserDetailsAdapter(this);
        ListView listView = (ListView) findViewById(R.id.activity_select_contact_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        bus.post(new Contacts.GetContactsRequest(true));
    }

    @Subscribe
    public void onContactsReceived(Contacts.GetContactsResponse response) {
        response.showErrorToast(this);

        if (progressFrame != null) {
            progressFrame.setVisibility(View.GONE);
        }

        adapter.clear();
        adapter.addAll(response.Contacts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        UserDetails userDetails = adapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra(RESULT_CONTACT, userDetails);
        setResult(RESULT_OK, intent);
        finish();
    }
}
