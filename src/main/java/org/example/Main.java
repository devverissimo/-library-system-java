import exception.LivroIndisponivelException;
import exception.LivroNaoEncontradoException;
import model.Livro;
import model.ReciboEmprestimo;
import service.BibliotecaService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    static BibliotecaService service = new BibliotecaService();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        popularCatalogo();

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            opcao = lerInt();
            switch (opcao) {
                case 1 -> listarDisponiveis();
                case 2 -> buscarPorTitulo();
                case 3 -> realizarEmprestimo();
                case 4 -> realizarDevolucao();
                case 5 -> adicionarLivro();
                case 0 -> System.out.println("\nAté mais!");
                default -> System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }



    private static void exibirMenu() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║     SISTEMA DE BIBLIOTECA    ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1. Listar livros disponíveis║");
        System.out.println("║  2. Buscar por título        ║");
        System.out.println("║  3. Emprestar livro          ║");
        System.out.println("║  4. Devolver livro           ║");
        System.out.println("║  5. Adicionar livro          ║");
        System.out.println("║  0. Sair                     ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.print("Escolha: ");
    }



    private static void listarDisponiveis() {
        List<Livro> disponiveis = service.listarDisponiveis();
        System.out.println("\n--- Livros disponíveis ---");
        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum livro disponível no momento.");
        } else {
            disponiveis.forEach(l ->
                    System.out.printf("  [%s] %s — %s%n", l.getIsbn(), l.getTitulo(), l.getAutor())
            );
        }
    }

    private static void buscarPorTitulo() {
        System.out.print("\nDigite o título: ");
        String titulo = scanner.nextLine();
        Optional<Livro> resultado = service.buscarPorTitulo(titulo);
        resultado.ifPresentOrElse(
                l -> System.out.printf("Encontrado: [%s] %s — %s | %s%n",
                        l.getIsbn(), l.getTitulo(), l.getAutor(),
                        l.isDisponivel() ? "Disponível" : "Emprestado"),
                () -> System.out.println("Nenhum livro encontrado com esse título.")
        );
    }

    private static void realizarEmprestimo() {
        System.out.print("\nISBN do livro: ");
        String isbn = scanner.nextLine();
        System.out.print("Nome do usuário: ");
        String usuario = scanner.nextLine();
        try {
            ReciboEmprestimo recibo = service.emprestarLivro(isbn, usuario);
            System.out.println("\nEmpréstimo realizado com sucesso!");
            System.out.println("  Livro   : " + recibo.livro().getTitulo());
            System.out.println("  Usuário : " + recibo.usuario());
            System.out.println("  Data    : " + recibo.dataEmprestimo());
        } catch (LivroIndisponivelException | LivroNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void realizarDevolucao() {
        System.out.print("\nISBN do livro a devolver: ");
        String isbn = scanner.nextLine();
        try {
            service.devolverLivro(isbn);
            System.out.println("Livro devolvido com sucesso!");
        } catch (LivroNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void adicionarLivro() {
        System.out.print("\nISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        service.adicionarLivro(new Livro(isbn, titulo, autor));
        System.out.println("Livro '" + titulo + "' adicionado ao catálogo!");
    }

    // catalogo inicial

    private static void popularCatalogo() {
        service.adicionarLivro(new Livro("978-0-13-110362-7", "The C Programming Language", "Kernighan & Ritchie"));
        service.adicionarLivro(new Livro("978-0-13-468599-1", "Clean Code", "Robert C. Martin"));
        service.adicionarLivro(new Livro("978-0-20-163361-5", "The Pragmatic Programmer", "Hunt & Thomas"));
        service.adicionarLivro(new Livro("978-0-13-235088-4", "Effective Java", "Joshua Bloch"));
        service.adicionarLivro(new Livro("978-0-59-651798-1", "Head First Design Patterns", "Freeman & Robson"));
        service.adicionarLivro(new Livro("978-0-13-346943-0", "Java: The Complete Reference", "Herbert Schildt"));
    }


    private static int lerInt() {
        try {
            int valor = Integer.parseInt(scanner.nextLine());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}