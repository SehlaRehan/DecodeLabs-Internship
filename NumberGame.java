package com.mycompany.numbergame2;

import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int totalScore = 0;
        boolean playAgain = true;

        System.out.println("=================================");
        System.out.println("      WELCOME TO NUMBER GAME");
        System.out.println("=================================");

        while (playAgain) {

            int secretNumber = random.nextInt(100) + 1;
            int attempts = 0;
            int maxAttempts = 10;
            boolean guessedCorrectly = false;

            System.out.println("\nI have selected a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts.");

            while (attempts < maxAttempts) {

                try {
                    System.out.print("\nEnter your guess: ");
                    int guess = sc.nextInt();

                    attempts++;

                    if (guess > secretNumber) {
                        System.out.println("Too High!");
                    }
                    else if (guess < secretNumber) {
                        System.out.println("Too Low!");
                    }
                    else {
                        System.out.println("\nCongratulations!");
                        System.out.println("You guessed the number correctly.");

                        System.out.println("Attempts Used: " + attempts);

                        int roundScore = (maxAttempts - attempts + 1) * 10;
                        totalScore += roundScore;

                        System.out.println("Round Score: " + roundScore);

                        guessedCorrectly = true;
                        break;
                    }

                    System.out.println("Remaining Attempts: "
                            + (maxAttempts - attempts));

                } catch (Exception e) {

                    System.out.println("Invalid Input!");
                    System.out.println("Please enter numbers only.");

                    sc.nextLine(); // clear invalid input
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\nGame Over!");
                System.out.println("The correct number was: "
                        + secretNumber);
            }

            System.out.println("\nTotal Score: " + totalScore);

            System.out.print("\nDo you want to play again? (yes/no): ");
            String choice = sc.next();

            if (!choice.equalsIgnoreCase("yes")) {
                playAgain = false;
            }
        }

        System.out.println("\n=================================");
        System.out.println("Thanks for Playing!");
        System.out.println("Final Score: " + totalScore);
        System.out.println("=================================");

        sc.close();
    }
}


