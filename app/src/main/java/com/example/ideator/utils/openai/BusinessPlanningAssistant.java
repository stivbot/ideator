package com.example.ideator.utils.openai;

import static dev.ai4j.openai4j.chat.JsonSchemaProperty.STRING;
import static dev.ai4j.openai4j.chat.JsonSchemaProperty.description;

import android.content.Context;

import com.example.ideator.model.idea.IdeaWithSections;

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
    public static final String PARAMETER_PROS = "pros";
    public static final String PARAMETER_CONS = "cons";

    public BusinessPlanningAssistant() {
        super(INSTRUCTIONS);
    }

    public void getTitleDescriptionProblematicSolution(Context context, String shortDescription, final OnResponse onResponse) {
        try {
            //First request = Enhance idea
            String instruction = this.computeInstructions(context, "openai.1.problematic.solution", shortDescription);
            this.answer(instruction, new Assistant.OnResponse<String>() {
                @Override
                public void onSuccess(String response) {
                    Function function = Function.builder()
                            .name("get_title_description_problematic_solution")
                            .description("Get the idea title, description, problematic and solution")
                            .addParameter(PARAMETER_TITLE, STRING, description("Title of the business idea"))
                            .addParameter(PARAMETER_DESCRIPTION, STRING, description("Short description of the business idea"))
                            .addParameter(PARAMETER_PROBLEMATIC, STRING, description("Problematic of the business idea"))
                            .addParameter(PARAMETER_SOLUTION, STRING, description("Solution of the business idea"))
                            .build();

                    BusinessPlanningAssistant.this.answer(function, new Assistant.OnResponse<FunctionCall>(){
                        @Override
                        public void onSuccess(FunctionCall functionCall) {
                            Map<String, Object> arguments = functionCall.argumentsAsMap();
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

    public void getProsCons(Context context, IdeaWithSections ideaWithSections, final OnProsConsResponse onResponse) {
        try {
            //First request = Enhance idea
            String instruction = this.computeInstructions(context, "openai.2.pros.cons",
                    ideaWithSections.idea.getDescription(),
                    ideaWithSections.sections.get(0).getDescription(),
                    ideaWithSections.sections.get(1).getDescription());
            this.answer(instruction, new Assistant.OnResponse<String>() {
                @Override
                public void onSuccess(String response) {
                    Function function = Function.builder()
                            .name("get_pros_cons")
                            .description("Get the pros and cons")
                            .addParameter(PARAMETER_PROS, STRING, description("Pros of the business idea"))
                            .addParameter(PARAMETER_CONS, STRING, description("Cons of the business idea"))
                            .build();

                    BusinessPlanningAssistant.this.answer(function, new Assistant.OnResponse<FunctionCall>(){
                        @Override
                        public void onSuccess(FunctionCall functionCall) {
                            Map<String, Object> arguments = functionCall.argumentsAsMap();
                            onResponse.onSuccess(
                                    arguments.get(PARAMETER_PROS).toString(),
                                    arguments.get(PARAMETER_CONS).toString()
                            );
                        }

                        @Override
                        public void onError(Throwable error) {
                            onResponse.onError(error);
                        }
                    });
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

    public interface OnProsConsResponse {
        void onSuccess(String pros, String cons);
        void onError(Throwable error);
    }
}
