/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *Classe de métodos utilitários.
 * @author geovana
 */
public class Utils {
    
    /**
     * Calculo da distancia euclidiana.
     * @param v1 Objeto Video 1
     * @param v2 Objeto Video 2
     * @return a distancia euclidina entre os vetores de v1 e v2
     */
    public static double DistanceEuclidian(Video v1, Video v2) {
        double dis = 0.0;
        for (int i=0; i<v1.getVector().size(); i++){
            dis+= Math.pow((v1.getVector().get(i) - v2.getVector().get(i)),2);
        }
        return Math.sqrt(dis);
    }
    
    /**
     * Calculo do coeficiente de Jaccard
     * @param v1 Objeto Video 1
     * @param v2 Objeto Video 2
     * @return o coeficiente de Jaccard entre os vetores de v1 e v2
     */
    public static double coeficienteJaccard(Video v1, Video v2){
        int count_a=0, count_b=0;
        double dis;
        for (int i=0; i<v1.getVector().size(); i++){
            if (v1.getVector().get(i)!=0 && v2.getVector().get(i)!=0){
                count_a++;
            } if (!(v1.getVector().get(i)==0 && v2.getVector().get(i)==0)){  
                count_b++;
            }
        }
        dis = (double) count_a / (count_a+count_b);
        return dis;
    }
    
    /**
     * Imprime dados dos vizinhos mais próximos
     * @param vizinhos os vizinho mais próximos do algoritmo kNN
     * @param video video de entrada que foi dado como busca
     */
    public static void print(TreeMap<Double, Video> vizinhos, Video video) {
        System.out.println("\nVídeo da busca:");
        System.out.println("Programa: " + video.getPrograma_titulo()
                + "\n Categoria: " + video.getCategoria() + "\n Título vídeo: " 
                + video.getTitulo()+"\n Tags: " + video.getTags());
        System.out.println("\n-------------------");
        System.out.println("Vídeos semelhantes");
        vizinhos.entrySet().stream().forEach((entry) -> {
            Double score = entry.getKey();
            Video v = entry.getValue();
            System.out.println("-------------------\n");
            System.out.println("\n Score: " + score+ " \n Programa: " + v.getPrograma_titulo()
                    + "\n Categoria: " + v.getCategoria() + "\n Título vídeo: " + v.getTitulo()
                    + "\n Tags: " + v.getTags());
        });
    }
       
    /**
     * Pre-processamento das palavras, remoção de acentos, conversão em minuscula, verificacao
     * se é stop word ou não.
     * @param str linha de entrada
     * @return linha sem acento, em minusculo e vazia se for wtop word.
     */
    public static String preProcess(String str){
        str = removerAcentos(str);        
        str = minuscula(str);
        if (stopWord(str)){
            return "";
        }else{
            return str;
        }
    }
    
