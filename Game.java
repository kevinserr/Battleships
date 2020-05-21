package battleships;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	private String[][] board ; 
	private Scanner input = new Scanner(System.in);
	private int numberOfHumanShips=5;
	private int numberOfComputerShips =5;
	ArrayList<Integer> rowsHumanUsed = new ArrayList<Integer>();
	ArrayList<Integer> colsHumanUsed = new ArrayList<Integer>();
	ArrayList<Integer> rowsComputerUsed = new ArrayList<Integer>();
	ArrayList<Integer> colsComputerUsed = new ArrayList<Integer>();

	/*	Constructor for Game
	*	Welcomes the players and displays the empty board
	*/
	public Game()
	{
		System.out.println("*** Welcome to Battle Ships Game ***");
		System.out.println();
		System.out.println("Right now the sea is empty");
		board = new String[10][14];

		createBoard();
	}
	
	/*
	*	Creates the game board. It uses a 10 by 14 2D array. 
	*
	*/
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
	*	Sets the ships for the computer
	*	Prints a sentence after each ship is generated
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
			}
		}
		System.out.println("----------------------------------------");
	}
	
	/*
	 *	Method to start the battle
	 *	Continues to ask the user and generates the move for the computer as long as either player has more than 0 ships.
	*/
	public void battle()
	{
		boolean shouldContinueAsking = true;
		while(numberOfHumanShips > 0 && numberOfComputerShips > 0 ) 
		{
			int rowAttack, colAttack;
			while(shouldContinueAsking) 
			{
				System.out.println("YOUR TURN:");
				System.out.print("Enter the row you wish to attack: ");
				rowAttack = input.nextInt();
				System.out.print("Enter the column you wish to attack: ");
				colAttack = input.nextInt();
				if(validHumanGuess(rowAttack, colAttack))
				{
					evaluateHumanAttack(rowAttack, colAttack);
					shouldContinueAsking = false;
				}
				rowsHumanUsed.add(rowAttack);
				colsHumanUsed.add(colAttack);
			}
			computerTurn();
			shouldContinueAsking = true;
			printBoard();
			System.out.println("Your ships: "+numberOfHumanShips+" | Computer Ships: "+numberOfComputerShips);
			System.out.println("----------------------------------------");
		}
		if(numberOfHumanShips > numberOfComputerShips) 
		{
			System.out.println("Hooray! You won the battle!");
		}
		else 
		{
			System.out.println("Boo you suck, Computer Won");
		}
	}
	
	
	/*
	*	Computes a turn for the computer by generating random numbers, checks to see if the move is valid through the use 
	*	of the evalueteComputerGuess method. 
	*/
	private void computerTurn()
	{
		boolean shouldContinueGuessing = true;
		while(shouldContinueGuessing)
		{
			System.out.println("COMPUTER'S TURN:");
			int rowAttack = (int)(Math.random()*10);
			int colAttack = (int)(Math.random() * ((9 - 2) + 1)) + 2;
			if(validComputerGuess(rowAttack, colAttack))
			{
				evaluateComputerAttack(rowAttack, colAttack);
				shouldContinueGuessing = false;
			}
			rowsComputerUsed.add(rowAttack);
			colsComputerUsed.add(colAttack);
		}
		//System.out.println(rowAttack +" "+colAttack);
	}
	
	/*
	*	@param row; indicates the row of the guess
	*	@param col; indicates the column of the guess
	*
	*	@return a boolean; true if the guess is within the board and if the player hasn't already made that guess
	*					   false if the guess is out of bound or has already been made. 
	*/
	private boolean validHumanGuess(int row, int col) 
	{
		// returns true is player hasn't already guessed it 
		if(row < 0 || row >9 && col < 2 || col > 9 ) 
		{
			System.out.println("OUT OF BOUND: try different coordinates");
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
		for(int i = 0; i<rowsHumanUsed.size(); i++){
			if(rowsHumanUsed.get(i) == row && colsHumanUsed.get(i) == col) 
			{
				System.out.println("You already guessed these coordinates, try again");
				return false;
			}
		}
		return true; 
	}
	
	
	/*
	* 
	*	Evaluates the human attack, checks if it hit a computer's ship or one of its own
	*	Prints appropriate messages occurring to each outcome. 
	*
	*/
	private void evaluateHumanAttack(int row, int col)
	{
		if(board[row][col+2].equals("2")) // attack computer
		{
			board[row][col+2] = "x";
			System.out.println("BOOM! You sunk the ship!");
			numberOfComputerShips--;
		}
		else if(board[row][col+2].equals("1")) // attacks own ship
		{
			board[row][col+2] = "y";
			System.out.println("OH no, You sunk your own ship!");
			numberOfHumanShips--;
		}
		else 
		{
			board[row][col+2] = "-";
			System.out.println("Sorry you missed");
		}
	}
	
	/*
	*	@param row; indicates the row of the guess
	*	@param col; indicates the column of the guess
	*
	*	@return a boolean; true if the guess is within the board and if the computer hasn't already made that guess
	*					   false if the guess is out of bound or has already been made. 
	*/
	private boolean validComputerGuess(int row, int col) 
	{
		// returns true is player hasn't already guessed it 
		if(row < 0 || row >9 && col < 2 || col > 9 ) { return false;}
		if(row < 0 || row >9){return false;}
		if(col < 2 || col > 9) {return false;}
		for(int i = 0; i<rowsComputerUsed.size(); i++){
			if(rowsComputerUsed.get(i) == row && colsComputerUsed.get(i) == col) 
			{
				return false;
			}
		}
		return true; 
	}
	
	
	/*
	 * 
	 *Evaluates the human attack, checks if it hit a computer's ship or one of its own
	 *Prints appropriate messages occurring to each outcome. 
	 *
	*/
	private void evaluateComputerAttack(int row, int col)
	{
		if(board[row][col+2].equals("2")) // attack computer
		{
			board[row][col+2] = "x"; // stores it as an "x" but prints as "!"
			System.out.println("The computer sunk one of its ships");
			numberOfComputerShips--;
		}
		else if(board[row][col+2].equals("1")) // attacks own ship
		{
			board[row][col+2] = "y"; // stores it as "y" but prints as "x"
			System.out.println("Computer sunk one of your ships");
			numberOfHumanShips--;
		}
		else 
		{
			System.out.println("Computer missed");
		}
	}
	
	
	/*
	 * This method check if the space is taken by another point already
	 * and intended for the human ships to use.
	 * @param row is the row intended to check
	 * @param col is the column intended to check
	 * @return false if the space is already taken
	 * @return true otherwise. 
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
	 * This method check if the space is taken by another point already
	 * and intended for the computer ships to use.
	 * @param row is the row intended to check
	 * @param col is the column intended to check
	 * @return false if the space is already taken
	 * @return true otherwise. 
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
	 * Prints the board to the console, and checks for specific values in order to change the way they appear. 
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
				else if(board[i][j].equals("y")) 
				{ 
					System.out.print("x"); 
				}
				else if(board[i][j].equals("1")) 
				{
					System.out.print("@");
				}
				else if(board[i][j].equals("2"))
				{
					System.out.print(" ");
				}
				else if(board[i][j].equals("x")) 
				{ 
					System.out.print("!"); 
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
	 * Method to print the board with the 1s & 2s in order to debug and not for user to use
	*/
	@SuppressWarnings("unused")
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
