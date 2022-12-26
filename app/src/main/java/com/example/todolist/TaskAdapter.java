package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    TaskItemEventListener taskItemEventListener;
    List<Task> tasks = new ArrayList<>();
    public TaskAdapter(TaskItemEventListener taskItemEventListener){
        //this.tasks=tasks;
        this.taskItemEventListener = taskItemEventListener;

    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.bind(tasks.get(position));

    }

    public void addItem(Task task){
//        tasks.add(task);
//        notifyDataSetChanged();

        tasks.add(0,task);
        notifyItemInserted(0);
    }

    public void deleteItem(Task task){
        for (int i=0 ; i<=tasks.size() ; i++){
            if (tasks.get(i).getId() == task.getId()){
                tasks.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }


    }

    public void updateItem(Task task){
        for (int i=0 ; i<=tasks.size() ; i++){
            if (tasks.get(i).getId() == task.getId()){
                tasks.set(i,task);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void clearItems(){
        this.tasks.clear();
        notifyDataSetChanged();
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void addItems(List<Task> tasks){
        this.tasks.addAll(tasks);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private AppCompatImageView img_delete;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            img_delete = itemView.findViewById(R.id.img_delete);

        }

        public void bind(Task task){
           checkBox.setOnCheckedChangeListener(null);
           checkBox.setChecked(task.isCompleted());
           checkBox.setText(task.getTitle());
           img_delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   taskItemEventListener.onDeleteButtonClicked(task);

               }
           });
           itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View view) {
                   taskItemEventListener.onItemLongPress(task);
                   return false;
               }
           });
           checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   task.setCompleted(b);
                   taskItemEventListener.onItemCheckChanged(task);
               }
           });
        }
    }

    public interface TaskItemEventListener{
         void onDeleteButtonClicked(Task task);
         void onItemLongPress(Task task);
         void onItemCheckChanged(Task task);

    }
}
