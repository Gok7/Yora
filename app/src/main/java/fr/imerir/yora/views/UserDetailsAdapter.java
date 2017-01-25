package fr.imerir.yora.views;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.imerir.yora.R;
import fr.imerir.yora.activities.BaseActivity;
import fr.imerir.yora.services.entities.UserDetails;


public class UserDetailsAdapter extends ArrayAdapter<UserDetails> {
    private LayoutInflater inflater;

    public UserDetailsAdapter(BaseActivity activity) {
        super(activity, 0);
        inflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder view;
        UserDetails details = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_user_details, parent, false);
            view = new ViewHolder(convertView);
            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        view.displayName.setText(details.getDisplayName());
        Picasso.with(getContext()).load(details.getAvatarUrl()).into(view.avatar);

        return convertView;
    }

    private class ViewHolder {
        public TextView displayName;
        public ImageView avatar;

        public ViewHolder(View view) {
            displayName = (TextView) view.findViewById(R.id.list_item_user_details_displayName);
            avatar = (ImageView) view.findViewById(R.id.list_item_user_details_avatar);
        }
    }
}