    /**
     * Código retirado da página:
     * http://pt.stackoverflow.com/questions/25924/regex-remo%C3%A7%C3%A3o-de-caracteres-especiais-c
     * @param str linha de entrada
     * @return linha sem acento
     */
    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^0-9a-zA-Z\\s]+", "");
    }
    
    /**
     * Converter todas as letras para minusculo.
     * @param str linha de entrada
     * @return  linha em minusculo
     */
    public static String minuscula(String str){
        return str.toLowerCase();
    }
    
    /**
     * Remove as stop words (Palavras com pouco significado semântico)        
     * Como descrito em:
     * https://www.kaggle.com/c/word2vec-nlp-tutorial/details/part-1-for-beginners-bag-of-words
     * @param str linha de entrada
     * @return true se a palavra for stop word e falso se não for
     */
    public static boolean stopWord(String str){
        return str.equals("a") || str.equals("e") || str.equals("do") || str.equals("da") ||
                str.equals("o") || str.equals("os") || str.equals("as") || str.equals("pra") ||
                str.equals("esta") || str.equals("em") || str.equals("que") || str.equals("para") || 
                str.equals("de") || str.equals("um") || str.equals("of") || str.equals("at") || 
                str.equals("no") || str.equals("ou") || str.equals("the") || str.equals("and") ||
                str.equals("na") || str.equals("pro") || str.equals("dos") || str.equals("is") ||
                str.equals("nas") || str.equals("on");
    }
    
    /**
     * Escreve as palavras de dentro do bow em disco.
     * @param bow Objeto bow que será escrito
     * @throws IOException 
     */
    public static void writeBOW(BOW bow) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./bow.txt"))) {
            for (int i=0; i<bow.size(); i++){
                writer.append(bow.get(i));
                writer.newLine();
                writer.flush();
            }
        }
    }
    
    /**
     * Ler as palavras do bow gravadas em disco
     * @return BOW preenchido
     * @throws IOException 
     */
    public static BOW readBOW() throws IOException {
        BOW bow = new BOW();
        if (Utils.fileExists("./bow.txt")){
            try (BufferedReader file = new BufferedReader(new FileReader("./bow.txt"))) {
                String str;
                while ((str = file.readLine()) != null) {
                    bow.add(str);
                }
            }
        }
        return bow;
    }
    
    /**
     * Escreve os histogramas em disco.
     * @param vl Lista de objetos Video contém os histogramas
     * @throws IOException 
     */
    public static void writeHistograms(VideosList vl) throws IOException{
        try (PrintWriter writer = new PrintWriter(new FileWriter("./histograms.csv"))) {
            vl.stream().forEach((v) -> {
                writer.print(v.getVector().toString().replace("[", "").replace("]", "")+"\n");
            });
            writer.close();
        }
    }
    
    /**
     * ler os histogramas em disco
     * @param vl Lista de objetos Video contém os histogramas q serão preenchidos
     * @return true se existir o arquivo e falso se não
     * @throws IOException 
     */
    public static boolean readHistograms(VideosList vl) throws IOException {
        if (Utils.fileExists("./histograms.csv")) {
            try (BufferedReader file = new BufferedReader(new FileReader("./histograms.csv"))) {
                String str;
                int i = 0;
                while ((str = file.readLine()) != null) {
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(str.split(",")));
                    ArrayList<Integer> list_int = new ArrayList<>();
                    for (int j = 0; j < list.size(); j++) {
                        list_int.add(Integer.parseInt(list.get(j).trim()));
                    }
                    vl.get(i).setVector(list_int);
                    i++;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Verifica se o arquivo existe
     * @param path caminho do arquivo
     * @return true se o arquivo existir e false se não existir
     */
    public static boolean fileExists(String path){
        File file = new File(path);
        return file.exists();
    }
    
    /**
     * Divisão balanceada dos dados em 80% para treinamento e 20% para teste, 
     * considerando o campo programa titulo como classe.
     * @param vl Lista de todos objetos Video
     * @param path caminho da escrita dos arquivos de treinamento e teste.
     * @throws IOException 
     */
      public static void divideData(VideosList vl, String path) throws IOException{
        HashMap<String, Integer> map = new HashMap<>();
        BufferedWriter writer_train = new BufferedWriter(new FileWriter(path.replace(".csv", "")+"_train.csv"));
        BufferedWriter writer_test = new BufferedWriter(new FileWriter(path.replace(".csv", "")+"_test.csv"));
          
        for (int i=0; i<vl.size();i++){
            if (map.containsKey(vl.get(i).getPrograma_titulo())){
                map.put(vl.get(i).getPrograma_titulo(), map.get(vl.get(i).getPrograma_titulo())+1);
            }else{
                map.put(vl.get(i).getPrograma_titulo(), 1);
            }             
        }
        
        map.entrySet().stream().forEach((entry) -> {
            int n = (int) ((int) entry.getValue()*0.2);
            entry.setValue(n);
        });
        
        for (int i=0; i<vl.size(); i++){
            if (map.get(vl.get(i).getPrograma_titulo())>=0){
                writer_test.append(vl.get(i).toString());
                writer_test.newLine();
                writer_test.flush();
                map.put(vl.get(i).getPrograma_titulo(), map.get(vl.get(i).getPrograma_titulo())-1);
            }else{
                writer_train.append(vl.get(i).toString());
                writer_train.newLine();
                writer_train.flush();
            }
        }
    }    
}

