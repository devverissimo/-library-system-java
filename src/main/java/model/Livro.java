package model;
import contract.Emprestavel;

public class Livro implements Emprestavel{
    private String isbn;
    private String titulo ;
    private String autor;
    private boolean disponivel;

    //construtor
    public Livro(String isbn, String titulo, String autor){
        this.isbn = isbn;
        this.autor = autor;
        this.titulo = titulo;
        this.disponivel = true;

    }
    //gettes
    public String getIsbn() {
        return isbn;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getAutor() {
        return autor;
    }

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
