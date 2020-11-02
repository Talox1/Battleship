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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

public class BattleshipController implements Observer{
    private ArrayList<Rectangle> listRectangleShoot = new ArrayList<>();
    private ArrayList<Rectangle> listRectangleBase = new ArrayList<>();
    private int cont_ships_available = 4;
    private int cont_ships_destroyed = 0;
    
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
    private GridPane my_board;

    @FXML
    private GridPane enemy_board;
    
    
    void initialize() {
        //ImageView myShip = new ImageView(new Image(this.getClass().getResourceAsStream("ships.png")));
    	for (int i = 0 ; i < 5 ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            my_board.getColumnConstraints().add(colConstraints);
            enemy_board.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < 5 ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            my_board.getRowConstraints().add(rowConstraints);
            enemy_board.getRowConstraints().add(rowConstraints);
        }
    }
    @FXML
    void exitGame(MouseEvent event) {
        System.exit(0);
    	socketClient.closeConnection();
        
    }

    @FXML
    void attack(MouseEvent event) {
        if(isMyTurn){
            
            for (int i = 0 ; i < 5 ; i++) {
                for (int j = 0; j < 5; j++) {
                    sendCoordsAttack(i, j);
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
        
        for (int i = 0 ; i < 5 ; i++) {
            for (int j = 0; j < 5; j++) {
                addMyImageShips(j, i);
            }
        }
    }
    
    private void addMyImageShips(int colIndex, int rowIndex) {

        Pane pane = new Pane();
        pane.setOnMouseClicked(e -> {
             if(cont_ships_available > 0){
                ImageView iv = new ImageView(getClass().getResource("images/ship.png")
                        .toExternalForm());
                iv.setFitWidth(50);
                iv.setFitHeight(30);
                
               // System.out.printf("Mouse clicked cell [%d, %d]%n", colIndex, rowIndex);
                my_board.add(iv, colIndex, rowIndex);
                board.setPositionShip(rowIndex, colIndex);
                cont_ships_available --;
             }else{
                 my_board.setDisable(true);
             }
            
        });
        my_board.add(pane, colIndex, rowIndex);
    }
    
    private void sendCoordsAttack(int colIndex, int rowIndex){
         
        Pane pane = new Pane();
        
        pane.setOnMouseClicked(e -> {
            //System.out.printf("Mouse clicked cell [%d, %d]%n", colIndex, rowIndex);
            if(isMyTurn){
                String message = "Atacando:("+rowIndex+","+colIndex+")";
                //System.out.println("message"+message);
            
                socketClient.sendCoordAtack(message);
            }
            
        });
        enemy_board.add(pane, colIndex, rowIndex);
    }

    @FXML
    void restartGame(MouseEvent event) {
        
    }

    @FXML
    void startGame(MouseEvent event) {
        socketClient = new ChatClient("192.168.1.66", 8989);
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
            socketClient.execute();
            btn_start.setDisable(true);
        }
    	
    }
    
    
    public void update (Observable o, Object arg){
    	Platform.runLater(new Runnable() {//update an UI component 
            @Override
            public void run() {
                //System.out.println("Controller msg (arg) 194-->"+String.valueOf(arg));
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
                        String[] coord_bomb = data[1].replace("(","").replace(")","").split(",");// save
                        int coord_x = Integer.parseInt(coord_bomb[1]); 
                        int coord_y = Integer.parseInt(coord_bomb[0]); 
                        //System.out.println("controller msg 228--> "+coord_bomb[0]+":"+coord_bomb[1]);
                        
                            if(board.markMyBoard(coord_x, coord_y )){
                                ImageView iv = new ImageView(getClass().getResource("images/ship_destroyed.png").toExternalForm());
                                iv.setFitWidth(50);
                                iv.setFitHeight(30);
                                //System.out.println("Controller msg --> destruyo mi barco");
                                //rectangle.setFill(Color.RED);
                                
                                    Pane pane = new Pane();
                                    my_board.add(iv, coord_x, coord_y);
                                    my_board.add(pane, coord_x, coord_y);

                                socketClient.sendMessageToEnemy("destruiste mi barco:"+data[1]);
                            }else{
                                socketClient.sendMessageToEnemy("fallaste el tiro:"+data[1]);
                                
                                ImageView iv = new ImageView(getClass().getResource("images/missile_failed.png").toExternalForm());
                                iv.setFitWidth(50);
                                iv.setFitHeight(30);
                                
                                Pane pane = new Pane();
                                my_board.add(iv, coord_x, coord_y);
                                my_board.add(pane, coord_x, coord_y);
                                //rectangle.setFill(Color.BLUE);
                            }
                                   
                       
                    }else if(data[0].equals("destruiste un barco enemigo")){// when u attack and destroy a enemy ship
                        String[] coordS = data[1].replace("(", "").replace(")","").split(",");
                        
                        int coord_x = Integer.parseInt(coordS[1]);
                        int coord_y = Integer.parseInt(coordS[0]);
                        //System.out.println("Conttoller mesaje, destruiste un barco enemigo"+data[1]);
                        cont_ships_destroyed ++;
                        
                        ImageView iv = new ImageView(getClass().getResource("images/ship_destroyed.png").toExternalForm());
                        iv.setFitWidth(50);
                        iv.setFitHeight(30);

                        Pane pane = new Pane();
                        enemy_board.add(iv, coord_x, coord_y);
                        enemy_board.add(pane, coord_x, coord_y);
                                
                        if(cont_ships_destroyed == 4){
                            socketClient.sendMessageToEnemy("Has perdido");//send message to enemy that he lose 
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Winner winner chicken dinner");
                            alert.setHeaderText("Has ganado la partida");
                            alert.showAndWait();
                            System.exit(0);
                            
                        }
                            
                       
                    }else if(data[0].equals("falle mi tiro")){
                        String [] coordS = data[1].replace("(", "").replace(")","").split(",");
                        int coord_x = Integer.parseInt(coordS[1]);
                        int coord_y = Integer.parseInt(coordS[0]);
                        
                        ImageView iv = new ImageView(getClass().getResource("images/missile_failed.png").toExternalForm());
                        iv.setFitWidth(50);
                        iv.setFitHeight(30);

                        Pane pane = new Pane();
                        enemy_board.add(iv, coord_x, coord_y);
                        enemy_board.add(pane, coord_x, coord_y);
                    }
                }
            }
        });
    }

    
}
