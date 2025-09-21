// Seleciona o formulário de login
const form = document.querySelector("form");

// Adiciona o ouvinte de evento para envio do formulário
form.addEventListener("submit", async (event) => {
    event.preventDefault(); // Impede o recarregamento da página
    console.log("checando login");

    // Pega os dados do formulário
    const email = document.querySelector("#email").value
    const senha = document.querySelector("#senha").value

    try {
        // Envia os dados para o backend
        console.log("fazendo fetch");
        const resposta = await fetch("http://localhost:3000/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, senha }),
        });
        console.log("fetch feito");
        // Verifica se o login foi bem-sucedido
        if (resposta.ok) {
            alert("Login bem-sucedido");
            const dados = await resposta.json();

            // Armazena o token no localStorage (ou sessionStorage)
            console.log("checando token");
            localStorage.setItem("token", dados.token);
            // Redireciona para a página inicial
            window.location.href = "index.html";
        } else {
            alert("Falha ao efetuar o login. Revise as informações inseridas.");
            const erro = await resposta.json();
            console.log("Erro ao fazer login: " + erro.mensagem);
        }
    } catch (erro) {
        console.log("Erro na requisição: " + erro.message);
        console.error(erro);
    }
});