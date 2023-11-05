package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        AtomicInteger counter3 = new AtomicInteger(0);
        AtomicInteger counter4 = new AtomicInteger(0);
        AtomicInteger counter5 = new AtomicInteger(0);


        new Thread(() -> {

            for (String nick : texts ) {

                switch (nick.length()) {
                    case  (3):
                        if (nick.charAt(0) == nick.charAt(2)) {
                            counter3.getAndIncrement();
                        }
                        break;
                    case (4):
                        if (nick.charAt(0) == nick.charAt(3) &&
                                nick.charAt(1) == nick.charAt(2)) {
                            counter4.getAndIncrement();
                        }
                        break;
                    case (5):
                        if (nick.charAt(0) == nick.charAt(4) &&
                                nick.charAt(1) == nick.charAt(3)) {
                            counter5.getAndIncrement();
                        }
                        break;
                }
            }
        }).start();

        new Thread(() -> {
            for (String nick : texts ) {
                boolean isGood = true;
                char firstLetter = nick.charAt(0);
                for (char letter : nick.toCharArray()) {
                    if (letter != firstLetter) {
                        isGood = false;
                    }
                }
                if (isGood) {
                    switch (nick.length()) {
                        case (3):
                            counter3.getAndIncrement();
                            break;
                        case (4):
                            counter4.getAndIncrement();
                            break;
                        case (5):
                            counter5.getAndIncrement();
                            break;
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (String nick : texts ) {
                char[] nickSortedArray = nick.toCharArray();
                Arrays.sort(nickSortedArray);
                if (nick.equals(new String(nickSortedArray))){
                    switch (nick.length()) {
                        case (3):
                            counter3.getAndIncrement();
                            break;
                        case (4):
                            counter4.getAndIncrement();
                            break;
                        case (5):
                            counter5.getAndIncrement();
                            break;
                    }
                }
            }
        }).start();

        System.out.print("Красивых слов с длиной 3: ");
        System.out.print(counter3);
        System.out.println(" шт");

        System.out.print("Красивых слов с длиной 4: ");
        System.out.print(counter4);
        System.out.println(" шт");

        System.out.print("Красивых слов с длиной 5: ");
        System.out.print(counter5);
        System.out.println(" шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }



}