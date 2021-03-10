package animals;

import java.util.Scanner;

public class Game {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        DialogueManager dialogs = new DialogueManager();

        dialogs.getGreeting();
        System.out.println();
        System.out.println("I want to learn about animals.");
        Node root = RepositoryDAO.getInstance().readFile();
        if (root == null) {
            System.out.println("Which animal do you like most?");
            root = new Node(scanner.nextLine(), null);
        }
        Tree tree = new Tree(root);

        System.out.println("Wonderful! I've learned so much about animals!");
        System.out.println("Let's play a game!");
        do {
            System.out.println("You think of an animal, and I guess it.");
            System.out.println("Press enter when you're ready.");
            scanner.nextLine();
            tree.startPlaying();
            System.out.println("Nice! I've learned so much about animals!");
            System.out.println();
            System.out.println("Would you like to play again?");
        } while (dialogs.getUserAnswer());
        dialogs.getGoodbye();
        RepositoryDAO.getInstance().writeFile(tree.getRoot());
    }
}
