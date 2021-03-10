package animals;

import animals.dialogs.ClarificationQuestions;
import animals.dialogs.Goodbyes;
import animals.dialogs.Greetings;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogueManager {
    private final Greetings greetings;
    private final Goodbyes goodbyes;
    private final ClarificationQuestions clarificationQuestions;
    private final Scanner scanner;
    private final Formatter formatter;

    public DialogueManager() {
        greetings = new Greetings();
        goodbyes = new Goodbyes();
        clarificationQuestions = new ClarificationQuestions();
        scanner = new Scanner(System.in);
        formatter = new Formatter();
    }

    public void getGreeting() {
        greetings.sayHello();
    }

    public void getGoodbye() {
        goodbyes.sayGoodbye();
    }

    // true means positive answer, false is negative
    public boolean getUserAnswer() {
        String input = scanner.nextLine().trim().toLowerCase();
        Pattern patYes = Pattern.compile(
                "(y|yes|yeah|yep|sure|right|affirmative|correct|indeed|you bet|exactly|you said it)[!.]?",
                Pattern.CASE_INSENSITIVE
        );
        Pattern patNo = Pattern.compile(
                "(n|no|no way|nah|nope|negative|I don't think so|yeah no)[!.]?",
                Pattern.CASE_INSENSITIVE
        );
        if (patYes.matcher(input).matches()) {
            return true;
        } else if (patNo.matcher(input).matches()) {
            return false;
        } else {
            clarificationQuestions.say();
            return getUserAnswer();
        }
    }

    // Returns array of Strings, where array[0] is generated question,
    // array[1] is "right" or "left", it means on which side of the Node we should
    // put a new animal
    public String[] getFact(String firstAnimal, String secondAnimal) {
        System.out.printf("Specify a fact that distinguishes %s from %s.\n",
                formatter.getNameOfAnimalWithArticle(firstAnimal),
                formatter.getNameOfAnimalWithArticle(secondAnimal));
        System.out.println("The sentence should satisfy one of the following templates:\n" +
                "- It can ...\n" +
                "- It has ...\n" +
                "- It is a/an ...\n" +
                "");
        String input = scanner.nextLine().trim();
        Pattern pattern = Pattern.compile("^it (can|has|is) (.*?)[.?!]?$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        while (!matcher.matches()) {
            System.out.printf("The examples of a statement:\n" +
                            " - It can fly\n" +
                            " - It has horn\n" +
                            " - It is a mammal\n" +
                            "Specify a fact that distinguishes %s from %s.\n" +
                            "The sentence should be of the format: 'It can/has/is ...'.\n",
                    formatter.getNameOfAnimalWithArticle(firstAnimal),
                    formatter.getNameOfAnimalWithArticle(secondAnimal));
            matcher = pattern.matcher(scanner.nextLine());
        }
        String canHasIs = matcher.group(1);
        String fact = matcher.group(2);
        System.out.printf("Is the statement correct for %s?\n",
                formatter.getNameOfAnimalWithArticle(secondAnimal));
        String leftOrRight;
        if (getUserAnswer()) {
            leftOrRight = "right";
            System.out.println("I have learned the following facts about animals:");
            System.out.printf("- The %s %s %s.\n",
                    formatter.getNameOfAnimalWithoutArticle(firstAnimal),
                    formatter.canHasIsNegative(canHasIs),
                    fact);
            System.out.printf("- The %s %s %s.\n",
                    formatter.getNameOfAnimalWithoutArticle(secondAnimal),
                    canHasIs,
                    fact);
        } else  {
            leftOrRight = "left";
            System.out.println("I have learned the following facts about animals:");
            System.out.printf("- The %s %s %s.\n",
                    formatter.getNameOfAnimalWithoutArticle(firstAnimal),
                    canHasIs,
                    fact);
            System.out.printf("- The %s %s %s.\n",
                    formatter.getNameOfAnimalWithoutArticle(secondAnimal),
                    formatter.canHasIsNegative(canHasIs),
                    fact);
        }
        System.out.println("I can distinguish these animals by asking the question:");
        String question = formatter.distinguishQuestion(canHasIs, fact);
        System.out.println("- " + question);
        return new String[]{question, leftOrRight};
    }
}