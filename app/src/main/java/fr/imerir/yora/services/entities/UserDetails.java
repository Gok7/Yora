package fr.imerir.yora.services.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class UserDetails implements Parcelable {

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel parcel) {

            return new UserDetails(0, false, null, null, null);
        }

        @Override
        public UserDetails[] newArray(int i) {
            return new UserDetails[0];
        }
    };
    private int id;
    private boolean isContact;
    private String displayName;
    private String userName;
    private String avatarUrl;

    public UserDetails(int id, boolean isContact, String displayName, String userName, String avatarUrl) {
        this.id = id;
        this.isContact = isContact;
        this.displayName = displayName;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
    }

    public int getId() {
        return id;
    }

    public boolean isContact() {
        return isContact;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
