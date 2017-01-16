package fr.imerir.yora.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fr.imerir.yora.R;
import fr.imerir.yora.activities.BaseActivity;
import fr.imerir.yora.activities.ContactsActivity;
import fr.imerir.yora.activities.MainActivity;
import fr.imerir.yora.activities.ProfileActivity;
import fr.imerir.yora.activities.SentMessagesActivity;
import fr.imerir.yora.infrastructure.User;

public class MainNavDrawer  extends  NavDrawer{

    private final TextView displayNameText;
    private final ImageView avatarImage;

    public MainNavDrawer(final BaseActivity activity) {
        super(activity);

        addItem(new ActivityNavDrawerItem(MainActivity.class,"Inbox",null, R.drawable.ic_action_unread,R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(SentMessagesActivity.class, "Sent messages", null, R.drawable.ic_action_send_now,R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ContactsActivity.class,"Contacts",null, R.drawable.ic_action_group, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ProfileActivity.class, "Profile", null, R.drawable.ic_person_black_24dp, R.id.include_main_nav_drawer_topItems));

        addItem(new BasicNavDrawerItem("Logout",null,R.drawable.ic_action_backspace,R.id.include_main_nav_drawer_bottomItems){

            @Override
            public void onClick(View view){
                Toast.makeText(activity,"You have logged out", Toast.LENGTH_SHORT).show();
            }
        });

        displayNameText = (TextView) navDrawerView.findViewById(R.id.include_main_nav_drawer_displayName);
        avatarImage = (ImageView) navDrawerView.findViewById(R.id.include_main_nav_drawer_avatar);

        User loggedInUser = activity.getYoraApplication().getAuth().getUser();
        displayNameText.setText(loggedInUser.getDisplayName());

        //TODO: change avatar image to avatarUrl from loggedInUser
    }
}
