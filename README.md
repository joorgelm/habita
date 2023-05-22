<!-- SOBRE O PROJETO -->
## API Rest para o gerenciamento de casas populares

Autor: Jorge Melgarejo

### Tecnologias utilizadas

* Java 17
* Spring Boot 3
* PostgresSQL
* H2 Database
* Maven
* Docker



<!-- INICIANDO PROJETO -->
## Iniciando o projeto

### Pré-requisitos

Para subir o projeto em um sistema operacional unix
é necessário:

* docker
* docker-compose
* Maven
* Java 17
* permissões para que o docker seja executado sem sudo

### Inicialização

* Abra o terminal e entre na raiz do projeto (ex: /habita)

* execute o comando

       bash iniciar.sh

<!-- ENDPOINTS -->
## Endpoints

## Cadastrar uma nova família
`POST /familia`

```
curl --request POST \
  --url http://localhost:8080/familia \
  --header 'Content-Type: application/json' \
  --data '{
	"rendaTotal": 999,
	"membros" : [
		{
			"nome": "Joao Gonçalves",
			"idade": 57
		},
		{
			"nome": "Fabiana Gonçalves",
			"idade": 11
		},
		{
			"nome": "Pedro Gonçalves",
			"idade": 20
		},
		{
			"nome": "Fabiana Gonçalves",
			"idade": 50
		},
		{
			"nome": "Pedro Gonçalves",
			"idade": 18
		}
	]
}'
```

### Response

    HTTP/1.1 201
    Content-Length: 0
    Date: Sun, 21 May 2023 21:28:06 GMT

## Realizar distribuição de casas
`POST /distribuicao`

```
curl --request POST \
  --url http://localhost:8080/distribuicao \
  --header 'Content-Type: application/json' \
  --data '{
	"qtdCasas": 1
}'
```

### Response

    HTTP/1.1 200
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Sun, 21 May 2023 22:04:45 GMT

```
{
"familiasContempladas": [
    {
        "id": 5,
        "membros": [
            {
                "nome": "Joao Gonçalves"
            },
            {
                "nome": "Fabiana Gonçalves"
            },
            {
                "nome": "Pedro Gonçalves"
            }
        ]
    }
]
}
```
## Entidades
Segue uma descrição básica de cada entidade do projeto:

*  **Distribuição**: responsável por representar cada rodada de distribuição de casas entre as famílias cadastradas.
*  **Membro**: Entidade que representa os membros de uma família.
*  **Família**: Entidade que representa uma família.

## Arquitetura
A aplicação foi implementada utilizando a seguinte arquitetura:
* **adapter** :Responsável por tratar as operações de entrada e saída de dados, persistir no banco de dados e produzir os Beans da injeção de dependências.
* **application**:Contém os casos de uso da aplicação, realiza operações utilizando as entidades e é responsável pela conversão de dados exigidos pela camada superior (adapter). _**(Não depende do framework Spring, apenas Java)**_.
* **domain**:Contém as regras de negócio, os critérios de avaliação familiar e as entidades. _**(Não depende do framework Spring, apenas Java)**_.

## Implementação dos critérios de avaliação familiar
Os critérios de avaliação familiar são implementados através de uma interface base `CriterioAvaliacaoStrategy`.

Para implementar um novo critério de avaliação, é necessário criar uma classe que implemente essa interface e implementar os métodos requiridos pela interface.

Exemplo de implementação de um critério de avaliação que leva em consideração o score de crédito de uma família:

```JAVA
public class ScoreCreditoStrategy implements CriterioAvalicaoStrategy {
    private static final int PONTUACAO_MAXIMA = 5;
    private static final int PONTUACAO_MINIMA = 3;
    private static final int LIMITE_CREDITO = 1000;
    
    @Override
    public int obterPontuacao(Familia familia) {
        if (familia.getLimiteCredito() < LIMITE_CREDITO) return PONTUACAO_MAXIMA;
        
        return PONTUACAO_MINIMA;
    }

    @Override
    public int pontuacaoMaxima() {
        return PONTUACAO_MAXIMA;
    }

    @Override
    public int pontuacaoMinima() {
        return PONTUACAO_MINIMA;
    }
}
```

Após a definição da classe de critério de avaliação, é necessário adicionar a mesma no arquivo `StrategyFactory.java`, para que seja criado um Bean do novo critério de avaliação.
Exemplo:

```JAVA
@Configuration
public class StrategyFactory {

    @Bean
    public ScoreCreditoStrategy criarScoreCreditoStrategy() {
        return ScoreCreditoStrategy.builder().build();
    }
}

```

Feito isso, ao realizar uma distribuição, o novo critério de avaliação já será utilizado na análise familiar.


## Observações

Durante o desenvolvimento foram identificadas algumas possíveis melhorias que não foram implementadas prezando pela 
simplicidade da solução e tendo em vista respeitar o prazo limite de entrega. São essas:

* Ter um cadastro de membros familiares mais completo, recebendo CPF, e renda por pessoa (atualmente a renda é uma informação única por família).
* Adicionar um atributo booleano para ativar ou desativar um critério de avaliação.
* Implementar um critério de desempate para famílias com a mesma pontuação final.
* Adicionar uma autenticação de usuários e uma camada de segurança para permitir que apenas usuários autenticados realizem o cadastro de famílias e distribuições.
* O endpoint de realizar distribuição, realiza uma consulta que pode se tornar um problema caso exista uma grande massa de dados. Portanto, poderia ser feita de forma paginada para não carregar tantos dados em memória.
