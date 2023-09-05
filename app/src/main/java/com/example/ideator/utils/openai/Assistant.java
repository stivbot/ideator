package com.example.ideator.utils.openai;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.ideator.model.idea.IdeaWithSections;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Assistant {
    public static final String OPENAI_PROPERTIES_FILE = "openai.properties";
    public static final Assistant BUSINESS_PLANNING_EXPERT = new Assistant("You are a business planning expert. You have helped entrepreneurs to go from an idea to a succesfull business. Your goal is to answer user demands.");

    private Conversation conversation;

    public Assistant(String instructions) {
        this.conversation = new Conversation();
        this.conversation.addSystemMessage(instructions);
    }

    private void answer(String message, final OnResponse onResponse) {
        conversation.addUserMessage(message);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work
            new OpenAi(response -> {
                String response_message = response.choices().get(0).message().content();
                conversation.addAssistantMessage(response_message);

                handler.post(() -> {
                    //UI Thread work
                    onResponse.onSuccess(response_message);
                });
            },
            error -> {
                handler.post(() -> {
                    //UI Thread work
                    onResponse.onError(error);
                });
            })
            .chat(conversation);
        });
    }

    public void getDescriptionProblematicSolution(Context context, String shortDescription, final OnResponse onResponse) {
        try {
            String instruction = this.computeInstructions(context, "openai.1.problematic.solution", shortDescription);
            this.answer(instruction, onResponse);
        } catch (IOException e) {
            onResponse.onError(e);
        }
    }

    private String computeInstructions(Context context, String key, String... instructions) throws IOException {
        Properties properties = new Properties();
        properties.load(context.getAssets().open(OPENAI_PROPERTIES_FILE));
        String emptyInstructions = properties.getProperty(key);
        return String.format(emptyInstructions, instructions);
    }

    public interface OnResponse {
        void onSuccess(String response);
        void onError(Throwable error);
    }
}
