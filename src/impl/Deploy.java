package impl;

import java.util.Random;
import java.util.Scanner;

public class Deploy {
    private char[][]deploy_board;
    private int rows;
    private int cols;
    private char[][]computer_board;
    public Deploy(Board b){
        deploy_board = b.get_board();
        computer_board = b.get_computer_board();
        this.rows = b.get_rows();
        this.cols = b.get_cols();
    }
//    deploy method for player
    public void deploy_ship(Board b){
        System.out.println("Please deploy your ships (size from 2 to 5)");
        Scanner scan = new Scanner(System.in);
        int i =2;
        while(i<=5){
            System.out.println("Here is your current board.");
//          choose x, y coordinate as description, where x from A-J and y from 1-10
            System.out.printf("Choose your X coordinate (A-J) for your ship %d: \n",i);
            char x = scan.next().charAt(0);
            System.out.printf("Choose your Y coordinate (1-10) for your ship %d: \n",i);
            int y = scan.nextInt();
//            parse x,y to the board coordinate then check if legal selection
            boolean legal_coor = set_pos(x-'A',y-1,i,deploy_board,true);
            if(legal_coor){
//                choose vertical or horizontal direction
                System.out.printf("Choose your direction (0 for horizontal and 1 for vertical) for your ship %d: \n",i);
                int dir = scan.nextInt();
//                check if the selected direction is legal for your selected coordinate
                boolean legal_dir =set_dir(dir,x-'A',y-1,i,true);
                if(legal_dir){
                    set_board(dir,x-'A',y-1,i,deploy_board);
                    i++;
                }
            }
            b.show_updated(deploy_board);
        }
        System.out.println("Player's board are successfully set!");
        System.out.println();
    }
//    deploy method for computer
//    since the computer use random number while the user should input, I split them into two functions
    public void computer_deploy_ship(Board b){
        Random rand = new Random();
        int i =2;
        while(i<=5){
            int x_c = rand.nextInt(10);
            int y_c = rand.nextInt(10);
            boolean legal_coor = set_pos(x_c,y_c,i,computer_board,false);
            if(legal_coor){
//                choose vertical or horizontal direction
                int dir_c = rand.nextInt(2);
                boolean legal_dir =set_dir(dir_c,x_c,y_c,i,false);
                if(legal_dir){
                    set_board(dir_c,x_c,y_c,i,computer_board);
                    i++;
                }
            }
        }
        b.show_updated(computer_board);
        System.out.println("Computer's board are successfully set!");
        System.out.println();

    }

    public boolean set_pos(int x, int y, int sz, char[][]board, boolean player){
        if(x <0 || x >=rows || y<0 || y>=cols){
            if(player) System.out.println("The position you set is out of board. Please enter again for size "+sz+".");
            return false;
        }else if(board[x][y] != '0'){
            if(player) System.out.printf("The position [%s, %d] is already occupied. Please choose another location.\n",(char)(x+'A'),y+1);
            return false;
        }else{
            if(player) System.out.printf("Your ship size "+sz+" will be placed at coordinate [%s, %d] \n",(char)(x+'A'),y+1);
        }
        return true;

    }

    public boolean set_dir(int dir, int x, int y, int ship_sz, boolean player){
        char[][]board;
        if(player){
            board = deploy_board;
        }else{
            board = computer_board;
        }
//      horizontal
        if(dir == 0){
            //check if out of bound
            if(y+ship_sz-1 >=cols){
                if(player) System.out.println("The direction you choose will cause the ship outside of the border (columns maximum 10). Please choose new coordinate and new direction.");
                return false;
            }else{
//                check if will intersect other ships already deployed
                for(int k=y;k<y+ship_sz;++k){
                    if(board[x][k] != '0'){
                        System.out.println("The direction you choose will cause the ship cross other deployed ships.Please choose new coordinate and new direction. ");
                        return false;
                    }
                }
            }
//      vertical
        }else if(dir == 1){
            //check if out of bound
            if(x+ship_sz-1 >=rows){
                if(player) System.out.println("The direction you choose will cause the ship outside of the border (rows maximum J). Please choose new coordinate and new direction.");
                return false;
            }else{
//                check if will intersect other ships already deployed
                for(int k=x;k<x+ship_sz;++k){
                    if(board[k][y] != '0'){
                        System.out.println("The direction you choose will cause the ship cross other deployed ships.Please choose new coordinate and new direction. ");
                        return false;
                    }
                }
            }
        }
        if(player) System.out.println("You successfully deployed the ship with size "+ship_sz+".");
        return true;

    }


//  deploy the board based one ship size/type and the input coordinate and direction
    public void set_board(int dir, int x, int y, int ship_sz, char[][]board){
        if(dir == 0){
//            System.out.println("enter here for direction 0 -----------------");
            for(int i =0;i<ship_sz;++i){
                board[x][y+i] = (char)('0'+ship_sz);
            }
            return;
        }
        if(dir == 1){
//            System.out.println("enter here for direction 1 -----------------");
            for(int j =0;j<ship_sz;++j){

                board[x+j][y] = (char)('0'+ship_sz);
            }
            return;
        }
    }

//  getter for user's board after deployed
    public char[][]getPlayer_board_res(){
        return deploy_board;
    }
//  getter for computer's board after deployed
    public char[][]getComputer_board_res(){
        return computer_board;
    }


}
