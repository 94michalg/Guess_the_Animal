package animals.dialogs;

import java.util.List;
import java.util.Random;

public class Goodbyes {
    private final List<String> goodbyeList = List.of(
            "Have a nice day!",
            "Goodbye!",
            "See you soon!"
    );

    public void sayGoodbye() {
        System.out.println(
                goodbyeList.get(
                        new Random().nextInt(goodbyeList.size())
                )
        );
    }
}