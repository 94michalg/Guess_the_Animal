package animals.dialogs;

import java.time.LocalTime;

public class Greetings {

    public void sayHello() {
        LocalTime localTime = LocalTime.now();
        if (localTime.isAfter(LocalTime.of(18, 0))) {
            System.out.println("Good evening");
        } else if(localTime.isAfter(LocalTime.of(12, 0))) {
            System.out.println("Good afternoon");
        } else {
            System.out.println("Good morning");
        }
    }
}