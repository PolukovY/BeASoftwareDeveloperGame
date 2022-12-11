package com.levik.game.infra.configuration;

import com.levik.game.domain.GameCommandManager;
import com.levik.game.domain.command.*;
import com.levik.game.domain.model.Button;
import com.levik.game.domain.model.ClientResponse;
import com.levik.game.domain.user.model.UserDetails;
import com.levik.game.domain.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class GameConfiguration {

    private static final String START_GAME_DETAILS = """
            Ever wonder what it would be like to be a professional Software Developer? Well, here is your chance. My name is Brian, and I've been a professional Software Developer for over 10 years now. I'm going to take you through some common situations that Software Developer's face. You'll have the opportunity to respond to each situation the way you feel it should be handled. The result of your choice may be positive, negative, or something in between.
                        
            Don't worry, no programming knowledge is required.
            """;

    private static final String PLAY_GAME = """
            Ever wonder what it would be like to be a professional Software Developer? Well, here is your chance. My name is Brian, and I've been a professional Software Developer for over 10 years now. I'm going to take you through some common situations that Software Developer's face. You'll have the opportunity to respond to each situation the way you feel it should be handled. The result of your choice may be positive, negative, or something in between.
                        
            The better your answer the more points you receive. Once you receive 15 points you win the game! But be careful, you only get 5 tries!
                        
            Don't worry, no programming knowledge is required.
            """;

    private static final String MAIN_MENU = """
            Main Menu
                        
            Current Score: %s out of 15
            Tries: 0 out of 5
                        
            Choose which situation you would you like to explore and earn points:
            """;

    private static final String MENU_1 = """
            Situation
                        
            You are part of a development team that supports a customer-facing website. You work for a relatively small company whose customers primarily reside within the United States. Because your company has been growing quite rapidly, the marketing team has decided to start marketing to other parts of the world, including Mexico, France, and Germany.
                        
            Task
                        
            They would like you to enhance the website to support Spanish, French, and German languages, not just English. They provide you with the translated text and ask for the changes to be done in two days.
                        
            Action
                        
            You start to put conditional logic into the website to show the different languages. However, after the first day you realize that this task will take more than the two days requested. You have identified two possible solutions to solve this problem. The first option appears to be the best solution, but will take the longest time, five more days. The second solution is an okay solution, but will only take two more days. During your research, you briefly read about other options that may be better, but you would need more time to fully understand.
                        
                        
            How would you handle this situation?
            """;

    private static final String MENU_1_ANSWER_1 = """
            Choice:
                        
            You have chosen to provide the quicker solution.
                        
            Result:
                        
            +1 point
                        
            You were close to meeting the deadline set by the business and the website supports all the requested languages. A week later the marketing team comes to you with a new request, "We need you to add ten more languages." With the quick solution you provided, each language you need to add keeps on adding to the total time it takes to complete. First languages took you three days. The next ten takes five days.
                        
            Wisdom:
                        
            Good job. Your solution works and you completed it rather quickly. But...
                        
            Speed isn't the only factor when it comes to successful programming. It looks good in the short-term, but it often comes back in two negative forms: defects (i.e., customer complaints) or really slow changes in the future. In this situation, the quick solution provided lacks quality. A quality solution should be able to be changed without creating more work each time.
            """;

    private static final String MENU_1_ANSWER_2 = """
            Choice:
                        
            You have chosen to provide the best solution you currently are aware of.
                        
            Result:
                        
            +3 point
                        
            You miss the deadline by four days. The translations look great. The business has mixed-feelings: they are happy that the customers are happy, but they are concerned any translations in the future will also take a long time. The following week the marketing team has a new request, "We would like you add ten more languages." It only takes you three days to add them.
                        
            Wisdom:
                        
            Great job. It isn't always easy to disappoint people by missing deadlines. And if its possible, it is ideal to both meet the deadline and deliver a good solution. You have placed a high value on quality, and that is a great thing. When the marketing asked for ten more languages, it took you less time than the original request. That is the result of a quality solution.
                        
            However, there may have been an even better solution available, but since you didn't spend the time to research more, the better solution may never be found.
            """;

    private static final String MENU_1_ANSWER_3 = """
            Choice:\n\nYou have chosen to provide the best solution you currently are aware of.\n\nResult:\n\n+3 point\n\nYou miss the deadline by four days. The translations look great. The business has mixed-feelings: they are happy that the customers are happy, but they are concerned any translations in the future will also take a long time. The following week the marketing team has a new request, \"We would like you add ten more languages.\" It only takes you three days to add them.\n\nWisdom:\n\nGreat job. It isn't always easy to disappoint people by missing deadlines. And if its possible, it is ideal to both meet the deadline and deliver a good solution. You have placed a high value on quality, and that is a great thing. When the marketing asked for ten more languages, it took you less time than the original request. That is the result of a quality solution.\n\nHowever, there may have been an even better solution available, but since you didn't spend the time to research more, the better solution may never be found.
            """;

    private static final String MENU_2 = """
            Situation
                        
            The business would like the company's website to accept credit card payments. They have not specified which credit card providers they would like supported.
                        
            Task
                        
            You are asked to provide the user's the ability to enter credit card information on the website.
                        
            Action
                        
            Soon after you start working on this request, you realize the business has not clearly provided the requirements. More specifically, they have not provided what credit card providers you should integrate with.
                        
            How would you handle this situation?
            """;

    private static final String MENU_2_ANSWER_1 = """
            Choice
                        
            You have chosen to ask the business for clarification before proceeding.
                        
            Result
                        
            +5 point
                        
            The business apologizes for their mistake. They tell you that they would like to support Visa and Mastercard. You make the change and now the website can accept both Visa and Mastercard as forms of payment.
                        
            Wisdom
                        
            Great job. It is good to remember that the business is responsible for providing functional requirements. Always ask for clarification if you are not sure what to build.
            """;

    private static final String MENU_2_ANSWER_2 = """
            Choice:
                        
            You have chosen to pick the provider yourself.
                        
            Result:
                        
            +0 point
                        
            When the business sees the website with your changes, they get really upset. You did not provide what they thought they asked for. You tell them they never clarified, but their response is what you would expect, "But you never asked."
                        
            Wisdom:
                        
            You made a mistake. You should always keep in mind what you are building is not for yourself. Unless you are self-employed, you work for a company, whose wishes are communicated to you by the business area of the company. Yes, there are times where the business is not sure what they want, but this situation wasn't one of those times ;-)
            """;

    private static final String MENU_2_ANSWER_3 = """
            Choice:
                        
            You have chosen to see what other websites support and do the same.
                        
            Result:
                        
            +2 point
                        
            When the business sees the website with your changes, they get really upset. You did not provide what they thought they asked for. You explain that you used other respectable websites to model from. The business appreciates the effort, but in this situation they already knew what they wanted.
                        
            Wisdom:
                        
            Okay job. It is good to remember that the business is responsible for providing functional requirements. It is usually always a bad idea to guess what they want. In this situation, you did some research to make an educated guess. Sometimes, the business does indeed need help to figure out what they want; the key is to ask if the business needs help, before spending time on research.
            """;

    private static final String MENU_3 = """
            Situation
                        
            You find yourself home by yourself on the weekend, with no obligations to attend to. There is this new programming language that you have heard about at work. You wonder if the programming language could help your team.
                        
            Task
                        
            You need to figure out what you are going to do with your weekend.
                        
            Action
                        
            You have decided to spend your time:
                        
            How would you handle this situation?
            """;

    private static final String MENU_3_ANSWER_1 = """
            Choice
                        
            You have chosen to spend the entire weekend playing video games.
                        
            Result
                        
            +1 point
                        
            You have a lot of fun playing games. However, you still know nothing about this new programming language. You remain ignorant about its pros and cons.
                        
            Wisdom
                        
            Playing video games can be a blast. But if you hope to further your programming enjoyment and career, then learning new languages and technologies is a must. The techological landscape changes at a rapid pace. Some of these technologies aren't useful or applicable to your job, but some can drastically improve productivity. Being informed of new languages is very wise.
            """;

    private static final String MENU_3_ANSWER_2 = """
            Choice
                        
            You have chosen to briefly research the new programming language.
                        
            Result
                        
            +3 point
                        
            You learn that this new language could really be useful in your current job. You have problems that this language could help with. You don't know all the details, but you have an awareness now to dig further if you choose to.
                        
            Wisdom
                        
            Good job. You have taken the first step to not being ignorant. You now have an awareness of a new language and have the foundation to build on if you choose to. This awareness doesn't take very long to aquire.
                        
            However, if this language is truly useful, being aware isn't enough. You need to dig in and learn it. It's only when you dig into a new techology that you truly can start making judgements and comparing it to other technologies.
            """;

    private static final String MENU_3_ANSWER_3 = """
            Choice
                        
            You have chosen to spend most of the weekend learning the new programming language.
                        
            Result
                        
            +5 point
                        
            You learn that this new language could really be useful in your current job. You dig in and gain a deeper understanding of its pros and cons. Upon returning to work, you start the conversation with the team and explain to them what you have learned.
                        
            Wisdom
                        
            Great job. You have expanded your skillset. You now have one more tool to pull out if the situation arises. If a conversation comes up about this technology it is highly likely you will be able to contribute and follow along.
                        
            You do need to use caution with technologies. If the likelihood of you using the technology is minimal, unless you truly want to, you may want to not spend your time digging into it too much. Your time is limited, so use it wisely on useful technologies.
            """;

    private static final String MENU_4 = """
            Situation
                        
            As part of a large project, your team is building a online store. Part of the functionality you need to support is accepting payments. In order to accept payments, you need to calculate shipping costs, so that the customer knows how much they will be charged to have their items shipped to them. The business has said they want the shipping costs to follow standard USPS charges.
                        
            Task
                        
            You need to add the ability for the payment process to calculate and show the customer the USPS shipping cost for their order so that the customer can make an informed decision about proceeding with the purchase.
                        
            Action
                        
            You must decide whether to build your own shipping costs service or to use an existing service.
                        
            How would you handle this situation?
            """;

    private static final String MENU_4_ANSWER_1 = """
            Choice
                        
            You have chosen to use an existing shipping cost service.
                        
            Result
                        
            +5 point
                        
            You spend a significant amount of time learning how to integrate with the existing service. As shipping rates change, your online store doesn't need to change, since the existing service takes care of all the updates.
                        
            Wisdom
                        
            Great job. If there is an existing service, library, framework, or anything else that is already built where someone else is responsible for maintaining it, then it is usually always best to use that existing thing. The only times you should need to build your own is if you have unique needs that nothing currently supports; the existing things are poor quality; or the business has decided that it must be built internally.
                        
            There is one major downside to use an external service: you are limited by what the service provides. If you come to a point where the business wants something the service does not provide, then it can be difficult to change.
            """;

    private static final String MENU_4_ANSWER_2 = """
            Choice
                        
            You have chosen to build your own shipping cost service.
                        
            Result
                        
            +2 point
                        
            You spend a significant amount of time building your own shipping cost service. As shipping rates change, you are constantly having to update the service. Over time, the service take so much time to support that a small team is put together to maintain it.
                        
            Wisdom
                        
            Okay job. You now have full control over the shipping cost service, but that control comes at a very heavy price. You pay the up-front cost of creating the service, and you continue to pay a price as it needs to change.
                        
            If there is an existing service, library, framework, or anything else that is already built where someone else is responsible for maintaining it, then it is usually always best to use that existing thing.
            """;

    private static final String MENU_4_ANSWER_3 = """
            Choice:
                        
            You have chosen to tell the business it's impossible to provide.
                        
            Result:
                        
            +0 point
                        
            The business isn't too happy with your answer. They end up asking around and find conclusive evidence that you are mistaken: there are plenty of external services that provide this service. Your relationship with the business is severly damaged.
                        
            Wisdom:
                        
            You made a mistake. If you want the business to trust you, then you should not mislead them.
            """;

    private static final String MENU_5 = """
            Situation
                        
            You are part of a team meeting with the business. As a group, you are going through upcoming requirements. The current requirement you are discussing is about moving all the data from the old system to the new system. The business wants to know how long it will take you to complete this requirement. You are not familiar with the old system, but you are familiar with the new system.
                        
            Task
                        
            You need to provide the busines with an estimate of time.
                        
            Action
                        
            You are uncertain how to respond to this request for an estimate of time. There is much uknown about this old system. There might be people within the company who are more familar with this old system.
                        
            How would you handle this situation?
            """;

    private static final String MENU_5_ANSWER_1 = """
            Choice
                        
            You have chosen to provide a high estimate since so much is unknown.
                        
            Result
                        
            +3 point
                        
            It ends up taking far less time than what you estimated. While the old system wasn't easy to understand, you had help from someone who is familiar with the old system which greatly helped. The business is a little confused why your estimate was so inaccurate. Next time you provide an estimate, the business questions your accuracy.
                        
            Wisdom
                        
            Good job. Estimating how long something will take when you are unfamilar with the impacted areas is very difficult. Estimating high is one way to accomdate this uncertainity. However, if you always estimate high, the usefulness of estimates becomes less over time. The business starts to question how much your estimates can be trusted.
            """;

    private static final String MENU_5_ANSWER_2 = """
            Choice
                        
            You have chosen to assume the old system can't be very difficult to understand.
                        
            Result
                        
            +0 point
                        
            It ends up taking far more time to complete than what you estimated. The business is frustrated, because they had planned work according to your estimate, but now everything dependent on this work now has to be pushed further into the future. Next time you provide an estimate, the business questions your accuracy.
                        
            Wisdom
                        
            You have made a mistake. Estimating how long something will take when you are unfamilar with the impacted areas is very difficult. Estimating low because you assume something is easy will not help overcome this difficulty. The business starts to question how much your estimates can be trusted.
            """;

    private static final String MENU_5_ANSWER_3 = """
            Choice
                        
            You have chosen to ask for more time to understand the old system first.
                        
            Result
                        
            +5 point
                        
            You discover that there is someone who is familiar with the old system. You work with this person for a day and together you decide on an estimate. The estimate is pretty close to the actual time it takes to do the work. The business plan for the requirement was planned and executed well.
                        
            Wisdom
                        
            Great job. Estimating how long something will take when you are unfamilar with the impacted areas is very difficult. Taking time to understand the unknowns is one of the best ways to overcome this difficulty. The business will thank you too, since their plans will be more accurate.
            """;

    private static final String MENU_6 = """
            Situation
                        
            You just completed development of a requirement. The testing team tests your requirement and discovers it is not working correctly. The tester who discovered the defect has just called you to discuss what they found.
                        
            Task
                        
            You need to decide how to respond to the news that your development has a defect.
                        
            Action
                        
            In response to what the tester is sharing you:
                        
            How would you handle this situation?
            """;

    private static final String MENU_6_ANSWER_1 = """
            Choice
                        
            You have chosen to thank the tester and fix the defect.
                        
            Result
                        
            +5 point
                        
            The tester is happy you recognize that they have protected you and the company by preventing the mistake from impacting the customers. You fix the defect and the requirement is provided to the customer and it works as expected.
                        
            Wisdom
                        
            Great job. A tester's job is to prevent mistakes from impacting the customer. The discovery of a defect is due to something you forgot or something that is incorrect in the code. The tester has helped you do your job. You should thank them and fix your mistake.
            """;

    private static final String MENU_6_ANSWER_2 = """
            Choice
                        
            You have chosen to tell the tester they tested it wrong.
                        
            Result
                        
            +0 point
                        
            The tester is upset that you are telling them they did their job wrong. They politely ask how they should have tested it. You are unable to explain why the testing was wrong. The tester leaves the conversation upset, and the defect still hasn't been resolved.
                        
            Wisdom
                        
            You have made a mistake. A tester's job is to prevent mistakes from impacting the customer. The discovery of a defect is due to something you forgot or something that is incorrect in the code. The tester has helped you do your job. You should thank them and fix your mistake. What you should not do is blame the tester for your own mistake.
            """;

    private static final String MENU_6_ANSWER_3 = """
            Choice
                        
            You have chosen to be upset but fix the defect anyway.
                        
            Result
                        
            +2 point
                        
            The tester feels like you blame them for the defect. You fix the defect and the requirement is provided to the customer and it works as expected. However, the tester is discouraged from doing their job, since they are afraid the next time they need to tell you about a defect you will get upset again.
                        
            Wisdom
                        
            Okay job. A tester's job is to prevent mistakes from impacting the customer. The discovery of a defect is due to something you forgot or something that is incorrect in the code. The tester has helped you do your job. You should thank them and fix your mistake. What you should not do is blame the tester for your own mistake.
                        
            It's natural to be upset at yourself, but be sure not to be mad at the messenger who is only trying to help.
            """;

    @Bean
    public Map<String, ClientResponse> gameStepStorage() {
        Map<String, ClientResponse> storage = new HashMap<>();
        storage.put("/start", create(START_GAME_DETAILS, List.of(createButton("/play", "Play Online"))));
        storage.put("/play", create(PLAY_GAME, List.of(createButton("/go", "Let's begin!"))));
        storage.put("/go", create(MAIN_MENU, List.of(
                createButton("/menu1", "Balance between quality and speed"),
                createButton("/menu2", "Unclear requirements"),
                createButton("/menu3", "Extracurricular programming"),
                createButton("/menu4", "Build or use"),
                createButton("/menu5", "Estimate time"),
                createButton("/menu6", "Defect found")
        )));

        storage.put("/menu1", create(MENU_1, List.of(
                createButton("/menu1_answer1", "Provide the quicker solution"),
                createButton("/menu1_answer2", "Provide the best solution you currently are aware of"),
                createButton("/menu1_answer3", "Spend one more day researching solutions, and then provide the best solution")
        )));

        storage.put("/menu1_answer1", create(MENU_1_ANSWER_1, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu1_answer2", create(MENU_1_ANSWER_2, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu1_answer3", create(MENU_1_ANSWER_3, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu2", create(MENU_2, List.of(
                createButton("/menu2_answer1", "Ask the business for clarification before proceeding"),
                createButton("/menu2_answer2", "Pick the provider yourself"),
                createButton("/menu2_answer3", "See what other websites support and do the same")
        )));

        storage.put("/menu2_answer1", create(MENU_2_ANSWER_1, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu2_answer2", create(MENU_2_ANSWER_2, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu2_answer3", create(MENU_2_ANSWER_3, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu3", create(MENU_3, List.of(
                createButton("/menu3_answer1", "Spend the entire weekend playing video games"),
                createButton("/menu3_answer2", "Briefly research the new programming language"),
                createButton("/menu3_answer3", "Spend most of the weekend learning the new programming language")
        )));

        storage.put("/menu3_answer1", create(MENU_3_ANSWER_1, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu3_answer2", create(MENU_3_ANSWER_2, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu3_answer3", create(MENU_3_ANSWER_3, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu4", create(MENU_4, List.of(
                createButton("/menu4_answer1", "Use an existing shipping cost service"),
                createButton("/menu4_answer2", "Build your own shipping cost service"),
                createButton("/menu4_answer3", "Tell the business it's impossible to provide")
        )));

        storage.put("/menu4_answer1", create(MENU_4_ANSWER_1, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu4_answer2", create(MENU_4_ANSWER_2, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu4_answer3", create(MENU_4_ANSWER_3, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu5", create(MENU_5, List.of(
                createButton("/menu5_answer1", "Provide a high estimate since so much is unknown"),
                createButton("/menu5_answer2", "Assume the old system can't be very difficult to understand"),
                createButton("/menu5_answer3", "Ask for more time to understand the old system first")
        )));

        storage.put("/menu5_answer1", create(MENU_5_ANSWER_1, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu5_answer2", create(MENU_5_ANSWER_2, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu5_answer3", create(MENU_5_ANSWER_3, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu6", create(MENU_6, List.of(
                createButton("/menu6_answer1", "Thank the tester and fix the defect"),
                createButton("/menu6_answer2", "Tell the tester they tested it wrong"),
                createButton("/menu6_answer3", "Be upset but fix the defect anyway")
        )));

        storage.put("/menu6_answer1", create(MENU_6_ANSWER_1, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu6_answer2", create(MENU_6_ANSWER_2, List.of(
                createButton("/go", "Back to Menu")
        )));

        storage.put("/menu6_answer3", create(MENU_6_ANSWER_3, List.of(
                createButton("/go", "Back to Menu")
        )));

        return storage;
    }

    @Bean
    public Map<String, Integer> scoreStorage() {
        var scoreStorage = new HashMap<String, Integer>();
        scoreStorage.put("/menu1_answer1", 1);
        scoreStorage.put("/menu1_answer2", 3);
        scoreStorage.put("/menu1_answer3", 3);

        scoreStorage.put("/menu2_answer1", 5);
        scoreStorage.put("/menu2_answer2", 0);
        scoreStorage.put("/menu2_answer3", 2);

        scoreStorage.put("/menu3_answer1", 1);
        scoreStorage.put("/menu3_answer2", 3);
        scoreStorage.put("/menu3_answer3", 5);

        scoreStorage.put("/menu4_answer1", 5);
        scoreStorage.put("/menu4_answer2", 2);
        scoreStorage.put("/menu4_answer3", 0);

        scoreStorage.put("/menu5_answer1", 3);
        scoreStorage.put("/menu5_answer2", 0);
        scoreStorage.put("/menu5_answer3", 5);

        scoreStorage.put("/menu6_answer1", 5);
        scoreStorage.put("/menu6_answer2", 0);
        scoreStorage.put("/menu6_answer3", 2);
        return scoreStorage;
    }

    @Bean
    public Command commandHelper(Map<String, ClientResponse> gameStepStorage) {
        return new CommandHelper(gameStepStorage);
    }

    @Bean
    public Command scoreCalculator(Command commandHelper, Map<String, Integer> scoreStorage, UserRepository userRepository) {
        return new ScoreCalculatorCommand(commandHelper, scoreStorage, userRepository);
    }

    @Bean
    public Command menuCommand(Command scoreCalculator, UserRepository userRepository) {
        return new MenuCommand(scoreCalculator, userRepository);
    }

    @Bean
    public Command gameOverCommand(Command menuCommand, UserRepository userRepository) {
        return new GameOverOrStartCommand(menuCommand, userRepository);
    }

    @Bean
    public GameCommandManager gameCommandManager(Command gameOverCommand) {
        return new GameCommandManager(gameOverCommand);
    }

    @Bean
    public UserRepository userRepository(Map<String, UserDetails> userStorage) {
        return new UserRepository(userStorage);
    }

    @Bean
    public Map<String, UserDetails> userStorage() {
        return new HashMap<>();
    }

    private ClientResponse create(String body, List<Button> buttons) {
        return new ClientResponse(body, buttons);
    }

    private Button createButton(String code, String label) {
        return new Button(code, label);
    }


}
