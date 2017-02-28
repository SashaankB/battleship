package battleship;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.applet.Main;

/** Launches GUI application for Battleship Game.
 * @author Kellin McAvoy, Nathan Kelderman, Sean Thomas */
public class GUI extends Application {
	
	/** Array for images of ship objects. */
	private ImageView[] shipImage;
	
	/**  Array of Togglebuttons for ship buttons. */
	private ToggleButton[] shipButton;
	
	/** Togglegroup for ship Togglebuttons. */
	private ToggleGroup ships;
	
	/** Main Anchorpane for application. */
	private AnchorPane pane;
	
	/** Two-dimensional grid of buttons to represent both boards. */
	private Button[][] aiGrid, grid;
	
	/** Integer length of currently selected ship ToggleButton. */
	private int shipButtonLength;
	
	/** Board objects for player and AI board.  */
	private Board aiBoard, board;
	
	/** Boolean for which direction to display ship. */
	private boolean direction;
	
	/** Boolean flag for whether ships can be placed on board. */
	private boolean allowPlacing;
	
	/** Start and reset buttons. */
	private Button start, reset;
	
	/** AI object which makes decisions for the aiBoard. */
	private AI ai;
	
	/**  The primary stage for the application. */
	private Stage primaryStage;
	
	/**  Main method which launches application. 
	 * @param args arguments for main method. */
    public static void main(String[] args) {
        launch(args);
    }
    
    /** Start method which is entry point to javaFX application.
     * @param primaryStage Primary stage which other scenes are added.  */
    @Override
    public void start(Stage primaryStage) {
        startGame(primaryStage);
    }
    
    /** Method which initializes instance variables and places
     *  GUI elements on to the primary stage.
     *  @param primaryStage The stage to place GUI elements.  */
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
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        
        pane.setBackground(new Background(background));
        pane.setPrefSize(1094, 625);
        createMenuBar();
        setAIBoard();
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
    
