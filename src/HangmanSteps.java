public enum HangmanSteps {
     ONE("|--------|\n|\n|\n|\n|\n|\n|\n|"),
     TWO("|--------|\n|\t\t\\O/\n|\n|\n|\n|\n|\n|\n"),
     THREE("|--------|\n|\t\t\\O/\n|\t\t |\n|\n|\n|\n|\n|\n|\n"),
     FOUR("|--------|\n|\t\t\\O/\n|\t\t |\n|\t\t/\n|\n|\n|\n|\n|\n"),
     FIVE("|--------|\n|\t\t\\O/\n|\t\t |\n|\t\t/\\\n|\n|\n|\n|\n|\n"),
     SIX("|--------|\n|\t\t\\O/\n|\t\t |\n|\t     /\\ \n|\t\tПоследняя попытка!\n|\n|\n|\n|\n"),
     DEAD("|--------|\n|\t\t\\O/\n|\t\t |\n|\t     /\\ \n|\t\tDEAD\n|\n|\n|\n|\n"), START("|\n|\n|\n|\n|\n|\n|\n|");

     private final String step;

     HangmanSteps(String step) {
          this.step = step;
     }


     @Override
     public String toString() {
          return step;
     }
}
