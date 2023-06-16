package com.example.vj20231.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vj20231.InfoPaisaje;
import com.example.vj20231.MapviwActivity;
import com.example.vj20231.R;
import com.example.vj20231.entities.Contact;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaisajeAdapter extends RecyclerView.Adapter<PaisajeAdapter.NameViewHolder> {

    private List<Contact> items;
    private Context context;

    public PaisajeAdapter(List<Contact> items, Context context) {
        this.items = items;
        this.context= context;
    }

    public void setContacts(List<Contact> contacts) {
        this.items = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_paisaje, parent, false);
        NameViewHolder viewHolder = new NameViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        Contact item = items.get(position);
        View view = holder.itemView;

        ImageView ivContactImage = view.findViewById(R.id.ivContactImage);
        TextView tvNombre = view.findViewById(R.id.tvNombre);
        Button btnVer = view.findViewById(R.id.btnVer);
        Button btnvermapa = view.findViewById(R.id.btnVerMapa);

        // Load the image using Picasso
        Picasso.get().load(item.getImgContact()).into(ivContactImage);

        tvNombre.setText(item.getNameContact());


        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                Contact clickedItem = items.get(clickedPosition);
                Intent intent = new Intent(v.getContext(), InfoPaisaje.class);
                intent.putExtra("id", clickedItem.getId());
                intent.putExtra("nombre", clickedItem.getNameContact());
                intent.putExtra("imagen", clickedItem.getImgContact());
                v.getContext().startActivity(intent);
            }
        });

        btnvermapa.setOnClickListener(view1 -> {
            int clickedPosition = holder.getAdapterPosition();
            Contact clickedItem = items.get(clickedPosition);
            Intent intent = new Intent(context, MapviwActivity.class);
            intent.putExtra("nombre", clickedItem.getNameContact());
            intent.putExtra("lat", items.get(position).latitude);
            intent.putExtra("lon", items.get(position).longitude);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addContact(Contact contact) {
        items.add(contact);
        notifyDataSetChanged();
    }

    public static class NameViewHolder extends RecyclerView.ViewHolder {

        Button btnVerMapa;
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
            btnVerMapa = itemView.findViewById(R.id.mapvista);
        }

    }
}

