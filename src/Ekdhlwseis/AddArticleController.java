/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ekdhlwseis;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

/**
 *
 * @author Fay
 */
public class AddArticleController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Label label;

    @FXML
    private HTMLEditor editor;

    @FXML
    private TextField title;

    @FXML
    private ChoiceBox status;

    @FXML
    private MyBrowser mybrowser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            status.setItems(FXCollections.observableArrayList("Draft", "Published"));
            // TODO
            articleInit();

            // AnchorPane.getChildren().add(openstreetmap); 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddArticleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String currentDate() {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf
                = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(dt);
    }
    
    public void goBack() throws IOException{
        
        Ekdhlwseis.articleId = -1;
        Parent root = FXMLLoader.load(getClass().getResource("ViewArticles.fxml"));
        Scene scene = new Scene(root);
        Ekdhlwseis.parentWindow.setScene(scene);
        Ekdhlwseis.parentWindow.show();
    }

    public void cancelAction() throws IOException {
            goBack();
    }

    public void saveAction() throws  ClassNotFoundException, IOException {
String sql="";
        if (Ekdhlwseis.articleId > 0) {
            sql = "UPDATE articles SET title=?,coordinate=?,status=?,date=?,text=? WHERE ID=?";

        } else if (Ekdhlwseis.articleId == -2) {
 
            sql = "INSERT INTO articles (title,coordinate,status,date,text)" +
        "VALUES (?, ?, ?, ?,?)";
        }
        
           try {
            PreparedStatement preparedStatement = DBConnect.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title.getText());
          
            preparedStatement.setString(2,  Location.getCoordinate() );
            preparedStatement.setString(3,  status.getSelectionModel().getSelectedItem().toString() );
            preparedStatement.setString(4,  currentDate() );
            preparedStatement.setString(5, editor.getHtmlText());
            if (Ekdhlwseis.articleId > 0){
            preparedStatement.setInt(6, Ekdhlwseis.articleId);
            }
            preparedStatement.execute();
            
                     goBack();
              
            
            
           }catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        
    }

    public void articleInit() throws ClassNotFoundException {

        String coordinate;

        if (Ekdhlwseis.articleId >= 0) {

            String query = "SELECT title,text,coordinate,status FROM articles WHERE ID =?";

            try {
    //   PreparedStatement preps = DBConnect.getConnection().prepareStatement(query);

                PreparedStatement preps = DBConnect.getConnection().prepareStatement(query);
                preps.setInt(1, Ekdhlwseis.articleId);
                ResultSet rs = preps.executeQuery();

                while (rs.next()) {
                    title.setText(rs.getString("title"));
                    editor.setHtmlText(rs.getString("text"));
                    
                   Location.setCoordinate(rs.getString("coordinate"));

                    if (rs.getString("status") == "draft") {

                        status.getSelectionModel().select(0);
                    } else {
                        status.getSelectionModel().select(1);
                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}
