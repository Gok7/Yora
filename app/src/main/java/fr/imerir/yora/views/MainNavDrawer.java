package fr.imerir.yora.views;

import android.view.View;
import android.widget.Toast;

import fr.imerir.yora.R;
import fr.imerir.yora.activities.BaseActivity;
import fr.imerir.yora.activities.MainActivity;

public class MainNavDrawer  extends  NavDrawer{
    public MainNavDrawer(final BaseActivity activity) {
        super(activity);

        addItem(new ActivityNavDrawerItem(MainActivity.class,"Inbox",null, R.drawable.ic_action_unread,0));

        addItem(new BasicNavDrawerItem("Logout",null,R.drawable.ic_action_backspace,0){

            @Override
            public void onClick(View view){
                Toast.makeText(activity,"You have logged",  Toast.LENGTH_SHORT).show();
            }
        });
    }
}