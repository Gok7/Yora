package fr.imerir.yora.services;

import java.util.List;

import fr.imerir.yora.infrastructure.ServiceResponse;
import fr.imerir.yora.services.entities.Message;

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
        public String fromContactId;
        public boolean includeSentMessages;
        public boolean includeReceivedMessages;

        public SearchMessagesRequest(String fromContactId, boolean includeSentMessages, boolean includeReceivedMessages) {
            this.fromContactId = fromContactId;
            this.includeSentMessages = includeSentMessages;
            this.includeReceivedMessages = includeReceivedMessages;
        }

        public SearchMessagesRequest(boolean includeSentMessages, boolean includeReceivedMessages) {
            this.includeSentMessages = includeSentMessages;
            this.includeReceivedMessages = includeReceivedMessages;
        }
    }

    public static class SearchMessagesResponse extends ServiceResponse {
        public List<Message> messages;
    }
}
