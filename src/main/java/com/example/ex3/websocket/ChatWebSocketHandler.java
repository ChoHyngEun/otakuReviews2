package com.example.ex3.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
    private static List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        logger.info("Connection established: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        logger.info("Message received: " + payload);
        for (WebSocketSession s : sessions) {
            try {
                s.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                logger.error("Error sending message to session " + s.getId() + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        logger.info("Connection closed: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("Transport error on session " + session.getId() + ": " + exception.getMessage());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
