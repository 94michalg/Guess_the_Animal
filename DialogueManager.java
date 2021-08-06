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

    // Gets user answer, returns true if the answer is positive, false if negative
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
    // array[1] has String "right" or "left", it means on which side of the Node we should
    // put a new animal
    public String[] getFact(Node firstAnimal, Node secondAnimal) {
        System.out.printf("Specify a fact that distinguishes %s from %s.\n",
                formatter.getNameOfAnimalWithArticle(firstAnimal.getValue()),
                formatter.getNameOfAnimalWithArticle(secondAnimal.getValue()));
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
                    formatter.getNameOfAnimalWithArticle(firstAnimal.getValue()),
                    formatter.getNameOfAnimalWithArticle(secondAnimal.getValue()));
            matcher = pattern.matcher(scanner.nextLine());
        }
        String canHasIs = matcher.group(1);
        String fact = matcher.group(2);
        System.out.printf("Is the statement correct for %s?\n",
                formatter.getNameOfAnimalWithArticle(secondAnimal.getValue()));
        String leftOrRight;
        if (getUserAnswer()) {
            leftOrRight = "right";
            System.out.println("I have learned the following facts about animals:");
            System.out.printf("- The %s %s %s.\n",
                    formatter.getNameOfAnimalWithoutArticle(firstAnimal.getValue()),
                    formatter.canHasIsNegative(canHasIs),
                    fact);
            System.out.printf("- The %s %s %s.\n",
                    formatter.getNameOfAnimalWithoutArticle(secondAnimal.getValue()),
                    canHasIs,
                    fact);
        } else {
            leftOrRight = "left";
            System.out.println("I have learned the following facts about animals:");
            System.out.printf("- The %s %s %s.\n",
                    formatter.getNameOfAnimalWithoutArticle(firstAnimal.getValue()),
                    canHasIs,
                    fact);

            System.out.printf("- The %s %s %s.\n",
                    formatter.getNameOfAnimalWithoutArticle(secondAnimal.getValue()),
                    formatter.canHasIsNegative(canHasIs),
                    fact);

        }
        System.out.println("I can distinguish these animals by asking the question:");
        String question = formatter.distinguishQuestion(canHasIs, fact);
        System.out.println("- " + question);
        return new String[]{question, leftOrRight};
    }

    // Simple UI, lets user choose a proper option
    public int getChoice() {
        System.out.println("\nWhat do you want to do?\n");
        System.out.println("1. Play the guessing game\n" +
                "2. List of all animals\n" +
                "3. Search for an animal\n" +
                "4. Calculate statistics\n" +
                "5. Print the Knowledge Tree\n" +
                "0. Exit");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 0 || choice > 5) {
                System.out.println("Wrong choice! Try again");
                return getChoice();
            }
            return choice;
        } catch (NumberFormatException e) {
            System.out.println("Wrong choice! Try again");
            return getChoice();
        }
    }
}