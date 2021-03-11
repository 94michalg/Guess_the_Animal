package animals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {

//    private static final Formatter instance = new Formatter();
//
//    public static Formatter getInstance() {
//        return instance;
//    }

    public String getNameOfAnimalWithArticle(String animal) {
        String input = animal.trim().toLowerCase();
        if (input.matches("(a|an)\\s\\w+")) {
            return animal;
        } else {
            return input.matches("^[aeiou]\\w+") ?
                    "an " + animal : "a " + animal;
        }
    }

    public String getNameOfAnimalWithoutArticle(String animal) {
        return animal.trim().toLowerCase().replaceAll(
                "(\\s|\\ban\\b|\\ba\\b|\\bthe\\b)", "");
    }

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

    public String getPositiveFact(String answer) {
        Pattern pattern = Pattern.compile("^(Can it|Does it have|Is it)([^?]*)");
        Matcher matcher = pattern.matcher(answer);
        matcher.find();
        String firstPart = matcher.group(1);
        String fact = matcher.group(2);
        StringBuilder sb = new StringBuilder("- ");
        switch (firstPart) {
            case "Can it":
                sb.append("It can");
                break;
            case "Does it have":
                sb.append("It has");
                break;
            case "Is it":
                sb.append("It is");
                break;
        }
        sb.append(fact);
        sb.append(".");
        return sb.toString();
    }

    public String getNegativeFact(String answer) {
        Pattern pattern = Pattern.compile("^(Can it|Does it have|Is it)([^?]*)");
        Matcher matcher = pattern.matcher(answer);
        matcher.find();
        String firstPart = matcher.group(1);
        String fact = matcher.group(2);
        StringBuilder sb = new StringBuilder("- ");
        switch (firstPart) {
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
        sb.append(fact);
        sb.append(".");
        return sb.toString();
    }
}