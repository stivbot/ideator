package com.example.ideator.utils.openai;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.ideator.model.idea.IdeaWithSections;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import dev.ai4j.openai4j.chat.Function;
import dev.ai4j.openai4j.chat.FunctionCall;

public class Assistant {
    public static final String OPENAI_PROPERTIES_FILE = "openai.properties";
    private Conversation conversation;

    public Assistant(String instructions) {
        this.conversation = new Conversation();
        this.conversation.addSystemMessage(instructions);
    }

    protected void answer(String message, final OnResponse<String> onResponse) {
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
                }
            )
            .chat(conversation);
        });
    }

    protected void answer(String message, Function function, final OnResponse<FunctionCall> onResponse) {
        conversation.addUserMessage(message);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work
            new OpenAi(response -> {
                    FunctionCall functionCall = response.choices().get(0).message().functionCall();
                    conversation.addAssistantMessage(functionCall.toString());

                    handler.post(() -> {
                        //UI Thread work
                        onResponse.onSuccess(functionCall);
                    });
                },
                error -> {
                    handler.post(() -> {
                        //UI Thread work
                        onResponse.onError(error);
                    });
                }
            )
            .chat(conversation, function);
        });
    }

    protected String computeInstructions(Context context, String key, String... instructions) throws IOException {
        Properties properties = new Properties();
        properties.load(context.getAssets().open(OPENAI_PROPERTIES_FILE));
        String emptyInstructions = properties.getProperty(key);
        return String.format(emptyInstructions, instructions);
    }

    protected interface OnResponse<T> {
        void onSuccess(T response);
        void onError(Throwable error);
    }
}
