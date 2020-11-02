
package battleship.view;



import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ImagenController {

    @FXML
    private Rectangle rectangulo;
    
    @FXML
    private Button btn;

    @FXML
    private GridPane tablero;
    
    @FXML
    private TableView table_view;
   
   
   void initialize(){
       for (int i = 0 ; i < 5 ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            tablero.getColumnConstraints().add(colConstraints);
            //enemy_board.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < 5 ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            tablero.getRowConstraints().add(rowConstraints);
            //enemy_board.getRowConstraints().add(rowConstraints);
        }
   }
   
    
    @FXML
    void getCoord(MouseEvent event) {
        
        for (int i = 0 ; i < 5 ; i++) {
            for (int j = 0; j < 5; j++) {
                addPane(i, j);
            }
        }
        
        
        
    }
    
     private void addPane(int colIndex, int rowIndex) {
        ImageView iv = new ImageView(getClass().getResource("images/1.jpg").toExternalForm());
        iv.setFitWidth(30);
        iv.setFitHeight(30);
         Pane pane = new Pane();
        pane.setOnMouseClicked(e -> {
             System.out.printf("Mouse clicked cell [%d, %d]%n", colIndex, rowIndex);
             tablero.add(iv, colIndex, rowIndex);
         });
        tablero.add(pane, colIndex, rowIndex);
    }
    
    @FXML
    void ponerImagen(ActionEvent event){
        ImageView iv = new ImageView(getClass().getResource("images/1.jpg").toExternalForm());
        iv.setFitWidth(30);
        iv.setFitHeight(30);
        Pane pane = new Pane();
        tablero.add(iv, 2, 2);
        tablero.add(pane, 2, 2);
    }
}
