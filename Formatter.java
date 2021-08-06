package animals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {

    // Returns name of animal with article e.g. "a cat", "an elephant"
    public String getNameOfAnimalWithArticle(String animal) {
        String input = animal.trim().toLowerCase();
        if (input.matches("(a|an)\\s\\w+")) {
            return animal;
        } else {
            return input.matches("^[aeiou]\\w+") ?
                    "an " + animal : "a " + animal;
        }
    }

    // Removes all articles from the animal name
    public String getNameOfAnimalWithoutArticle(String animal) {
        return animal.trim().toLowerCase().replaceAll(
                "(\\s|\\ban\\b|\\ba\\b|\\bthe\\b)", "");
    }

    // Creates proper question out of fact e.g. "Can it climb trees?"
    public String distinguishQuestion(String canHasIs, String fact) {
        switch (canHasIs) {
            case "can":
                return "Can it " + fact + "?";
            case "has":
                return "Does it have " + fact + "?";
            case "is":
                return "Is it " + fact + "?";
        }
        return null;
    }

    // Makes a negation out of affirmative
    public String canHasIsNegative(String can) {
        switch (can) {
            case "can":
                return "can't";
            case "has":
                return "doesn't have";
            case "is":
                return "isn't";
        }
        return null;
    }

    // Makes an affirmative or negation out of the question, depending on isTrue boolean parameter
    public String getFact(String question, boolean isTrue) {
        Pattern pattern = Pattern.compile("^(Can it|Does it have|Is it)([^?]*)");
        Matcher matcher = pattern.matcher(question);
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            if (isTrue) {
                switch (matcher.group(1)) {
                    case "Can it":
                        sb.append("It can");
                        break;
                    case "Does it have":
                        sb.append("It have");
                        break;
                    case "Is it":
                        sb.append("It is");
                        break;
                }
            } else {
                switch (matcher.group(1)) {
                    case "Can it":
                        sb.append("It can't");
                        break;
                    case "Does it have":
                        sb.append("It doesn't have");
                        break;
                    case "Is it":
                        sb.append("It isn't");
                        break;
                }
            }
            sb.append(matcher.group(2));
        }
        return sb.toString();
    }
}