package kku.sukruam.pattaradanai.sqlite_lab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



/**
 * Created by MThai on 2/9/2018.
 */

public class EditData extends Activity implements View.OnClickListener {
    Button btn_save,btn_cancel;
    EditText edit_UpName,edit_UpTel, edit_UpId;
    String tag_id = "StudentID",tag_name = "Name", tag_tel = "Tel";
    String stdId,name,tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        init();
    }

    private void init() {
        edit_UpName = (EditText) findViewById(R.id.edit_UpName);
        edit_UpId = (EditText) findViewById(R.id.edit_UpId);
        edit_UpTel = (EditText) findViewById(R.id.edit_UpMobile);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        //get intent extra
        Bundle bundle = getIntent().getExtras();
        stdId = bundle.getString(tag_id);
        name = bundle.getString(tag_name);
        tel = bundle.getString(tag_tel);
        //set text
        edit_UpId.setText(stdId);
        edit_UpName.setText(name);
        edit_UpTel.setText(tel);

    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.btn_cancel:
                i = new Intent(getApplicationContext(), main.class);
                break;
            case R.id.btn_save:
                MyDatabase myDB = new MyDatabase(this);
                myDB.getWritableDatabase();
                String newName = edit_UpName.getText().toString();
                String newTel = edit_UpTel.getText().toString();
                long flag = myDB.UpdateData(stdId, newName, newTel);
                if (flag > 0) {
                    i = new Intent(getApplicationContext(), main.class);
                    Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_LONG).show();
                }
        }
        if (i != null) {
            startActivity(i);
            finish();
        }
    }
}
