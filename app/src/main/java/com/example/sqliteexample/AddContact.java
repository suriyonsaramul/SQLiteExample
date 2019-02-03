package com.example.sqliteexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity implements View.OnClickListener {

    private Button btnAdd;
    private EditText edtName, edtEmail;
    private ContactSQLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initComponent();
        setTitle("Add Contact");
        helper = new ContactSQLiteHelper(this);
        btnAdd.setOnClickListener(this);
    }

    private void initComponent() {
        btnAdd = findViewById(R.id.btn_add);
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();

                Contact contact = new Contact(name, email);
                helper.insert(contact);

                Intent intent = new Intent(AddContact.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
