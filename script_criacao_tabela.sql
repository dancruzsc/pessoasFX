create database if not exists pessoas_app;
use pessoas_app;

create table if not exists pessoas (
	id int not null auto_increment primary key,
	nome varchar(100) not null,
	cpf char(11) not null,
	telefone varchar(20),
	email varchar(100),
	cep char(8) not null,
	logradouro varchar(255) not null,
	numEndereco int unsigned not null,
	complemento varchar(255),
	bairro varchar(100) not null,
	cidade varchar(50) not null,
	uf char(2) not null
);