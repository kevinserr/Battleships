package battleships;

import java.util.Scanner;

public class Game {
	
	private String[][] board ; 
	private Scanner input = new Scanner(System.in);

	public Game()
	{
		System.out.println("*** Welcome to Battle Ships Game ***");
		System.out.println();
		System.out.println("Right now the sea is empty");
		board = new String[10][14];

		createBoard();
	}
	
	private void createBoard() 
	{
		for (int i = 0; i <board.length; i++) 
		{
			board[i] = new String[] {
					String.valueOf(i), " |"," "," "," "," "," "," "," "," "," "," ","| ",String.valueOf(i)
			};
		}
		printBoard();
	}
	
	/*
	 * Method to set the human ship and is accessible to the user 
	 * It calls setComputerShip 
	*/
	public void setHumanShips()
	{
		int count =0;
		while(count < 5) 
		{
			System.out.print("Enter the row    for ship # "+(count+1)+": ");
			int row = input.nextInt();
			System.out.print("Enter the column for ship # "+(count+1)+": ");
			int col = input.nextInt();
			if(validHumanSpace(row, col))
			{
				board[row][col+2] = "1";
				count++;
			}
		}
		System.out.println();
		setComputerShip();
		printBoard();
	}
	
	/*
	Sets the ships for the computer
	Prints a sentence after each ship is generated
	*/
	private void setComputerShip()
	{
		int count =0;
		System.out.println("Computer deploying ships!");
		while(count < 5) 
		{
			int row = (int)(Math.random()*10);
			int col = (int)(Math.random() * ((9 - 2) + 1)) + 2;
			if(validComputerSpace(row, col))
			{
				board[row][col+2] = "2";
				count++;
				System.out.println(count+". ship DEPLOYED");
			}
		}
		System.out.println("----------------------------------------");
	}
	
	/*
	 This method check if the space is taken by another point already
	 and intended for the human ships to use.
	 @param row is the row intended to check
	 @param col is the column intended to check
	 returns false if the space is already taken
	 return true otherwise. 
	*/
	private boolean validHumanSpace(int row, int col) 
	{
		if(row < 0 || row >9 && col < 2 || col > 9 ) 
		{
			System.out.println("OUT OF BOUND: try different cordinates");
			return false;
		}
		if(row < 0 || row >9)
		{
			System.out.println("OUT OF BOUND: try a different row");
			return false;
		}
		if(col < 0 || col > 9) 
		{
			System.out.println("OUT OF BOUND: try a different column");
			return false;
		}
		if(board[row][col+2].equals("1")) 
		{
			System.out.println("Space is taken, please try again");
			return false;
		}
		return true;
	}
	
	/*
	 This method check if the space is taken by another point already
	 and intended for the computer generated spaces to use.
	 @param row is the row intended to check
	 @param col is the column intended to check
	 returns false if the space is already taken
	 return true otherwise. 
	*/
	private boolean validComputerSpace(int row, int col) 
	{
		if(row < 0 || row >9 && col < 2 || col > 9 ) { return false;}
		if(row < 0 || row >9){return false;}
		if(col < 2 || col > 9) {return false;}
		if(board[row][col+2].equals("2")) {return false;}
		if(board[row][col+2].equals("1")) {return false;}
		
		return true;
	}
	
	/*
		Method to print board, it should be accessible to the user
	*/
	public void printBoard()
	{
		System.out.println("   0123456789");
		for (int i = 0; i <board.length; i++) 
		{
			for (int j = 0; j <board[i].length; j++) 
			{
				if(i == 1 && (j == 0 || j ==13))
				{
					System.out.print(board[i][j]);
				}
				else if (i == 2 && (j == 0 || j ==13))
				{
					System.out.print(board[i][j]);
				}
				else if(board[i][j].equals("1")) 
				{
					System.out.print("@");
				}
				else if(board[i][j].equals("2"))
				{
					System.out.print(" ");
				}
				else
				{
					System.out.print(board[i][j]);
				}
			}
			System.out.println();
		}
		System.out.println("   0123456789");
	}
	
	/*
	 * Method to print the board with the 1s & 2s in order to debug
	*/
	private void printBoardWith()
	{
		System.out.println("   0123456789");
		for (int i = 0; i <board.length; i++) 
		{
			for (int j = 0; j <board[i].length; j++) 
			{
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		System.out.println("   0123456789");
	}

}
