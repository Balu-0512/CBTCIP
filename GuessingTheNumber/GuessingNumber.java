import java.util.Scanner;
public class GuessingNumber {
    static int score=0;
    public static void main(String[] args) {
        
        //rules of the game

        System.out.println("\n\n\t"+"Welcome to the Number Guessing Game"+"\t");
        System.out.println();
        System.out.println("Rules: ");
        System.out.println("1. The guessing number should be a natural number between 1-100.");
        System.out.println("2. Your score will be added based on number of attemtps. \n   For Eg: Your limit is 4 and you guees the number in the first attempt.\n   So, you score will add up with 4 Points");
        System.out.println("3. Once you complete a game you can choose to RESTART or QUIT.");
        System.out.println("Cheers, let's start the game!\n");
        int res = 1;

        //calls the method game until the var res==1. if res!=1 then the game will be terminated
        while (res == 1) {
            res = game();
        }
        System.out.println("Thanks for Playing .. Have a nice day !");
    }

    //Method game()

    static int game() {
        Scanner sc = new Scanner(System.in);

        int num = (int)(Math.random()*100); //generates random num

        // System.out.println(num);
        int limit = 4;  //limit as 4
        System.out.println();

        //the loop will execute until no chances are left.
        while (limit > 0) {
            System.out.println("Guess the number: ");

            // the user enters the number
            int guess = sc.nextInt();


            //base condition
            if (guess < 1 || guess > 100) {
                System.out.println("Invalid input! The number should be between 1 and 100.");
                continue;
            }

            //conditions

            if (guess == num) { //if guess num ==  actual num then he won
                System.out.println("Yahooo! You guessed the correct number.\nYour score added with "+limit);

                score=score+limit; // score will be added according to no. of chances he left with.

                break;
            } 
            // if the diff between actual and guess number is greater then 10 then this condition will be excuted.

            else if (Math.abs(guess - num) >= 5 && Math.abs(guess - num) < 10) {
                System.out.println("You are close!");
            }
            //else if the diff is less then 5 the this condition will be excuted
            else if (Math.abs(guess - num) < 5) {
                System.out.println("You are too close! Just a minute difference");
            }
            // if the guessed number is greater then actual
             else if (guess > num) {
                System.out.println("Oh no! Your guessing number is greater than the actual number.");
            } 
            // if the guessed number is lesser then actual
            else {
                System.out.println("Oh no! Your guessing number is lesser than the actual number.");
            }
            
            //Decreasing limit by 1
            limit--;
            if (limit > 0) {
                System.out.println("Remember, you are only left with " + limit + " chances.\n");
            }
        }

        //if limit =0 then he lost the game
        if (limit == 0) {
            System.out.println("I'm sorry! You lost the game."+"\n");

            //the correct answer will displayed
            System.out.println("the correct Answer is : "+num);
            System.out.println();
            System.out.println("Dont Loose Hope ! Let's Restart....");
            System.out.println();
        }
        // the current score will be displayed 
        System.out.println("your Current score is : "+score+"\n");
        System.out.println();

        // he had 2 options one for restart to restart the game and quit to quit the game
        System.out.println("Choose "+"\n"+"1. Restart"+"\n"+"2. Quit");
        int restart = sc.nextInt();
        System.out.println();
        return restart;
    }
}