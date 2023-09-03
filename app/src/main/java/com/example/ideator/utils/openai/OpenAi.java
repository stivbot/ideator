package com.example.ideator.utils.openai;

import android.os.AsyncTask;

import com.example.ideator.BuildConfig;

import java.util.function.Consumer;

import dev.ai4j.openai4j.Model;
import dev.ai4j.openai4j.OpenAiClient;
import dev.ai4j.openai4j.chat.ChatCompletionRequest;
import dev.ai4j.openai4j.chat.ChatCompletionResponse;


public class OpenAi extends AsyncTask<Void, Void, Void>{
    public static final Model MODEL = Model.GPT_3_5_TURBO;
    public static final int MAX_TOKENS = 1000;
    public static final double TEMPERATURE = 0.9;

    private OpenAiClient client;
    private Conversation conversation;
    private Consumer<ChatCompletionResponse> onSuccess;
    private Consumer<Throwable> onError;

    public OpenAi(Conversation conversation, Consumer<ChatCompletionResponse> onSuccess, Consumer<Throwable> onError) {
        client = new OpenAiClient(BuildConfig.OPENAI_API_KEY);
        this.conversation = conversation;
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(MODEL)
                .maxTokens(MAX_TOKENS)
                .temperature(TEMPERATURE)
                .messages(this.conversation)
                .build();

        client.chatCompletion(request)
                .onResponse(this.onSuccess)
                .onError(this.onError)
                .execute();
        return null;
    }
}
