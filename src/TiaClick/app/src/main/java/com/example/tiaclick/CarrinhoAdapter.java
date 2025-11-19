package com.example.tiaclick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    ArrayList<Produto> lista;
    Context context;

    public CarrinhoAdapter(ArrayList<Produto> lista, Context ctx) {
        this.lista = lista;
        this.context = ctx;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco, quantidade;
        ImageView imagem;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeItemCarrinho);
            preco = itemView.findViewById(R.id.precoItemCarrinho);
            imagem = itemView.findViewById(R.id.imgItemCarrinho);
            quantidade = itemView.findViewById(R.id.quantidadeItemCarrinho);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto p = lista.get(position);

        holder.nome.setText(p.getNome());

        double precoTotal = p.getPreco() * p.getQuantidade();
        holder.preco.setText("R$ " + String.format("%.2f", precoTotal));

        holder.quantidade.setText("" + p.getQuantidade());

        Glide.with(context)
                .load(p.getImagemUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.imagem);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}