package animals;

import java.util.*;

public class Game {

    private static final DialogueManager dialogs = new DialogueManager();
    private static final Scanner scanner = new Scanner(System.in);

    public void start() {
        dialogs.getGreeting();
        System.out.println();

        // Tries to read the tree from the file, if the root == null create new tree
        Node root = RepositoryDAO.getInstance().readFile();
        if (root == null) {
            System.out.println("I want to learn about animals.");
            System.out.println("Which animal do you like most?");
            root = new Node(
                    new Formatter().getNameOfAnimalWithArticle(
                            scanner.nextLine()),
                    null);
        }
        Tree tree = new Tree(root);

        System.out.println("Welcome to the animal expert system!");

        // Handle user choice
        int choice = dialogs.getChoice();
        while (choice != 0) {
            switch (choice) {
                case 1: // Play
                    do {
                        System.out.println("You think of an animal, and I guess it.");
                        System.out.println("Press enter when you're ready.");
                        scanner.nextLine();
                        tree.startPlaying(); //Play the game and save changes
                        System.out.println("Nice! I've learned so much about animals!");
                        System.out.println();
                        System.out.println("Would you like to play again?");
                    } while (dialogs.getUserAnswer());
                    break;
                case 2: // List all animals
                    System.out.println("Your choice:\n" +
                            "2\n" +
                            "Here are the animals I know:");

                    List<String> animalsList = tree.listAllAnimals();
                    Collections.sort(animalsList);
                    for (String s : animalsList) {
                        System.out.println("- " + s);
                    }
                    break;
                case 3: // Print all the facts about particular animal
                    System.out.println("Your choice:");
                    System.out.println("3");
                    System.out.println("Enter the animal:");
                    String animalToFind = scanner.nextLine().trim().toLowerCase();
                    Map<String, Boolean> facts = new LinkedHashMap<>();
                    boolean found = tree.searchAndPrint(animalToFind, tree.getRoot(), facts);
                    if (!found) {
                        System.out.printf("No facts about the %s.", animalToFind);
                    } else {
                        System.out.printf("Facts about the %s:\n", animalToFind);
                        List<String> reverseOrderedKeys = new ArrayList<>(facts.keySet());
                        Collections.reverse(reverseOrderedKeys);
                        for (String s: reverseOrderedKeys) {
                            System.out.printf("- %s.\n",
                                    new Formatter().getFact(s, facts.get(s)));
                        }
                    }
                    break;
                case 4: // Print tree statistics
                    tree.printStatistics();
                    break;
                case 5: // Print the tree
                    tree.print();
                    break;
                default:
                    break;
            }
            choice = dialogs.getChoice();
        }

        dialogs.getGoodbye();

        // Save the tree in the file
        RepositoryDAO.getInstance().writeFile(tree.getRoot());
    }

}
