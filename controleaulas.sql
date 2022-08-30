CREATE TABLE aula (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Dia date,
  HoraInicio datetime,
  HoraFim datetime,
  Aluno varchar(60) NOT NULL,
  Disciplina varchar(60) NOT NULL,
  Professor varchar(100) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (Aluno) REFERENCES aluno (Id),
  FOREIGN KEY (Disciplina) REFERENCES disciplina (Id),
  FOREIGN KEY (Professor) REFERENCES professor (Id)
);