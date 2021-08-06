package animals;

import java.util.*;

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

    // Play the game
    public void startPlaying() {
        Node oldAnimal = traverse(root); //It starts playing and finishes at question "Is it a dog?"
        System.out.println(" ");
        if (dialogs.getUserAnswer()) { //we won, nothing changes in the tree
            System.out.println("I won!");
        } else {        //we lost, we have to ask question and change the tree
            System.out.println("I give up. What animal do you have in mind?");
            Node newAnimal = new Node(
                    formatter.getNameOfAnimalWithArticle(
                            scanner.nextLine()), null);

            //Below it just gets a new generated question and side, on which
            //we should add a newAnimal
            String[] array = dialogs.getFact(oldAnimal, newAnimal);

            Node newQuestion;
            String newFact = array[0];
            String leftOrRight = array[1];

            if (oldAnimal.getParent() == null) { // Old animal was the root, so we have to change it
                newQuestion = new Node(newFact, null);
                root = newQuestion;
            } else if (oldAnimal.getParent().getLeft() == oldAnimal) {
                newQuestion = new Node(newFact, oldAnimal.getParent());
                oldAnimal.getParent().setLeft(newQuestion);
            } else {
                newQuestion = new Node(newFact, oldAnimal.getParent());
                oldAnimal.getParent().setRight(newQuestion);
            }

            oldAnimal.setParent(newQuestion);
            newAnimal.setParent(newQuestion);

            switch (leftOrRight) {
                case "left":
                    newQuestion.setLeft(newAnimal);
                    newQuestion.setRight(oldAnimal);
                    break;
                case "right":
                    newQuestion.setLeft(oldAnimal);
                    newQuestion.setRight(newAnimal);
                    break;
                default:
                    newQuestion.setLeft(null);
                    newQuestion.setRight(null);
                    break;
            }
        }
    }

    // Search for the animal and gather facts while traversing, returns false if the animal is not found
    public boolean searchAndPrint(String name, Node t, Map<String, Boolean> mapOfFacts) {
        if (t == null) {
            return false;
        }

        String nameOfNode = t.getValue();
        if (nameOfNode.matches("(a|an)\\s" + name)) {
            return true;
        }

        if (searchAndPrint(name, t.getLeft(), mapOfFacts)) {
            mapOfFacts.put(t.getValue(), false);
            return true;
        }

        if (searchAndPrint(name, t.getRight(), mapOfFacts)) {
            mapOfFacts.put(t.getValue(), true);
            return true;
        }
        return false;
    }

    // Traverse the tree, ask the proper question at every Node
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

    // Make a list of all animals in the tree
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

    // Prints the statistics of the tree
    public void printStatistics() {
        System.out.println("The Knowledge Tree stats\n" +
                "");
        System.out.println("- root node " +
                formatter.getFact(root.getValue(), true));
        int numberOfNodes = totalNumberOfNodes(root);
        System.out.println("- total number of nodes " + numberOfNodes);
        int numberOfStatements = numberOfStatements(root);
        int numberOfAnimals = numberOfNodes - numberOfStatements;
        System.out.println("- total number of animals " + numberOfAnimals);
        System.out.println("- total number of statements " + numberOfStatements);
        int maxDepth = maxDepth(root);
        System.out.println("- height of the tree " + maxDepth);
        int minDepth = minDepth(root);
        System.out.println("- minimum animal's depth " + minDepth);
        System.out.println("- average animal's depth " + (double)sumOfDepthOfLeaves(root, 0)/numberOfAnimals);

    }

    // Get max depth
    private int maxDepth(Node t) {
        if (t == null) {
            return -1;
        } else {
            return 1 + Math.max(maxDepth(t.getLeft()), maxDepth(t.getRight()));
        }
    }

    private int sumOfDepthOfLeaves(Node t, int depth) {
        if (t == null) {
            return 0;
        } else if(!t.isQuestion()) {
            return depth;
        } else {
            return sumOfDepthOfLeaves(t.getLeft(), depth + 1) +
                    sumOfDepthOfLeaves(t.getRight(), depth + 1);
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

    public void print() {
        preorderTraversePrint(root);
    }

    private void preorderTraversePrint(Node t) {
        if (t == null) {
            return;
        }
        System.out.println(t.getValue());
        preorderTraversePrint(t.getLeft());
        preorderTraversePrint(t.getRight());
    }
}