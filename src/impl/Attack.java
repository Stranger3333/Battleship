package impl;

import java.util.Random;
import java.util.Scanner;

public class Attack {
    private char[][]cur_player;
    private char[][]cur_computer;
    private int rows;
    private int cols;
    private int num_sunk_player = 0;
    private int num_sunk_computer = 0;
    public Attack(Board b, Deploy dp){
        cur_player = dp.getPlayer_board_res();
        cur_computer = dp.getComputer_board_res();
        rows = b.get_rows();
        cols = b.get_cols();
    }

    public void action(){
        Random rand = new Random();
        while(num_sunk_computer != 4 && num_sunk_player != 4){
//            check if player input coordinate is legal
            boolean player_legal = false;
            char x;
            int y;
//            if input not legal, will continue asking for input until legal
            while (!player_legal){
                Scanner scan = new Scanner(System.in);
                System.out.println("It's your turn to attack the computer!");
                System.out.println("Choose your X coordinate (A-J) to attack : ");
                x = scan.next().charAt(0);
                System.out.println("Choose your Y coordinate (1-10) to attack : ");
                y = scan.nextInt();
                player_legal = legal_coord(x-'A',y-1);
                if(player_legal) hit_res(x-'A',y-1,true);
            }
//            end = gameEnd(cur_computer);
            if(num_sunk_computer == 4){
                System.out.println("All the ships for computer has sunk!");
                System.out.println("You win the game!!!");
                return;
            }
//             computer input always legal, so no need to check the validity
            System.out.println("------------------------------------------------------------------------");
            System.out.println("It's Computer's turn to attack you!");
            int x_c = rand.nextInt(10);
            int y_c = rand.nextInt(10);
            hit_res(x_c,y_c,false);

            if(num_sunk_player == 4){
                System.out.println("All of your ships has sunk!");
                System.out.println("Computer win the game!!!");
                return;
            }
            System.out.println("------------------------------------------------------------------------");


        }

    }

//  check if the current selected coordinate is legal, prevent user illegal input
    public boolean legal_coord(int x, int y){
        if(x<0 || x>=rows || y<0 || y>=cols){
            System.out.println("Your input coordinate is not in the board! Please enter a legal coordinate.");
            return false;
        }
        return true;
    }

//    function for hitting the ship
    public void hit_res(int x, int y, boolean player){
        char[][]board;
        if(player){
            board = cur_computer;
        }else{
            board = cur_player;
        }

//      no ship, so miss
        if(board[x][y] == '0'){
            System.out.println("It was a miss!");
        }else{
//            hit the ship
//            need to determine if hit or sunk
            char ship = board[x][y];
            int type = ship-'0';
//            hit, so set the current grid to be '0', which means empty
            board[x][y] = '0';
            if(check_sunk(x,y,board,ship,type)){
                System.out.printf("Ship %d for your opponent has sunk!\n",type);
                if(player){
                    num_sunk_computer++;
                }else{
                    num_sunk_player++;
                }
            }else{
                System.out.printf("It was a hit to ship %d!\n",type);
            }
        }
//        display(board);

    }

//    function to check 4 directions to make sure if there is any same type or not
    public boolean check_sunk(int x, int y, char[][]board, char ship, int type){

        int left_b = Math.max(0,y-type+1);
        int right_b = Math.min(cols-1,y+type-1);
        int up_b = Math.max(0,x-type+1);
        int down_b = Math.min(rows-1,x+type-1);
        boolean isSunk = true;
//        check four directions if there still any same type of elemnts (2,3,4,5 stands for 4 different ships)
//        if so, means the ship still not sunk
//        else, means all zero or other ship types, which means this ship has sunk
        for(int i=y;i>=left_b;--i){
            if(board[x][i] == ship){
                isSunk = false;
            }
        }
        for(int i=y;i<=right_b;++i){
            if(board[x][i] == ship){
                isSunk = false;
            }
        }

        for(int i=x;i>=up_b;--i){
            if(board[i][y] == ship){
                isSunk = false;
            }
        }

        for(int i=x;i<=down_b;++i){
            if(board[i][y] == ship){
                isSunk = false;
            }
        }

        return isSunk;

    }

    public void display(char[][]board){
        int count = 0;
        for(int i=0;i<rows;++i){
            for(int j=0;j<cols;++j){
                System.out.print("| "+board[i][j]+" ");
                if(j == 9) System.out.print("|");
//                if(board[i][j] != '0'){
//                    count++;
//                }
            }
            System.out.println();
        }
//        System.out.println(count);
    }
}
