package kku.sukruam.pattaradanai.sqlite_lab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class main extends Activity implements View.OnClickListener {
    Button btnAdd, btnSearch;
    ArrayList<HashMap<String, String>> StudentList;
    ListView listView1;

    String tag_id = "StudentID", tag_name = "Name", tag_tel = "Tel", tag_edit = "Edit", tag_delete = "Delete";
    String[] strCmd = {tag_edit, tag_delete};
    EditText edit_search;
    public static String id_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        btnAdd.setOnClickListener(this);
        //new set on click
        btnSearch.setOnClickListener(this);

    }

    private void init() {
        btnAdd = (Button) findViewById(R.id.btn_Add);
        MyDatabase myDB = new MyDatabase(this);
        myDB.getWritableDatabase();

        //select data
        StudentList = myDB.SelectAllData();
        //listview
        listView1 = (ListView) findViewById(R.id.listView);

        SimpleAdapter simAdap;
        simAdap = new SimpleAdapter(getApplicationContext(), StudentList, R.layout.layout_row, new String[]{"StudentID", "Name", "Tel"}, new int[]{R.id.col_Id, R.id.col_name, R.id.col_mobile});
        listView1.setAdapter(simAdap);
        //register listview
        registerForContextMenu(listView1);
        edit_search = (EditText) findViewById(R.id.edit_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_Add:
                intent = new Intent(getApplicationContext(), InsertData.class);
                break;
            case R.id.btn_search:
                String strSearch = edit_search.getText().toString();
                MyDatabase myDB = new MyDatabase(this);
                myDB.getWritableDatabase();
                StudentList = myDB.SearchData(strSearch);
                SimpleAdapter simAdap;
                simAdap = new SimpleAdapter(getApplicationContext(), StudentList, R.layout.layout_row,
                        new String[]{"StudentID", "Name", "Tel"}, new int[]{R.id.col_Id, R.id.col_name, R.id.col_mobile});
                listView1.setAdapter(simAdap);

                break;
        }
        if (intent != null) {
            startActivity(intent);
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderIcon(android.R.drawable.ic_menu_edit);
        menu.setHeaderTitle("[ " + StudentList.get(info.position).get(tag_name).toString() + " ]");
        String[] menuItems = strCmd;
        for (int i = 0; i < menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }
        Log.d("Test 1", "pass 1");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItem = strCmd;
        String CmdName = menuItem[menuItemIndex];
        Log.d("Test 2", "pass 2");
        Toast.makeText(getApplicationContext(), "Test Dialog ", Toast.LENGTH_LONG).show();
        String id = StudentList.get(info.position).get(tag_id).toString();
        String name = StudentList.get(info.position).get(tag_name).toString();
        String tel = StudentList.get(info.position).get(tag_tel).toString();

        main.id_delete = id;

        //check event command
        if (tag_edit.equals(CmdName)) {
            Intent i = new Intent(getApplicationContext(), EditData.class);
            i.putExtra(tag_id, id);
            i.putExtra(tag_name, name);
            i.putExtra(tag_tel, tel);
            Toast.makeText(getApplicationContext(), "Edit (StudentID = " + id + " )", Toast.LENGTH_LONG).show();
            startActivity(i);
        } else if (tag_delete.equals(CmdName)) {
            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);
            viewDetail.setIcon(android.R.drawable.ic_delete);
            viewDetail.setTitle("Confirm Delete ?");
            viewDetail.setMessage("ID : " + id + "\nName : " + name + "\nTel : " + tel + "");
            viewDetail.setPositiveButton("Delete",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //delete
                            MyDatabase db = new MyDatabase(getApplicationContext());
                            db.getWritableDatabase();//write data
                            long flag = db.DeleteData(main.id_delete);
                            if (flag > 0) {
                                Toast.makeText(getApplicationContext(), "Delete (StudentID = "
                                        + main.id_delete + " ) Successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), main.class);
                                startActivity(i);
                                finish();
                                main.id_delete = "";
                            } else {
                                Toast.makeText(getApplicationContext(), "Delete failed ", Toast.LENGTH_LONG).show();

                            }

                        }
                    });
            viewDetail.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            viewDetail.show();

        }
        return super.onContextItemSelected(item);
    }

}

