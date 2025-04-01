package ZZKJunit;

import ZZKJunit.dominio.Pessoa;
import ZZKJunit.service.PessoaServico;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Test01 {
    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa(18);
        boolean adult = new PessoaServico().isAdult(pessoa);
        log.info("Is adult? '{}'", adult);
    }
}
