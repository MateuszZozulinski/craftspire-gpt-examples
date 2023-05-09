package it.craftspire.openai.example;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ChatCompletions {

    private static final Logger logger =  Logger.getLogger(Completions.class.getName());
    private OpenAiService service;
    @Before
    public void setUp() {
        String key = System.getenv("OPENAI_KEY");
        service = new OpenAiService(key, Duration.ofSeconds(120));
    }

    /**
     * An example where OpenAI /chat/completions endpoint is used to complete the unfinished sentence
     */
    @Test
    public void shouldExecuteSimpleCompletionRequest() {
        //given
        String userPrompt = "The GPT AI is amazing because";

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.1)
                .maxTokens(1024)
                .messages(Arrays.asList(new ChatMessage("user", userPrompt)))
                .build();

        //when
        ChatCompletionResult result = service.createChatCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getMessage().getContent());
    }

    /**
     * An example where OpenAI /chat/completions endpoint is presented a task, and perform work based on the task
     */
    @Test
    public void shouldExecuteSimpleCompletionRequestWithTask() {
        //given
        String userPrompt = "Write a short essay on why GPT AI is amazing.";

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.1)
                .maxTokens(1024)
                .messages(Arrays.asList(new ChatMessage("user", userPrompt)))
                .build();

        //when
        ChatCompletionResult result = service.createChatCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getMessage().getContent());
    }

    /**
     * An example where OpenAI /chat/completions endpoint is used to execute a task, but at the same time a role is suggested to the AI, impacting the results.
     * Compare the results with situation when assigning the role directly to the system.
     */
    @Test
    public void shouldUseARoleWhileExecutingCompletionRequest() {
        //given
        String userPrompt = "You are an assistant that speaks in the style of pirate Blackbeard. Write a short essay on why GPT AI is amazing.";

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.3)
                .maxTokens(1024)
                .messages(Arrays.asList(new ChatMessage("user", userPrompt)))
                .build();

        //when
        ChatCompletionResult result = service.createChatCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getMessage().getContent());
    }

    /**
     * An example where OpenAI /chat/completions endpoint is used to execute a task, but the role is assigned directly to the system
     */
    @Test
    public void shouldUseARoleAssignedToSystemWhileExecutingCompletionRequest() {
        //given
        String systemRole = "You are a helpful assistant that always speaks in the style of pirate Blackbeard";
        String userPrompt = "Explain asynchronous programming";

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.3)
                .maxTokens(1024)
                .messages(Arrays.asList(
                        new ChatMessage("system", systemRole),
                        new ChatMessage("user", userPrompt)))
                .build();

        //when
        ChatCompletionResult result = service.createChatCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getMessage().getContent());
    }

    /**
     * An example where OpenAI /chat/completions endpoint is used to provide a context, and learn chat by providing a conversation example
     */
    @Test
    public void shouldLearnTheTaskFromExamples() {
        //given
        List<ChatMessage> conversationExample = new ArrayList<>(List.of(
                new ChatMessage("user", "one plus one equals two"),
                new ChatMessage("assistant", "1+1=2"),
                new ChatMessage("user", "a squared plus b squared equals c squaredx"),
                new ChatMessage("assistant", "a^2+b^2=c^2"),
                new ChatMessage("user", "f function of x equals x times two"),
                new ChatMessage("assistant", "f(x)=2x")
        ));


        ChatMessage userPrompt = new ChatMessage("user", "logarithm of two times x");
        conversationExample.add(userPrompt);

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.3)
                .maxTokens(1024)
                .messages(conversationExample)
                .build();

        //when
        ChatCompletionResult result = service.createChatCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getMessage().getContent());
    }

    /**
     * An example where similar effect is achieved with just defining the system task
     */
    @Test
    public void shouldInduceTheTaskFromSystemDescription() {
        //given
        List<ChatMessage> conversationExample = new ArrayList<>(List.of(
                new ChatMessage("system", "You are an AI assistant that translates the provided natural language description of mathematical equations into proper mathematical notation. Respond only with equation")
        ));


        ChatMessage userPrompt = new ChatMessage("user", "logarithm of two times x");
        conversationExample.add(userPrompt);

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.3)
                .maxTokens(1024)
                .messages(conversationExample)
                .build();

        //when
        ChatCompletionResult result = service.createChatCompletion(completionRequest);

        //then
        logger.info(result.getChoices().get(0).getMessage().getContent());
    }

}
