package fr.imerir.yora.services;

import com.squareup.otto.Subscribe;

import java.io.File;

import fr.imerir.yora.infrastructure.Auth;
import fr.imerir.yora.infrastructure.RetrofitCallback;
import fr.imerir.yora.infrastructure.RetrofitCallbackPost;
import fr.imerir.yora.infrastructure.User;
import fr.imerir.yora.infrastructure.YoraApplication;
import retrofit.mime.TypedFile;

public class LiveAccountService extends BaseLiveService {

    private final Auth auth;


    public LiveAccountService(YoraApplication application, YoraWebService api) {
        super(application, api);
        auth = application.getAuth();
    }

    @Subscribe
    public void register(Account.RegisterRequest request) {
        api.register(request,
                new RetrofitCallback<Account.RegisterResponse>(Account.RegisterResponse.class) {
                    @Override
                    protected void onResponse(Account.RegisterResponse registerResponse) {
                        if (registerResponse.didSucceed()) {
                            loginUser(registerResponse);
                        }

                        bus.post(registerResponse);
                    }
                });
    }

    @Subscribe
    public void loginWithUsername(final Account.LoginWithUserNameRequest request) {
        api.login(
                request.userName,
                request.password,
                "android",
                "password",
                new RetrofitCallback<YoraWebService.LoginResponse>(YoraWebService.LoginResponse.class) {
                    @Override
                    protected void onResponse(YoraWebService.LoginResponse loginResponse) {

                        if (!loginResponse.didSucceed()) {
                            Account.LoginWithUserNameResponse response = new Account.LoginWithUserNameResponse();
                            response.setPropertyError("userName", loginResponse.errorDescription);
                            bus.post(response);
                            return;
                        }

                        auth.setAuthToken(loginResponse.Token);
                        api.getAccount(new RetrofitCallback<Account.LoginWithLocalTokenResponse>(
                                Account.LoginWithLocalTokenResponse.class) {
                            @Override
                            protected void onResponse(Account.LoginWithLocalTokenResponse loginResponse) {
                                if (!loginResponse.didSucceed()) {
                                    Account.LoginWithUserNameResponse response = new Account.LoginWithUserNameResponse();
                                    response.setOperationError(loginResponse.getOperationError());
                                    bus.post(response);
                                    return;
                                }

                                loginUser(loginResponse);
                                bus.post(new Account.LoginWithUserNameResponse());
                            }
                        });
                    }
                });
    }

    @Subscribe
    public void loginWithLocalToken(final Account.LoginWithLocalTokenRequest request) {

        api.getAccount(new RetrofitCallbackPost<Account.LoginWithLocalTokenResponse>
                (Account.LoginWithLocalTokenResponse.class, bus) {

            @Override
            protected void onResponse(Account.LoginWithLocalTokenResponse response) {
                loginUser(response);
                super.onResponse(response);
            }
        });
    }

    @Subscribe
    public void updateProfile(final Account.UpdateProfileRequest request) {
        api.updateProfile(request, new RetrofitCallbackPost<Account.UpdateProfileResponse>(Account.UpdateProfileResponse.class, bus) {
            @Override
            protected void onResponse(Account.UpdateProfileResponse response) {
                User user = auth.getUser();
                user.setDisplayName(response.displayName);
                user.setEmail(response.email);

                super.onResponse(response);
                bus.post(new Account.UserDetailsUpdatedEvent(user));
            }
        });
    }

    @Subscribe
    public void updateAvatar(final Account.ChangeAvatarRequest request) {

        api.updateAvatar(
                new TypedFile("image/jpeg", new File(request.NewAvatarUri.getPath())),
                new RetrofitCallbackPost<Account.ChangeAvatarResponse>(Account.ChangeAvatarResponse.class, bus) {

                    @Override
                    protected void onResponse(Account.ChangeAvatarResponse response) {
                        User user = auth.getUser();
                        user.setAvatarUrl(response.avatarUrl);

                        super.onResponse(response);
                        bus.post(new Account.UserDetailsUpdatedEvent(user));
                    }
                });
    }

    @Subscribe
    public void changePassword(Account.ChangePasswordRequest request) {
        api.updatePassword(request, new RetrofitCallbackPost<Account.ChangePasswordResponse>
                (Account.ChangePasswordResponse.class, bus) {
            @Override
            protected void onResponse(Account.ChangePasswordResponse response) {

                if (response.didSucceed()) {
                    auth.getUser().setHasPassword(true);
                }

                super.onResponse(response);
            }
        });
    }

    @Subscribe
    public void loginWithExternalToken(Account.LoginWithExternalTokenRequest request) {
        api.loginWithExternalToken(request, new RetrofitCallbackPost<Account.LoginWithExternalTokenResponse>
                (Account.LoginWithExternalTokenResponse.class, bus) {
            @Override
            protected void onResponse(Account.LoginWithExternalTokenResponse response) {
                loginUser(response);
                super.onResponse(response);
            }
        });
    }

    @Subscribe
    public void registerWithExternalToken(Account.RegisterWithExternalTokenRequest request) {

        api.registerExternal(request, new RetrofitCallbackPost<Account.RegisterWithExternalTokenResponse>
                (Account.RegisterWithExternalTokenResponse.class, bus) {
            @Override
            protected void onResponse(Account.RegisterWithExternalTokenResponse response) {
                loginUser(response);
                super.onResponse(response);
            }
        });
    }

    @Subscribe
    public void registerGcm(Account.UpdateGcmRegistrationRequest request) {
        api.updateGcmRegistration(request, new RetrofitCallbackPost<>
                (Account.UpdateUpdateGcmRegistrationResponse.class, bus));
    }

    private void loginUser(Account.UserResponse response) {

        if (response.authToken != null && !response.authToken.isEmpty()) {
            auth.setAuthToken(response.authToken);
        }

        User user = auth.getUser();
        user.setId(response.id);
        user.setDisplayName(response.displayName);
        user.setUserName(response.userName);
        user.setEmail(response.email);
        user.setAvatarUrl(response.avatarUrl);
        user.setHasPassword(response.hasPassword);
        user.setLoggedIn(true);

        bus.post(new Account.UserDetailsUpdatedEvent(user));
    }
}
