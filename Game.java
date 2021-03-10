package animals;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {

    private static DialogueManager dialogs = new DialogueManager();
    private static Scanner scanner = new Scanner(System.in);

    public void start() {
        dialogs.getGreeting();
        System.out.println();
        Node root = RepositoryDAO.getInstance().readFile();
        if (root == null) {
            System.out.println("I want to learn about animals.");
            System.out.println("Which animal do you like most?");
            root = new Node(
                    scanner.nextLine().trim().toLowerCase(),
                    null);
        }
        Tree tree = new Tree(root);

        System.out.println("Welcome to the animal expert system!\n");
        int choice = dialogs.getChoice();
        do {
            switch (choice) {
                case 1:
                    do {
                        System.out.println("You think of an animal, and I guess it.");
                        System.out.println("Press enter when you're ready.");
                        scanner.nextLine();
                        tree.startPlaying();
                        System.out.println("Nice! I've learned so much about animals!");
                        System.out.println();
                        System.out.println("Would you like to play again?");
                    } while (dialogs.getUserAnswer());
                    break;
                case 2:
                    System.out.println("Your choice:\n" +
                            "2\n" +
                            "Here are the animals I know:");

                    List<String> animalsList = tree.listAllAnimals();
                    Collections.sort(animalsList);
                    for (String s: animalsList) {
                        System.out.println("- " + s);
                    }
                    break;
            }
            choice = dialogs.getChoice();
        } while (choice != 0);

        dialogs.getGoodbye();
        RepositoryDAO.getInstance().writeFile(tree.getRoot());
    }

}
