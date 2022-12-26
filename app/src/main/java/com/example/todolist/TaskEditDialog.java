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

public class TaskEditDialog extends DialogFragment {
    EditTaskCallBack editTaskCallBack;
    private Task task;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        editTaskCallBack = (EditTaskCallBack) context;
        task = getArguments().getParcelable("task");
        if (task == null){
            dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_task,null,false);
        builder.setView(view);
        TextInputEditText textInputEditText = view.findViewById(R.id.edt_edit_dialog);
        TextInputLayout textInputLayout = view.findViewById(R.id.edtl_edit_dialog);
        MaterialButton btn_save_dialog = view.findViewById(R.id.btn_save_EditDialog);
        textInputEditText.setText(task.getTitle());
        btn_save_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textInputEditText.length()>0){
                    task.setTitle(textInputEditText.getText().toString());
                    editTaskCallBack.onEditItem(task);
                    dismiss();
                }
                else {
                    textInputLayout.setError("عنوان نباید خالی باشد");
                }
            }
        });
        return builder.create();
    }

    public interface EditTaskCallBack{
        public void onEditItem(Task task);
    }
}
