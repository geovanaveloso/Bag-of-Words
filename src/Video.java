

import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Objeto Video.
 * @author geovana
 */
public class Video {
    private String video_id;
    private String programa_id;
    private String programa_titulo;
    private String programa_descricao;
    private String categoria;
    private String titulo;
    private String descricao;
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Integer> vector = new ArrayList<>();

    public Video(String video_id, String programa_id, String programa_titulo, String programa_descricao,
            String categoria, String titulo, String descricao, ArrayList<String> tags) {
        this.video_id = video_id;
        this.programa_id = programa_id;
        this.programa_titulo = programa_titulo;
        this.programa_descricao = programa_descricao;
        this.categoria = categoria;
        this.titulo = titulo;
        this.descricao = descricao;
        this.tags = tags;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
    
    public String getPrograma_id() {
        return programa_id;
    }

    public void setPrograma_id(String programa_id) {
        this.programa_id = programa_id;
    }

    public String getPrograma_titulo() {
        return programa_titulo;
    }

    public void setPrograma_titulo(String programa_titulo) {
        this.programa_titulo = programa_titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getPrograma_descricao() {
        return programa_descricao;
    }

    public void setPrograma_descricao(String programa_descricao) {
        this.programa_descricao = programa_descricao;
    }
    
    public int getSize(){
        return this.getTags().size();
    }

    public ArrayList<Integer> getVector() {
        return vector;
    }

    public void setVector(ArrayList<Integer> vector) {
        this.vector = vector;
    }

    /**
     * Separa as frases em palavras
     * @return ArrayList com as palavras no objeto Video
    */
    public ArrayList<String> tonkenizer(){
        ArrayList<String> all = new ArrayList<>();        
        all.addAll(Arrays.asList(this.programa_descricao.split(" ")));
        all.addAll(Arrays.asList(this.categoria.split(" ")));
        all.addAll(Arrays.asList(this.titulo.split(" ")));
        all.addAll(Arrays.asList(this.descricao.split(" ")));
        for (int i=0; i<tags.size(); i++){
            all.addAll(Arrays.asList(this.tags.get(i).split(" ")));
        }
        return all;
    } 
    
    /**
     * Retorna os dados presentes no objeto Video
     * @return dados do objeto video
     */
    
    @Override
    public String toString() {
        return this.video_id + "," + this.programa_id+ "," + this.programa_titulo+","+
                this.programa_descricao+","+this.categoria+","+this.titulo+","+","+this.descricao+
                ","+this.tags.toString().replace("[", "").replace("]", "").replace(",", ";");
    }
    
    
}
