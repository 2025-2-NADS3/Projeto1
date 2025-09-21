async function carregarUsuarioEditar() {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Você precisa estar logado.");
        window.location.href = "login.html";
        return;
    }

    try {
        const res = await fetch("http://localhost:3000/api/privado", {
            method: "GET",
            headers: { Authorization: `Bearer ${token}` },
        });

        if (!res.ok) throw new Error("Token inválido ou expirado");

        const usuario = await res.json();

        // Preenche os inputs do form de edição
        document.querySelector("#nome").value = usuario.nome_consumidor || "";
        document.querySelector("#email").value = usuario.email || "";
        document.querySelector("#idade").value = usuario.idade || "";


        // Mostra RA somente se for aluno
        const raGroup = document.querySelector("#ra-group");
        if (usuario.tipo === "aluno") {
            raGroup.style.display = "block";
            document.querySelector("#ra").value = usuario.ra || "";
        } else {
            raGroup.style.display = "none";
        }

    } catch (erro) {
        console.error("Erro ao carregar dados do usuário:", erro);
        localStorage.removeItem("token");
        window.location.href = "login.html";
    }
}


async function atualizarCadastro(event) {
    event.preventDefault(); // evita recarregar a página

    // Pega o token do localStorage
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Você precisa estar logado para atualizar seu cadastro.");
        window.location.href = "login.html";
        return;
    }

    // Captura os dados do formulário
    const nome = document.querySelector("#nome").value.trim();
    const email = document.querySelector("#email").value.trim();
    const senha = document.querySelector("#senha").value;
    const idade = parseInt(document.querySelector("#idade").value, 10);
    const raInput = document.querySelector("#ra");
    const ra = raInput && raInput.value ? raInput.value.trim() : null;

    try {
        const resposta = await fetch("http://localhost:3000/api/editar", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`, // envia JWT
            },
            body: JSON.stringify({ nome, email, senha, idade, ra }),
        });

        if (resposta.ok) {
            alert("Cadastro atualizado com sucesso!");
            window.location.href = 'index.html'
        } else {
            const erroTexto = await resposta.text();
            console.log(`Erro ao atualizar cadastro: ${erroTexto}`);
        }
    } catch (erro) {
        console.error("Erro na requisição:", erro);
        console.log("Erro ao atualizar cadastro. Tente novamente.");
    }
}

const formEditar = document.getElementById("editar-form");
if (formEditar) {
    formEditar.addEventListener("submit", atualizarCadastro);
}



carregarUsuarioEditar();