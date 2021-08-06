package animals.dialogs;

import java.util.List;
import java.util.Random;

public class ClarificationQuestions {
    private final List<String> clarificationList = List.of(
            "I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?",
            "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?",
            "Oh, no, don't try to confuse me: say yes or no."
    );

    public void say() {
        System.out.println(
                clarificationList.get(
                        new Random().nextInt(clarificationList.size())
                ));
    }
}