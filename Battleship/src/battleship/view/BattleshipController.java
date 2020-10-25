package battleship.view;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;


import battleship.model.socket.client.*;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;	
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

public class BattleshipController implements Observer{
	ArrayList<Rectangle> listRectangleShoot = new ArrayList<>();
	ArrayList<Rectangle> listRectangleBase = new ArrayList<>();
	private int cont_ships_available = 4;
	Semaphore turno ;
	@FXML
    private AnchorPane root;
	
    @FXML
    private TableColumn<?, ?> tblUser1;

    @FXML
    private TableColumn<?, ?> tblUser2;

    @FXML
    private Rectangle rect_shoot_0_0, rect_shoot_0_1, rect_shoot_0_2, rect_shoot_0_3, rect_shoot_0_4, rect_shoot_1_0, rect_shoot_1_1, rect_shoot_1_2, rect_shoot_1_3, 
    	rect_shoot_1_4,  rect_shoot_2_0, rect_shoot_2_1, rect_shoot_2_2,  rect_shoot_2_3, rect_shoot_2_4, rect_shoot_3_0, rect_shoot_3_1, rect_shoot_3_2,rect_shoot_3_3,
    	rect_shoot_3_4, rect_shoot_4_0, rect_shoot_4_1, rect_shoot_4_2, rect_shoot_4_3, rect_shoot_4_4;

    @FXML
    private Rectangle rect_base_0_0, rect_base_0_1, rect_base_0_2, rect_base_0_3, rect_base_0_4, rect_base_1_0, rect_base_1_1, rect_base_1_2, rect_base_1_3, rect_base_1_4, 
    	rect_base_2_0, rect_base_2_1, rect_base_2_2, rect_base_2_3, rect_base_2_4, rect_base_3_0, rect_base_3_1, rect_base_3_2, rect_base_3_3, rect_base_3_4, 
    	rect_base_4_0, rect_base_4_1, rect_base_4_2, rect_base_4_3, rect_base_4_4;

    
    @FXML
    void initialize() {
    	listRectangleShoot.add(rect_shoot_0_0);
    	listRectangleShoot.add(rect_shoot_0_1);
    	listRectangleShoot.add(rect_shoot_0_2);
    	listRectangleShoot.add(rect_shoot_0_3);
    	listRectangleShoot.add(rect_shoot_0_4);
    	listRectangleShoot.add(rect_shoot_1_0);
    	listRectangleShoot.add(rect_shoot_1_1);
    	listRectangleShoot.add(rect_shoot_1_2);
    	listRectangleShoot.add(rect_shoot_1_3);
    	listRectangleShoot.add(rect_shoot_1_4);
    	listRectangleShoot.add(rect_shoot_2_0);
    	listRectangleShoot.add(rect_shoot_2_1);
    	listRectangleShoot.add(rect_shoot_2_2);
    	listRectangleShoot.add(rect_shoot_2_3);
    	listRectangleShoot.add(rect_shoot_2_4);
    	listRectangleShoot.add(rect_shoot_3_0);
    	listRectangleShoot.add(rect_shoot_3_1);
    	listRectangleShoot.add(rect_shoot_3_2);
    	listRectangleShoot.add(rect_shoot_3_3);
    	listRectangleShoot.add(rect_shoot_3_4);
    	listRectangleShoot.add(rect_shoot_4_0);
    	listRectangleShoot.add(rect_shoot_4_1);
    	listRectangleShoot.add(rect_shoot_4_2);
    	listRectangleShoot.add(rect_shoot_4_3);
    	listRectangleShoot.add(rect_shoot_4_4);

    	listRectangleBase.add(rect_base_0_0);
    	listRectangleBase.add(rect_base_0_1);
    	listRectangleBase.add(rect_base_0_2);
    	listRectangleBase.add(rect_base_0_3);
    	listRectangleBase.add(rect_base_0_4);
    	listRectangleBase.add(rect_base_1_0);
    	listRectangleBase.add(rect_base_1_1);
    	listRectangleBase.add(rect_base_1_2);
    	listRectangleBase.add(rect_base_1_3);
    	listRectangleBase.add(rect_base_1_4);
    	listRectangleBase.add(rect_base_2_0);
    	listRectangleBase.add(rect_base_2_1);
    	listRectangleBase.add(rect_base_2_2);
    	listRectangleBase.add(rect_base_2_3);
    	listRectangleBase.add(rect_base_2_4);
    	listRectangleBase.add(rect_base_3_0);
    	listRectangleBase.add(rect_base_3_1);
    	listRectangleBase.add(rect_base_3_2);
    	listRectangleBase.add(rect_base_3_3);
    	listRectangleBase.add(rect_base_3_4);
    	listRectangleBase.add(rect_base_4_0);
    	listRectangleBase.add(rect_base_4_1);
    	listRectangleBase.add(rect_base_4_2);
    	listRectangleBase.add(rect_base_4_3);
    	listRectangleBase.add(rect_base_4_4);
    	
    	
    }
    @FXML
    void exitGame(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void pressAtack(MouseEvent event) {
    	//get rectangle's id to put a image inside it
    	String [] data_event = event.getSource().toString().split("\\[");
    	data_event = data_event[1].replace("id", "").replace("=", "").split(",");
    	String id_rect = data_event[0];
    	
    	//search of rect selected
    	for (Rectangle rectangle : listRectangleShoot) {
    		if(id_rect.equals(rectangle.getId().toString()) && cont_ships_available > 0) {
    			rectangle.setFill(Color.RED);
    			break; // to broke process
    		}
		}
    }

    @FXML
    void putShips(MouseEvent event) {
    	//get rectangle's id to put a image inside it
    	String [] data_event = event.getSource().toString().split("\\[");
    	data_event = data_event[1].replace("id", "").replace("=", "").split(",");
    	String id_rect = data_event[0];
    	
    	//search of rect selected
    	for (Rectangle rectangle : listRectangleBase) {
    		if(id_rect.equals(rectangle.getId().toString()) && cont_ships_available > 0) {
    			rectangle.setFill(Color.YELLOW);
    			
    			cont_ships_available --;
    			break; // to broke process
    		}
		}
    }

    @FXML
    void restartGame(MouseEvent event) {

    }

    @FXML
    void startGame(MouseEvent event) {
    	ChatClient socketClient = new ChatClient("192.168.1.74", 8989);
    	socketClient.execute();
    	
    }
    
    public void update (Observable o, Object arg){
    	
    }

}
