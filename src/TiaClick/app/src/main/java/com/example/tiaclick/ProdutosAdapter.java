package com.example.tiaclick;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ViewHolder> {

    ArrayList<Produto> lista;
    Context context;

    public ProdutosAdapter(Context context, ArrayList<Produto> lista) {
        this.context = context;
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco;
        ImageView imagem;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeProduto);
            preco = itemView.findViewById(R.id.precoProduto);
            imagem = itemView.findViewById(R.id.imagemProduto);
        }
    }

    @NonNull
    @Override
    public ProdutosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosAdapter.ViewHolder holder, int position) {
        Produto p = lista.get(position);

        holder.nome.setText(p.getNome());
        holder.preco.setText("R$ " + String.format("%.2f", p.getPreco()));

        Glide.with(context)
                .load(p.getImagemUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(holder.imagem);

        holder.itemView.setOnClickListener(v -> {
        Intent intent = new Intent(context, DetalhesProdutoActivity.class);

            intent.putExtra("nome", p.getNome());
            intent.putExtra("descricao", p.getDescricao());
            intent.putExtra("preco", p.getPreco());
            intent.putExtra("imagem", p.getImagemUrl());
            intent.putExtra("nota", p.getNota());
            intent.putExtra("uid", p.getUid());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
