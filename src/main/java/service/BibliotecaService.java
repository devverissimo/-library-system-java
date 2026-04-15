package service;

import exception.LivroIndisponivelException;
import exception.LivroNaoEncontradoException;
import model.Livro;
import model.ReciboEmprestimo;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BibliotecaService {

    private Map<String, Livro> catalogo = new HashMap<>();
    private Map<String, List<String>> filaEspera = new HashMap<>();

    public void adicionarLivro(Livro livro) {
        catalogo.put(livro.getIsbn(), livro);
        filaEspera.putIfAbsent(livro.getIsbn(), new ArrayList<>());
    }

    public ReciboEmprestimo emprestarLivro(String isbn, String usuario) {
        // 1. Livro existe?
        Livro livro = catalogo.get(isbn);
        if (livro == null) {
            throw new LivroNaoEncontradoException("Livro com ISBN " + isbn + " não encontrado.");
        }

        // 2. Está disponível?
        if (!livro.isDisponivel()) {
            filaEspera.get(isbn).add(usuario); // coloca na fila
            throw new LivroIndisponivelException(
                    "Livro '" + livro.getTitulo() + "' indisponível. " +
                            usuario + " adicionado à fila de espera."
            );
        }

        // 3. Empresta
        livro.emprestar();
        return new ReciboEmprestimo(livro, usuario, LocalDate.now());
    }

    public void devolverLivro(String isbn) {
        Livro livro = catalogo.get(isbn);
        if (livro == null) {
            throw new LivroNaoEncontradoException("Livro com ISBN " + isbn + " não encontrado.");
        }

        livro.devolver();

        // Avisa quem tá na fila (só remove o primeiro)
        List<String> fila = filaEspera.get(isbn);
        if (!fila.isEmpty()) {
            String proximoUsuario = fila.remove(0);
            System.out.println("Aviso: livro '" + livro.getTitulo() +
                    "' disponível! Próximo da fila: " + proximoUsuario);
        }
    }

    public List<Livro> listarDisponiveis() {
        return catalogo.values().stream()
                .filter(Livro::isDisponivel)
                .collect(Collectors.toList());
    }

    public Optional<Livro> buscarPorTitulo(String titulo) {
        return catalogo.values().stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }
}