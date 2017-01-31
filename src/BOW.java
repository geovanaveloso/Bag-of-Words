import java.io.IOException;
import java.util.ArrayList;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Dicionário de palavras.
 * @author geovana
 */
public class BOW extends ArrayList<String> {
    
    /**      
    * Construção do dicionário de palavras.
    * @param vl lista de videos a serem extraídas as palavras
    */
    public void buildBOW(VideosList vl) {
        String word;
        for (int i=0; i<vl.size(); i++){
            //separacao das strings em palavras
            ArrayList<String> t = vl.get(i).tonkenizer();
            
            for (int j=0; j<t.size(); j++){
                // pre processamento da palavra
                word = Utils.preProcess(t.get(j));
                // se a palavra não estiver no bow e não for vazia ela e add ao bow
                if (!this.contains(word) && !word.isEmpty()){
                    this.add(word);
                }
            }
        }
    }  
       
    /**
    * Construção do vector de caracteristicas baseado no dicionário de palavras.
    * @param vl Lista de videos 
    * @throws java.io.IOException 
    */
    public void buildVector(VideosList vl) throws IOException {
        String word;
        int[] vector = new int[this.size()];

        for (int i = 0; i < vl.size(); i++) {
            for (int x=0; x<vector.length;x++){
                vector[x]=0;
            }
                
            ArrayList<String> t = vl.get(i).tonkenizer();
            
            for (int x = 0; x < this.size(); x++) {
                for (int y=0; y<t.size(); y++){
                    word = Utils.preProcess(t.get(y));
                    if (this.get(x).equals(word)){
                        vector[x]++;
                        break;
                    }
                }
            }
            ArrayList<Integer> vl_in = new ArrayList<>();
            for (int x=0; x<vector.length; x++){
                vl_in.add(vector[x]);
            }
            vl.get(i).setVector(vl_in);
        }
    }
    
    /**
    * Construção do vector de caracteristicas baseado no dicionário de palavras.
    * @param v Objeto Videos 
    * @throws java.io.IOException 
    */
    public void buildVector(Video v) throws IOException {
        String word;
        int[] vector = new int[this.size()];
        ArrayList<String> t = v.tonkenizer();
        for (int x=0; x<vector.length;x++){
                vector[x]=0;
        }            
        for (int x = 0; x < this.size(); x++) {
            for (int y=0; y<t.size(); y++){
                word = Utils.preProcess(t.get(y));
                if (this.get(x).equals(word)){
                    vector[x]++;
                    break;
                }
            }
        }
        ArrayList<Integer> v_in = new ArrayList<>();
        for (int x=0; x<vector.length; x++){
            v_in.add(vector[x]);
        }
        v.setVector(v_in);
    }
}