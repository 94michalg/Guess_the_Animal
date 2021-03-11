package animals;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id")
public class Node {

    //value can be a question "Is it a mammal?" or just an animal like "Dog"
    private String value;
    private Node parent;
    private Node left;
    private Node right;
    private Set<String> facts;

    @JsonIgnore
    public boolean isQuestion() {
        return !(left == null && right == null);
    }

    @JsonIgnore
    public void askQuestion() {
        if (this.isQuestion()) {
            System.out.println(this.value);
        } else {
            System.out.printf("Is it %s?", this.value);
        }
    }

    public Node(String value, Node parent) {
        this.value = value;
        this.parent = parent;
        this.left = null;
        this.right = null;
        this.facts = new HashSet<>();
    }

    public void addNewFact(String fact) {
        facts.add(fact);
    }

    public void addAllToFacts(Set<String> newSet) {
        facts.addAll(newSet);
    }

    public void printFacts() {
        for (String s: facts) {
            System.out.printf("- %s.\n", s);
        }
    }

    //Created for proper loading from File
    public Node() {

    }

    public String getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}