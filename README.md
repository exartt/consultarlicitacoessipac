# Consultar Licitações

Foi feito um deploy utilizando o heroku para melhor usabilidade. Os endpoints para serem consumidos da API encontram-se na documentação abaixo.

Documentação, DDL para a geração do banco e modelagem.
[Documentação e Modelagem](https://fuchsia-rotate-8b7.notion.site/Consultar-Licita-es-da-plataforma-Sipac-7255a5ccd0ea47e8944660b28750f1cb)

---

Essa API foi desenvolvida no intuito de coletar as informações das licitações fornecidas pela plataforma do sipac.

Como a plataforma não oferece api para consultar as informações, o desenvolvimento foi idelizado e executado utilizando web scrapping.

A API tem como funções retornar em json as informações da página, persistir os dados, atualizar o status de lido/não lido.

As tecnologias utilizadas foram JAVA utilizando o framework spring boot e para o banco de dados foi utilizado o PostgreSQL.

*O package war do Sistema se encontra gerado na pasta principal do sistema para necessidade de deploy.*
