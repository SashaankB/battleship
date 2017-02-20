package battleship;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sun.applet.Main;
 
public class GUI extends Application {
	private ImageView[] shipImage;
	private Button[] shipButton;
	private AnchorPane pane;
	
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
        shipButton = new Button[5];
        setShip(0, 65, 112);
        setShip(1, 46, 180);
        setShip(2, 49, 245);
        setShip(3, 20, 312);
        setShip(4, 9, 388);
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //example button implementation, would also start hover cursor over grid
        shipButton[0].addEventHandler(MouseEvent.MOUSE_PRESSED,
    		    new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	shipImage[0].setOpacity(0.5);
    		        }
        });
    }
    

    	

    private void setShip(int index, double x, double y){
    	Image temp = new Image(Main.class.getResourceAsStream("/ship" + (index+1) + ".png"));
    	shipImage[index] = new ImageView(temp);
    	shipImage[index].setLayoutX(x);
    	shipImage[index].setLayoutY(y);
    	shipButton[index] = new Button("");
    	shipButton[index].setLayoutX(x);
    	shipButton[index].setLayoutY(y);
    	shipButton[index].setPrefSize(temp.getWidth(), temp.getHeight());
    	shipButton[index].setOpacity(0.0);
    	pane.getChildren().add(shipImage[index]);
    	pane.getChildren().add(shipButton[index]);
    	
    }
    	
    	//TODO
    	//Create a 10x10 of buttons on top of the grid for placing the ships, using for loop
    	//Figure out how to cursor hover for buttons
}