package model;

import java.time.LocalDate;

public record ReciboEmprestimo(
        Livro livro,
                     String usuario,
                     LocalDate dataEmprestimo) {
}
