package fr.imerir.yora.views;

import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.imerir.yora.activities.BaseActivity;

public class NavDrawer {

    private ArrayList<NavDrawerItem> items;
    private NavDrawerItem selectedItem;

    protected BaseActivity activity;
    protected DrawerLayout drawerLayout;
    protected ViewGroup navDrawerView;

    public NavDrawer(BaseActivity activity){


    }

    public void addItem(NavDrawerItem item){

    }

    public boolean isOpen(){

        return false;
    }

    public void setOpen(boolean isOpen){


    }

    public void setSelectedItem(NavDrawerItem item){

    }

    public void create(){

    }

    public static abstract class NavDrawerItem{

        protected NavDrawer navDrawer;

        public abstract void inflate(LayoutInflater inflater, ViewGroup container);
        public abstract void setSelected(boolean isSelected);
    }

    public static class BasicNavDrawerItem extends NavDrawerItem implements View.OnClickListener{

        private String text;
        private String badge;
        private int iconDrawable;
        private int containerId;

        private ImageView icon;
        private TextView textView;
        private TextView badgeTextView;
        private View view;
        private int defaultTextColor;

        public BasicNavDrawerItem(String text,String badge, int iconDrawable, int containerId){


        }
        public void setText(String text){

        }
        public void setBadge(String badge){

        }
        public void setIcon(int iconDrawable){

        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void inflate(LayoutInflater inflater, ViewGroup container) {

        }

        @Override
        public void setSelected(boolean isSelected) {

        }
    }
    public static class ActivityNavDrawerItem extends BasicNavDrawerItem{

        private final Class targetActivity;

        public ActivityNavDrawerItem(Class targetActivity,String text, String badge,
                                     int iconDrawable, int containerId) {

            super(text, badge, iconDrawable, containerId);
            this.targetActivity = targetActivity;
        }
    }
}
