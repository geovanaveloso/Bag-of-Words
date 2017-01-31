
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author geovana
 */
public class Main {

    /**
     * Classe main
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        String id_video;
        int k, opcao;
        Video v;
        long start, stop;
        Knn knn = new Knn();
        BOW bow = new BOW();
        VideosList videoList_train = new VideosList();
        VideosList videoList_test = new VideosList();
        Scanner scanner = new Scanner(System.in);
        String path = args[0]+"videos.csv";
        String path_train = args[0]+"videos_train.csv";
        String path_test = args[0]+"videos_test.csv";
        
        if (!Utils.fileExists(path_train)){
            VideosList videoList = new VideosList();
            videoList.readFile(path);
            Utils.divideData(videoList, path);
        }
                
        bow = Utils.readBOW();
        videoList_train.readFile(path_train);

        if(bow.isEmpty()){      
            start = System.currentTimeMillis();
            bow.buildBOW(videoList_train);
            Utils.writeBOW(bow);
            stop = System.currentTimeMillis();
            System.out.println("Criacao vocabulario: "+ ((stop-start)/60));
        }
        
        if (!Utils.readHistograms(videoList_train)){
            start = System.currentTimeMillis();
            bow.buildVector(videoList_train);
            Utils.writeHistograms(videoList_train);
            stop = System.currentTimeMillis();
            System.out.println("Criacao histograma: "+ ((stop-start)/60));
        }
        
        videoList_test.readFile(path_test);
        System.out.println("Informe o id do vídeo");
        id_video = scanner.nextLine();
        System.out.println("Quantos vídeos semelhantes devem ser retornados?");
        k = scanner.nextInt();
        System.out.println("Utilizar:\n 1-Coeficiente de Jaccard \n 2-Distância Euclidiana");
        opcao = scanner.nextInt();
        v = videoList_test.searchVideo(id_video);
        bow.buildVector(v);
        
        if (v==null){
            System.out.println("Não foi possível encontrar a id desse vídeo");   
        }else{
            if (opcao==1){
                knn.runknnCoeficienteJaccard(videoList_train, v, k);
            }else{
                knn.runknnDistanceEuclidiana(videoList_train, v, k);
            }
        }
    }
}
