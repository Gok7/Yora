package fr.imerir.yora.services;


import android.net.Uri;

import fr.imerir.yora.infrastructure.ServiceResponse;

public final class Account {

    private Account() {

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

        public String DisplayName;
        public String Email;

        public UpdateProfileRequest(String displayName, String email) {
            DisplayName = displayName;
            Email = email;
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
}
