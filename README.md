# pessoasFX
Aplicação JavaFX para gerenciamento de pessoas.

O javadoc pode ser consultado neste link.


## Considerações iniciais
Esta aplicação desktop utiliza a linguagem de programação Java, juntamente com a biblioteca interna JavaFX para renderização dos elementos GUI. A aplicação utiliza o banco de dados MySQL para manter a persistência dos dados informados durante uma sessão.

A aplicação gerencia uma lista de pessoas, permitindo a adição de novas pessoas e alteração ou exclusão de pessoas existentes. A aplicação também permite que pessoas sejam pesquisadas por seu nome, como também permite que os dados sejam salvos em um arquivo de texto (extensão .txt).

O sistema possui apenas duas janelas principais: 
* A janela que exibe a lista completa de usuários e os botões de adição, alteração, exclusão de pessoas, geração de arquivo de texto e um campo para pesquisa;
* A janela para adicionar pessoas, também utilizada na alteração de pessoas.

## Instruções de compilação e execução

Esta aplicação foi desenvolvida e depurada na IDE Netbeans 8.2 utilizando o JDK 8 update 162.

### Instruções iniciais
Você muito provavelmente precisará ajustar a aplicação para que esta utilize o seu SGBD. Estes dados devem ser alterados na classe ConnectionFactory.

### Netbeans
  - Com o projeto aberto e selecionado, vá em Executar > Construir Projeto, ou em Executar > Executar Projeto;
  - Caso tenha feito a construção do projeto, o arquivo .jar resultante encontra-se na pasta `dist/` do projeto.

- Eclipse: 
  - Adicionar passos para o Eclipse


## Decisões de design
Desde o começo a aplicação foi projetada a manter uma estrutura MVC ou semelhante. A utilização do JavaFX fez com que esta estrutura fosse integralmente mantida pois sua estrutura interna faz com que devam existir as entidades Model, Control e View.

### View
Existem duas formas de criar uma estrutura GUI no JavaFX: código Java, onde deve-se criar e posicionar cada elemento manualmente, ou a utilização de um arquivo XML modificado (chamado de FXML) juntamente com uma classe Controller responsável pelas ações de cada elemento. Optei pela última opção para poder ganhar tempo no desenvolvimento. 

Para criar os arquivos FXML utilizei uma aplicação chamada SceneBuilder cujo código-fonte pertence à Oracle. Não há a estrita necessidade de utilizar este programa durante o desenvolvimento, é totalmente possível criar e editar um arquivo FXML manualmente; o SceneBuilder é apenas um facilitador, disponibilizando um ambiente drag and drop semelhante ao editor de elementos Sqing do NetBeans, facilitando a criação das janelas do sistema.

### Control
As classes Control devem realizar uma espécie de bind com os arquivos FXML - cada arquivo de marcação possui um controlador, e um elemento manipulável da GUI (botões, campos de texto etc) devem ter uma representação no controlador através de um atributo da classe. Este aspecto parece reduzir a qualidade de manutenção do código. Por exemplo, para criar uma coluna na tabela de pessoas é necessário criar a representação da coluna na GUI e na classe Controller.

Uma atenção especial fica na janela de edição de pessoas, que contém tanto o validador do CPF quanto a conexão com a API pública de consulta do CEP. Efetuei o parse do JSON utilizando expressões regulares. Além disso todos os campos de texto desta janela contém "condicionais" limitando a quantidade de caracteres para respeitar o limite dos campos no banco de dados, e em alguns casos fazendo com que somente números sejam aceitos.

### Model
O sistema possui apenas uma classe Model (Pessoa), contendo representações internas de String utilizadas pelo JavaFX. A utilização de tais tipos facilitou muito o desenvolvimento; os atributos se tornavam em uma espécie de beans do JPA, por exemplo.

A classe também é responsável pelas operações CRUD com o banco de dados. Optei por manter estas operações no Model porque esta classe já manipula os próprios dados por natureza.

### Outros
O sistema possui uma classe Factory responsável pela conexão com o banco de dados. Nesta classe o usuário, senha e schema do banco devem ser alterados para que possa se integrar com o SGBD correto.