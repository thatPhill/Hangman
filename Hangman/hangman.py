import random
import re
from pathlib import Path


def print_hangman(mistakes):
    hangman_stages = [
        r"""
           -----
           |    
           |    
           |    
           |    
          ===    
        """,
        r"""
           -----
           |   O  
           |    
           |    
           |    
          ===    
        """,
        r"""
           -----
           |   O  
           |   |  
           |    
           |    
          ===    
        """,
        r"""
           -----
           |   O  
           |  /|  
           |    
           |    
          ===    
        """,
        r"""
           -----
           |   O  
           |  /|\  
           |    
           |    
          ===    
        """,
        r"""
           -----
           |   O  
           |  /|\  
           |  /   
           |    
          ===    
        """,
        r"""
           -----
           |   O  
           |  /|\  
           |  / \  
           |    
          ===    
        """
    ]
    print(hangman_stages[mistakes])


def get_random_word():
    BASE_DIR = Path(__file__).resolve().parent
    WORDS_FILE = BASE_DIR / "words.txt"

    with open(WORDS_FILE, "r", encoding="utf-8") as file:
        words = [line.strip().upper() for line in file]
    return random.choice(words)


def open_correct_guess(hidden_word, letter, random_word):
    return "".join([letter if random_word[i] == letter else hidden_word[i] for i in range(len(random_word))])


def get_user_input(user_inputs):
    while True:
        user_input = input("Введите букву: ").upper()
        if len(user_input) != 1 or not re.match(r'[А-ЯЁ]', user_input):
            print("Введите одну букву кириллицы!")
            continue
        if user_input in user_inputs:
            print("Вы уже вводили эту букву.")
            continue
        return user_input


def play_round():
    max_attempts = 6
    mistakes = 0
    user_inputs = set()
    random_word = get_random_word()
    hidden_word = "_" * len(random_word)

    print_hangman(mistakes)
    print(f"Слово: {hidden_word}")

    while mistakes < max_attempts and hidden_word != random_word:
        user_input = get_user_input(user_inputs)
        user_inputs.add(user_input)

        if user_input in random_word:
            hidden_word = open_correct_guess(hidden_word, user_input, random_word)
        else:
            mistakes += 1

        print_hangman(mistakes)
        print(f"Слово: {hidden_word}")
        print(f"Ошибок: {mistakes}/{max_attempts}")

    return hidden_word == random_word, random_word


def start_game():
    while True:
        won, random_word = play_round()
        if won:
            print(f"Поздравляем! Вы угадали слово: {random_word}")
        else:
            print(f"Вы проиграли! Загаданное слово было: {random_word}")

        choice = input("Хотите сыграть еще раз? (Р - рестарт, В - выход): ").upper()

        while choice != "Р":
            if choice == "В":
                break
            print("Некорректный ввод")
            choice = input("Хотите сыграть еще раз? (Р - рестарт, В - выход): ").upper()

        if choice == "В":
            print("Выход из игры.")
            break


start_game()
