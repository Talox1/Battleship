package battleship.model;

import java.util.Observable;

public class Board extends Observable{
	String [][] my_board = new String [5][5];
	
        
        public Board(){
            for (int i = 0; i < 5; i++){
                for (int j = 0; j < 5; j++){
                    my_board[i][j] = "";
                }
            }
        }
                
	
	public void setPositionShip(String id_rect_ship) {
            String result[] = id_rect_ship.replaceAll("[a-z]+[\\_]","").split("_");//keep only coord (eg: 1_2), then split it in array
            //System.out.println("Result "+result[0]+" , "+result[1]);
            int coordX = Integer.parseInt(result[0]);
            int coordY = Integer.parseInt(result[1]);
            
            my_board[coordX][coordY] = "ship";
	}
	
	public boolean markMyBoard(String id_rect_ship) {// mark when enemy attack
            boolean isMyShipDestroyed = false;
            String result[] = id_rect_ship.replaceAll("[a-z]+[\\_]","").split("_");//keep only coord (eg: 1_2), then split it in array
            //System.out.println("Result "+result[0]+" , "+result[1]);
            int coordX = Integer.parseInt(result[0]);
            int coordY = Integer.parseInt(result[1]);
            
            
            
            //System.out.println("Board msg: "+my_board[coordX][coordY]);
            if(my_board[coordX][coordY].equals("ship") && my_board[coordX][coordY]!= "") {
                my_board[coordX][coordY] = "ship_destroyed";
                isMyShipDestroyed = true;
            }else {
                my_board[coordX][coordY] = "missile_failed";
                this.notifyObservers("misil fallido:"+id_rect_ship);
                isMyShipDestroyed = false;
            }   
            return isMyShipDestroyed;
	}
        
	
	public String [][] getMyBoard(){
            return my_board;
	}
}
