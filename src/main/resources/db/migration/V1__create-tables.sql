CREATE TABLE TbPaciente (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(75) NOT NULL,
  sobrenome VARCHAR(75) NOT NULL,
  genero VARCHAR(1) NOT NULL,
  dataNascimento DATE NOT NULL,
  tipoSanguineo VARCHAR(3) NOT NULL,
  email VARCHAR(100) NOT NULL,
  telefone VARCHAR(11) NOT NULL,
  PRIMARY KEY (id)
  );

CREATE TABLE TbDoador (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(75) NOT NULL,
  sobrenome VARCHAR(75) NOT NULL,
  genero VARCHAR(1) NOT NULL,
  dataNascimento DATE NOT NULL,
  tipoSanguineo VARCHAR(3) NOT NULL,
  ultimaDoacao DATE,
  ativo TINYINT NOT NULL,
  email VARCHAR(100) NOT NULL,
  telefone VARCHAR(11) NOT NULL,
  PRIMARY KEY (id)
  );

CREATE TABLE TbEnfermeiro (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(75) NOT NULL,
  sobrenome VARCHAR(75) NOT NULL,
  genero VARCHAR(1) NOT NULL,
  email VARCHAR(100) NOT NULL,
  telefone VARCHAR(11) NOT NULL,
  PRIMARY KEY (id)
  );

CREATE TABLE TbDoacao (
  id INT NOT NULL AUTO_INCREMENT,
  idDoador INT NOT NULL,
  idEnfermeiro INT NOT NULL,
  dataDoacao TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (idDoador) REFERENCES TbDoador(id),
  FOREIGN KEY (idEnfermeiro) REFERENCES TbEnfermeiro(id)
  );

CREATE TABLE TbTransfusao (
  id INT NOT NULL AUTO_INCREMENT,
  idDoacao INT NOT NULL,
  idPaciente INT NOT NULL,
  idEnfermeiro INT NOT NULL,
  dataTransfusao TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (idDoacao) REFERENCES TbDoacao(id),
  FOREIGN KEY (idPaciente) REFERENCES TbPaciente(id),
  FOREIGN KEY (idEnfermeiro) REFERENCES TbEnfermeiro(id)
  );