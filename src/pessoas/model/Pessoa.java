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

    public static List<Pessoa> getAll() throws Exception {

        List<Pessoa> pessoas = new ArrayList<>();

        Connection c = new ConnectionFactory().getConnection();
        String query = "select * from pessoas";
        PreparedStatement stmt = c.prepareStatement(query);

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

            p.setId(id);

            pessoas.add(p);
        }

        rs.close();
        stmt.close();
        c.close();

        return pessoas;
    }

    public void adicionaPessoa() throws Exception {

        Connection c = new ConnectionFactory().getConnection();
        String query = "insert into pessoas (nome, cpf, telefone, email, cep, "
                + "logradouro, numEndereco, complemento, bairro, cidade, uf) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

        stmt.executeUpdate();
        
        stmt = c.prepareStatement("select last_insert_id() as id");
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) id.set(rs.getString("id"));
        
        rs.close();
        stmt.close();
        c.close();
    }
    
    public void editaPessoa() throws Exception {
        Connection c = new ConnectionFactory().getConnection();
        
        String query = "update pessoas set nome = ?, cpf = ?, telefone = ?, "
                + "email = ?, cep = ?, logradouro = ?, numEndereco = ?, "
                + "complemento = ?, bairro = ?, cidade = ?, uf = ? where id = ?";
        
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
        
        stmt.executeUpdate();
        
        stmt.close();
        c.close();
    }
    
    public void removePessoa() throws Exception {
        Connection c = new ConnectionFactory().getConnection();
        
        String query = "delete from pessoas where id = ?";
        PreparedStatement stmt = c.prepareStatement(query);
        
        stmt.setString(1, id.get());
        
        stmt.executeUpdate();
        
        stmt.close();
        c.close();
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if(getClass() != o.getClass()) return false;
        
        Pessoa p = (Pessoa) o;
        return Objects.equals(id.get(), p.id.get()) && 
                Objects.equals(nome.get(), p.nome.get()) &&
                Objects.equals(cpf.get(), p.cpf.get()) &&
                Objects.equals(telefone.get(), p.telefone.get()) &&
                Objects.equals(email.get(), p.email.get()) &&
                Objects.equals(cep.get(), p.cep.get()) &&
                Objects.equals(logradouro.get(), p.logradouro.get()) &&
                Objects.equals(numEndereco.get(), p.numEndereco.get()) &&
                Objects.equals(complemento.get(), p.complemento.get()) &&
                Objects.equals(bairro.get(), p.bairro.get()) &&
                Objects.equals(cidade.get(), p.cidade.get()) &&
                Objects.equals(uf.get(), p.uf.get());
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

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

}
