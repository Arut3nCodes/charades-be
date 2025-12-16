package com.example.koornikbe.ws;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.example.koornikbe.model.ChatMessage;
import com.example.koornikbe.model.Game;
import com.example.koornikbe.service.GameService;
import com.example.koornikbe.service.RoundService;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final GameService gameService;
    private final RoundService roundService;

    private final ConcurrentMap<Long, Set<WebSocketSession>> gameSessions = new ConcurrentHashMap<>();
    private final Set<WebSocketSession> globalSessions = ConcurrentHashMap.newKeySet();

    private final ObjectMapper mapper = new ObjectMapper();

    public ChatWebSocketHandler(GameService gameService, RoundService roundService) {
        this.gameService = gameService;
        this.roundService = roundService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        globalSessions.add(session);
        System.out.println("WS CONNECTED: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        globalSessions.remove(session);
        for (Set<WebSocketSession> set : gameSessions.values()) {
            set.remove(session);
        }
        System.out.println("WS DISCONNECTED: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMsg = mapper.readValue(message.getPayload(), ChatMessage.class);


        if (chatMsg.getGameId() == null) {
            globalSessions.add(session);
            String jsonGlobal = mapper.writeValueAsString(chatMsg);
            for (WebSocketSession s : globalSessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(jsonGlobal));
                }
            }
            return;
        }


        gameSessions.putIfAbsent(chatMsg.getGameId(), ConcurrentHashMap.newKeySet());
        Set<WebSocketSession> sessions = gameSessions.get(chatMsg.getGameId());
        sessions.add(session);

        Game game = gameService.getGameById(chatMsg.getGameId());
        String currentPrompt = chatMsg.getRoundId() != null
                ? roundService.getPromptNameByRoundId(chatMsg.getRoundId())
                : null;

        if (currentPrompt != null && chatMsg.getContent().equalsIgnoreCase(currentPrompt)) {
            chatMsg.setSystemMessage(true);
            chatMsg.setContent(chatMsg.getSender() + " zgadł hasło!");
        }

        String jsonToSend = mapper.writeValueAsString(chatMsg);
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(jsonToSend));
            }
        }
    }
}
