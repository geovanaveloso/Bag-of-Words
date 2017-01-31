/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *Lista de objetos Video.
 * @author geovana
 */
public class VideosList extends ArrayList<Video> {
    
    /**
    *Leitura do arquivo de videos e adição do objeto na classe Video.
    * @param filename caminho do arquivo q estao as instancias do video.
    */ 
    public void readFile(String filename) {
        BufferedReader br;
        String str;
        try {
            br = new BufferedReader(new FileReader(filename));  
            while ((str = br.readLine()) != null) {
                String[] data = str.split(",");
                String[] data1 = data[data.length-1].split(";");
                ArrayList<String> tags = new ArrayList<>(Arrays.asList(data1));         
                if (data.length >= 7){
                    this.add(new Video(data[0], data[1], data[2], data[3], data[4], data[5], data[6],
                            tags));
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VideosList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VideosList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** 
     * Procura o video pelo id_video
     * @param id_video id do video a ser procurado
     * @return Objeto video encontrado, se nao for encontrado retorna null.
     */
    public Video searchVideo(String id_video){
        for (Video v : this) {
            if (v.getVideo_id().equalsIgnoreCase(id_video)) {
                return v;
            }
        }
        return null;
    }    
}
