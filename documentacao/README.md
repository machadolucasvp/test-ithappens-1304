*******
1. [Dependências](#dependencias)
2. [Documentação](#documentação)
3. [Modelo Relacional DB](#dbmodel)
4. [Querys](#querys)

*******


<div id="dependencias"/>

### Dependências

A solução proposta tem como uma de suas dependências o projeto *Lombok*, um pre-processador que reduz código boilerplate atravês de suas anotações

```
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```
Por injetar código em tempo de compilação é necessário instalar os plugins do *Lombok* e habilitar o pre-processador  de seu editor de texto/IDE, para mais informações [veja](https://projectlombok.org/setup/overview).


<div id="documentação"/>

### Documentação

A API desenvolvida durante o desafio foi toda documentada utilizando a plataforma **APIARY** em conjunto com a linguagem de especificação **Blueprint**, por favor dê uma olhada na [**documentação da API aqui**](https://ithappensinterview.docs.apiary.io/).

<div id="dbmodel"/>

## Modelagem do Banco de Dados

A aplicação atualmente ta mapeando para este banco de dados:

![bdmodeloAtual](https://user-images.githubusercontent.com/44952113/77496679-48f63c00-6e2a-11ea-8f37-53de3668450d.png)


1. Escrever uma consulta que retorne todos os produtos com quantidade maior ou iguala 100
```SQL
SELECT * FROM produto WHERE id IN 
(SELECT filial_id from filial_produto WHERE quantidade_estoque >= 100);
```


2. Escrever uma consulta que traga todos os produtos que têm estoque para a filial de código 60

```SQL
SELECT * FROM produto WHERE id IN
(SELECT filial_id FROM filial_produto 
WHERE quantidade_estoque > 0 and filial_id = 60);
```

3. Escrever consulta que liste todos os campos para o domínio PedidoEstoque e ItensPedido filtrando apenas o produto de código 7993
```SQL
SELECT pedido.*, item_pedido.* ,
produto.codigo_de_barras FROM pedido
INNER JOIN item_pedido ON pedido.id=item_pedido.pedido_id
INNER JOIN produto ON item_pedido.produto_id=produto_id
WHERE codigo_de_barras="7993";
```

4. Escrever uma consulta que liste os pedidos com suas respectivas formas de pagamento.
```SQL
SELECT pedido.* , pagamento.* FROM pedido 
INNER JOIN pagamento ON pagamento.id=pedido.id;
```

5. Escrever um consulta para sumarizar e bater os valores da capa do pedido com os
valores dos ítens de pedido
```SQL
SELECT pedido.id, custo_total AS pedido_custo_total,
SUM( custo_unitario * quantidade ) AS soma_sub_total 
FROM pedido INNER JOIN item_pedido
ON pedido.id=item_pedido.pedido_id 
group by pedido.id, pedido_custo_total;
```

6. Escrever uma consulta para sumarizar o total dos itens por pedido e que filtre apenas os pedidos no qual a soma total da quantidade de ítens de pedido seja maior que 10
```SQL
SELECT pedido_id, SUM(quantidade) AS quantidade_de_itens FROM pedido
INNER JOIN item_pedido ON pedido.id=item_pedido.pedido_id 
GROUP BY pedido.id HAVING quantidade_de_itens>10;
```

******
Url explícita da documentação da API: https://ithappensinterview.docs.apiary.io/ 