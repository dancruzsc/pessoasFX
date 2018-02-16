package pessoas.model;

import java.sql.SQLException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de testes unitários para a classe {@link Pessoa}.
 *
 * @author Danilo Cruz
 */
public class PessoaTest {

    /**
     * Teste para o método {@link Pessoa#getAll()} em caso de sucesso.
     */
    @Test
    public void testGetAll() throws Exception {
        List<Pessoa> result = Pessoa.getAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Teste para o método {@link Pessoa#adicionaPessoa() } em caso de sucesso.
     * Os dados adicionados respeitam a estrutura definida no DDL.
     */
    @Test
    public void testAdicionaPessoa() throws Exception {
        Pessoa instance = new Pessoa("000", "nome", "cpf",
                "telefone", "email", "00000000", "logradouro", "000",
                "complemento", "bairro", "cidade", "UF");
        instance.adicionaPessoa();
    }

    /**
     * Teste para o método {@link Pessoa#adicionaPessoa() } em caso de erro.
     * Alguns dados adicionados não respeitam a estrutura definida no DDL.
     */
    @Test(expected = SQLException.class)
    public void testAdicionaPessoaErro() throws Exception {
        Pessoa instance = new Pessoa("id", "nome", "cpf",
                "telefone", "email", "cep", "logradouro", "numero",
                "complemento", "bairro", "cidade", "estado");
        instance.adicionaPessoa();
    }

    /**
     * Teste para o método {@link Pessoa#editaPessoa()} em caso de sucesso.
     */
    @Test
    public void testEditaPessoa() throws Exception {
        Pessoa instance = new Pessoa();
        instance.setId("11");
        instance.setBairro("novo bairro");
        instance.setNumEndereco("15");
        instance.editaPessoa();
    }

    /**
     * Teste para o método {@link Pessoa#editaPessoa()} em caso de erro onde não
     * se respeita a estrutura definida pelo DDL.
     */
    @Test(expected = SQLException.class)
    public void testEditaPessoaErro() throws Exception {
        Pessoa instance = new Pessoa();
        instance.setId("12");
        instance.setNumEndereco("teste");
        instance.editaPessoa();
    }

    /**
     * Teste para o método {@link Pessoa#removePessoa()} em caso de sucesso.
     */
    @Test
    public void testRemovePessoa() throws Exception {
        Pessoa instance = new Pessoa();
        instance.setId("11");
        instance.removePessoa();
    }
}
