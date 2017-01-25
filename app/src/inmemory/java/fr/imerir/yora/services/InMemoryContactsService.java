package fr.imerir.yora.services;


import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import fr.imerir.yora.infrastructure.YoraApplication;
import fr.imerir.yora.services.entities.ContactRequest;
import fr.imerir.yora.services.entities.UserDetails;

public class InMemoryContactsService extends BaseInMemoryService {

    public InMemoryContactsService(YoraApplication application) {
        super(application);
    }

    @Subscribe
    public void getContactRequests(Contacts.GetContactRequestsRequest request) {
        Contacts.GetContactRequestResponse response = new Contacts.GetContactRequestResponse();
        response.requests = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            response.requests.add(
                    new ContactRequest(i,
                            request.fromUs,
                            createFakeUser(i, false)
                            , new GregorianCalendar()));
        }

        postDelayed(response);
    }

    @Subscribe
    public void getContacts(Contacts.GetContactsRequest request) {
        Contacts.GetContactsResponse response = new Contacts.GetContactsResponse();
        response.Contacts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            response.Contacts.add(createFakeUser(i, true));
        }

        postDelayed(response);
    }

    @Subscribe
    public void sendContactsRequest(Contacts.SendContactRequestRequest request) {

        if (request.userId == 2) {
            Contacts.SendContactRequestsResponse response = new Contacts.SendContactRequestsResponse();
            response.setOperationError("something bad happened");
            postDelayed(response);

        } else {
            postDelayed(new Contacts.SendContactRequestsResponse());
        }
    }

    @Subscribe
    public void respondToContactsRequest(Contacts.RespondToContactRequestRequest request) {

        postDelayed(new Contacts.RespondToContactRequestResponse());
    }

    private UserDetails createFakeUser(int id, boolean isContact) {
        String idString = Integer.toString(id);
        return new UserDetails(
                id,
                isContact,
                "Contact " + idString,
                "Contact" + idString,
                "http://www.gravatar.com/avatar/" + idString + "?d=identicon&s=64");
    }
}
