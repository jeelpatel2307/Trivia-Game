import requests
import json

class TriviaGame:
    def __init__(self):
        self.questions = []
        self.score = 0

    def fetch_questions(self, category, amount, difficulty):
        """
        Fetch trivia questions from an external API.
        """
        url = f"https://opentdb.com/api.php?amount={amount}&category={category}&difficulty={difficulty}&type=multiple"
        response = requests.get(url)
        if response.status_code == 200:
            data = json.loads(response.text)
            if data['response_code'] == 0:
                self.questions = data['results']
            else:
                print("Error: Unable to fetch questions.")
        else:
            print("Error: Unable to connect to the API.")

    def play_game(self):
        """
        Start the trivia game.
        """
        if not self.questions:
            print("Please fetch questions before starting the game.")
            return

        print("Welcome to the Trivia Game!")
        print("Answer the following questions:")

        for i, question in enumerate(self.questions, start=1):
            print(f"\nQuestion {i}: {question['question']}")
            options = question['incorrect_answers'] + [question['correct_answer']]
            options.sort()
            for j, option in enumerate(options, start=1):
                print(f"{j}. {option}")

            user_answer = input("Your answer: ")
            if user_answer.lower() == question['correct_answer'].lower():
                print("Correct!")
                self.score += 1
            else:
                print(f"Incorrect! The correct answer is: {question['correct_answer']}")

        print(f"\nGame Over! Your final score is: {self.score}")

if __name__ == "__main__":
    game = TriviaGame()
    category = input("Enter category ID (9 for General Knowledge): ")
    amount = int(input("Enter number of questions: "))
    difficulty = input("Enter difficulty (easy, medium, hard): ").lower()
    game.fetch_questions(category, amount, difficulty)
    game.play_game()
