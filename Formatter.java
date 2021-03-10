package animals;

public class Formatter {

    private static final Formatter instance = new Formatter();

    public static Formatter getInstance() {
        return instance;
    }

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
}