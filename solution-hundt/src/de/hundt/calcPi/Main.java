 package de.hundt.calcPi;
 
 import java.io.IOException;
 import javafx.application.Application;
 import javafx.fxml.FXMLLoader;
 import javafx.scene.Scene;
 import javafx.scene.layout.VBox;
 import javafx.stage.Stage;
 
 
 
 
 public class Main
   extends Application
 {
   public void start(Stage primaryStage) {
     VBox main = null;
 
     
     try {
       main = (VBox)FXMLLoader.load(getClass().getResource("/MainView.fxml"));
     }
     catch (IOException e) {
       
       e.printStackTrace();
     } 
     if (main != null) {
       
       primaryStage.setTitle("Calculate Pi Application");
       Scene scene = new Scene(main);
       scene.getStylesheets().add(getClass().getResource("/MainView.css").toExternalForm());
       primaryStage.setScene(scene);
       primaryStage.show();
     } 
   }
 
 
   
   public static void main(String[] args) { launch(args); }
 }

