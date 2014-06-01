ProjetoX
========
Projeto construído a partir da proposta de um desafio.

Requisitos para compilação
-------------------------------------------
 * [Maven](http://maven.apache.org/download.cgi)
 * [Java EE SDK 1.6 ou superior](http://www.oracle.com/technetwork/java/javaee/downloads/java-ee-sdk-7-downloads-1956236.html)

Instruções de compilação
------------------------------------

Após a devida instalação dos requisitos para compilação, navegar até a pasta onde está localizado o projeto e proceder com o seguinte comando:

* mvn package

Logo após, será criado o arquivo projetoX-1.0.war no diretório de nome target da pasta do projeto. Este deve ser usado para deploy no servidor Tomcat.

Requisitos para execução
-------------------------------------
* [Apache Tomcat 7.0](http://tomcat.apache.org/download-70.cgi)
* [Banco de dados MySQL](http://dev.mysql.com/downloads/mysql/5.6.html)

Instruções para execução
-------------------------------------
Por padrão, a aplicação procurará por uma instancia do MySQL Server localmente através da porta 3306 com um banco de dados vazio de nome projetox_db existente. Tais configurações podem ser alteradas após um deploy inicial com a modificação do arquivo META-INF/persintence.xml e reinicialização do servidor Tomcat.

Após configuração do MySQL Server para uso da aplicação, o arquivo projetoX-1.0.war deve ser copiado para pasta webapps dentro da pasta de instalação do Apache Tomcat 7.0. Em seguida deve-se inicializar o servidor.

Informações sobre o projeto
====================

Geração de logs
-----------------------
A cobertura de logs abrage as operações relativas ao nível INFO e WARN padrão. Sendo assim, são logadas ações de nível INFO da api Hibernate e requisições aceitas pela aplicação juntamente às ações em que resultam. Exemplo:

2014-06-01 11:47:03 WebServices [INFO] GET /person params: limitQtd=0<br>
2014-06-01 13:35:53 WebServices [INFO] POST /person params: facebookId=100007710667474<br>
2014-06-01 13:35:53 UserRepository [INFO] Inserindo usuario na base de dados com facebookId = 100007710667474<br>
2014-06-01 13:36:12 WebServices [INFO] DELETE /person params: facebookId=100007710667474<br>
2014-06-01 13:36:12 UserRepository [INFO] Deletando usuario na base de dados com facebookId = 100007710667474<br>

Utilização dos serviços
--------------------------------
A utilização dos serviços de GET, POST e DELETE deve ser feita com base no endereço do servidor mais o nome projetoX-1.0 (proveniente do projetoX-1.0.war) com adição do recurso /person. Exemplo:
 
* curl -X POST -F facebookId=100007710667474 http://localhost:8080/projetoX-1.0/person -v

* curl http://localhost:8080/projetoX-1.0/person/?limit=0 -v

* curl -X DELETE http://localhost:8080/projetoX-1.0/person/100007710667474 -v

Cobertura dos testes unitários
-------------------------------------------
A utilização de testes unitários é permitida a partir da execução da classe WebServicesTest como JUnit Test. Ele efetua testes seguindo os seguintes passo:

* POST para http://localhost:8080/projetoX-1.0/person com parametro facebookId=100007710667474 e espera receber o status de resposta HTTP CREATED.
* POST idêntico ao acima e espera receber o status de resposta ACCEPTED, de forma que a requisição foi recebida, porém nenhuma ação foi executada uma vez que o usuário já está cadastrado na base.
* GET em http://localhost:8080/projetoX-1.0/person e espera receber um código de status HTTP OK com uma lista com os usuários cadastrados contendo o facebookId 100007710667474 e a String renato.luizalabs
* DELETE em http://localhost:8080/projetoX-1.0/person/100007710667474  e espera receber um código de status HTTP NO_CONTENT, que representa que o usuário foi deletado da base de dados.
* DELETE em http://localhost:8080/projetoX-1.0/person/100007710667474  e espera receber um código de status HTTP NOT_FOUND, que representa que o usuário não foi encontrado na base de dados e pois já foi deletado.
* GET em http://localhost:8080/projetoX-1.0/person e espera receber um código de status HTTP OK com uma lista com os usuários cadastrados sem conter o facebookId 100007710667474 e a String renato.luizalabs.