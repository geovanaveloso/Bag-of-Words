/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe do classificador kNN.
 * @author geovana
 */
public class Knn {
    
    /**
     * Classificaçao kNN com o coeficiente de Jaccard.
     * @param vl_train Lista de dados de treinamento
     * @param v dado de classificaçao
     * @param k numero de vizinho do algoritmo kNN
     */
    public void runknnCoeficienteJaccard(VideosList vl_train, Video v, int k) {
        TreeMap<Double, Video> vizinhos = new TreeMap<>(Collections.reverseOrder());
        for (int i = 0; i < vl_train.size(); i++) {
            double e = Utils.coeficienteJaccard(v, vl_train.get(i));
            vizinhos.put(e, vl_train.get(i)); 
        }        
        while (vizinhos.size() > k ) {
            double d = vizinhos.lastKey();
            vizinhos.remove(d);
        }       
        Utils.print(vizinhos, v);
        checkAnswer(v, vl_train, vizinhos);
    }
    
    /**
     * Classificaçao kNN com a distancia euclidiana
     * @param vl_train Lista de dados de treinamento
     * @param v dado de classificaçao
     * @param k numero de vizinho do algoritmo kNN
     */
    public void runknnDistanceEuclidiana(VideosList vl_train, Video v, int k) {
        TreeMap<Double, Video> vizinhos = new TreeMap<>();
        for (int i = 0; i < vl_train.size(); i++) {
            double e = Utils.DistanceEuclidian(v, vl_train.get(i));
            vizinhos.put(e, vl_train.get(i));   
        }        
        while (vizinhos.size() > k ) {
            double d = vizinhos.lastKey();
            vizinhos.remove(d);
        }       
        Utils.print(vizinhos, v);
        checkAnswer(v, vl_train, vizinhos);
    }
    
    /**
     * Calcular a precisao e recall da classificaçao
     * @param v dado de teste
     * @param vl Lista de dados de treinamento
     * @param vizinhos os k vizinhos mais proximos de v
     */
     public void checkAnswer(Video v, VideosList vl, TreeMap<Double, Video> vizinhos) {
        int total_classe=0, truePositive=0, falseNegative=0;
        double precisao, recall;
  
        for (int i=0; i<vl.size(); i++){
            if (vl.get(i).getPrograma_titulo().equals(v.getPrograma_titulo())){
                total_classe++;
            }
        }
        
      for (Map.Entry<Double, Video> entry : vizinhos.entrySet()){
           if (v.getPrograma_titulo().equals(entry.getValue().getPrograma_titulo())){
                 truePositive++;
             }else{
                 falseNegative++;
             }
      }
         
        recall = (double) truePositive/total_classe;
        precisao = (double) truePositive/ truePositive+falseNegative;
        
        results(precisao, recall);
    }

     /**
      * Imprime o recall e a precisao.
      * @param precisao Valor da precisao da classificaçao
      * @param recall Valor do recall da classificaçao
      */
    public void results(double precisao, double recall){
        System.out.println("\nResultados");
        System.out.println("Precisão " + precisao);
        System.out.println("Recall " + recall);

    }
}
