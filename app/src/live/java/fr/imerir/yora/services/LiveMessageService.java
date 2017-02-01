package fr.imerir.yora.services;


import com.squareup.otto.Subscribe;

import java.io.File;

import fr.imerir.yora.infrastructure.RetrofitCallbackPost;
import fr.imerir.yora.infrastructure.YoraApplication;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class LiveMessageService extends BaseLiveService {
    protected LiveMessageService(YoraApplication application, YoraWebService api) {
        super(application, api);
    }

    @Subscribe
    public void sendMessage(Messages.SendMessageRequest request) {

        api.sendMessage(
                new TypedString(request.getMessage()),
                new TypedString(Integer.toString(request.getRecipient().getId())),
                new TypedFile("image/jpeg", new File(request.getImagePath().getPath())),
                new RetrofitCallbackPost<>(Messages.SendMessageResponse.class, bus));
    }

    @Subscribe
    public void searchMessages(Messages.SearchMessagesRequest request) {

        if (request.fromContactId != -1) {

            api.searchMessages(
                    request.fromContactId,
                    request.includeSentMessages,
                    request.includeReceivedMessages,
                    new RetrofitCallbackPost<>(Messages.SearchMessagesResponse.class, bus));
        } else {
            api.searchMessages(
                    request.includeSentMessages,
                    request.includeReceivedMessages,
                    new RetrofitCallbackPost<>(Messages.SearchMessagesResponse.class, bus));

        }
    }

    @Subscribe
    public void deleteMessage(Messages.DeleteMessageRequest request) {
        api.deleteMessage(request.messageId, new RetrofitCallbackPost<>
                (Messages.DeleteMessageResponse.class, bus));
    }

    @Subscribe
    public void markMessageAsRead(Messages.MarkMessageAsReadRequest request) {
        api.markMessageAsRead(request.MessageId, new RetrofitCallbackPost<>
                (Messages.MarkMessageAsReadResponse.class, bus));
    }

    @Subscribe
    public void getMessageDetails(Messages.GetMessageDetailsRequest request) {
        api.getMessageDetails(request.Id, new RetrofitCallbackPost<>
                (Messages.GetMessageDetailsResponse.class, bus));
    }
}
