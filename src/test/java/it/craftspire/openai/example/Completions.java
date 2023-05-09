package it.craftspire.openai.example;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.logging.Logger;

public class Completions {

    private static final Logger logger =  Logger.getLogger(Completions.class.getName());
    private OpenAiService service;

    @Before
    public void setUp() {
        String key = System.getenv("OPENAI_KEY");
        service = new OpenAiService(key, Duration.ofSeconds(120));
    }

    /**
     * An example where OpenAI /completions endpoint is used to complete the unfinished sentence
     */
    @Test
    public void shouldExecuteSimpleCompletionRequest() {
        //given
        String userPrompt = "The GPT AI is amazing because";

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .temperature(0.1)
                .maxTokens(1024)
                .prompt(userPrompt)
                .build();

        //when
        CompletionResult result = service.createCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getText());
    }

    /**
     * An example where OpenAI /completions endpoint is presented a task, and perform work based on the task
     */
    @Test
    public void shouldExecuteSimpleCompletionRequestWithTask() {
        //given
        String userPrompt = "Write a short essay on why GPT AI is amazing.";

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .temperature(0.1)
                .maxTokens(1024)
                .prompt(userPrompt)
                .build();

        //when
        CompletionResult result = service.createCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getText());
    }

    /**
     * An example where OpenAI /completions endpoint is used to execute a task, but at the same time a role is suggested to the AI, impacting the results.
     * Note that behaviour will be different now than when using the Chat completions AI.
     */
    @Test
    public void shouldUseARoleWhileExecutingCompletionRequest() {
        //given
        String userPrompt = "You are an assistant that speaks in the style of pirate Blackbeard. Write a short essay on why GPT AI is amazing.";

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .temperature(0.3)
                .maxTokens(1024)
                .prompt(userPrompt)
                .build();

        //when
        CompletionResult result = service.createCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getText());
    }

}
