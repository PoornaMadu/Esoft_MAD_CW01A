package com.example.crudexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTodo extends AppCompatActivity {
    EditText title,desc;
    Button edit;
    private DBHandler dbHandler;
    private Context context;
    private Long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        title=findViewById(R.id.editToDoTextTitle);
        desc=findViewById(R.id.editTodoTextDescription);
        edit=findViewById(R.id.buttonEdit);
        context=this;
        dbHandler=new DBHandler(context);

        final String id=getIntent().getStringExtra("id");
        Todo todo=dbHandler.getSingleTodo(Integer.parseInt(id));
        title.setText((todo.getTitle()));
        desc.setText(todo.getDescription());

        System.out.println(id);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText=title.getText().toString();
                String desText=desc.getText().toString();
                updateDate=System.currentTimeMillis();

                Todo toDo=new Todo(Integer.parseInt(id),titleText,desText,updateDate,0);
                int state=dbHandler.updateSingleTodo(toDo);

                if(state==1){
                    Toast toast = Toast.makeText(context, "Update Success..", Toast.LENGTH_LONG);
                    toast.show();

                }
                System.out.println(state);
                startActivity(new Intent(context,MainActivity.class));
            }
        });

    }
}





































































