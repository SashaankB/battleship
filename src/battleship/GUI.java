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
	private Board board;
	
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
        board = new Board();
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
        int startY = 421;
        int sizeB = 38;
    	aiGrid = new Button[10][10];
    	
        for (int x = 0; x < 10; x++){
        	for (int y = 0; y < 10; y++){
        		final Integer innerX = new Integer(x);
        		final Integer innerY = new Integer(y);
        		aiGrid[x][y] = new Button();
        		aiGrid[x][y].setLayoutX(startX + sizeB * x);
        		aiGrid[x][y].setLayoutY(startY - sizeB * y);
        		aiGrid[x][y].setPrefSize(sizeB, sizeB);
        		aiGrid[x][y].setOpacity(0.6);
        		aiGrid[x][y].setOnMouseEntered(new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	if (!allowShip(innerX, innerY))
    		        		return;
    		        	for (int a = shipButtonLength-1; a >= 0; a--){  
    		        		aiGrid[innerX][innerY + a].setOpacity(0.20);
    		        	}
    		        }
        		});
        		aiGrid[x][y].setOnMouseExited(new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	if (!allowShip(innerX, innerY))
    		        		return;
    		        	for (int a = shipButtonLength-1; a >= 0; a--){
    		        		aiGrid[innerX][innerY + a].setOpacity(0.6);
    		        	}
    		        }
        		});
        		aiGrid[x][y].setOnMousePressed(new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	if (!allowShip(innerX, innerY))
    		        		return;
    		        	Ship ship = new Ship(new Point(innerX, innerY), false, shipButtonLength);
    		        	board.addShip(ship);
    		        	ships.getSelectedToggle().setUserData(0);
    		        	for (int a =  shipButtonLength-1; a >= 0; a--){  
    		        		//aiGrid[innerX][innerY + a].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
    		        		aiGrid[innerX][innerY + a].setOpacity(0.0);
    		        	}
    		        	shipButtonLength = 0;
    		        }
        		});
        		pane.getChildren().add(aiGrid[x][y]);
        	}
        }
    }
    
    private boolean allowShip(int x, int y) {
    	if (y + shipButtonLength > 10)
    		return false;
    	for (int i = shipButtonLength-1; i >= 0; i--){
    		if (board.isShip(new Point(x, y + i)))
    			return false;
    	}
    	return true;
    }
}