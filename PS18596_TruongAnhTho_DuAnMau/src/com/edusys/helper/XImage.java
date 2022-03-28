/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.helper;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

/**
 *
 * @author Tho
 */
public class XImage {
    
    public static final Image APP_ICON;
    
    static{
        String file = "/com/edusys/icon/fpt.png";      
        APP_ICON = new ImageIcon(Auth.class.getResource(file)).getImage();
    }
    
    public static boolean saveLogo(File file){
        File dir = new File("logos");  
        if(!dir.exists()){
           dir.mkdirs();
        }
        File newFile = new File(dir, file.getName());
        try {
            Path source = Paths.get(file.getAbsolutePath());
            Path destination = Paths.get(newFile.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (Exception ex) {
        return false;
        }
    }
    
    public static void changeBackgroundDialog(JDialog j){
        j.getContentPane().setBackground(new Color(245,245,245) );
    }

    public static ImageIcon readLogo(String fileName){
       File path = new File("logos", fileName);
       return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
    }
    
    
    
    
}
