package fr.imerir.yora.views;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.imerir.yora.R;
import fr.imerir.yora.activities.BaseActivity;
import fr.imerir.yora.services.entities.ContactRequest;


public class ContactRequestsAdapter extends ArrayAdapter<ContactRequest> {

    private LayoutInflater inflater;

    public ContactRequestsAdapter(BaseActivity activity) {
        super(activity, 0);
        inflater = activity.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactRequest request = getItem(position);
        ViewHolder view;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_contact_request, parent, false);
            view = new ViewHolder(convertView);
            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        view.displayName.setText(request.getUser().getDisplayName());
        Picasso.with(getContext()).load(request.getUser().getAvatarUrl()).into(view.avatar);

        String createdAt = DateUtils.formatDateTime(
                getContext(),
                request.getCreatedAt().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);

        if (request.isFromUs()) {
            view.createdAt.setText("Sent at" + createdAt);
        } else {
            view.createdAt.setText("Received" + createdAt);
        }

        return convertView;
    }

    private class ViewHolder {
        public TextView displayName;
        public TextView createdAt;
        public ImageView avatar;

        public ViewHolder(View view) {
            displayName = (TextView) view.findViewById(R.id.list_item_contact_request_displayName);
            createdAt = (TextView) view.findViewById(R.id.list_item_contact_request_createdAt);
            avatar = (ImageView) view.findViewById(R.id.list_item_contact_request_avatar);
        }
    }
}
