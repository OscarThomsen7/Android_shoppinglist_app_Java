package com.layoutexample.Shopping_list_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    ImageView buttonAdd;
    FloatingActionButton deleteList;
    EditText input;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    Repository repository;
    RelativeLayout layoutBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layoutBackground = findViewById(R.id.layoutBackground);
        deleteList = findViewById(R.id.deleteList);
        deleteList.setVisibility(View.INVISIBLE);
        buttonAdd = findViewById(R.id.add);
        input = findViewById(R.id.input);
        recyclerView = findViewById(R.id.recyclerViewID);
        repository = SqliteRepo.getInstance(getApplicationContext());

        displayItemsInRecyclerView();

        if (adapter.items.size() != 0) {
            deleteList.setVisibility(View.VISIBLE);
            displayItemsInRecyclerView();
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ItemModel itemModel = new ItemModel();
                int itemId = itemModel.getId();

                String inputText = input.getText().toString();

                ItemModel item = new ItemModel(itemId, inputText, 0);

                if (inputText.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Can not add empty item", Toast.LENGTH_SHORT).show();
                    input.setText("");
                }
                else {
                    repository.save(item);
                    input.setText("");
                    Toast.makeText(getApplicationContext(), "Added " + "'" +inputText +"'", Toast.LENGTH_SHORT).show();


                    displayItemsInRecyclerView();

                    deleteList.setVisibility(View.VISIBLE);
                    deleteList.isClickable();
                }
            }
        });

        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    repository.deleteAll();
                    displayItemsInRecyclerView();
                    toast("Deleted list");
                    deleteList.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void checkBoxIsClicked(ItemModel itemModel) {
        repository.save(itemModel);
    }

    @Override
    public void deleteItem(int position) {
        ItemModel itemModel = repository.findAllItems().get(position);

        if(adapter.items.size() == 1) {
            repository.deleteItem(itemModel);
            deleteList.setVisibility(View.INVISIBLE);
        }
        repository.deleteItem(itemModel);

        displayItemsInRecyclerView();
    }

    private void displayItemsInRecyclerView() {
        adapter = new CustomAdapter(this, repository.findAllItems(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}