    /** Creates menu bar for application.  */
    private void createMenuBar() {
    	MenuBar menuBar = new MenuBar();
        menuBar.setPrefSize(1094, 25);
        Menu file = new Menu("File");
        MenuItem resetMI = new MenuItem("Reset");
        resetMI.setOnAction(e -> {
        	allowPlacing = false;
    		startGame(primaryStage);
        });
        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> {
        	Platform.exit();
        	System.exit(0);
        });
        file.getItems().addAll(resetMI, close);
        menuBar.getMenus().addAll(file);
        pane.getChildren().add(menuBar);
    }
    
    /** Creates all five ships and puts them in Togglegroup. */
    private void createShips() {
    	setShip(0, 65, 112);
        setShip(1, 46, 180);
        setShip(2, 49, 245);
        setShip(3, 20, 312);
        setShip(4, 9, 388);
        
        ships.selectedToggleProperty().addListener(o -> {
        	if (ships.getSelectedToggle() == null) {
        		shipButtonLength = 0;
        	} else {
        		int[] data = (int[]) ships.getSelectedToggle().getUserData();
        		shipButtonLength = data[0];
        	}
        });
    }
    
    /** Creates and places reset game button. */
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
    
    /** Creates and places start game button. */
    private void sGameButton() {
    	start = new Button("Start Game");
    	start.setLayoutX(733);
    	start.setLayoutY(502);
    	start.setPrefSize(122, 52);
    	start.setFont(new Font(16.0));
    	start.setOnMousePressed(e -> {
    		if (!aiBoard.allShips()) {
    			JOptionPane.showMessageDialog(null, "All ships must be " + "placed to start game.", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
    		} else {
    			if (allowPlacing) {
    				return;
    			}
    			allowPlacing = true;
    			aiBoard.startGame();
    			JOptionPane.showMessageDialog(null, "Game starting...", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
    		}
    	});
    	pane.getChildren().add(start);
    }
    
    
    /** Logic for selecting a location on user's grid. 
     * @param x Integer x position.
     * @param y Integer y position. */
    private void gridMousePressed(int x, int y) {
    	Point p = new Point(x, y);
    	if (!aiBoard.allShips() || board.getPoint(p) || !allowPlacing) {
			return;
		}
    	
    	ImageView check = new ImageView(new Image(
    			Main.class.getResourceAsStream("/check.png"), 30, 30, true, true));
    	ImageView redx = new ImageView(new Image(
    			Main.class.getResourceAsStream(
    			"/redx.png"), 30, 30, true, true));
    	if (board.setPoint(p)) {
    		grid[x][y].setGraphic(check);
    		grid[x][y].setStyle("-fx-padding: 0, 0, 0, 0;");
    		if (board.sankShip()) {
				JOptionPane.showMessageDialog(null, "You have sank an enemy ship!", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
			}
    		if (board.gameOver()) {
				JOptionPane.showMessageDialog(null, "Congratulations! You won!", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
			}
    	} else {
    		grid[x][y].setGraphic(redx);
    		grid[x][y].setStyle("-fx-padding: 0, 0, 0, 0;");
    	}
    	advanceTurn();
    }
    
    /** Makes AI decision and advances turn. */
    private void advanceTurn() {
    	Point p = ai.nextTurn(aiBoard);
    	ImageView check = new ImageView(new Image(Main.class.getResourceAsStream("/check.png"), 30, 30, true, true));
    	ImageView redx = new ImageView(new Image(Main.class.getResourceAsStream("/redx.png"), 30, 30, true, true));
    	if (aiBoard.setPoint(p)) {
    		aiGrid[p.x][p.y].setGraphic(check);
    		aiGrid[p.x][p.y].setStyle("-fx-padding: 0, 0, 0, 0;");
    		aiGrid[p.x][p.y].setOpacity(0.5);
    		if (aiBoard.sankShip()) {
				JOptionPane.showMessageDialog(null, "You have lost a ship!", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
			}
    		if (aiBoard.gameOver()) {
				JOptionPane.showMessageDialog(null, "You lose.", "Battleship", JOptionPane.INFORMATION_MESSAGE, null);
			}
    	} else {
    		aiGrid[p.x][p.y].setGraphic(redx);
    		aiGrid[p.x][p.y].setStyle("-fx-padding: 0, 0, 0, 0;");
    	}
    }

    /** Places ship ToggleButton and ship image on application.
     * @param index Ship to place.
     * @param x location x of ship on application.
     * @param y location y of ship on application. */
    private void setShip(int index, double x, double y) {
    	Image temp = new Image(Main.class.getResourceAsStream("/ship" + (index + 1) + ".png"));
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

    /** Places user board buttons on grid. */
    private void setBoard() {
    	int startX = 704;
        int startY = 421;
        int sizeB = 38;
    	grid = new Button[10][10];
    	
        for (int x = 0; x < 10; x++) {
        	for (int y = 0; y < 10; y++) {
        		final Integer innerX = Integer.valueOf(x);
        		final Integer innerY = Integer.valueOf(y);
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
    
    /** Places AI board buttons on grid.  */
    private void setAIBoard() {
    	int startX = 236;
        int startY = 421;
        int sizeB = 38;
    	aiGrid = new Button[10][10];
    	
        for (int x = 0; x < 10; x++) {
        	for (int y = 0; y < 10; y++) {
        		final Integer innerX = Integer.valueOf(x);
        		final Integer innerY = Integer.valueOf(y);
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
    
    /** Logic for mouse entering a grid location on grid buttons.
     * @param x Integer x position,
     * @param y Integer y position. */
    private void mouseEntered(int x, int y) {
    	if (!allowWholeShip(x, y)) {
			return;
		}
    	for (int a = shipButtonLength - 1; a >= 0; a--) {  
    		if (!direction) {
				aiGrid[x][y + a].setOpacity(0.2);
			} else {
				aiGrid[x + a][y].setOpacity(0.2);
			}
    	}
    }
    
    /** Logic for mouse exiting a grid location on grid buttons.
     * @param x Integer x position,
     * @param y Integer y position. */
    private void mouseExited(int x, int y) {
    	if (!allowWholeShip(x, y)) {
			return;
		}
    	for (int a = shipButtonLength - 1; a >= 0; a--) {
    		if (!direction) {
				aiGrid[x][y + a].setOpacity(0.6);
			} else {
				aiGrid[x + a][y].setOpacity(0.6);
			}
    	}
    }
    
    /** Logic for left click and right click mouse press on grids.
     * @param x Integer x position,
     * @param y Integer y position. 
     * @param e Mouse event which occurred. */
    private void mousePressed(int x, int y, MouseEvent e) {
    	if (e.getButton() == MouseButton.SECONDARY) {
    		direction = !direction;
    		if (!allowWholeShip(x, y)) {
        		direction = !direction;
        		return;
    		}
    		direction = !direction;
        	for (int a = shipButtonLength - 1; a >= 0; a--) {
        		if (!direction) {
        			if (allowShip(x, y + a)) {
						aiGrid[x][y + a].setOpacity(0.6);
					}
        			if (allowShip(x + a, y)) {
						aiGrid[x + a][y].setOpacity(0.2);
        			}
        		} else {
        			if (allowShip(x + a, y)) {
						aiGrid[x + a][y].setOpacity(0.6);
					}
        			if (allowShip(x, y + a)) {
						aiGrid[x][y + a].setOpacity(0.2);
					}
        		}
        	}
        	direction = !direction;
    		return;
    	} else {
    		if (ships.getSelectedToggle() == null) {
				return;
			}
        	if (!allowWholeShip(x, y)) {
				return;
			}
        	Ship ship = new Ship(new Point(x, y), direction, shipButtonLength);
        	aiBoard.addShip(ship);
        	
        	//!!!!NEEDS TO BE DELETED ONCE AI WORKS!!!!
        	Ship ship2 = new Ship(new Point(x, y), direction, shipButtonLength);
        	board.addShip(ship2);
        	//!!DELETE ONCE AI WORKS!!
        	
        	int[] data = (int[]) ships.getSelectedToggle().getUserData();
    		shipImage[data[1]].setOpacity(0.4);
        	ships.getSelectedToggle().setUserData(new int[] {0, data[1]});
        	for (int a =  shipButtonLength - 1; a >= 0; a--) {
        		if (!direction) {
					aiGrid[x][y + a].setOpacity(0.0);
				} else {
					aiGrid[x + a][y].setOpacity(0.0);
				}

        	}
        	shipButtonLength = 0;
    	}
    }
    
    /** Checks singular point for valid location.
     * @param x Integer x position.
     * @param y Integer y position.
     * @return boolean true if point is a valid location. */
    private boolean allowShip(int x, int y) {
    	if (aiBoard.isShip(new Point(x, y))) {
    		return false;
    	}
    	if ((!direction && y > 9) || x > 9) {
    		return false;
    	}
    	return true;
    }
    
    /** Checks entire length of ship start at x, y for valid location.
     * @param x Integer x position.
     * @param y Integer y position.
     * @return boolean true if ship is in valid location. */
    private boolean allowWholeShip(int x, int y) {
    	for (int i = shipButtonLength - 1; i >= 0; i--) {
    		if (!direction) {
    			if (!allowShip(x, y + i)) {
    				return false;
    			}
    		} else {
    			if (!allowShip(x + i, y)) {
					return false;
				}
    		}
    	}
    	return true;
    }
}
