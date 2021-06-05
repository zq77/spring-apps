package com.z.websocket.view;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageData implements Decoder {
    public String message;
    public String toUserId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
