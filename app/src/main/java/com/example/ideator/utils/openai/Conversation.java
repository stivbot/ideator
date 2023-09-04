package com.example.ideator.utils.openai;

import java.util.ArrayList;
import java.util.List;

import dev.ai4j.openai4j.chat.Message;

public class Conversation extends ArrayList<Message> {

    public void addSystemMessage(String message) {
        this.add(Message.systemMessage(message));
    }

    public void addAssistantMessage(String message) {
        this.add(Message.assistantMessage(message));
    }

    public void addUserMessage(String message) {
        this.add(Message.userMessage(message));
    }
}
