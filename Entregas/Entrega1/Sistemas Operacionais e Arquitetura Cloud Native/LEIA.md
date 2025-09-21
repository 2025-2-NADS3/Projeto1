# Explicação
Como informado em sala de aula, o CRUD foi feito em JavaScript com servidor Node.js

## Como rodar o projeto
Para rodar o projeto, é necessário ter o Node.js e o MySQL Wokbench + Server baixados em seu computador.

### MySQL
No MySQL, tem que pegar o arquivo do TiaDB.sql, jogar lá, dar um ctrl+a e criar tudo que está nele.

### Node.js
Para baixar as dependências do projeto na sua máquina, deve rodar so seguinte comando:
```
cd Backend
npm install
```

Depois de baixar as dependências localmente, para executar o projeto, deve rodar os seguinte comando:
```
cd ../js
npx nodemon server.js
```