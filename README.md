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

Logo após, será criado o arquivo projetoX-1.0.war no diretório de nome target da pasta do projeto que deve ser usado para deploy no servidor Tomcat.

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

