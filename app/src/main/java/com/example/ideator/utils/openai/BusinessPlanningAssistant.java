package com.example.ideator.utils.openai;

import static dev.ai4j.openai4j.chat.JsonSchemaProperty.STRING;
import static dev.ai4j.openai4j.chat.JsonSchemaProperty.description;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.ai4j.openai4j.chat.Function;
import dev.ai4j.openai4j.chat.FunctionCall;

public class BusinessPlanningAssistant extends Assistant {

    public static final String INSTRUCTIONS = "You are a business planning expert. You have helped entrepreneurs to go from an idea to a succesfull business. Your goal is to answer user demands.";
    public static final String PARAMETER_TITLE = "title";
    public static final String PARAMETER_DESCRIPTION = "description";
    public static final String PARAMETER_PROBLEMATIC = "problematic";
    public static final String PARAMETER_SOLUTION = "solution";

    public BusinessPlanningAssistant() {
        super(INSTRUCTIONS);
    }

    public void getTitleDescriptionProblematicSolution(Context context, String shortDescription, final OnResponse onResponse) {
        try {
            String instruction = this.computeInstructions(context, "openai.1.problematic.solution", shortDescription);

            Function function = Function.builder()
                    .name("get_title_sescription_sroblematic_solution")
                    .description("Get the idea title, description, problematic and solution ")
                    .addParameter(PARAMETER_TITLE, STRING, description("Propose a title for the business idea"))
                    .addParameter(PARAMETER_DESCRIPTION, STRING, description("Short description of the business idea"))
                    .addParameter(PARAMETER_PROBLEMATIC, STRING, description("Explain the problematic of the business idea"))
                    .addParameter(PARAMETER_SOLUTION, STRING, description("Explain the solution of the business idea"))
                    .build();

            this.answer(instruction, function, new Assistant.OnResponse<FunctionCall>(){
                @Override
                public void onSuccess(FunctionCall functionCall) {
                    Map<String, Object> arguments = functionCall.argumentsAsMap();
                    arguments.get("title");
                    onResponse.onSuccess(
                            arguments.get(PARAMETER_TITLE).toString(),
                            arguments.get(PARAMETER_DESCRIPTION).toString(),
                            arguments.get(PARAMETER_PROBLEMATIC).toString(),
                            arguments.get(PARAMETER_SOLUTION).toString()
                    );
                }

                @Override
                public void onError(Throwable error) {
                    onResponse.onError(error);
                }
            });
        } catch (IOException e) {
            onResponse.onError(e);
        }
    }

    public interface OnResponse {
        void onSuccess(String title, String description, String problematic, String solution);
        void onError(Throwable error);
    }
}
