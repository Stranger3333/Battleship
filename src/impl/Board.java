package impl;

public class Board {

    private char[][]board;
    private char[][]computer_board;
    private int rows = 10;
    private int cols = 10;
    public Board(){
        board = new char[10][10];
        computer_board = new char[10][10];
        for(int i=0;i<10;++i){
            for(int j=0;j<10;++j){
                computer_board[i][j] = '0';
                board[i][j] = '0';
            }
        }
    }

//    display method for the board
    public void show_updated(char[][]input){
        for(int i=0;i<10;++i){
            for(int j=0;j<10;++j){
                System.out.print("| "+input[i][j]+" ");
                if(j == 9) System.out.print("|");
            }
            System.out.println();
        }
    }

//    helper function to get initialized player's board
    public char[][]get_board(){

        return board;
    }

//    helper function to get initialized player's board
    public char[][]get_computer_board(){

        return computer_board;
    }

//  helper function to get rows num
    public int get_rows(){

        return rows;
    }

//  helper function to get cols num
    public int get_cols(){

        return cols;
    }
}
