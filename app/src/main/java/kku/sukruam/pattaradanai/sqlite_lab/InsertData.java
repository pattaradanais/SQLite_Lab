package kku.sukruam.pattaradanai.sqlite_lab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MThai on 2/2/2018.
 */

public class InsertData extends Activity implements View.OnClickListener {
    Button btn_insert;
    EditText edit_StdId,edit_StdName, edit_StdTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_insert);
        init();
        btn_insert.setOnClickListener(this);

    }

    private void init() {
        btn_insert = (Button) findViewById(R.id.btn_Insert);
        edit_StdId = (EditText) findViewById(R.id.edit_Id);
        edit_StdName = (EditText) findViewById(R.id.edit_Name);
        edit_StdTel = (EditText) findViewById(R.id.edit_Mobile);

    }

    @Override
    public void onClick(View v) {
        String stdId = edit_StdId.getText().toString();
        String stdName = edit_StdName.getText().toString();
        String stdTel = edit_StdTel.getText().toString();
        final MyDatabase myDB = new MyDatabase(this);
        long flag = myDB.insertData(stdId, stdName, stdTel);
        if (flag > 0) {
            Toast.makeText(getApplicationContext(), "Insert DataSuccessfully.", Toast.LENGTH_LONG).show();
            Log.d("Insert Data", "Insert successfully");
            Intent i = new Intent(getApplication(), main.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Insert DataSuccessfully.",Toast.LENGTH_LONG).show();
            Log.d("Insert Data", "Insert Failed");
        }
    }
}
