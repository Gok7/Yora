package fr.imerir.yora.services;


import java.util.List;

import fr.imerir.yora.infrastructure.ServiceResponse;
import fr.imerir.yora.services.entities.ContactRequest;
import fr.imerir.yora.services.entities.UserDetails;

public final class Contacts {

    private Contacts() {
    }

    public static class GetContactRequestsRequest {
        public boolean fromUs;

        public GetContactRequestsRequest(boolean fromUs) {
            this.fromUs = fromUs;
        }
    }

    public static class GetContactRequestResponse extends ServiceResponse {
        public List<ContactRequest> requests;
    }

    public static class GetContactsRequest {
        public boolean includePendingContacts;

        public GetContactsRequest(boolean includePendingContacts) {
            this.includePendingContacts = includePendingContacts;
        }
    }

    public static class GetContactsResponse extends ServiceResponse {
        public List<UserDetails> Contacts;
    }

    public static class SendContactRequestRequest {
        public int userId;

        public SendContactRequestRequest(int userId) {
            this.userId = userId;
        }
    }

    public static class SendContactRequestsResponse extends ServiceResponse {
    }

    public static class RespondToContactRequestRequest {
        public int ContactRequestId;
        public boolean Accept;

        public RespondToContactRequestRequest(int contactRequestId, boolean accept) {
            ContactRequestId = contactRequestId;
            Accept = accept;
        }
    }

    public static class RespondToContactRequestResponse extends ServiceResponse {

    }

    public static class RemoveContactRequest {
        public int contactId;

        public RemoveContactRequest(int contactId) {
            this.contactId = contactId;
        }
    }

    public static class RemoveContactResponse extends ServiceResponse {
        public int RemovedContactId;
    }

    public static class SearchUsersRequest {

        public String query;

        public SearchUsersRequest(String query) {
            this.query = query;
        }
    }

    public static class SearchUsersResponse extends ServiceResponse {
        public List<UserDetails> users;
        public String query;
    }
}




















