/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ekdhlwseis;

import com.sun.prism.impl.Disposer.Record;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Fay
 *
 *
 */
public class ViewArticlesController implements Initializable {

    @FXML
    private TableView articles;
    @FXML
    private TableColumn edit;
    @FXML
    private TableColumn id;

    @FXML
    private TableColumn title;

    @FXML
    private TableColumn date;

    @FXML
    private Label notify;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        buildData();

    }

    private int getSelIndex() {

        return data.indexOf(articles.getSelectionModel().getSelectedItem());
    }

    private void goToAddArticle(int Id) throws IOException {
        Ekdhlwseis.articleId = Id;
        Parent root = FXMLLoader.load(getClass().getResource("AddArticle.fxml"));
        Scene scene = new Scene(root);
        Ekdhlwseis.parentWindow.setScene(scene);
        Ekdhlwseis.parentWindow.show();
    }

    private int getArticleId() {

        int selIndex = getSelIndex();
        if (selIndex >= 0) {
            String row = articles.getSelectionModel().getSelectedItem().toString();
            return Integer.parseInt(row.toString().split(",")[0].substring(1));
        } else {
            return -1;
        }
    }

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;

    //MAIN EXECUTOR
    //CONNECTION DATABASE
    public void editAction() throws IOException {

        if (getArticleId() != -1) {

            goToAddArticle(getArticleId());
        } else {
            notify.setTextFill(Color.RED);
            notify.setText("Please select an article to edit");

        }
    }

    public void deleteAction() throws ClassNotFoundException {
        //to index ths epilegmenhs seiras
        int selIndex = getSelIndex();

        //To id ths epilegmenhs seiras
        int indexID = getArticleId();

        if (selIndex >= 0) {
            System.out.println(indexID);
            data.remove(selIndex);
            articles.getSelectionModel().clearSelection();
        }

        String query = "DELETE FROM articles WHERE ID =?";
        try {
            PreparedStatement preps = DBConnect.getConnection().prepareStatement(query);
            preps.setInt(1, indexID);
            preps.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void newAction() throws IOException {
        goToAddArticle(-2);

    }

    public void saveAction() throws ClassNotFoundException, IOException {

        String sql = "UPDATE default_coord SET coordinate=? WHERE id=1";

        try {
            PreparedStatement preparedStatement = DBConnect.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, Location.getCoordinate());

            preparedStatement.execute();
            notify.setTextFill(Color.GREEN);
            notify.setText("Default location was saved");

        } catch (SQLException ex) {
            notify.setTextFill(Color.RED);
            notify.setText("Default location couldn't be saved");
            System.out.println(ex.getMessage());
        }

    }

    public void buildData() {

        Connection c;
        data = FXCollections.observableArrayList();
        try {
            Location.initCoord();
            c = DBConnect.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT id,title,date from articles";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            //  for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            //  final int j = i;                
            //TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            // TableColumn col=title;
            id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                    return new SimpleStringProperty(param.getValue().get(0).toString());
                }

            });

            title.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                    return new SimpleStringProperty(param.getValue().get(1).toString());
                }

            });

            date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                    return new SimpleStringProperty(param.getValue().get(2).toString());
                }

            });

            //articles.getColumns().add(col_action);
            //  articles.getColumns().addAll(col); 
            //    System.out.println("Column ["+i+"] ");
            //   }
            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));

                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            articles.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

}
