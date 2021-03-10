package animals;

import java.util.Scanner;

public class Tree {
    private Node root;
    private final DialogueManager dialogs;
    private final Scanner scanner;
    private final Formatter formatter;

    public Tree(Node root) {
        this.root = root;
        scanner = new Scanner(System.in);
        dialogs = new DialogueManager();
        formatter = new Formatter();
    }

    public Node getRoot() {
        return root;
    }

    public void startPlaying() {
        Node leaf = traverse(root); //It starts playing and finishes at question "Is it a dog?"
        if (dialogs.getUserAnswer()) { //we won, nothing changes in the tree
            System.out.println("I won!");
        } else {        //we lost, we have to ask question and change the tree
            System.out.println("I give up. What animal do you have in mind?");
            String newAnimal =
                    formatter.getNameOfAnimalWithArticle(
                            scanner.nextLine());
            String oldAnimal =
                    formatter.getNameOfAnimalWithArticle(
                            leaf.getValue());

            //Below it just gets a new generated question and side, on which
            //we should add a newAnimal
            String[] array = dialogs.getFact(oldAnimal, newAnimal);
            Node newQuestion;
            String newFact = array[0];
            String leftOrRight = array[1];
            if (leaf.getParent() == null) {
                newQuestion = new Node(newFact, null);
                root = newQuestion;
            } else if (leaf.getParent().getLeft() == leaf) {
                newQuestion = new Node(newFact, leaf.getParent());
                leaf.getParent().setLeft(newQuestion);
            } else {
                newQuestion = new Node(newFact, leaf.getParent());
                leaf.getParent().setRight(newQuestion);
            }
            Node left, right;
            switch (leftOrRight) {
                case "left":
                    left = new Node(newAnimal, newQuestion);
                    right = new Node(oldAnimal, newQuestion);
                    break;
                case "right":
                    left = new Node(oldAnimal, newQuestion);
                    right = new Node(newAnimal, newQuestion);
                    break;
                default:
                    left = null;
                    right = null;
                    break;
            }
            newQuestion.setLeft(left);
            newQuestion.setRight(right);
        }
    }

    private Node traverse(Node t) {
        t. askQuestion();
        if (t.isQuestion()) { //Means that it is a question and node has children, we continue
            boolean choice = dialogs.getUserAnswer();
            if (choice) {
                return traverse(t.getRight());
            } else {
                return traverse(t.getLeft());
            }
        } else { //We get to the leaf (animal) like dog, cat etc. so traverse is finished
            return t;
        }
    }
}