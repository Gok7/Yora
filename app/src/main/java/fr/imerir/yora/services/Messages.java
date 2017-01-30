package fr.imerir.yora.services;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import fr.imerir.yora.infrastructure.ServiceResponse;
import fr.imerir.yora.services.entities.Message;
import fr.imerir.yora.services.entities.UserDetails;

public final class Messages {

    private Messages() {
    }

    public static class DeleteMessageRequest {
        public int messageId;

        public DeleteMessageRequest(int messageId) {
            this.messageId = messageId;
        }
    }

    public static class DeleteMessageResponse extends ServiceResponse {
        public int messageId;
    }

    public static class SearchMessagesRequest {
        public int fromContactId;
        public boolean includeSentMessages;
        public boolean includeReceivedMessages;

        public SearchMessagesRequest(int fromContactId, boolean includeSentMessages, boolean includeReceivedMessages) {
            this.fromContactId = fromContactId;
            this.includeSentMessages = includeSentMessages;
            this.includeReceivedMessages = includeReceivedMessages;
        }

        public SearchMessagesRequest(boolean includeSentMessages, boolean includeReceivedMessages) {
            this.fromContactId = -1;
            this.includeSentMessages = includeSentMessages;
            this.includeReceivedMessages = includeReceivedMessages;
        }
    }

    public static class SearchMessagesResponse extends ServiceResponse {
        public List<Message> messages;
    }

    public static class SendMessageRequest implements Parcelable {
        public static Creator<SendMessageRequest> CREATOR = new Creator<SendMessageRequest>() {
            @Override
            public SendMessageRequest createFromParcel(Parcel source) {
                return new SendMessageRequest(source);
            }

            @Override
            public SendMessageRequest[] newArray(int size) {
                return new SendMessageRequest[size];
            }
        };
        private UserDetails recipient;
        private Uri imagePath;
        private String message;

        public SendMessageRequest() {
        }

        private SendMessageRequest(Parcel in) {
            recipient = in.readParcelable(UserDetails.class.getClassLoader());
            imagePath = in.readParcelable(Uri.class.getClassLoader());
            message = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeParcelable(recipient, 0);
            out.writeParcelable(imagePath, 0);
            out.writeString(message);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public UserDetails getRecipient() {
            return recipient;
        }

        public void setRecipient(UserDetails recipient) {
            this.recipient = recipient;
        }

        public Uri getImagePath() {
            return imagePath;
        }

        public void setImagePath(Uri imagePath) {
            this.imagePath = imagePath;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class SendMessageResponse extends ServiceResponse {

        public Message message;
    }

    public static class MarkMessageAsReadRequest {
        public int MessageId;

        public MarkMessageAsReadRequest(int messageId) {
            MessageId = messageId;
        }
    }

    public static class MarkMessageAsReadResponse extends ServiceResponse {

    }

    public static class GetMessageDetailsRequest {
        public int Id;

        public GetMessageDetailsRequest(int id) {
            Id = id;
        }
    }

    public static class GetMessageDetailsResponse {
        public Message Message;
    }
}