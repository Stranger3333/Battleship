package impl;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Welcome to the battleship game.");
        System.out.println("Your turn to deploy ships! You four ships with size 2,3,4,5 to deploy.");
        System.out.println("The game board is a 10x10 square.");
        Board b = new Board();
        System.out.println();
        Deploy dp = new Deploy(b);
        dp.deploy_ship(b);

        System.out.println("Computer's turn to deploy ships!");

        System.out.println("Computer finished the ships deployment.");
        dp.computer_deploy_ship(b);
        System.out.println("Let's start to attack some ships!");
        Attack atck = new Attack(b,dp);
        atck.action();
        System.out.println("Game Ended. Please re-activate the program to play again.");
    }
}
