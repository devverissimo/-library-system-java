import exception.LivroIndisponivelException;
import exception.LivroNaoEncontradoException;
import model.Livro;
import model.ReciboEmprestimo;
import service.BibliotecaService;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        BibliotecaService service = new BibliotecaService();

        // --- Cadastro de livros ---
        System.out.println("=== Cadastrando livros ===");
        service.adicionarLivro(new Livro("978-0-13-110362-7", "The C Programming Language", "Kernighan & Ritchie"));
        service.adicionarLivro(new Livro("978-0-13-468599-1", "Clean Code", "Robert C. Martin"));
        service.adicionarLivro(new Livro("978-0-20-163361-5", "The Pragmatic Programmer", "Hunt & Thomas"));
        System.out.println("3 livros cadastrados.\n");

        // --- Listar disponíveis ---
        System.out.println("=== Livros disponíveis ===");
        listarDisponiveis(service);

        // --- Busca por título ---
        System.out.println("\n=== Busca por título: 'Clean Code' ===");
        Optional<Livro> encontrado = service.buscarPorTitulo("Clean Code");
        encontrado.ifPresentOrElse(
                l -> System.out.println("Encontrado: " + l.getTitulo() + " — " + l.getAutor()),
                () -> System.out.println("Livro não encontrado.")
        );

        // --- Empréstimo normal ---
        System.out.println("\n=== Empréstimo: Maria pega Clean Code ===");
        emprestar(service, "978-0-13-468599-1", "Maria");

        // --- Listar depois do empréstimo ---
        System.out.println("\n=== Livros disponíveis após empréstimo ===");
        listarDisponiveis(service);

        // --- Tentativa de pegar livro já emprestado ---
        System.out.println("\n=== João tenta pegar Clean Code (indisponível) ===");
        emprestar(service, "978-0-13-468599-1", "João");

        // --- ISBN inexistente ---
        System.out.println("\n=== Tentativa com ISBN inválido ===");
        emprestar(service, "000-0-00-000000-0", "Ana");

        // --- Devolução ---
        System.out.println("\n=== Maria devolve Clean Code ===");
        devolver(service, "978-0-13-468599-1");

        // --- Listar após devolução ---
        System.out.println("\n=== Livros disponíveis após devolução ===");
        listarDisponiveis(service);
    }

    // --- Métodos auxiliares para não poluir o main ---

    private static void emprestar(BibliotecaService service, String isbn, String usuario) {
        try {
            ReciboEmprestimo recibo = service.emprestarLivro(isbn, usuario);
            System.out.println("Empréstimo realizado!");
            System.out.println("  Livro   : " + recibo.livro().getTitulo());
            System.out.println("  Usuário : " + recibo.usuario());
            System.out.println("  Data    : " + recibo.dataEmprestimo());
        } catch (LivroIndisponivelException | LivroNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void devolver(BibliotecaService service, String isbn) {
        try {
            service.devolverLivro(isbn);
            System.out.println("Livro devolvido com sucesso.");
        } catch (LivroNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarDisponiveis(BibliotecaService service) {
        List<Livro> disponiveis = service.listarDisponiveis();
        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum livro disponível no momento.");
        } else {
            disponiveis.forEach(l ->
                    System.out.println("  [" + l.getIsbn() + "] " + l.getTitulo() + " — " + l.getAutor())
            );
        }
    }
}