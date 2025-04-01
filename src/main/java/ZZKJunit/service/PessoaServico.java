package ZZKJunit.service;

import ZZKJunit.dominio.Pessoa;

import java.util.Objects;

public class PessoaServico {
    public boolean isAdult(Pessoa pessoa) {
        Objects.requireNonNull(pessoa, "Não pode ser null");
        return pessoa.getAge() >= 18;
    }
}
