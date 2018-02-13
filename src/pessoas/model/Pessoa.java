package pessoas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pessoas.ConnectionFactory;

/**
 * Classe responsável pela representação da entidade Pessoa da aplicação.
 * @author Danilo Cruz
 */

public class Pessoa {
    
    private StringProperty id;
    private StringProperty nome;
    private StringProperty cpf;
    private StringProperty telefone;
    private StringProperty email;
    private StringProperty cep;
    private StringProperty logradouro;
    private StringProperty numEndereco;
    private StringProperty complemento;
    private StringProperty bairro;
    private StringProperty cidade;
    private StringProperty uf;

    /**
     * Construtor sem argumentos para executar inicialização dos campos Property.
     */
    public Pessoa() {
        id = new SimpleStringProperty();
        nome = new SimpleStringProperty();
        cpf = new SimpleStringProperty();
        telefone = new SimpleStringProperty();
        email = new SimpleStringProperty();
        cep = new SimpleStringProperty();
        logradouro = new SimpleStringProperty();
        numEndereco = new SimpleStringProperty();
        complemento = new SimpleStringProperty();
        bairro = new SimpleStringProperty();
        cidade = new SimpleStringProperty();
        uf = new SimpleStringProperty();
    }

    /**
     * Inicializador com argumentos para imediata atribuição dos mesmos ao novo objeto.
     * @param id ID da pessoa, utilizado no banco de dados
     * @param nome Nome da pessoa
     * @param cpf CPF da pessoa
     * @param telefone Telefone da pessoa
     * @param email Email da pessoa
     * @param cep CEP onde a pessoa reside
     * @param logradouro Logradouro onde a pessoa reside
     * @param numEndereco Número do local onde a pessoa reside
     * @param complemento Informações complementares do local onde a pessoa reside
     * @param bairro Bairro onde a pessoa reside
     * @param cidade Cidade onde a pessoa reside
     * @param uf Estado onde a pessoa reside
     */
    public Pessoa(String id, String nome, String cpf, String telefone, String email,
            String cep, String logradouro, String numEndereco,
            String complemento, String bairro, String cidade, String uf) {

        this.id = new SimpleStringProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cpf = new SimpleStringProperty(cpf);
        this.telefone = new SimpleStringProperty(telefone);
        this.email = new SimpleStringProperty(email);
        this.cep = new SimpleStringProperty(cep);
        this.logradouro = new SimpleStringProperty(logradouro);
        this.numEndereco = new SimpleStringProperty(numEndereco);
        this.complemento = new SimpleStringProperty(complemento);
        this.bairro = new SimpleStringProperty(bairro);
        this.cidade = new SimpleStringProperty(cidade);
        this.uf = new SimpleStringProperty(uf);
    }

    /**
     * Método responsável por consultar todas as entidades salvas no banco de dados.
     * @return {@link List} de objetos {@link Pessoa} com todas as pessoas gravadas no banco de dados
     * @throws Exception em caso de erro de conexão ou erro de consulta com o banco de dados
     */
    public static List<Pessoa> getAll() throws Exception {

        List<Pessoa> pessoas = new ArrayList<>();

        // Cria nova conexão e prepara a query de consulta
        Connection c = new ConnectionFactory().getConnection();
        String query = "select * from pessoas";
        PreparedStatement stmt = c.prepareStatement(query);

        // Transfere o resultado gerado para a lista de pessoas
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String id = rs.getString("id");
            String nome = rs.getString("nome");
            String cpf = rs.getString("cpf");
            String telefone = rs.getString("telefone");
            String email = rs.getString("email");
            String cep = rs.getString("cep");
            String logradouro = rs.getString("logradouro");
            String numEndereco = rs.getString("numEndereco");
            String complemento = rs.getString("complemento");
            String bairro = rs.getString("bairro");
            String cidade = rs.getString("cidade");
            String uf = rs.getString("uf");

            Pessoa p = new Pessoa(id, nome, cpf, telefone, email, cep, logradouro,
                    numEndereco, complemento, bairro, cidade, uf);
            
            pessoas.add(p);
        }

        // Fecha as conexões existentes
        rs.close();
        stmt.close();
        c.close();

