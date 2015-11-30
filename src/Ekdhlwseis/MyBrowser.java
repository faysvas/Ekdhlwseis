/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ekdhlwseis;


 
import java.net.URL;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;




 
/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class MyBrowser extends Region{
        HBox toolbar;
 
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
       
         
        public MyBrowser(){
             webView.setPrefWidth(445); 
             webView.setPrefHeight(445); 
            final URL urlOSM = getClass().getResource("openstreetmap.html");
            webEngine.load(urlOSM.toExternalForm());
             
            getChildren().add(webView);
            
            
           
            
            
            webEngine.getLoadWorker().stateProperty().addListener(
  new ChangeListener<State>() {  
    @Override public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
      if (newState == State.SUCCEEDED) {
        JSObject win = (JSObject) webEngine.executeScript("window");
        win.setMember("app", new Location());
    
      
      
      // var lon=Number("+coord[0]+");var lat=Number("+coord[1]+"); 
        webView.getEngine().executeScript("var lonlat = ol.proj.transform(["+Location.getLon()+","+Location.getLat()+"], 'EPSG:3857', 'EPSG:4326');var lon = lonlat[0];var lat = lonlat[1];"
                + "var map = new ol.Map({\n"
                + ""
                + "" +
                
"    interactions: ol.interaction.defaults({ doubleClickZoom: false }),\n" +
"  layers: [layer],\n" +
"  target: 'map',\n" +
"  view: new ol.View({\n" +
"    center: ["+Location.getLon()+","+Location.getLat()+"],\n" +
"    zoom: 13\n" +
"  })\n" +
"});\n" +
""
                + "var pos = ol.proj.fromLonLat([lon,lat]);var marker = new ol.Overlay({\n" +
"  position:pos,\n" +
"  positioning: 'center-center',\n" +
"  element: document.getElementById('marker'),\n" +
"  stopEvent: false\n" +
"});\n" +
"map.addOverlay(marker);\n" +
"\n" +
"// Vienna label\n" +
"var vienna = new ol.Overlay({\n" +
"  position: pos,\n" +
"  element: document.getElementById('vienna')\n" +
"});\n" +
"map.addOverlay(vienna);"
                + "map.on('dblclick', function(evt) {\n" +
"    \n" +
"\n" +
"  var coordinate = evt.coordinate;\n" +
"  var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(\n" +
"      coordinate, 'EPSG:3857', 'EPSG:4326'));\n" +
"    " +
"   app.location(coordinate);\n" +
"       \n" +
"var marker = new ol.Overlay({\n" +
"  position: coordinate,\n" +
"  positioning: 'center-center',\n" +
"  element: document.getElementById('marker'),\n" +
"  stopEvent: false\n" +
"});\n" +
"map.addOverlay(marker);\n" +
"var vienna = new ol.Overlay({\n" +
"  position: coordinate,\n" +
"  element: document.getElementById('vienna')\n" +
"});\n" +
"map.addOverlay(vienna);\n" +
"\n" +
"\n" +
"});"
                + "");
      }
    }
  });
}
         
        }


