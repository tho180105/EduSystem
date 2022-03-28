/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tho
 */
public class jdbcHelper {
    public static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"; 
    public static String dburl="jdbc:sqlserver://localhost:1433;databaseName=EduSys";
    public static String username="sa";
    public static String password="songlong";
    
    static{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(jdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public static PreparedStatement getStmt(String sql,Object...args)throws SQLException{
        
        Connection con=DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt=null;
        
        if(sql.startsWith("{")){
            pstmt=con.prepareCall(sql); 
        } else {
            pstmt=con.prepareStatement(sql);
        }
            
        for(int i=0; i<args.length;i++){
            pstmt.setObject(i+1, args[i]);
        }
        
        return pstmt;
    }
    
    public static int executeUpdate(String sql,Object...args){
        try {
            PreparedStatement pstmt= getStmt(sql, args);
            try{
              return  pstmt.executeUpdate();
            }finally{
                pstmt.getConnection().close();            
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static ResultSet executeQuery(String sql, Object...args){
        try {
            PreparedStatement pstmt=getStmt(sql, args);
            try{
                return pstmt.executeQuery();
            }finally{
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);         
        }
    }
    
    public static Object value(String sql, Object...args){
        try{
            ResultSet rs =jdbcHelper.executeQuery(sql, args);
            if(rs.next()){
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
        }catch(Exception e){
        }
        return null;   
    }
    
}