        return pessoas;
    }

    /**
     * Método responsável por adicionar uma nova pessoa ao banco de dados.
     * @throws Exception em caso de erro de conexão ou erro de consulta do banco de dados
     */
    public void adicionaPessoa() throws Exception {

        // Cria nova conexão e prepara query de inserção
        Connection c = new ConnectionFactory().getConnection();
        String query = "insert into pessoas (nome, cpf, telefone, email, cep, "
                + "logradouro, numEndereco, complemento, bairro, cidade, uf) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Utiliza-se o PreparedStatement para evitar SQL injection
        PreparedStatement stmt = c.prepareStatement(query);
        stmt.setString(1, nome.get());
        stmt.setString(2, cpf.get());
        stmt.setString(3, telefone.get());
        stmt.setString(4, email.get());
        stmt.setString(5, cep.get());
        stmt.setString(6, logradouro.get());
        stmt.setString(7, numEndereco.get());
        stmt.setString(8, complemento.get());
        stmt.setString(9, bairro.get());
        stmt.setString(10, cidade.get());
        stmt.setString(11, uf.get());

        // Executa a query
        stmt.executeUpdate();
        
        /*
            Coleta o id da última tupla inserida na tabela e a salva no campo
            id da entidade, para poder exibir o valor ao usuário
        */
        stmt = c.prepareStatement("select last_insert_id() as id");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) id.set(rs.getString("id"));
        
        // Encerramento das conexões
        rs.close();
        stmt.close();
        c.close();
    }
    
    /**
     * Método responsável por alterar uma tupla já existente no banco de dados.
     * @throws Exception em caso de erro de conexão ou erro de consulta do banco de dados
     */
    public void editaPessoa() throws Exception {
        
        // Cria nova conexão e prepara query de alteração
        Connection c = new ConnectionFactory().getConnection();
        
        String query = "update pessoas set nome = ?, cpf = ?, telefone = ?, "
                + "email = ?, cep = ?, logradouro = ?, numEndereco = ?, "
                + "complemento = ?, bairro = ?, cidade = ?, uf = ? where id = ?";
        
        // Utilização de PreparedStatement para evitar SQL injection
        PreparedStatement stmt = c.prepareStatement(query);
        stmt.setString(1, nome.get());
        stmt.setString(2, cpf.get());
        stmt.setString(3, telefone.get());
        stmt.setString(4, email.get());
        stmt.setString(5, cep.get());
        stmt.setString(6, logradouro.get());
        stmt.setString(7, numEndereco.get());
        stmt.setString(8, complemento.get());
        stmt.setString(9, bairro.get());
        stmt.setString(10, cidade.get());
        stmt.setString(11, uf.get());
        stmt.setString(12, id.get());
        
        // Executa a query
        stmt.executeUpdate();
        
        // Encerra as conexões
        stmt.close();
        c.close();
    }
    
    /**
     * Método responsável em remover uma tupla já existente do banco de dados.
     * @throws Exception em caso de erro de conexão ou erro de consulta do banco de dados
     */
    public void removePessoa() throws Exception {
        
        // Cria nova conexão e prepara query de exclusão
        Connection c = new ConnectionFactory().getConnection();
        String query = "delete from pessoas where id = ?";
        
        // Utilização de PreparedStatement para evitar SQL injection
        PreparedStatement stmt = c.prepareStatement(query);
        stmt.setString(1, id.get());
        
        // Executa query
        stmt.executeUpdate();
        
        // Encerramento das conexões
        stmt.close();
        c.close();
    }
    
    /**
     * Sobrescrita do método toString para retornar uma linha contendo todos os atributos da entidade.
     * @return {@link String} contendo todos os atributos da entidade 
     */
    @Override
    public String toString() {
        String pessoa = "";
        
        pessoa += id.get() + " # ";
        pessoa += nome.get() + " # ";
        pessoa += cpf.get() + " # ";
        pessoa += telefone.get() + " # ";
        pessoa += email.get() + " # ";
        pessoa += cep.get() + " # ";
        pessoa += logradouro.get() + " # ";
        pessoa += numEndereco.get() + " # ";
        pessoa += complemento.get() + " # ";
        pessoa += bairro.get() + " # ";
        pessoa += cidade.get() + " # ";
        pessoa += uf.get();
        
        return pessoa;
    }
    
    /**
     * Método getter do atributo {@link Pessoa#id} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#id} da entidade
     */
    public String getId() {
        return id.get();
    }
    
    /**
     * Método setter do atributo {@link Pessoa#id}
     * @param id {@link String} contendo a informação a ser inserida em {@link Pessoa#id}
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Método getter do atributo {@link Pessoa#id}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#id} da entidade
     */
    public StringProperty idProperty() {
        return id;
    }
    
    /**
     * Método getter do atributo {@link Pessoa#nome} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#nome} da entidade
     */
    public String getNome() {
        return nome.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#nome} como {@link String}
     * @param nome {@link String} contendo a informação a ser inserida em {@link Pessoa#nome}
     */
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /**
     * Método getter do atributo {@link Pessoa#nome}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#nome} da entidade
     */
    public StringProperty nomeProperty() {
        return nome;
    }
    
    /**
     * Método getter do atributo {@link Pessoa#cpf} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#cpf} da entidade
     */
    public String getCpf() {
        return cpf.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#cpf} como {@link String}
     * @param cpf {@link String} contendo a informação a ser inserida em {@link Pessoa#cpf}
     */
    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }

    /**
     * Método getter do atributo {@link Pessoa#cpf}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#cpf} da entidade
     */
    public StringProperty cpfProperty() {
        return cpf;
    }

    /**
     * Método getter do atributo {@link Pessoa#telefone} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#telefone} da entidade
     */
    public String getTelefone() {
        return telefone.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#telefone} como {@link String}
     * @param telefone {@link String} contendo a informação a ser inserida em {@link Pessoa#telefone}
     */
    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    /**
     * Método getter do atributo {@link Pessoa#telefone}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#telefone} da entidade
     */
    public StringProperty telefoneProperty() {
        return telefone;
    }

    /**
     * Método getter do atributo {@link Pessoa#email} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#email} da entidade
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#email} como {@link String}
     * @param email {@link String} contendo a informação a ser inserida em {@link Pessoa#email}
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * Método getter do atributo {@link Pessoa#email}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#email} da entidade
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * Método getter do atributo {@link Pessoa#cep} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#cep} da entidade
     */
    public String getCep() {
        return cep.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#cep} como {@link String}
     * @param cep {@link String} contendo a informação a ser inserida em {@link Pessoa#cep}
     */
    public void setCep(String cep) {
        this.cep.set(cep);
    }

    /**
     * Método getter do atributo {@link Pessoa#cep}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#cep} da entidade
     */
    public StringProperty cepProperty() {
        return cep;
    }

    /**
     * Método getter do atributo {@link Pessoa#logradouro} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#logradouro} da entidade
     */
    public String getLogradouro() {
        return logradouro.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#logradouro} como {@link String}
     * @param logradouro {@link String} contendo a informação a ser inserida em {@link Pessoa#logradouro}
     */
    public void setLogradouro(String logradouro) {
        this.logradouro.set(logradouro);
    }

    /**
     * Método getter do atributo {@link Pessoa#logradouro}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#logradouro} da entidade
     */
    public StringProperty logradouroProperty() {
        return logradouro;
    }

    /**
     * Método getter do atributo {@link Pessoa#numEndereco} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#numEndereco} da entidade
     */
    public String getNumEndereco() {
        return numEndereco.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#numEndereco} como {@link String}
     * @param numEndereco {@link String} contendo a informação a ser inserida em {@link Pessoa#numEndereco}
     */
    public void setNumEndereco(String numEndereco) {
        this.numEndereco.set(numEndereco);
    }

    /**
     * Método getter do atributo {@link Pessoa#numEndereco}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#numEndereco} da entidade
     */
    public StringProperty numEnderecoProperty() {
        return numEndereco;
    }

    /**
     * Método getter do atributo {@link Pessoa#complemento} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#complemento} da entidade
     */
    public String getComplemento() {
        return complemento.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#complemento} como {@link String}
     * @param complemento {@link String} contendo a informação a ser inserida em {@link Pessoa#complemento}
     */
    public void setComplemento(String complemento) {
        this.complemento.set(complemento);
    }

    /**
     * Método getter do atributo {@link Pessoa#complemento}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#complemento} da entidade
     */
    public StringProperty complementoProperty() {
        return complemento;
    }

    /**
     * Método getter do atributo {@link Pessoa#bairro} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#bairro} da entidade
     */
    public String getBairro() {
        return bairro.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#bairro} como {@link String}
     * @param bairro {@link String} contendo a informação a ser inserida em {@link Pessoa#bairro}
     */
    public void setBairro(String bairro) {
        this.bairro.set(bairro);
    }

    /**
     * Método getter do atributo {@link Pessoa#bairro}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#bairro} da entidade
     */
    public StringProperty bairroProperty() {
        return bairro;
    }

    /**
     * Método getter do atributo {@link Pessoa#cidade} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#cidade} da entidade
     */
    public String getCidade() {
        return cidade.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#cidade} como {@link String}
     * @param cidade {@link String} contendo a informação a ser inserida em {@link Pessoa#cidade}
     */
    public void setCidade(String cidade) {
        this.cidade.set(cidade);
    }

    /**
     * Método getter do atributo {@link Pessoa#cidade}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#cidade} da entidade
     */
    public StringProperty cidadeProperty() {
        return cidade;
    }

    /**
     * Método getter do atributo {@link Pessoa#uf} como {@link String}
     * @return {@link String} contendo o campo {@link Pessoa#uf} da entidade
     */
    public String getUf() {
        return uf.get();
    }

    /**
     * Método setter do atributo {@link Pessoa#uf} como {@link String}
     * @param uf {@link String} contendo a informação a ser inserida em {@link Pessoa#uf}
     */
    public void setUf(String uf) {
        this.uf.set(uf);
    }

    /**
     * Método getter do atributo {@link Pessoa#uf}
     * @return {@link StringProperty} contendo o campo {@link Pessoa#uf} da entidade
     */
    public StringProperty ufProperty() {
        return uf;
    }
}
