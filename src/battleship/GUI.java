package battleship;

import java.io.IOException;
import java.lang.reflect.Array;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
	private boolean direction, allowPlacing;
	private Button start, reset;
	private AI ai;
	private Stage primaryStage;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        startGame(primaryStage);
    }
    
    private void startGame(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    	shipImage = new ImageView[5];
        shipButton = new ToggleButton[5];
        ships = new ToggleGroup();
        aiBoard = new Board();
        board = new Board();
        ai = new AI();
        pane = new AnchorPane();
       
        Image image = new javafx.scene.image.Image(getClass().getResource("/BattleshipBackground.png").toExternalForm());
        BackgroundImage BI = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        
        pane.setBackground(new Background(BI));
        pane.setPrefSize(1094, 625);
        createMenuBar();
        placeShips();
        sGameButton();
        resetButton();
        setBoard();
        createShips();
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Battleship");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
    
    private void createMenuBar() {
    	MenuBar menuBar = new MenuBar();
        menuBar.setPrefSize(1094, 25);
        Menu file = new Menu("File");
        MenuItem reset = new MenuItem("Reset");
        reset.setOnAction(e -> {
        	allowPlacing = false;
    		startGame(primaryStage);
        });
        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> {
        	Platform.exit();
        	System.exit(0);
        });
        file.getItems().addAll(reset, close);
        menuBar.getMenus().addAll(file);
        pane.getChildren().add(menuBar);
    }
    
    private void createShips() {
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
    }
    
    private void resetButton() {
    	reset = new Button("Reset");
    	reset.setLayoutX(893);
    	reset.setLayoutY(502);
    	reset.setPrefSize(122, 52);
    	reset.setFont(new Font(16.0));
    	reset.setOnMousePressed(e -> {
    		allowPlacing = false;
    		startGame(primaryStage);
    	});
    	pane.getChildren().add(reset);
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
    			if (allowPlacing)
    				return;
    			allowPlacing = true;
    			aiBoard.stopPlacing();
    			JOptionPane.showMessageDialog(null, "Game starting...", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
    		}
    	});
    	pane.getChildren().add(start);
    }
    
    private void setBoard() {
    	int startX = 704;
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
        		grid[x][y].setMaxSize(sizeB, sizeB);
        		grid[x][y].setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; -fx-background-insets: -1.4, 0, 1, 2; ");
        		grid[x][y].setOpacity(0.6);
        		grid[x][y].setOnMousePressed(e -> gridMousePressed(innerX, innerY));
        		pane.getChildren().add(grid[x][y]);
        	}
        }
    }
    
    private void gridMousePressed(int x, int y) {
    	Point p = new Point(x, y);
    	if (!aiBoard.allShips() || board.getPoint(p) || !allowPlacing)
    		return;
    	
    	ImageView check = new ImageView(new Image(Main.class.getResourceAsStream("/check.png"), 30, 30, true, true));
    	ImageView redx = new ImageView(new Image(Main.class.getResourceAsStream("/redx.png"), 30, 30, true, true));
    	if (board.setPoint(p)) {
    		grid[x][y].setGraphic(check);
    		grid[x][y].setStyle("-fx-padding: 0, 0, 0, 0;");
    		if (board.sankShip())
    			JOptionPane.showMessageDialog(null, "You have sank an enemy ship!", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
    		if (board.gameOver())
    			JOptionPane.showMessageDialog(null, "Congratulations! You won!", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
    	} else {
    		grid[x][y].setGraphic(redx);
    		grid[x][y].setStyle("-fx-padding: 0, 0, 0, 0;");
    	}
    	advanceTurn();
    }
    
    private void advanceTurn() {
    	Point p = ai.nextTurn(aiBoard);
    	ImageView check = new ImageView(new Image(Main.class.getResourceAsStream("/check.png"), 30, 30, true, true));
    	ImageView redx = new ImageView(new Image(Main.class.getResourceAsStream("/redx.png"), 30, 30, true, true));
    	if (aiBoard.setPoint(p)) {
    		aiGrid[p.x][p.y].setGraphic(check);
    		aiGrid[p.x][p.y].setStyle("-fx-padding: 0, 0, 0, 0;");
    		aiGrid[p.x][p.y].setOpacity(0.5);
    		if (aiBoard.sankShip())
    			JOptionPane.showMessageDialog(null, "You have lost a ship!", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
    		if (aiBoard.gameOver())
    			JOptionPane.showMessageDialog(null, "You lose.", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
    	} else {
    		aiGrid[p.x][p.y].setGraphic(redx);
    		aiGrid[p.x][p.y].setStyle("-fx-padding: 0, 0, 0, 0;");
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
        		aiGrid[x][y].setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; -fx-background-insets: -1.4, 0, 1, 2; ");
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
    		direction = !direction;
        	for (int a = shipButtonLength-1; a >= 0; a--){
        		if (!direction) {
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
        	direction = !direction;
    		return;
    	} else {
    		if (ships.getSelectedToggle() == null)
    			return;
        	if (!allowWholeShip(innerX, innerY))
        		return;
        	Ship ship = new Ship(new Point(innerX, innerY), direction, shipButtonLength);
        	aiBoard.addShip(ship);
        	
        	//!!!!NEEDS TO BE DELETED ONCE AI WORKS!!!!
        	Ship ship2 = new Ship(new Point(innerX, innerY), direction, shipButtonLength);
        	board.addShip(ship2);
        	//!!DELETE ONCE AI WORKS!!
        	
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