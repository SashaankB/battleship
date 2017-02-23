package battleship;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.applet.Main;
 
public class GUI extends Application {
	private ImageView[] shipImage;
	private ToggleButton[] shipButton;
	private ToggleGroup ships;
	private AnchorPane pane;
	private Button[][] aiGrid;
	private int shipButtonLength;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Battleship");
        
        BackgroundImage BI = new BackgroundImage(new Image("battleshipBackground.png", 1094, 625,false,true),
        		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/View.fxml"));
        pane =  loader.load();
        pane.setBackground(new Background(BI));
        
        shipImage = new ImageView[5];
        shipButton = new ToggleButton[5];
        ships = new ToggleGroup();
        setShip(0, 65, 112);
        setShip(1, 46, 180);
        setShip(2, 49, 245);
        setShip(3, 20, 312);
        setShip(4, 9, 388);
        
        ships.selectedToggleProperty().addListener(o -> {
        	if (ships.getSelectedToggle() == null)
        		shipButtonLength = 0;
        	else
        		shipButtonLength = (int) ships.getSelectedToggle().getUserData();
        });
        placeShips();
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
             
    }
    

    	

    private void setShip(int index, double x, double y){
    	Image temp = new Image(Main.class.getResourceAsStream("/ship" + (index+1) + ".png"));
    	shipImage[index] = new ImageView(temp);
    	shipImage[index].setLayoutX(x);
    	shipImage[index].setLayoutY(y);
    	shipButton[index] = new ToggleButton("");
    	shipButton[index].setLayoutX(x);
    	shipButton[index].setLayoutY(y);
    	shipButton[index].setPrefSize(temp.getWidth(), temp.getHeight());
    	shipButton[index].setOpacity(0.0);
    	shipButton[index].setToggleGroup(ships);
    	//length of ship is width of ship image / 34 pixels
    	shipButton[index].setUserData((int) temp.getWidth() / 34);
    	pane.getChildren().add(shipImage[index]);
    	pane.getChildren().add(shipButton[index]);    
    	
    }
    
    private void placeShips(){
    	int startX = 236;
        int startY = 80;
        int sizeB = 38;
    	aiGrid = new Button[10][10];
    	
        for (int i = 0; i < 10; i++){
        	for (int j = 0; j < 10; j++){
        		final Integer innerI = new Integer(i);
        		final Integer innerJ = new Integer(j);
        		aiGrid[i][j] = new Button("");
        		aiGrid[i][j].setLayoutX(startX + sizeB * i);
        		aiGrid[i][j].setLayoutY(startY + sizeB * j);
        		aiGrid[i][j].setPrefSize(sizeB, sizeB);
        		aiGrid[i][j].setOpacity(0.6);
        		aiGrid[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	for (int x = shipButtonLength-1; x >= 0; x--){  
    		        		if (innerJ + x > 9)
    		        			break;
    		        		aiGrid[innerI][innerJ + x].setOpacity(0.15);
    		        	}
    		        }
        		});
        		aiGrid[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	for (int x = shipButtonLength-1; x >= 0; x--){
    		        		if (innerJ + x > 9)
    		        			break;
    		        		aiGrid[innerI][innerJ + x].setOpacity(0.6);
    		        	}
    		        }
        		});
        		aiGrid[i][j].setOnMousePressed(new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	if (innerJ + shipButtonLength > 10)
		        			return;
    		        	Ship ship = new Ship(new Point(innerJ, innerI), true, shipButtonLength);
    		        	for (int x =  shipButtonLength-1; x >= 0; x--){  
    		        		aiGrid[innerI][innerJ + x].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
    		        		aiGrid[innerI][innerJ + x].setOpacity(0.0);
    		        	}
    		        }
        		});
        		pane.getChildren().add(aiGrid[i][j]);
        	}
        }
    }
}