package battleship.view;


import battleship.model.Board;
import battleship.model.socket.client.ChatClient;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;



import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;	
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

public class BattleshipController implements Observer{
    private ArrayList<Rectangle> listRectangleShoot = new ArrayList<>();
    private ArrayList<Rectangle> listRectangleBase = new ArrayList<>();
    private int cont_ships_available = 4;
    private int cont_ships_destroyed = 0;
    private String id_last_ship_attacked = "";
    boolean isMyTurn = false;
    private Board board = new Board();
    private ChatClient socketClient;
    
    @FXML
    private Label lbl_turnoStatus;
    
    @FXML
    private Button btn_start;
    
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
    	
        
        //ImageView myShip = new ImageView(new Image(this.getClass().getResourceAsStream("ships.png")));
    	
    }
    @FXML
    void exitGame(MouseEvent event) {
    	System.exit(0);
        socketClient.closeConecction();
    }

    @FXML
    void pressAtack(MouseEvent event) {
    	//get rectangle's id to put a image inside it
    	String [] data_event = event.getSource().toString().split("\\[");
    	data_event = data_event[1].replace("id", "").replace("=", "").split(",");
    	String id_rect = data_event[0];
      
        
        if(isMyTurn){
           
            for (Rectangle rectangle : listRectangleShoot) {
                if(id_rect.equals(rectangle.getId().toString()) ) {
                    if(rectangle.getFill()!= Color.RED && rectangle.getFill()!=Color.BLUE ){
                        rectangle.setFill(Color.BLUE);
                        id_last_ship_attacked = id_rect;
                        String message = "Atacando:" + id_rect;
            
                        socketClient.sendCoordAtack(message);
                    }
                    break; // to broke process
                }
            }
            
            
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wait");
            alert.setHeaderText("Aun no es tu turno para disparar");
            alert.setContentText("Espera tu turno");

            alert.showAndWait();
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
                if(rectangle.getFill()!= Color.GREEN){
                    rectangle.setFill(Color.GREEN);
                    cont_ships_available --;
                    board.setPositionShip(id_rect);
                }
                break; // to broke process
            }
        }
    }

    @FXML
    void restartGame(MouseEvent event) {

    }

    @FXML
    void startGame(MouseEvent event) {
        socketClient = new ChatClient("192.168.1.74", 8989);
        socketClient.addObserver(this);
        if(cont_ships_available > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ships incomplete");
            alert.setHeaderText("No has puesto todos los barcos");
            alert.setContentText("Pon mas barcos e intentalo de nuevo");

            alert.showAndWait();
            Platform.runLater(new Runnable() {//update an UI component 
                @Override
                public void run() {
                    lbl_turnoStatus.setText("Esperando contricante");
                }
            });
            
        }else{
            /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Iniciando juego");
            //alert.setHeaderText("No has puesto todos los barcos");
            alert.setContentText("Presione ok");

            alert.showAndWait();*/
            socketClient.execute();
            btn_start.setDisable(true);
        }
    	
    }
    
    public void update (Observable o, Object arg){
    	Platform.runLater(new Runnable() {//update an UI component 
            @Override
            public void run() {
                
                String data [] = String.valueOf(arg).split(":");
                //System.out.println("arg = "+arg);
                
                
                if(data.length <= 1){
                    if(String.valueOf(arg).startsWith("ya es tu turno")){
                        isMyTurn = true;
                        lbl_turnoStatus.setText("Es tu turno");
                    }else if(String.valueOf(arg).startsWith("ya no es tu turno")){
                        isMyTurn = false;
                        lbl_turnoStatus.setText("Turno del contricante");

                    }else if(String.valueOf(arg).equals("He perdido la partida")){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("You done");
                        alert.setHeaderText("Has perdido la partida");
                        alert.showAndWait();
                        System.exit(0);
                    }else if(String.valueOf(arg).equals("No se establecion conexion con el servidor")){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("I/O Error");
                        alert.setHeaderText("Connection refused");
                        alert.setContentText(String.valueOf(arg));
                        alert.showAndWait();
                        if(btn_start.isDisable())
                            btn_start.setDisable(false);
                    }
                }else{
                    if(data[0].equals("el enemigo te ataco")){//when the enemy attacks u
                       //.out.println("Controller msg --> marcar mi tablero");
                        String id_ship_attacked = data[1].replace("rect_shoot", "rect_base");// change id to find my ships
                        
                         for (Rectangle rectangle : listRectangleBase) {
                             
                           if(id_ship_attacked.equals(rectangle.getId().toString())) {
                               if(board.markMyBoard(id_ship_attacked)){
                                   //System.out.println("Controller msg --> destruyo mi barco");
                                   rectangle.setFill(Color.RED);
                                   socketClient.sendMessageToEnemy("destruiste mi barco:"+id_ship_attacked);
                               }else{
                                   socketClient.sendMessageToEnemy("fallaste el tiro:"+id_ship_attacked);
                                   rectangle.setFill(Color.BLUE);
                               }
                                   
                               break; // to broke process
                           }
                       }
                    }else if(data[0].equals("destruiste un barco enemigo")){// when u attack and destroy a enemy ship
                        
                        for (Rectangle rectangle : listRectangleShoot) {
                           if(id_last_ship_attacked.equals(rectangle.getId().toString())) {
                               rectangle.setFill(Color.RED);
                               break; // to broke process
                           }
                        }
                        cont_ships_destroyed ++;
                        if(cont_ships_destroyed == 4){
                            socketClient.sendMessageToEnemy("Has perdido");//send message to enemy that he lose 
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Winner winner chicken dinner");
                            alert.setHeaderText("Has ganado la partida");
                            alert.showAndWait();
                            System.exit(0);
                            
                        }
                            
                       
                    }
                }
            }
        });
    }

    
}
