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
        AnchorPane pane =  loader.load();
        pane.setBackground(new Background(BI));
        setShipButtons(pane);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void setShipButtons(AnchorPane pane){
    	ImageView viewShip1 = new ImageView(new Image(Main.class.getResourceAsStream("/ship1.png")));
    	viewShip1.setLayoutX(65);
    	viewShip1.setLayoutY(112);
    	Button ship1 = new Button("");
    	ship1.setLayoutX(65);
    	ship1.setLayoutY(112);
    	ship1.setPrefSize(71, 36);
    	ship1.setOpacity(0.0);
    	pane.getChildren().add(viewShip1);
    	pane.getChildren().add(ship1);
    	
    	ship1.addEventHandler(MouseEvent.MOUSE_PRESSED,
    		    new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	viewShip1.setOpacity(0.5);
    		        }
    	});
    	
    	//TODO
    	//Move the other 4 ship buttons/imageViews from View.fxml into setShipButtons
    	//Using the PrefSize/Layout from View.fxml to set that info here
    	//Gives us control when we want to have events happen on button press like you see above
    	
    	//TODO
    	//Create a 10x10 of buttons on top of the grid for placing the ships, using for loop
    	//Figure out how to cursor hover for buttons
    }
}