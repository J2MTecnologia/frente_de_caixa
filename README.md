swing-springjdbc-crud
=====================

O projeto é uma demonstração de aplicativo desktop desenvolvido com as tecnologias Swing e Spring JDBC.

A aplicação utiliza o HSQLDB (HyperSQL DataBase), um banco de dados relacional escrito em Java, adequado para projetos com propósitos de estudos.

Essa aplicação disponibiliza um CRUD, com funcionalidades identicas do projeto swing-jdbc-crud [https://github.com/yaw/swing-jdbc-crud].
Além de utilizar uma API alto nível (Spring JDBC) para resolver a integração com o Banco, essa aplicação define a arquitetura MVC (Model View Controller). 

Tecnologias utilizadas na implementação:
* Swing: utilizamos o framework Swing para construção das interfaces e componentes gráficos da aplicação (camada cliente);
* Spring JDBC: API alto nível do Spring Framework para integração e execução de comandos no banco de dados. Essa API reduz o volume de código JDBC e disponibiliza uma série de funcionalidades para mapear os objetos em entidades relacionais;
* Collection: reunimos uma relação de objeto em memória via coleções do Java;
* Thread: algumas ações (eventos) dos componentes da tela com o banco de dados são tratados em outra thread (SwingUtilities), de forma que o usuário tenha uma melhor experiência no uso da aplicação.

Para facilitar o uso de bibliotecas externas e a construção, o projeto utiliza o Maven.

Pré-requisitos
-------
* JDK - última versão do Kit de desenvolvimento Java;
* Maven;
* IDE de sua preferencia (recomendamos Eclipse ou NetBeans);

Saiba mais
-------
Visite a página do projeto:
http://www.yaw.com.br/open/projetos/swing-springjdbc-crud/