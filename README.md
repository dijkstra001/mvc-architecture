# Implementação do Padrão de Projeto M.V.C. (Model, View e Controller)
### Projeto para a disciplina de Projeto e Arquitetura de Software

#### 1. Definição:

O M.V.C. é utilizado em muitos projetos devido a arquitetura que possui, o que possibilita a divisão do projeto em camadas muito bem definidas. Cada uma delas, o Model, o Controller e a View, executa apenas o que for atrubuído à sua responsabilidade.

A utilização do padrão M.V.C. traz como benefício o isolamento das regras de negócios da lógica de apresentação, que é a interface com o usuário. Isto possibilita a existência de várias interfaces com o usuário que podem ser modificadas sem a necessidade de alterar as regras de negócios, proporcionando muito mais flexibilidade e oportunidades de reuso das classes.

De forma resumida, podemos descrever o Model, a View e o Controller como:

#### Model: 
Responsável por realizar a regra de negócio e retornar o resultado para o controller. Ele é o único que conhece a camada de visualização.

#### View: 
É a interface gráfica da aplicação. É nela onde o usuário irá interagir e visualizar as respostas do sistema.

#### Controller:
A comunicação entre interfaces e regras de negócios é definida através de um controlador, que separa as camadas. Quando um evento é executado na interface gráfica, como um clique em um botão, a interface se comunicará com o controlador, que por sua vez se comunica com as regras de negócios.

#### 2. Desenvolvimento:
O desenvolvimento foi realizado com a linguagem JAVA, assim como JAVAFX para a interface gráfica, utilizando um aplicativo de terceiro para a construção das views (Scene Builder). Na parte de banco de dados, foi utilizado o banco MYSQL.

#### 2.1 Configuração:
Para a configuração do JAVAFX, acessar o link https://download.eclipse.org/efxclipse/updates-released/3.7.0/site/.
Dentro do framework eclipse, há um atalho em HELP > INSTALL NEW SOFTWARE. Ao informar o link, será demonstrado o software para a instalação. Importante lembrar da   pasta de instalação, pois posteriormente será necessário apontá-la para configuração dos JAR's no momento de adicionar LIBS EXTERNAS.
Além disso, no RUN CONFIGURATIONS da classe MAIN do aplicativo (MAIN.java >> aplications.package), em ARGUMENTS é necessário informar o seguinte código:

--module-path PATH_\lib --add-modules=javafx.fxml,javafx.controls

onde o PATH precisa ser exatamente o local de instalação dos arquivos .JAR do JAVAFX.

Para a conexão com o banco MYSQL, é necessário ter instalado o banco na sua máquina, assim como o conector CONNECTOR/J. Será necessário apontar esse conector ao configurar as LIBS EXTERNAS. As credenciais de acesso, url do banco e SSL precisam ser informados no arquivo db.properties e referenciado em classes que atuarão na conexão com o banco. No nosso exemplo, essas classes são DBCONNECTOR, DBEXCEPTION E DBINTEGRITYEXCEPTION, que serão responsáveis pela conexão, exceções e erros de integridade do banco, respectivamente.

Para a utilização do SCENE BUILDER para visualização e desenvolvimento das views com interface gráfica, é necessário realizar a instalação do software na sua máquina e apontar o local de instalação no caminho do SCENEBUILDER EXECUTABLE. Esse campo se encontra em WINDOW >> PREFERENCES >> JAVAFX.

#### 2.2 Código:
Na tabela abaixo, constam os links para acessar os pacotes de cada componente da Arquitetura M.V.C.. Nosso aplicativo para exemplificar essa arquitetura se resume em cadastrar vendedores e departamentos, assim como as 4 operações de um C.R.U.D. de um banco de dados (Create, Read, Update and Delete).

| Pacote  |  Link  |
| ------------------- | ------------------- |
|  Controllers |  Célula de conteúdo |
|  Views |  Célula de conteúdo |
|  Models¹ |  Célula de conteúdo |
|  Models² |  Célula de conteúdo |
|  Models³ |  Célula de conteúdo |

¹ Refere-se ao model das entidades.
² Refere-se ao model do acesso ao banco (impl)
³ Refere-se ao model xxx

#### 3. Conclusão:

<img alt="Java" src="https://img.shields.io/badge/java-%23ED8B00.svg?&style=for-the-badge&logo=java&logoColor=white"/> 
