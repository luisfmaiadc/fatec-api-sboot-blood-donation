<h1 align="center">Sistema de Doação de Sangue - API REST SpringBoot</h1>

<p align="center">
 <a href="#started">Começando</a> • 
  <a href="#cloning">Clonando</a> •
 <a href="#creating">Banco de Dados</a> •
 <a href="#routes">API Endpoints</a> •
 <a href="#colab">Colaboradores</a>
</p>

<p align="center" style="margin-bottom: 20;">
    <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
    <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring" />
    <img src="https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
    <img src="https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white" alt="Flyway" />
    <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white" alt="Hibernate" />
    <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="Apache Maven" />
    <img src="https://github.com/user-attachments/assets/24db6b41-83df-4384-8674-084e64dbe3cb" width="65">
</p>

<p align="justify">Este projeto é uma API REST desenvolvida com Spring Boot 3.4.0 e Java 21 para gerenciar um sistema de doação de sangue. Oferece funcionalidades como cadastro de pacientes, doadores, doações e transfusões, com integração para validação de compatibilidade sanguínea. Os dados são armazenados em MySQL, com migrações gerenciadas pelo Flyway, e a documentação dos endpoints é gerada pelo SpringDoc. Desenvolvido como atividade acadêmica na Fatec Mauá.</p>
<h2 id="started">🚀 Começando</h2>

Antes de começar, verifique se você possui as seguintes ferramentas instaladas em sua máquina:

