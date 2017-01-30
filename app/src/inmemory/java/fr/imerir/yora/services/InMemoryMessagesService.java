package fr.imerir.yora.services;


import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import fr.imerir.yora.infrastructure.YoraApplication;
import fr.imerir.yora.services.entities.Message;
import fr.imerir.yora.services.entities.UserDetails;

public class InMemoryMessagesService extends BaseInMemoryService {
    public InMemoryMessagesService(YoraApplication application) {
        super(application);
    }

    @Subscribe
    public void deleteMessage(Messages.DeleteMessageRequest request) {
        Messages.DeleteMessageResponse response = new Messages.DeleteMessageResponse();
        response.messageId = request.messageId;
        postDelayed(response);
    }

    @Subscribe
    public void searchMessages(Messages.SearchMessagesRequest request) {

        Messages.SearchMessagesResponse response = new Messages.SearchMessagesResponse();
        response.messages = new ArrayList<>();

        UserDetails[] users = new UserDetails[10];

        for (int i = 0; i < users.length; i++) {
            String stringId = Integer.toString(i);
            users[i] = new UserDetails(
                    i,
                    true,
                    "User " + stringId,
                    "user_" + stringId,
                    "http://gravatar.com/avatar/" + stringId + "?d=identicon");
        }

        Random random = new Random();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -100);

        for (int i = 0; i < 100; i++) {
            boolean isFromUs;

            if (request.includeReceivedMessages && request.includeSentMessages) {
                isFromUs = random.nextBoolean();
            } else {
                isFromUs = !request.includeReceivedMessages;
            }

            date.set(Calendar.MINUTE, random.nextInt(60 * 24));

            String numberString = Integer.toString(i);
            response.messages.add(new Message(
                    i,
                    (Calendar) date.clone(),
                    "Short message " + numberString,
                    "Long message " + numberString,
                    "",
                    users[random.nextInt(users.length)],
                    isFromUs,
                    i > 4));
        }

        postDelayed(response, 2000);//2 sec to get messages.
    }

    @Subscribe
    public void sendMessage(Messages.SendMessageRequest request) {

        Messages.SendMessageResponse response = new Messages.SendMessageResponse();
        if (request.getMessage().equals("error")) {
            response.setOperationError("Something bad happened");
        } else if (request.getMessage().equals("error-message")) {
            response.setPropertyError("message", "Invalid message");
        }

        postDelayed(response, 1500, 3000);
    }

    @Subscribe
    public void markMessageAsRead(Messages.MarkMessageAsReadRequest request) {
        postDelayed(new Messages.MarkMessageAsReadResponse());
    }

    @Subscribe
    public void getMessageDetails(Messages.GetMessageDetailsRequest request) {

        Messages.GetMessageDetailsResponse response = new Messages.GetMessageDetailsResponse();
        response.Message = new Message(
                1,
                Calendar.getInstance(),
                "Short Message",
                "Long message",
                null,
                new UserDetails(1, true, "Displayname", "Username", ""),
                false,
                false);
        postDelayed(response);
    }
}
