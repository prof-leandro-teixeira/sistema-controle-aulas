CREATE TABLE disciplina (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Nome varchar(60) DEFAULT NULL,
  Area varchar(60) DEFAULT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE aluno (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Nome varchar(60) NOT NULL,
  Responsavel varchar(60) NOT NULL,
  Email varchar(100) NOT NULL,
  Telefone int(11) NOT NULL,
  Endereco varchar(60) NOT NULL,
  Email varchar(60) NOT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE professor (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Nome varchar(60) NOT NULL,
  Disciplina varchar(60) NOT NULL,
  Email varchar(100) NOT NULL,
  Telefone int(11) NOT NULL,
  Endereco varchar(60) NOT NULL,
  Email varchar(60) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (DisciplinaNome) REFERENCES disciplina (nome)
);


INSERT INTO disciplina (Nome, Area) VALUES 

 ("Biologia","Ciências Exatas e da Natureza"),
 ("Física","Ciências Exatas e da Natureza"),
 ("Geografia","Ciências Humanas"),
 ("História","Ciências Humanas"),
 ("Matemática","Ciências Exatas e da Natureza"),
 ("Português","Linguagens"),
 ("Química","Ciências Exatas e da Natureza");
  