package fr.imerir.yora.services;


import android.net.Uri;

import fr.imerir.yora.infrastructure.ServiceResponse;
import fr.imerir.yora.infrastructure.User;

public final class Account {

    private Account() {

    }

    //base class for our service responses that represent a service call for a user successfully logged in
    //prototype response for all login types.
    public static class UserResponse extends ServiceResponse {

        public int id;
        public String avatarUrl;
        public String displayName;
        public String userName;
        public String email;
        public String authToken;
        public boolean hasPassword;

    }

    public static class LoginWithUserNameRequest {

        public String userName;
        public String password;

        public LoginWithUserNameRequest(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
    }

    public static class LoginWithUserNameResponse extends UserResponse {
    }

    public static class LoginWithLocalTokenRequest {

        public String AuthToken;

        public LoginWithLocalTokenRequest(String authToken) {
            AuthToken = authToken;
        }
    }

    public static class LoginWithLocalTokenResponse extends UserResponse {
    }

    public static class LoginWithExternalTokenRequest {

        public String provider;
        public String token;
        public String clientId;

        public LoginWithExternalTokenRequest(String provider, String token) {
            this.provider = provider;
            this.token = token;
            this.clientId = "android";
        }
    }

    //when login from google or facebook, we need to retrieve auth token from the Oauth provider.
    //we need to send this token to our server to exchange it with our token.
    public static class LoginWithExternalTokenResponse extends UserResponse {

    }

    public static class RegisteRequest {

        public String userName;
        public String email;
        public String password;
        public String clientId;

        public RegisteRequest(String userName, String email, String password) {
            this.userName = userName;
            this.email = email;
            this.password = password;
            this.clientId = "android";
        }
    }

    //log in and create account at same time
    public static class RegisterResponse extends UserResponse {

    }
    //when you log in to google or facebook, you may not have a local account on yora
    //setting or preference, etc needs to be saved. if you don't have an account you need to create one
    //when you log in with google for the first time, redirection to register activity
    //when you register from there it create local token with local account

    public static class RegisterWithExternalTokenRequest {

        public String userName;
        public String email;
        public String provider;
        public String token;
        public String clientId;

        public RegisterWithExternalTokenRequest(String userName, String email, String provider, String token) {
            this.userName = userName;
            this.email = email;
            this.provider = provider;
            this.token = token;
            this.clientId = "android";
        }
    }

    public static class RegisterWithExternalTokenResponse extends UserResponse {

    }

    public static class ChangeAvatarRequest {

        public Uri NewAvatarUri;

        public ChangeAvatarRequest(Uri newAvatarUri) {
            NewAvatarUri = newAvatarUri;
        }
    }

    public static class ChangeAvatarResponse extends ServiceResponse {
    }

    public static class UpdateProfileRequest {

        public String displayName;
        public String email;

        public UpdateProfileRequest(String displayName, String email) {
            this.displayName = displayName;
            this.email = email;
        }
    }

    public static class UpdateProfileResponse extends ServiceResponse {
    }

    public static class ChangePasswordRequest {

        public String currentPassword;
        public String newPassword;
        public String confirmNewPassword;

        public ChangePasswordRequest(String currentPassword, String newPassword, String confirmNewPassword) {
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
            this.confirmNewPassword = confirmNewPassword;
        }
    }

    public static class ChangePasswordResponse extends ServiceResponse {

    }

    //event to dispatch to event bus whenever a service call updates an user.
    //example : change user avatar, we let the system know that that happened.
    public static class UserDetailsUpdatedEvent {

        public User user;

        public UserDetailsUpdatedEvent(User user) {
            this.user = user;
        }
    }
}
