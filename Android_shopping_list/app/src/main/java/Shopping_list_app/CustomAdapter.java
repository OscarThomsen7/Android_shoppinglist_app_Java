package com.layoutexample.Shopping_list_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        RecyclerViewInterface recyclerViewInterface;
        Context context;
        ArrayList<ItemModel> items;

        public CustomAdapter(Context context, ArrayList<ItemModel> items, RecyclerViewInterface recyclerViewInterface) {
            this.context = context;
            this.items = items;
            this.recyclerViewInterface = recyclerViewInterface;
        }




        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.custom_layout, parent, false);

            ViewHolder holder = new ViewHolder(itemView, recyclerViewInterface);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
            ItemModel item = items.get(position);

            holder.txtItem.setText(item.getItem());
            holder.checkBox.setChecked(item.getIsChecked() == 1);



            holder.checkBox.setOnClickListener(view -> {
                if (item.getIsChecked()==0)
                    item.setIsChecked(1);
                else
                    item.setIsChecked(0);

                try {
                    recyclerViewInterface.checkBoxIsClicked(item);
                } catch (ClassCastException e) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder{
            public final TextView txtItem;
            public ImageView delete, edit;
            public CheckBox checkBox;

            public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
                super(itemView);

                txtItem = itemView.findViewById(R.id.outPutText);
                delete = itemView.findViewById(R.id.delete);
                checkBox = itemView.findViewById(R.id.checkbox);


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();

                        recyclerViewInterface.deleteItem(position);

                        toast("Deleted " + items.get(position).getItem());

                    }
                });
            }
        }
        private void toast(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

