package me.dri.ProductService.exception;

public class FailedToSentProductToTopic extends RuntimeException {
    public FailedToSentProductToTopic(String message) {
        super(message);
    }
}
