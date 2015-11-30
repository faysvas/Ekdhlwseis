/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ekdhlwseis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Location {
    
   //arxikopoihsh tou coordinate
  private static String coordinate;

  public static void initCoord() throws SQLException, ClassNotFoundException
  {
        String query = "SELECT coordinate FROM default_coord";

            PreparedStatement preps = DBConnect.getConnection().prepareStatement(query);
            
            ResultSet rs = preps.executeQuery();
            
            while (rs.next()) {
                coordinate=rs.getString("coordinate"); 
            }
    
      
  }
    public static String getCoordinate(){
        return coordinate;
    }
     public static void setCoordinate(String coord){
         coordinate=coord;
     }
  public void location(String coordinate) {
    System.out.println(coordinate);
    setCoordinate(coordinate);
  }
  
  public String getLocation(String coordinate) {
    System.out.println("in get location");
    return getCoordinate();
  }
  
 public static Float getLon(){
     String[] parts = getCoordinate().split(",");
     float lon = Float.parseFloat(parts[0]); 
     System.out.println(lon);
     return lon;
 } 
 
public static Float getLat(){
     String[] parts = getCoordinate().split(",");
     float lat = Float.parseFloat(parts[1]); 
     System.out.println(lat);
     return lat;
 } 
  
}