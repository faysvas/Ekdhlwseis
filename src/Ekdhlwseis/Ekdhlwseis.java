/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ekdhlwseis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Fay
 */
public class Ekdhlwseis extends Application {
    public static Stage parentWindow;
    //otan einai -1 den einai tipota epilegmeno, otan einai -2, pame se kainourio arthro
    public static int articleId=-1;
    
    @Override
    public void start(Stage stage) throws Exception {
        
       
       parentWindow = stage;
        Parent root = FXMLLoader.load(getClass().getResource("ViewArticles.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
    }

     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    
    
    
   
     
     
        //TableView
   
      
}
