// Seleciona o formulário de cadastro
const form = document.querySelector("form");

// Adiciona o ouvinte para envio do formulário
form.addEventListener("submit", async (event) => {
    event.preventDefault(); // Evita recarregar a página

    // Captura os dados preenchidos no formulário
    const nome = document.querySelector("#nome").value.trim();
    const email = document.querySelector("#email").value.trim();
    const senha = document.querySelector("#senha").value;
    const senhaConfirm = document.querySelector("#passwordagain").value;
    const idade = parseInt(document.querySelector("#idade").value, 10);
    const tipo = document.querySelector("#tipo").value; // select: aluno | professor | visitante
    const raInput = document.querySelector("#ra");
    const ra = raInput && raInput.value ? parseInt(raInput.value, 10) : null;

    // ✅ Verifica se as senhas batem
    if (senha !== senhaConfirm) {
        alert("As senhas não coincidem! Por favor, verifique.");
        return; // interrompe o envio do formulário
    }

    try {
        // Envia os dados para o backend
        const resposta = await fetch("http://localhost:3000/api/cadastro", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ nome, email, senha, idade, tipo, ra }),
        });

        // Se tudo der certo, redireciona para a tela de login
        if (resposta.ok) {
            alert("Cadastro realizado com sucesso!");
            window.location.href = "login.html";
        } else {
            const erroTexto = await resposta.text();
            alert("Erro ao cadastrar. Revise as informações inseridas.");
            console.log("Erro ao cadastrar: " + erroTexto);
        }
    } catch (erro) {
        console.log("Erro na requisição: " + erro.message);
    }
});

console.log("Script de cadastro carregado");