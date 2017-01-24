package fr.imerir.yora.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.imerir.yora.R;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private Button loginButton;
    private Callbacks callbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedState){

        View view = inflater.inflate(R.layout.fragment_login, root, false);

        loginButton = (Button) view.findViewById(R.id.fragment_login_loginButton);
        loginButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == loginButton){
            //notify parent activity
            application.getAuth().getUser().setLoggedIn(true);
            application.getAuth().getUser().setDisplayName("Nelson Laquet");
            callbacks.onLoggedIn();
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        callbacks = (Callbacks) activity;

    }

    @Override
    public void onDetach(){
        super.onDetach();
        callbacks = null;
    }

    public interface Callbacks{

        void onLoggedIn();
    }
}

//We want to hook this service layer. Authentication token. API call to webservice.
//Inbox : we want to list our inbox : we need to hit API endpoint online on some sort of service
//we want to send a token, information that indicates that we are logged in and we are authenticated
//kind a like a cookie would work if you were using a browser.
//Authentication token : when you log in, the server on the other end is going to generate a random token
//and hand it back to the application. then the application needs to make another request to the server,
//it will pass along that authentication token. allowing the server to validate to verify that you are a
//valid user, more importantly what user you are.
//We take that auth token to store it in local storage. if you close the application and re-lauch it
//it will know that you were logged in and re-authenticate itself with the service.
//that's how we're going to handle this, hell yeah baby.
