package animals;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id")
public class Node {

    // Value can be a question "Is it a mammal?" or just an animal like "Dog"
    private String value;
    private Node parent;
    private Node left;
    private Node right;

    // Returns true if it has children, which means it is a question e.g. "Is it a mammal"
    // Returns false if it has no children, means it is an animal e.g. "cat"
    @JsonIgnore
    public boolean isQuestion() {
        return !(left == null && right == null);
    }

    // If it's a question just ask it if it's an animal ask "Is it a dog?"
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