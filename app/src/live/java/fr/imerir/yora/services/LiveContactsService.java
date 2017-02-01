package fr.imerir.yora.services;


import com.squareup.otto.Subscribe;

import fr.imerir.yora.infrastructure.RetrofitCallbackPost;
import fr.imerir.yora.infrastructure.YoraApplication;

public class LiveContactsService extends BaseLiveService {
    public LiveContactsService(YoraApplication application, YoraWebService api) {
        super(application, api);
    }

    @Subscribe
    public void searchUsers(Contacts.SearchUsersRequest request) {

        api.searchUsers(request.query, new RetrofitCallbackPost<>
                (Contacts.SearchUsersResponse.class, bus));
    }

    @Subscribe
    public void sendContactRequest(Contacts.SendContactRequestRequest request) {
        api.sendContactRequest(request.userId, new RetrofitCallbackPost<>
                (Contacts.SendContactRequestsResponse.class, bus));
    }

    @Subscribe
    public void getContactRequests(Contacts.GetContactRequestsRequest request) {

        if (request.fromUs) {
            api.getContactRequestsFromUs(new RetrofitCallbackPost<>
                    (Contacts.GetContactRequestResponse.class, bus));
        } else {
            api.getContactRequestsToUs(new RetrofitCallbackPost<>
                    (Contacts.GetContactRequestResponse.class, bus));
        }
    }

    @Subscribe
    public void respondToContactRequest(Contacts.RespondToContactRequestRequest request) {

        String response;

        if (request.Accept) {
            response = "accept";
        } else {
            response = "reject";
        }

        api.respondToContactRequest(request.ContactRequestId,
                new YoraWebService.RespondToContactRequest(response),
                new RetrofitCallbackPost<>(
                        Contacts.RespondToContactRequestResponse.class, bus));
    }

    @Subscribe
    public void getContacts(Contacts.GetContactsRequest request) {
        api.getContacts(new RetrofitCallbackPost<>(Contacts.GetContactsResponse.class, bus));
    }

    @Subscribe
    public void removeContact(Contacts.RemoveContactRequest request) {
        api.removeContact(request.contactId, new RetrofitCallbackPost<>(
                Contacts.RemoveContactResponse.class, bus));
    }
}




















