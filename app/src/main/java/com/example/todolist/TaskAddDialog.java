package com.example.todolist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TaskAddDialog extends DialogFragment {
    SQLiteHelper sqLiteHelper;
    AddNewItemListener addNewItemListener;
//    public TaskDialog(AddNewItemListener addNewItemListener){
//        this.addNewItemListener=addNewItemListener;
//
//    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addNewItemListener = (AddNewItemListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task,null,false);
        builder.setView(view);
        TextInputEditText textInputEditText = view.findViewById(R.id.edt_dialog);
        TextInputLayout textInputLayout = view.findViewById(R.id.edtl_dialog);
        MaterialButton btn_save_dialog = view.findViewById(R.id.btn_save_dialog);
        btn_save_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textInputEditText.length()>0){
                    //sqLiteHelper = new SQLiteHelper(getContext());
                    Task task = new Task();
                    task.setTitle(textInputEditText.getText().toString());
                    task.setCompleted(false);
                    //Long result = sqLiteHelper.addTask(task);
//                    if (result>0){
//                        Toast.makeText(getContext(),"insert",Toast.LENGTH_SHORT).show();
                        addNewItemListener.onAddItem(task);
                        dismiss();
                   // }
                }
                else {
                    textInputLayout.setError("عنوان نباید خالی باشد");
                }
            }
        });
        return builder.create();
    }

    public interface AddNewItemListener{
        public void onAddItem(Task task);
    }
}
