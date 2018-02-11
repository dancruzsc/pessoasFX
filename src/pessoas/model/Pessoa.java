package pessoas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pessoas.ConnectionFactory;

public class Pessoa {

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

    public Pessoa() {
    }
    
    

    public Pessoa(String nome, String cpf, String telefone, String email,
            String cep, String logradouro, String numEndereco,
            String complemento, String bairro, String cidade, String uf) {

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

    public static List<Pessoa> getAll() throws Exception {

        List<Pessoa> pessoas = new ArrayList<>();

        Connection c = new ConnectionFactory().getConnection();
        String query = "select * from pessoas";
        PreparedStatement stmt = c.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
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

            Pessoa p = new Pessoa(nome, cpf, telefone, email, cep, logradouro,
                    numEndereco, complemento, bairro, cidade, uf);

            pessoas.add(p);
        }

        rs.close();
        stmt.close();
        c.close();

        return pessoas;
    }
    
    public static void adicionaPessoa(Pessoa pessoa) throws Exception {
        
        Connection c = new ConnectionFactory().getConnection();
        String query = "insert into pessoas (nome, cpf, telefone, email, cep, "
                + "logradouro, numEndereco, complemento, bairro, cidade, uf) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement stmt = c.prepareStatement(query);
        stmt.setString(1, pessoa.getNome());
        stmt.setString(2, pessoa.getCpf());
        stmt.setString(3, pessoa.getTelefone());
        stmt.setString(4, pessoa.getEmail());
        stmt.setString(5, pessoa.getCep());
        stmt.setString(6, pessoa.getLogradouro());
        stmt.setString(7, pessoa.getNumEndereco());
        stmt.setString(8, pessoa.getComplemento());
        stmt.setString(9, pessoa.getBairro());
        stmt.setString(10, pessoa.getCidade());
        stmt.setString(11, pessoa.getUf());
        
        stmt.execute();
        stmt.close();
        c.close();
        
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getCpf() {
        return cpf.get();
    }

    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public String getTelefone() {
        return telefone.get();
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getCep() {
        return cep.get();
    }

    public void setCep(String cep) {
        this.cep.set(cep);
    }

    public StringProperty cepProperty() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro.get();
    }

    public void setLogradouro(String logradouro) {
        this.logradouro.set(logradouro);
    }

    public StringProperty logradouroProperty() {
        return logradouro;
    }

    public String getNumEndereco() {
        return numEndereco.get();
    }

    public void setNumEndereco(String numEndereco) {
        this.numEndereco.set(numEndereco);
    }

    public StringProperty numEnderecoProperty() {
        return numEndereco;
    }

    public String getComplemento() {
        return complemento.get();
    }

    public void setComplemento(String complemento) {
        this.complemento.set(complemento);
    }

    public StringProperty complementoProperty() {
        return complemento;
    }

    public String getBairro() {
        return bairro.get();
    }

    public void setBairro(String bairro) {
        this.bairro.set(bairro);
    }

    public StringProperty bairroProperty() {
        return bairro;
    }

    public String getCidade() {
        return cidade.get();
    }

    public void setCidade(String cidade) {
        this.cidade.set(cidade);
    }

    public StringProperty cidadeProperty() {
        return cidade;
    }

    public String getUf() {
        return uf.get();
    }

    public void setUf(String uf) {
        this.uf.set(uf);
    }

    public StringProperty ufProperty() {
        return uf;
    }

}
