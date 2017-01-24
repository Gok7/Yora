package fr.imerir.yora.infrastructure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import fr.imerir.yora.activities.LoginActivity;

public class Auth {
    private static final String AUTH_PREFERENCES = "AUTH_PREFERENCES";
    private static final String AUTH_PREFERENCES_TOKEN = "AUTH_PREFERENCE_TOKEN";

    private final Context context;
    private final SharedPreferences preferences;

    private User user;
    private String authToken;



    public Auth(Context context) {
        this.context = context;
        user = new User();

        //shared preferences : android key-value store. we store little bits of data indexed by string keys
        //getting it from the context, sharedpreferences object. we want authpreferences.

        preferences = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);

//        int MODE_PRIVATE
//        File creation mode: the default mode, where the created file can only be accessed
//        by the calling application (or all applications sharing the same user ID).

        //go get from the sharedpreferences object, a value that is keyed with AUTH_PREFERENCE_TOKEN
        //if you can't find it return null
        authToken = preferences.getString(AUTH_PREFERENCES_TOKEN, null);
    }

    public User getUser() {
        return user;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;

        //store the authtoken in sharedpreferences
        //we need a reference to sharedpreferences editor

        SharedPreferences.Editor editor = preferences.edit();
        //now we can make changes to the sharedpreferences
        editor.putString(AUTH_PREFERENCES_TOKEN, authToken);//we store the authtoken
        editor.commit();//commit changes in sharedpreferences
    }

    public boolean hasAuthToken() {
        return authToken != null && !authToken.isEmpty();
    }

    public void logout() {
        setAuthToken(null);

        Intent loginIntent = new Intent(context, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(loginIntent);
    }
}

//you invoke the login activity it clears out the auth token from sharedpreferences
//then clears entire app
//restarts it on the login activity
