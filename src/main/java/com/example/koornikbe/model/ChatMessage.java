package com.example.koornikbe.model;

public class ChatMessage {
    private Long gameId;     
    private Long roundId;    
    private String sender;   
    private String content;  
    private boolean systemMessage = false; 

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }
    
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getRoundId() { return roundId; }     
    public void setRoundId(Long roundId) { this.roundId = roundId; } 
    public boolean isSystemMessage() { return systemMessage; }
    public void setSystemMessage(boolean systemMessage) { this.systemMessage = systemMessage; }
}


