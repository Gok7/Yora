package fr.imerir.yora.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Subscribe;

import fr.imerir.yora.R;
import fr.imerir.yora.services.Account;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private Button registerButton;
    private View progressBar;
    private String defaultRegisterButtonText;

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);

        setContentView(R.layout.activity_register);

        usernameText = (EditText) findViewById(R.id.activity_register_userName);
        emailText = (EditText) findViewById(R.id.activity_register_email);
        passwordText = (EditText) findViewById(R.id.activity_register_password);
        registerButton = (Button) findViewById(R.id.activity_register_registerButton);
        progressBar = findViewById(R.id.activity_register_progressBar);

        registerButton.setOnClickListener(this);
        defaultRegisterButtonText = registerButton.getText().toString();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if(view == registerButton){

            progressBar.setVisibility(View.VISIBLE);
            registerButton.setText("");
            registerButton.setEnabled(false);
            usernameText.setEnabled(false);
            passwordText.setEnabled(false);
            emailText.setEnabled(false);

            bus.post(new Account.RegisterRequest(
                    usernameText.getText().toString(),
                    emailText.getText().toString(),
                    passwordText.getText().toString()));
        }
    }

    @Subscribe
    public void registerResponse(Account.RegisterResponse response) {
        onUserResponse(response);
    }

    @Subscribe
    public void externalRegisterResponse(Account.RegisterWithExternalTokenResponse response) {
        onUserResponse(response);
    }

    private void onUserResponse(Account.UserResponse response) {

        if (response.didSucceed()) {

            setResult(RESULT_OK);
            finish();
            return;
        }

        response.showErrorToast(this);
        usernameText.setError(response.getPropertyError("userName"));
        passwordText.setError(response.getPropertyError("password"));
        emailText.setError(response.getPropertyError("email"));

        registerButton.setEnabled(true);
        usernameText.setEnabled(true);
        passwordText.setEnabled(true);
        emailText.setEnabled(false);

        progressBar.setVisibility(View.GONE);
        registerButton.setText(defaultRegisterButtonText);

    }
}
