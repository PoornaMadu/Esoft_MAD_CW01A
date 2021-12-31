package com.example.crudexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button add,view;
    ListView listView;
    TextView count;
    Context context;
    private DBHandler dbHandler;
    private List<Todo> toDos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.add);
        listView=findViewById(R.id.todolist);
        count=findViewById(R.id.todocount);
        view=findViewById(R.id.view);
        context=this;
        dbHandler=new DBHandler(this);
        toDos=new ArrayList<>();

        toDos= dbHandler.getAllTodo();
        ToDoAdapter adapter=new ToDoAdapter(context,R.layout.single_todo,toDos);
        listView.setAdapter(adapter);

        // todo count
        int countTodo=dbHandler.countTodo();
        count.setText("You have " +countTodo+" Task Please Check Your Tasks... ");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,List.class));

            }
        });
            add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,AddTodo.class));


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int position, long l) {

                Todo todo=toDos.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(todo.getTitle());
                builder.setMessage(todo.getDescription());

                builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(context,MainActivity.class));

                    }
                });
//                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        dbHandler.deleteToDo(todo.getId());
//
//                    }
//                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteToDo(todo.getId());
                        startActivity(new Intent(context,MainActivity.class));
                    }
                });

                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(context,EditTodo.class);
                        intent.putExtra("id",String.valueOf(todo.getId()));
                        startActivity(intent);


                    }
                });
                builder.show();
            }
        });
    }
}




































