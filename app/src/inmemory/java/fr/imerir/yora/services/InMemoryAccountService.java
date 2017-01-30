package fr.imerir.yora.services;


import android.util.Log;

import com.squareup.otto.Subscribe;

import fr.imerir.yora.infrastructure.Auth;
import fr.imerir.yora.infrastructure.User;
import fr.imerir.yora.infrastructure.YoraApplication;

public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService(YoraApplication application) {

        super(application);
    }

    @Subscribe
    public void updateProfile(final Account.UpdateProfileRequest request) {

        final Account.UpdateProfileResponse response = new Account.UpdateProfileResponse();

        if (request.displayName.equals("nelson")) {
            response.setPropertyError("displayName", "You may not be named Nelson");
        }

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setDisplayName(request.displayName);
                user.setEmail(request.email);

                bus.post(response);
                //give our sidebar notification that user changed
                bus.post(new Account.UserDetailsUpdatedEvent(user));
            }
        }, 2000, 3000);
    }

    @Subscribe
    public void updateAvatar(final Account.ChangeAvatarRequest request) {

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setAvatarUrl(request.NewAvatarUri.toString());

                bus.post(new Account.ChangeAvatarResponse());
                bus.post(new Account.UserDetailsUpdatedEvent(user));
            }
        }, 4000, 5000);

    }

    @Subscribe
    public void changePassword(Account.ChangePasswordRequest request) {

        Account.ChangePasswordResponse response = new Account.ChangePasswordResponse();

        if (!request.newPassword.equals(request.confirmNewPassword)) {
            response.setPropertyError("confirmNewPassword", "Password must match");
            Log.e("tag", "password must match");
        }

        if (request.newPassword.length() <= 3) {
            response.setPropertyError("newPassword", "Password must be larger than 3 characters");
        }

        postDelayed(response);
    }

    @Subscribe
    public void loginWithUserName(final Account.LoginWithUserNameRequest request) {

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWithUserNameResponse response = new Account.LoginWithUserNameResponse();

                if (request.userName.equals("nelson"))
                    response.setPropertyError("userName", "Invalid username or password");

                //must be invoked first, so auth token has been set before we respond in a positive way
                loginUser(new Account.UserResponse());
                bus.post(response);
            }
        }, 1000, 2000);
    }

    @Subscribe
    public void loginWithExternalToken(Account.LoginWithExternalTokenRequest request) {

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWithExternalTokenResponse response = new Account.LoginWithExternalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000, 2000);
    }

    @Subscribe
    public void loginWithLocalToken(Account.LoginWithLocalTokenRequest request) {

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWithLocalTokenResponse response = new Account.LoginWithLocalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000, 2000);
    }

    @Subscribe
    public void register(Account.RegisterRequest request) {

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.RegisterResponse response = new Account.RegisterResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000, 2000);
    }

    @Subscribe
    public void externalRegister(Account.RegisterWithExternalTokenRequest request) {

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.RegisterResponse response = new Account.RegisterResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000, 2000);
    }

    //used in all our fake login methods.
    private void loginUser(Account.UserResponse response) {

        Auth auth = application.getAuth();
        User user = auth.getUser();

        user.setDisplayName("nelson laquet");
        user.setUserName("nelsonlaquet");
        user.setUserName("nelson@3dbuzz.com");
        user.setAvatarUrl("http://fr.gravatar.com/avatar/1?d=identicon");
        user.setLoggedIn(true);
        user.setId(123);
        bus.post(new Account.UserDetailsUpdatedEvent(user));
        auth.setAuthToken("fakeauthtoken");

        response.displayName = user.getDisplayName();
        response.userName = user.getUserName();
        response.email = user.getEmail();
        response.avatarUrl = user.getAvatarUrl();
        response.id = user.getId();
        response.authToken = auth.getAuthToken();
    }

    @Subscribe
    public void updateGcmRegistration(Account.UpdateGcmRegistrationRequest request) {
        postDelayed(new Account.UpdateUpdateGcmRegistrationResponse());
    }
}
