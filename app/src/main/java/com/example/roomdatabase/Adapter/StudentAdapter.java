package com.example.roomdatabase.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.Entity.EntityStudent;
import com.example.roomdatabase.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {

    public interface onDeleteClickListener{
        void onDeleteClickListener(EntityStudent entityStudent);
    }

    public interface onUpdateClickListener{
        void onUpdateClickListener(EntityStudent entityStudent);
    }

    Context context;
    List<EntityStudent> studentList;
    private onDeleteClickListener onDeleteClickListener;
    private onUpdateClickListener onUpdateClickListener;

    public StudentAdapter(Context context, List<EntityStudent> studentList, onDeleteClickListener listener, onUpdateClickListener onUpdateClickListener) {
        this.context = context;
        this.studentList = studentList;
        this.onDeleteClickListener = listener;
        this.onUpdateClickListener = onUpdateClickListener;
    }

    @NonNull
    @Override
    public final StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.studentdata_adapter, parent, false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentHolder holder, int position) {

        holder.name.setText(studentList.get(position).getName());
        holder.Class.setText(studentList.get(position).getStudentClass());
        holder.age.setText(studentList.get(position).getAge());
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (onDeleteClickListener != null){
                     onDeleteClickListener.onDeleteClickListener(studentList.get(position));
                 }
            }
        });

        holder.updateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntityStudent studentTask = studentList.get(position);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.update_data_dialog, null);
                EditText name1 = promptView.findViewById(R.id.name);
                EditText class1 = promptView.findViewById(R.id.studentClass);
                EditText age1 = promptView.findViewById(R.id.age);
                Button cancel = promptView.findViewById(R.id.cancel);
                Button update = promptView.findViewById(R.id.updateData);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptView);
                AlertDialog alert = alertDialogBuilder.create();
                alert.setCancelable(true);
                alert.show();

                String studentName = studentTask.getName();
                name1.setText(studentName);
                String studentClass = studentTask.getStudentClass();
                class1.setText(studentClass);
                String studentAge = studentTask.getAge();
                age1.setText(studentAge);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name2 = name1.getText().toString();
                        String class2 = class1.getText().toString();
                        String age2 = age1.getText().toString();

                        int id = studentList.get(position).getId();
                        EntityStudent entityStudent = new EntityStudent();
                        if(name1.getText().toString().isEmpty() || class1.getText().toString().isEmpty() || age1.getText().toString().isEmpty()){
                            Toast.makeText( context, "Please Fill All Fields", Toast.LENGTH_SHORT ).show();
                        }
                        else {
                            entityStudent.setId(id);
                            entityStudent.setName(name2);
                            entityStudent.setStudentClass(class2);
                            entityStudent.setAge(age2);
                            if (onUpdateClickListener != null){
                                onUpdateClickListener.onUpdateClickListener(entityStudent);
                            }
                            name1.setText("");
                            class1.setText("");
                            age1.setText("");
                            alert.dismiss();
                            Log.e("Coming","Student Data" + name1 + class1 + age1);
                            Toast.makeText(context,"Data Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView Class;
        private TextView age;
        private ImageView deleteIcon;
        LinearLayout updateLayout;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            Class = itemView.findViewById(R.id.studentClass);
            age = itemView.findViewById(R.id.age);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            updateLayout = itemView.findViewById(R.id.layout);
        }
    }
}
