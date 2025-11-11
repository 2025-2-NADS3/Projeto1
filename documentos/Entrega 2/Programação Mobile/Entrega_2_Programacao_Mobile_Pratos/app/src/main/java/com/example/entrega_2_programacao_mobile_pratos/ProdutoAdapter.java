package com.example.entrega_2_programacao_mobile_pratos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {

    // guarda o contexto e a lista de produtos
    private Context context;
    private List<Produto> produtos;

    // construtor pra receber os produtos e o contexto da tela
    public ProdutoAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    // cria o layout de cada item da lista (um "cartão" de produto)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_produto, parent, false);
        return new ViewHolder(view);
    }

    // liga os dados do produto com os elementos visuais
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // pega o produto atual da lista
        Produto p = produtos.get(position);

        // mostra o nome e o preço
        holder.nome.setText(p.getNome());
        holder.preco.setText("R$ " + p.getPreco());

        // carrega a imagem do produto com a biblioteca Glide
        Glide.with(context)
                .load(p.getImagemUrl()) // link da imagem
                .placeholder(android.R.drawable.ic_menu_gallery) // imagem padrão enquanto carrega
                .error(android.R.drawable.ic_delete) // imagem se der erro
                .into(holder.imagem); // coloca na imagem do item

        // quando clicar no item, abre a tela com as infos do produto
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TelaInfoPrato.class);

            // envia todos os dados do produto pra próxima tela
            intent.putExtra("id", p.getId());
            intent.putExtra("tipo", p.getTipo());
            intent.putExtra("nome", p.getNome());
            intent.putExtra("descricao", p.getDescricao());
            intent.putExtra("preco", p.getPreco());
            intent.putExtra("estoque", p.getEstoque());
            intent.putExtra("disponivel", p.isDisponivel());
            intent.putExtra("imagemUrl", p.getImagemUrl());
            intent.putExtra("rating", p.getRating());

            // inicia a nova tela
            context.startActivity(intent);
        });
    }

    // retorna quantos itens tem na lista
    @Override
    public int getItemCount() {
        return produtos.size();
    }

    // classe que representa os elementos visuais de um item
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagem;
        TextView nome, preco;

        public ViewHolder(View itemView) {
            super(itemView);
            imagem = itemView.findViewById(R.id.imgProduto);
            nome = itemView.findViewById(R.id.txtNomeProduto);
            preco = itemView.findViewById(R.id.txtPrecoProduto);
        }
    }
}
