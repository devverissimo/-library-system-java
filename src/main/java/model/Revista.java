package model;

import contract.Emprestavel;

public class Revista implements Emprestavel {
    private String titulo;
    private String editora;
    private int edicao;
    private boolean disponivel;

    //construtor
    public Revista(String titulo, String editora, int edicao){
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.disponivel = true;
    }

    public String getTitulo() {
        return titulo;
    }
    public int getEdicao() {
        return edicao;
    }
    public String getEditora() {
        return editora;
    }

    @Override
    public boolean isDisponivel() {
        return disponivel;
    }
    @Override
    public void emprestar(){
        this.disponivel = false;
    }
    @Override
    public void devolver(){
        this.disponivel = true;
    }

}