- [Java](https://www.oracle.com/java/technologies/downloads/#java22)
- [MySQL](https://dev.mysql.com/downloads/installer/)

<h2 id="cloning">👾 Clonando</h2>

Como clonar o projeto:
```bash
git clone https://github.com/luisfmaiadc/fatec-api-sboot-blood-donation.git
```

<h2 id="creating">💾 Criando Banco de Dados</h2>
<p style="margin-bottom: 20;">Para que o projeto funcione corretamente, é necessário criar um banco de dados MySQL com o nome <i>dbDoacaoSangue</i> a partir do comando abaixo:</p>


```SQL
CREATE DATABASE dbDoacaoSangue;
```

<p style="margin-top: 20;">Não é necessário se preocupar com a criação das tabelas manualmente, pois estou utilizando o Flyway para gerenciar as migrações do banco de dados. Assim, ao rodar o projeto, as tabelas serão criadas automaticamente.</p>

<h3>Tabela paciente</h3>
<p style="margin-bottom: 20;">
  Representa os pacientes cadastrados no sistema. Cada paciente possui informações pessoais como nome, sobrenome, gênero, data de nascimento, tipo sanguíneo, email e telefone. Esta tabela é fundamental para gerenciar as transfusões realizadas.
</p>

- <b>id:</b> Identificador único do paciente.
- <b>nome e sobrenome:</b> Nome completo do paciente.
- <b>genero:</b> Gênero do paciente (ex.: M, F).
- <b>data_nascimento:</b> Data de nascimento do paciente.
- <b>tipo_sanguineo:</b> Tipo sanguíneo (ex.: O+, A-).
- <b>email e telefone:</b> Informações de contato.

<h3>Tabela doador</h3>
<p style="margin-bottom: 20;">
  Armazena dados dos doadores de sangue cadastrados. Além das informações pessoais, registra a última doação e um indicador de status ativo ou inativo.
</p>

- <b>id:</b> Identificador único do doador.
- <b>nome e sobrenome:</b> Nome completo do doador.
- <b>genero:</b> Gênero do doador.
- <b>data_nascimento:</b> Data de nascimento.
- <b>tipo_sanguineo:</b> Tipo sanguíneo.
- <b>ultima_doacao:</b> Data da última doação realizada.
- <b>ativo:</b> Status do doador (1 para ativo, 0 para inativo).
- <b>email e telefone:</b> Informações de contato.

<h3>Tabela enfermeiro</h3>
<p style="margin-bottom: 20;">
  Contém informações dos enfermeiros responsáveis pelas doações e transfusões.
</p>

- <b>id:</b> Identificador único do enfermeiro.
- <b>nome e sobrenome:</b> Nome completo do enfermeiro.
- <b>genero:</b> Gênero do enfermeiro.
- <b>email e telefone:</b> Informações de contato.

<h3>Tabela doacao</h3>
<p style="margin-bottom: 20;">
  Registra as doações realizadas, vinculando um doador a um enfermeiro e registrando a data da doação.
</p>

- <b>id:</b> Identificador único da doação.
- <b>id_doador:</b> Referência ao doador responsável pela doação.
- <b>id_enfermeiro:</b> Referência ao enfermeiro que realizou o procedimento.
- <b>data_doacao:</b> Data e hora da doação.

<i>Chaves estrangeiras:</i>

- <b>id_doador:</b> Relacionado à tabela doador.
- <b>id_enfermeiro:</b> Relacionado à tabela enfermeiro.

<h3>Tabela transfusao</h3>
<p style="margin-bottom: 20;">
  Gerencia os registros de transfusões realizadas, associando um paciente, uma doação e um enfermeiro responsável.
</p>

- <b>id:</b> Identificador único da transfusão.
- <b>id_doacao:</b> Referência à doação utilizada na transfusão.
- <b>id_paciente:</b> Referência ao paciente que recebeu a transfusão.
- <b>id_enfermeiro:</b> Referência ao enfermeiro responsável pela transfusão.
- <b>data_transfusao:</b> Data e hora da transfusão.

<i>Chaves estrangeiras:</i>

- <b>id_doacao:</b> Relacionado à tabela doacao.
- <b>id_paciente:</b> Relacionado à tabela paciente.
- <b>id_enfermeiro:</b> Relacionado à tabela enfermeiro.

Essa estrutura garante a rastreabilidade das doações e transfusões, facilitando a gestão do sistema e a validação de compatibilidade sanguínea.

<h2 id="routes">📍 API Endpoints</h2>

Ao iniciar o projeto, a biblioteca SpringDoc, integrada à aplicação, facilita a visualização e a interação com todos os endpoints disponíveis. Ela exibe as informações detalhadas sobre cada endpoint, incluindo os formatos esperados para as requests e responses, permitindo uma experiência de uso prática e intuitiva para o desenvolvedor.

Para acessar a documentação interativa, utilize os seguintes links:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html#/): interface gráfica que permite explorar e testar os endpoints.
- [API Docs JSON](http://localhost:8080/v3/api-docs): documentação em formato JSON para integração e consulta.

<b>Obs.</b> A aplicação está configurada para rodar na porta padrão 8080. Caso essa porta já esteja em uso ou você precise alterá-la, atualize o arquivo application.properties.

<h2 id="colab">🤝 Colaboradores</h2>
<p style="margin-bottom: 20;">Este projeto foi desenvolvido com o objetivo de aprimorar minhas habilidades em Spring Boot, especialmente no trabalho com tabelas de relacionamentos complexos. Além disso, contou com a colaboração de Miguel Ferreira, colega de classe na Fatec Mauá, que participou da modelagem do banco de dados.</p>
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/luisfmaiadc">
        <img src="https://github.com/user-attachments/assets/b38280aa-a62a-4c33-bc98-8cae0e67fd75" width="100px;" alt="Luis Felipe Maia da Costa Profile Picture"/><br>
        <sub>
          <b>Luis Felipe Maia da Costa</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/ferreiraxzz">
        <img src="https://github.com/user-attachments/assets/515d5688-eaa1-4127-af63-c8df66e00126" width="100px;" alt="Miguel Ferreira Profile Picture"/><br>
        <sub>
          <b>Miguel Ferreira</b>
        </sub>
      </a>
    </td>   
  </tr>
</table>
