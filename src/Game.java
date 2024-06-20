import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Game {
    private String randomWord;
    private final Scanner scanner = new Scanner(System.in);
    private int tries = 6;
    private boolean gameOver = false;
    private StringBuilder hiddenWord;
    private char[] randomWordChars;
    HashSet<Character> usersGuesses = new HashSet<Character>();

    private void getRandomWordFromFile() {
        String filePath = "words.txt";
        try {
            List<String> words = Files.readAllLines(Paths.get(filePath));
            Random random = new Random();
            int randomIndex = random.nextInt(words.size());
            randomWord = words.get(randomIndex);
//            System.out.println("Случайное слово: " + randomWord);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createHiddenWord() {
        hiddenWord = new StringBuilder("*".repeat(randomWord.length()));
        randomWordChars = randomWord.toCharArray();
    }

    private void showHangman() {
        switch (tries) {
            case 6:
                System.out.println(HangmanSteps.START);
                break;
            case 5:
                System.out.println(HangmanSteps.ONE);
                break;
            case 4:
                System.out.println(HangmanSteps.TWO);
                break;
            case 3:
                System.out.println(HangmanSteps.THREE);
                break;
            case 2:
                System.out.println(HangmanSteps.FOUR);
                break;
            case 1:
                System.out.println(HangmanSteps.FIVE);
                break;
            case 0:
                System.out.println(HangmanSteps.DEAD);
                break;
        }
    }

    private void checkWinOrLose() {
        String exit;
        if (hiddenWord.toString().equals(randomWord)) {
            gameOver = true;
            System.out.println("Поздравляем! Вы угадали слово: " + randomWord);
            System.out.println("Чтобы начать заного нажмите любую клавишу, для выхода напишите слово: выход");
            exit = scanner.nextLine().toLowerCase();
            if (!exit.equals("выход")) {
                getRandomWordFromFile();
                createHiddenWord();
                tries = 6;
                gameOver = false;
            }

        }
        if (tries == 0) {
            gameOver = true;
            System.out.println("Игра окончена! Вы не угадали слово: " + randomWord);
            System.out.println("Чтобы начать заного нажмите любую клавишу, для выхода напишите слово: выход");
            exit = scanner.nextLine().toLowerCase();
            if (!exit.equals("выход")) {
                getRandomWordFromFile();
                createHiddenWord();
                tries = 6;
                gameOver = false;
            }


        }
    }

    private void userGuessingLetters() {
        System.out.println(hiddenWord);
        System.out.println("Буква?");
        try {
            char usersGuess = Character.toUpperCase(scanner.nextLine().charAt(0));
            //Проверка на повтор буквы и кириллицу
            if (!Character.UnicodeBlock.of(usersGuess).equals(Character.UnicodeBlock.CYRILLIC)) {
                System.out.println("Только кириллица!" + " Попыток осталось: " + tries);
                return;
            }
            if (usersGuesses.contains(usersGuess)) {
                System.out.println("Вы уже вводили эту букву. Попробуйте другую." + " Попыток осталось: " + tries);
                return;
            }
            usersGuesses.add(usersGuess);

            boolean correctGuess = false;
            for (int i = 0; i < randomWordChars.length; i++) {
                if (randomWordChars[i] == usersGuess) {
                    hiddenWord.setCharAt(i, usersGuess);
                    correctGuess = true;
                }
            }

            if (correctGuess) {
                System.out.println("Попыток осталось: " + tries);
            } else {
                tries--;
                System.out.printf("Неверная буква. Попыток осталось: %d\n", tries);
            }
        } catch (Exception e) {

        }
    }

    public void starGame() {
        getRandomWordFromFile();
        createHiddenWord();
        while (!gameOver && tries > 0) {
            userGuessingLetters();
            showHangman();
            checkWinOrLose();
        }
    }

}
