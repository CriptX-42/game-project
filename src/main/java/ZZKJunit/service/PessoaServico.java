package ZZKJunit.service;

import ZZKJunit.dominio.Pessoa;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PessoaServico {
    public boolean isAdult(Pessoa pessoa) {
//        Objects.requireNonNull(pessoa, "Não pode ser null");
        if(pessoa == null) {
            throw new IllegalArgumentException();
        }
        return pessoa.getAge() >= 18;
    }

    public List<Pessoa> filterRemovingNotAdult(List<Pessoa> personList) {
        return personList.stream().filter(this::isAdult).collect(Collectors.toList());
    }
}
