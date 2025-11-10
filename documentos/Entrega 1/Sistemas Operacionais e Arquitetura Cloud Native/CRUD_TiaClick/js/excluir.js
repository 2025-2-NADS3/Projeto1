async function excluirConta() {
  const token = localStorage.getItem("token"); // <<< pega o token do storage
  if (!token) {
    alert("Você precisa estar logado para excluir a conta.");
    return;
  }

   try {
    const res = await fetch("http://localhost:3000/api/excluir", {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`, // agora token existe
      }
    })

    if (res.ok) {
      alert('Conta excluída com sucesso!');
      localStorage.removeItem("token");
      window.location.href = 'signup.html';
    } else {
      const errMsg = await res.text();
      console.log(`Erro ao excluir conta: ${errMsg}`);
    }
  } catch (err) {
    console.error(err);
    alert('Erro na requisição. Tente novamente.');
  }
}
