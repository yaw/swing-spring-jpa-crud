swing-spring-jpa-crud
=====================

O projeto é uma demonstração de aplicativo desktop desenvolvido com as tecnologias Swing, Spring Framework, JPA (Java Persistence API) e Hibernate.

A aplicação utiliza o HSQLDB (HyperSQL DataBase), um banco de dados relacional escrito em Java, adequado para projetos com propósitos de estudos.

teste

Essa aplicação disponibiliza um CRUD, com funcionalidades identicas do projeto swing-jpa-crud [https://github.com/yaw/swing-jpa-crud].
Além de utilizar uma tecnologia padrão para o mapeamento objeto relacional (ORM), essa aplicação define a arquitetura MVC (Model View Controller) com um modelo de objetos flexível. Dessa forma o Spring é responsável por gerenciar e integrar (IoC) boa parte dos componentes MVC, com funcionalidades extra para otimizar o uso da JPA.

Tecnologias utilizadas na implementação:
* Swing: utilizamos o framework Swing para construção das interfaces e componentes gráficos da aplicação (camada cliente);
* Spring Framework: utilizamos alguns componentes do Spring Framework como Core, Transactional, ORM, Data JPA e integração com JSR 250;
* JPA: API alto nível, padrão da tecnologia Java, para definir o mapeamento objeto relacional (ORM);
* Bean Validation: mecanismo padrão do Java para determinar regras de validação dos dados através de anotações;
* Hibernate: provedor JPA para mapeamento objeto relacional (ORM);
* Collection: reunimos uma relação de objeto em memória via coleções do Java;
* Thread: algumas ações (eventos) dos componentes da tela com o banco de dados são tratados em outra thread (SwingUtilities), de forma que o usuário tenha uma melhor experiência no uso da aplicação;

Para facilitar o uso de bibliotecas externas e a construção, o projeto utiliza o Maven.

Pré-requisitos
-------
* JDK - última versão do Kit de desenvolvimento Java;
* Maven;
* IDE de sua preferencia (recomendamos Eclipse ou NetBeans);

Saiba mais
-------
Visite a página do projeto:
http://www.yaw.com.br/open/projetos/swing-spring-jpa-crud/
