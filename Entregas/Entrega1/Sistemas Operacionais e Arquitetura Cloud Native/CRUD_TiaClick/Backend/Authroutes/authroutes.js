import express from 'express'
import bcrypt from 'bcryptjs'
import jwt from 'jsonwebtoken'
import db from '../db.js'
import authMiddleware from '../Middleware/AuthMiddleware.js'

const router = express.Router()


router.post('/cadastro', async (req, res) => {
    const {nome, email, senha, idade, tipo, ra} = req.body
    console.log(req.body)

    try {
        //Checagem se existe um usuário com mais de uma conta
        const [existing] = await db.query(
            'SELECT * FROM consumidor WHERE email = ?', [email]
        )
        console.log(existing); 

        if (existing.length > 0) {
            return res.status(400).send('Usuário já existe')
        }

        //Validar RA antes de evniar para o banco de dados
        if(tipo === 'aluno' && !ra){
            return res.status(400).send('Aluno precisa ter RA')
        }
        if(tipo !== 'aluno' && ra){
            return res.status(400).send('Apenas alunos têm RA')
        }

        //Criptografia de senha
        const hashed = await bcrypt.hash(senha, 10)
        await db.query(
            `INSERT INTO consumidor
            (nome_consumidor, email, senha, idade, tipo_consumidor, ra)
            VALUES(?, ?, ?, ?, ?, ?)`,
            [nome, email, hashed, idade, tipo, ra ?? null]
        )
        
        res.status(201).send('Usuário registrado com sucesso!')  // Enviar resposta de sucesso
    } catch (err) {
        console.error('Erro ao registrar usuário:',err)  // Mostrar o erro completo
        res.status(500).send('Erro ao registrar usuário')
    }
})


router.post('/login', async (req, res) => {
  const { email, senha } = req.body

  try {
    //Busca do usuário pelo e-mail
    const [rows] = await db.query(
      'SELECT * FROM consumidor WHERE email = ?',
      [email]
    )

    const user = rows[0]
    if (!user) {
      return res.status(400).send('Usuário não encontrado')
    }

    //Comparação de senha digitada e armazenada
    const valido = await bcrypt.compare(senha, user.senha)
    if (!valido) {
      return res.status(401).send('Senha incorreta')
    }

    //Gerando token
    const token = jwt.sign(
      {
        id: user.id_consumidor,
        nome: user.nome_consumidor,
        email: user.email,
        idade: user.idade,
        tipo: user.tipo_consumidor,
        ra: user.ra,
      },
      process.env.JWT_SECRET,
      { expiresIn: '1h' }
    )

    res.json({ token })
  } catch (err) {
    console.error('Erro ao fazer login:', err)
    res.status(500).send('Erro ao fazer login')
  }
})


router.put('/editar', authMiddleware, async (req, res) => {
    const id = req.user.id;
    const { nome, email, senha, idade, ra } = req.body;

    try {
        const [userCheck] = await db.query(
            'SELECT * FROM consumidor WHERE id_consumidor = ?',
            [id]
        );

        if (userCheck.length === 0) return res.status(404).send('Usuário não encontrado');

        const usuarioAtual = userCheck[0];
        const tipoUsuario = usuarioAtual.tipo_consumidor;

        // Valida RA apenas se for aluno
        if (tipoUsuario === 'aluno' && ra === "") {
            return res.status(400).send('Aluno precisa ter RA');
            
        }
        if (tipoUsuario !== 'aluno' && ra) {
            return res.status(400).send('Apenas alunos têm RA');
        }

        // Criptografia de senha se for alterada
        let hashed = usuarioAtual.senha;
        if (senha && senha.trim() !== '') {
            hashed = await bcrypt.hash(senha, 10);
        }

        // Atualiza apenas os campos fornecidos
        const atualizar = {
            nome_consumidor: nome || usuarioAtual.nome_consumidor,
            email: email || usuarioAtual.email,
            senha: hashed,
            idade: idade || usuarioAtual.idade,
            ra: tipoUsuario === 'aluno' ? ra || usuarioAtual.ra : null
        };

        await db.query(
            `UPDATE consumidor 
             SET nome_consumidor = ?, email = ?, senha = ?, idade = ?, ra = ?
             WHERE id_consumidor = ?`,
            [atualizar.nome_consumidor, atualizar.email, atualizar.senha, atualizar.idade, atualizar.ra, id]
        );

        res.status(200).send('Cadastro atualizado com sucesso!');
    } catch (err) {
        console.error('Erro ao atualizar cadastro:', err);
        res.status(500).send('Erro ao atualizar cadastro');
    }
});



router.delete('/excluir', authMiddleware, async (req, res) => {
    const id = req.user.id; // pega do token

    try {
        const [userCheck] = await db.query(
            'SELECT * FROM consumidor WHERE id_consumidor = ?',
            [id]
        );

        if (userCheck.length === 0) {
            return res.status(404).send('Usuário não encontrado');
        }

        await db.query(
            'DELETE FROM consumidor WHERE id_consumidor = ?',
            [id]
        );

        res.status(200).send('Usuário excluído com sucesso!');
    } catch (err) {
        console.error('Erro ao excluir usuário:', err);
        res.status(500).send('Erro ao excluir usuário');
    }
}); 


router.get('/privado', authMiddleware, (req, res) => {
    console.log("Entrando")
    res.json({ message: `Bem-vindo, ${req.user.nome}`})
});


router.get('/', async (req, res) => {
    try {
        const [rows] = await db.query(
            'SELECT * FROM consumidor'
        )
        res.json(rows)
    } catch (err) {
        console.error('Erro ao buscar consumidores:', err)
        res.status(500).json({ error: 'Erro ao buscar logins' })
    }
})


export default router