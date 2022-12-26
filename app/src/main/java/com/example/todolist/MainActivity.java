package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAddDialog.AddNewItemListener, TaskAdapter.TaskItemEventListener,TaskEditDialog.EditTaskCallBack {
    SQLiteHelper sqLiteHelper;
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    FloatingActionButton floatingActionButton;
    AppCompatImageView clear_Btn;
    AppCompatEditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteHelper = new SQLiteHelper(this);
        recyclerView = findViewById(R.id.rc);
        floatingActionButton = findViewById(R.id.floatingBtn);
        clear_Btn = findViewById(R.id.clearBtn);
        edtSearch = findViewById(R.id.edtSearch);
        taskAdapter = new TaskAdapter(this);



        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(taskAdapter);

        List<Task> tasks = sqLiteHelper.getTasks();
        taskAdapter.addItems(tasks);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskAddDialog taskAddDialog = new TaskAddDialog();
                taskAddDialog.show(getSupportFragmentManager(),null);
            }
        });
        clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteHelper.clearAllTasks();
                taskAdapter.clearItems();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>0){
                    List<Task> tasks = sqLiteHelper.searchTasks(charSequence.toString());
                    taskAdapter.setTasks(tasks);
                }
                else {
                    List<Task> tasks = sqLiteHelper.getTasks();
                    taskAdapter.setTasks(tasks);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onAddItem(Task task) {
        Long taskID = sqLiteHelper.addTask(task);
        if (taskID > 0){
            task.setId(taskID);
            taskAdapter.addItem(task);
            recyclerView.scrollToPosition(0);
        }

    }

    @Override
    public void onDeleteButtonClicked(Task task) {
        int idDeleted=sqLiteHelper.deleteTask(task);
        if(idDeleted>0){
            taskAdapter.deleteItem(task);
        }

    }

    @Override
    public void onItemLongPress(Task task) {
        TaskEditDialog taskEditDialog = new TaskEditDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task",task);
        taskEditDialog.setArguments(bundle);
        taskEditDialog.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onItemCheckChanged(Task task) {
        sqLiteHelper.updateTask(task);
    }

    @Override
    public void onEditItem(Task task) {
        int result=sqLiteHelper.updateTask(task);
        if (result>0){
            taskAdapter.updateItem(task);

        }
    }
}