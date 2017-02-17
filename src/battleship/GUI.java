package battleship;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
 
public class GUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    
    //just messing around with transparent buttons and background images
    //image needs to be fixed and GUI modeled in scene builder
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Battleship");
        BackgroundImage BI = new BackgroundImage(new Image("battleshipBI.png", 1313, 750,false,true),
        		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        
        BorderPane border = new BorderPane();
        border.setBackground(new Background(BI));
        FlowPane flow = new FlowPane();
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        Button btn2 = new Button();
        btn2.setText("Say 'Hello World'");
        Button btn3 = new Button();
        btn3.setText("Say 'Hello World'");
        btn.setOpacity(0.1);
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        flow.getChildren().add(btn);
        flow.getChildren().add(btn2);
        flow.getChildren().add(btn3);
        flow.setPrefWrapLength(170);
        border.setLeft(flow);
        primaryStage.setScene(new Scene(border, 1313, 750));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}