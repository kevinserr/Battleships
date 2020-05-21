package battleships;

public class BattleShipGame {

	public static void main(String[] args)
	{
		Game g  =new Game();
		g.setHumanShips();
		g.battle();
		
		g.printBoard();
	}

}
