CREATE SEQUENCE ES12_CLIENTE_SEQ START 1;
CREATE TABLE public.ES12_CLIENTE(
                             ES12_COD_CLIENTE BIGINT NOT NULL DEFAULT nextval('public.ES12_CLIENTE_SEQ'::regclass),
                             ES12_NOME VARCHAR(255),
                             ES12_CPF VARCHAR(14)
);

