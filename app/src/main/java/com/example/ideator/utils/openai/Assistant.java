package com.example.ideator.utils.openai;

import com.example.ideator.model.idea.IdeaWithSections;

import java.util.function.Consumer;

public class Assistant {
    public static Assistant BUSINESS_PLANNING_EXPERT = new Assistant("You are a business planning expert. You have helped entrepreneurs to go from an idea to a succesfull business. Your goal is to answer user demands.");

    private Conversation conversation;

    public Assistant(String instructions) {
        this.conversation = new Conversation();
        this.conversation.addSystemMessage(instructions);
    }

    public void answer(String message, final OnResponse onResponse) {
        conversation.addSUserMessage(message);
        new OpenAi(conversation,
                response -> {
                    String response_message = response.choices().get(0).message().content();
                    conversation.addAssistantMessage(response_message);

                    onResponse.onSuccess(response_message);
                },
                error -> {
                    onResponse.onError(error);
                })
                .execute();
    }

    public interface OnResponse {
        void onSuccess(String response);
        void onError(Throwable error);
    }

    public void identifyProblematicAndSolution(String short_description) {

        //Return Name, Description, Problematic, Solution
    }
}
