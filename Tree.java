package animals;

import java.util.ArrayList;
import java.util.List;
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

    private List<String> listAnimalsTraverse(Node t) {
        List<String> arrayList = new ArrayList<>();

        if (t == null) {
            return arrayList;
        }else if (!t.isQuestion()) {
            arrayList.add(
                    formatter.getNameOfAnimalWithoutArticle(
                            t.getValue()));
        } else {
            arrayList.addAll(listAnimalsTraverse(t.getLeft()));
            arrayList.addAll(listAnimalsTraverse(t.getRight()));
        }
        return arrayList;
    }

    public List<String> listAllAnimals() {
        return listAnimalsTraverse(root);
    }

    public void printStatistics() {
        System.out.println("The Knowledge Tree stats\n" +
                "");
        System.out.println("- root node " + root.getValue());
        int numberOfNodes = totalNumberOfNodes(root);
        System.out.println("- total number of nodes " + numberOfNodes);
        int numberOfStatements = numberOfStatements(root);
        int numberOfAnimals = numberOfNodes - numberOfStatements;
        System.out.println("- total number of animals " + numberOfAnimals);
        System.out.println("- total number of statements " + numberOfStatements);
        int maxDepth = maxDepth(root);
        int height = maxDepth + 1;
        System.out.println("- height of the tree " + height);
        int minDepth = minDepth(root);
        System.out.println("- minimum animal's depth " + minDepth);
        System.out.println("- maximum animal's depth " + maxDepth);

    }

    private int maxDepth(Node t) {
        if (t == null) {
            return -1;
        } else {
            return 1 + Math.max(maxDepth(t.getLeft()), maxDepth(t.getRight()));
        }
    }

    private int minDepth(Node t) {
        if (t == null) {
            return -1;
        } else {
            return 1 + Math.min(minDepth(t.getLeft()), minDepth(t.getRight()));
        }
    }

    private int totalNumberOfNodes(Node t) {
        int count = 1;
        if (t.getLeft() != null) {
            count += totalNumberOfNodes(t.getLeft());
        }
        if (t.getRight() != null) {
            count += totalNumberOfNodes(t.getRight());
        }
        return count;
    }

    private int numberOfStatements(Node t) {
        if (!t.isQuestion()) {
            return 0;
        }
        int count = 1;
        if (t.getLeft() != null) {
            count += numberOfStatements(t.getLeft());
        }
        if (t.getRight() != null) {
            count += numberOfStatements(t.getRight());
        }
        return count;
    }
}