package battleship.model;

public class BoardShoot {
	String [][] board = new String [3][3];
	
	
	public void setPositionShip(int coordX, int coordY) {
		board[coordX][coordY] = "ship";
	}
	
	public void putMissile(int coordX, int coordY) {
		if(board[coordX][coordY].equals("ship")) {
			board[coordX][coordY] = "ship_destroyed";
		}else {
			board[coordX][coordY] = "missile_failed";
		}
	}
	
	public String [][] getBoard(){
		return board;
	}
}
