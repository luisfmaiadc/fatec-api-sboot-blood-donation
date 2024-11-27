CREATE TABLE paciente (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(75) NOT NULL,
  sobrenome VARCHAR(75) NOT NULL,
  genero VARCHAR(1) NOT NULL,
  data_nascimento DATE NOT NULL,
  tipo_sanguineo VARCHAR(3) NOT NULL,
  email VARCHAR(100) NOT NULL,
  telefone VARCHAR(11) NOT NULL,
  PRIMARY KEY (id)
  );

CREATE TABLE doador (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(75) NOT NULL,
  sobrenome VARCHAR(75) NOT NULL,
  genero VARCHAR(1) NOT NULL,
  data_nascimento DATE NOT NULL,
  tipo_sanguineo VARCHAR(3) NOT NULL,
  ultima_doacao DATE,
  email VARCHAR(100) NOT NULL,
  telefone VARCHAR(11) NOT NULL,
  PRIMARY KEY (id)
  );

CREATE TABLE enfermeiro (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(75) NOT NULL,
  sobrenome VARCHAR(75) NOT NULL,
  genero VARCHAR(1) NOT NULL,
  email VARCHAR(100) NOT NULL,
  telefone VARCHAR(11) NOT NULL,
  PRIMARY KEY (id)
  );

CREATE TABLE doacao (
  id INT NOT NULL AUTO_INCREMENT,
  id_doador INT NOT NULL,
  id_enfermeiro INT NOT NULL,
  data_doacao TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_doador) REFERENCES doador(id),
  FOREIGN KEY (id_enfermeiro) REFERENCES enfermeiro(id)
  );

CREATE TABLE transfusao (
  id INT NOT NULL AUTO_INCREMENT,
  id_doacao INT NOT NULL,
  id_paciente INT NOT NULL,
  id_enfermeiro INT NOT NULL,
  data_transfusao TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_doacao) REFERENCES doacao(id),
  FOREIGN KEY (id_paciente) REFERENCES paciente(id),
  FOREIGN KEY (id_enfermeiro) REFERENCES enfermeiro(id)
  );