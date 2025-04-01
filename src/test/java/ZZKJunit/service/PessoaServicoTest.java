package ZZKJunit.service;

import ZZKJunit.dominio.Pessoa;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaServicoTest {

    @Test
    void isAdult_WhenAgeIsLowerThan18() {
        Pessoa pessoa = new Pessoa(15);
        PessoaServico pessoaServico = new PessoaServico();
        Assert.assertFalse(pessoaServico.isAdult(pessoa));
    }

}