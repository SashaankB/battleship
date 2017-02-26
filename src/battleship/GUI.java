package battleship;

import java.io.IOException;
import java.lang.reflect.Array;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.applet.Main;
 
public class GUI extends Application {
	private ImageView[] shipImage;
	private ToggleButton[] shipButton;
	private ToggleGroup ships;
	private AnchorPane pane;
	private Button[][] aiGrid, grid;
	private int shipButtonLength;
	private Board aiBoard, board;
	private boolean direction;
	private Button start;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Battleship");
        Image image = new javafx.scene.image.Image(getClass().getResource("/BattleshipBackground.png").toExternalForm());
        BackgroundImage BI = new BackgroundImage(image,
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
        aiBoard = new Board();
        board = new Board();
        setShip(0, 65, 112);
        setShip(1, 46, 180);
        setShip(2, 49, 245);
        setShip(3, 20, 312);
        setShip(4, 9, 388);
        
        ships.selectedToggleProperty().addListener(o -> {
        	if (ships.getSelectedToggle() == null)
        		shipButtonLength = 0;
        	else {
        		int[] data = (int[]) ships.getSelectedToggle().getUserData();
        		shipButtonLength = data[0];
        	}
        });
        placeShips();
        sGameButton();
        setBoard();
        
        
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
             
    }
    
    private void sGameButton() {
    	start = new Button("Start Game");
    	start.setLayoutX(733);
    	start.setLayoutY(502);
    	start.setPrefSize(122, 52);
    	start.setFont(new Font(16.0));
    	start.setOnMousePressed(e -> {
    		if (!aiBoard.allShips())
    			JOptionPane.showMessageDialog(null, "All ships must be placed to start game.", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
    		else {
    			aiBoard.stopPlacing();
    		}
    	});
    	pane.getChildren().add(start);
    }
    
    private void setBoard() {
    	int startX = 705;
        int startY = 421;
        int sizeB = 38;
    	grid = new Button[10][10];
    	
        for (int x = 0; x < 10; x++){
        	for (int y = 0; y < 10; y++){
        		final Integer innerX = new Integer(x);
        		final Integer innerY = new Integer(y);
        		grid[x][y] = new Button();
        		grid[x][y].setLayoutX(startX + sizeB * x);
        		grid[x][y].setLayoutY(startY - sizeB * y);
        		grid[x][y].setPrefSize(sizeB, sizeB);
        		grid[x][y].setOpacity(0.6);
        		//grid[x][y].setOnMouseEntered(e -> mouseEntered(innerX, innerY));
        		//grid[x][y].setOnMouseExited(e -> mouseExited(innerX, innerY));
        		//grid[x][y].setOnMousePressed(e -> mousePressed(innerX, innerY, e));
        		pane.getChildren().add(grid[x][y]);
        	}
        }
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
    	int[] data = {(int) temp.getWidth() / 34, index};
    	shipButton[index].setUserData(data);
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
        		aiGrid[x][y].setOnMouseEntered(e -> mouseEntered(innerX, innerY));
        		aiGrid[x][y].setOnMouseExited(e -> mouseExited(innerX, innerY));
        		aiGrid[x][y].setOnMousePressed(e -> mousePressed(innerX, innerY, e));
        		pane.getChildren().add(aiGrid[x][y]);
        	}
        }
    }
    
    private void mouseEntered(int x, int y) {
    	if (!allowWholeShip(x, y))
    		return;
    	for (int a = shipButtonLength-1; a >= 0; a--){  
    		if (!direction)
    			aiGrid[x][y + a].setOpacity(0.2);
    		else
    			aiGrid[x + a][y].setOpacity(0.2);
    	}
    }
    
    private void mouseExited(int x, int y) {
    	if (!allowWholeShip(x, y))
    		return;
    	for (int a = shipButtonLength-1; a >= 0; a--){
    		if (!direction)
    			aiGrid[x][y + a].setOpacity(0.6);
    		else
    			aiGrid[x + a][y].setOpacity(0.6);
    	}
    }
    
    private void mousePressed(int innerX, int innerY, MouseEvent e) {
    	if (e.getButton() == MouseButton.SECONDARY) {
    		direction = !direction;
    		if (!allowWholeShip(innerX, innerY)) {
        		direction = !direction;
        		return;
    		}
        	for (int a = shipButtonLength-1; a >= 0; a--){
        		if (direction) {
        			if (allowShip(innerX, innerY + a))
        				aiGrid[innerX][innerY + a].setOpacity(0.6);
        			if (allowShip(innerX + a, innerY))
        				aiGrid[innerX + a][innerY].setOpacity(0.2);
        			
        		}
        		else {
        			if (allowShip(innerX + a, innerY))
        				aiGrid[innerX + a][innerY].setOpacity(0.6);
        			if (allowShip(innerX, innerY + a))
        				aiGrid[innerX][innerY + a].setOpacity(0.2);
        		}
        	}
    		return;
    	} else {
    		if (ships.getSelectedToggle() == null)
    			return;
        	if (!allowWholeShip(innerX, innerY))
        		return;
        	Ship ship = new Ship(new Point(innerX, innerY), direction, shipButtonLength);
        	aiBoard.addShip(ship);
        	
        	int[] data = (int[]) ships.getSelectedToggle().getUserData();
    		shipImage[data[1]].setOpacity(0.4);
        	ships.getSelectedToggle().setUserData(new int[] {0, data[1]});
        	for (int a =  shipButtonLength-1; a >= 0; a--){
        		if (!direction)
        			aiGrid[innerX][innerY + a].setOpacity(0.0);
        		else
        			aiGrid[innerX + a][innerY].setOpacity(0.0);

        	}
        	shipButtonLength = 0;
    	}
    }
    
    private boolean allowShip(int x, int y) {
    	if (!direction) {
	    	if (y > 9)
	    		return false;
    		if (aiBoard.isShip(new Point(x, y)))
    			return false;
    	} else {
    		if (x > 9)
	    		return false;
    		if (aiBoard.isShip(new Point(x, y)))
    			return false;
    	}
    	return true;
    }
    
    private boolean allowWholeShip(int x, int y){
    	for (int i = shipButtonLength-1; i >= 0; i--){
    		if (!direction) {
    			if (!allowShip(x, y + i)) {
    				return false;
    			}
    		} else {
    			if (!allowShip(x + i, y))
    				return false;
    		}
    	}
    	return true;
    }
}