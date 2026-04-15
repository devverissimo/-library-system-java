package service;
import model.Livro;
import model.ReciboEmprestimo;

import java.util.*;

public class BibliotecaService {

        private Map<String, Livro> catalogo = new HashMap<>();
        private Map<String, List<String>> filaEspera = new HashMap<>();

        public void adicionarLivro(Livro livro) {
            catalogo.put(livro.getIsbn(), livro);
            filaEspera.putIfAbsent(livro.getIsbn(), new ArrayList<>());
        }

        public ReciboEmprestimo emprestarLivro(String isbn, String usuario) {
            return null;
        }

        public void devolverLivro(String isbn) {
        }

        public List<Livro> listarDisponiveis() {
            return null;
        }

        public Optional<Livro> buscarPorTitulo(String titulo) {
            return Optional.empty();
        }
    }
