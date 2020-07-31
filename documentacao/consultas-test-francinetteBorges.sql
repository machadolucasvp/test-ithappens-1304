--Consultas Desafio Técnico -test-ithappens-1101 (Francinette Borges)
--Retorna produtos do estoque com quantidade maior ou igual a 100
SELECT prd.es04_cod_produto AS COD_PRODUTO,
       prd.es04_cdg_barras AS CDG_BARRAS_PRODUTO,
       prd.es04_descricao AS DESCRICAO_PRODUTO,
       etq.es01_qtd_total AS QTD_ESTOQUE,
       fil.es10_descricao AS ESTOQUE_FILIAL
FROM es01_estoque as etq
JOIN es04_produto prd on etq.fkes01es04_cod_produto = prd.es04_cod_produto
JOIN es10_filial fil on etq.fkes01es10_cod_filial = fil.es10_cod_filial
WHERE etq.es01_qtd_total >= 100;

--Retorna todos os produtos que tem estoque para a filial de código 60
SELECT  prd.es04_cod_produto AS COD_PRODUTO,
        prd.es04_cdg_barras AS CDG_BARRAS_PRODUTO,
        prd.es04_descricao AS DESCRICAO_PRODUTO,
        etq.es01_qtd_total AS QTD_ESTOQUE,
        fil.es10_descricao AS ESTOQUE_FILIAL
FROM es01_estoque AS etq
JOIN es04_produto prd on etq.fkes01es04_cod_produto = prd.es04_cod_produto
JOIN es10_filial fil on etq.fkes01es10_cod_filial = fil.es10_cod_filial
WHERE fil.es10_cod_filial=60;

--Retorna todos os campos para o domínio PedidoEstoque e ItemPedido filtrando apenas o produto de código 7993
SELECT ped.* , itp.*
FROM es02_pedido_estoque AS ped
JOIN es03_item_pedido itp on ped.es02_cod_pedido_estoque = itp.fkes03es02_cod_pedido_estoque
WHERE itp.fkes03es04_cod_produto = 7993;

--Retorna os pedidos de saída com suas respectivas formas de pagamento
SELECT ped.es02_cod_pedido_estoque AS COD_PEDIDO,
       tpd.es05_descricao AS TIPO_PEDIDO,
       ped.es02_valor_total AS VALOR_PEDIDO,
       cli.es12_nome AS CLIENTE,
       pag.es07_descricao AS FORMA_PAG
FROM es02_pedido_estoque AS ped
JOIN es05_tipo_pedido tpd on ped.fkes02es05_cod_tipo_pedido = tpd.es05_cod_tipo_pedido
JOIN es09_compra cmp on ped.es02_cod_pedido_estoque = cmp.fkes09es02_cod_pedido_estoque
JOIN es07_forma_pag pag on cmp.fkes09es07_cod_forma_pag = pag.es07_cod_forma_pag
JOIN es11_usuario usr on cmp.fkes09es11_cod_usuario = usr.es11_cod_usuario
JOIN es12_cliente cli on cmp.fkes09es12_cod_cliente = cli.es12_cod_cliente;

--Sumariza e bate valores da capa do pedido com os valores dos itens pedidos
SELECT ped.es02_cod_pedido_estoque AS COD_PEDIDO,
       ped.es02_valor_total AS VALOR_PEDIDO,
       SUM(itp.es03_quantidade) AS QTD_TOTAL_ITENS,
       SUM(itp.es03_valor_unitario*itp.es03_quantidade) AS SUM_VALOR_UNITARIO_ITENS
FROM es02_pedido_estoque AS ped
JOIN es03_item_pedido itp on ped.es02_cod_pedido_estoque = itp.fkes03es02_cod_pedido_estoque
JOIN es04_produto prd on itp.fkes03es04_cod_produto = prd.es04_cod_produto
WHERE itp.fkes03es06_cod_status = 1
GROUP BY ped.es02_cod_pedido_estoque ORDER BY ped.es02_cod_pedido_estoque;

--Sumariza o total dos itens por pedido e filtra apenas os pedidos cuja soma total da quantidade de itens pedidos seja maior que 10
SELECT ped.es02_cod_pedido_estoque AS COD_PEDIDO,
       SUM(itp.es03_quantidade) AS QTD_TOTAL_ITENS_PEDIDO
FROM es02_pedido_estoque AS ped
         JOIN es03_item_pedido itp on ped.es02_cod_pedido_estoque = itp.fkes03es02_cod_pedido_estoque
         JOIN es04_produto prd on itp.fkes03es04_cod_produto = prd.es04_cod_produto
WHERE itp.fkes03es06_cod_status = 1
GROUP BY ped.es02_cod_pedido_estoque
HAVING SUM(itp.es03_quantidade) > 10
ORDER BY ped.es02_cod_pedido_estoque;












