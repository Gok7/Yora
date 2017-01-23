package fr.imerir.yora.services;


import android.util.Log;

import com.squareup.otto.Subscribe;

import fr.imerir.yora.infrastructure.YoraApplication;

public class InMemoryAccountService extends BaseInMemoryService {

    public InMemoryAccountService(YoraApplication application) {

        super(application);
    }

    @Subscribe
    public void updateProfile(Account.UpdateProfileRequest request) {

        Account.UpdateProfileResponse response = new Account.UpdateProfileResponse();

        if (request.DisplayName.equals("nelson")) {
            response.setPropertyError("displayName", "You may not be named Nelson");
        }

        postDelayed(response, 1000);
    }

    @Subscribe
    public void updateAvatar(Account.ChangeAvatarRequest request) {

        postDelayed(new Account.ChangeAvatarResponse());

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
}
