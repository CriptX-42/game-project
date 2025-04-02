package ZZKJunit.service;

import ZZKJunit.dominio.Pessoa;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PessoaServicoTest {
    private Pessoa adult;
    private Pessoa notAdult;
    private PessoaServico pessoaServico;

    @BeforeEach
    public void setUp() {
        adult = new Pessoa(18);
        notAdult = new Pessoa(15);
        pessoaServico = new PessoaServico();
    }

    @Test
    void isAdult_WhenAgeIsLowerThan18() {
        Assert.assertFalse(pessoaServico.isAdult(notAdult));
    }


    @Test
    void isAdult_WhenAgeIsGreaterOrEqualsThan18() {
        assertTrue(pessoaServico.isAdult(adult));
    }

    @Test
    void isAdult_ShouldThrowException_WhenPersonIsNull() {
        assertThrows(IllegalArgumentException.class, () ->  pessoaServico.isAdult(null));
    }

    @Test
    void filterAndRemoveNotAdult() {
        Pessoa pessoa = new Pessoa(17);
        Pessoa pessoa2 = new Pessoa(18);
        Pessoa pessoa3 = new Pessoa(42);
        Pessoa pessoa5 = new Pessoa(12);
        List<Pessoa> pessoa1 = List.of(pessoa, pessoa2, pessoa3, pessoa5);
        assertEquals(2, pessoaServico.filterRemovingNotAdult(pessoa1).size());
    }


}
