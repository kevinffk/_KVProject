package com.kv.iprojectdemo.db;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.kv.iprojectdemo.R;
import com.kv.iprojectdemo.db.helper.DbHelper;
import com.kv.iprojectdemo.db.model.Person;

public class OrmliteMainAct extends Activity implements OnClickListener {

    private Dao<Person, String> personDao;

    private Button btnAdd;

    private Button btnUpdate;

    private Button btnGet;

    private Button btnDelete;

    private TextView outTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ormlite_test);
        try {
            personDao = DbHelper.getHelper(this).getDao(Person.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        btnAdd = (Button) findViewById(R.id.btn1);
        btnUpdate = (Button) findViewById(R.id.btn2);
        btnGet = (Button) findViewById(R.id.btn3);
        btnDelete = (Button) findViewById(R.id.btn4);
        outTV = (TextView) findViewById(R.id.tv);
        
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnGet.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn1:
            try {
                long count = personDao.countOf();
                int ret = personDao.create(new Person("No_" + count, "kevin" + count, 11, "job" + count, "female", true));
                outTV.setText("add:" + ret);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
        case R.id.btn2:
            try {
                Person person = personDao.queryForId("No_" + 0);
                if (person != null) {
                    person.setAge(person.getAge() + 1);
                    int ret = personDao.update(person);
                    outTV.setText("update:" + ret);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
        case R.id.btn3:
            try {
                List<Person> personList = personDao.queryForAll();
                StringBuilder sb = new StringBuilder();
                for (Person person : personList) {
                    sb.append(person.getJobNo()).append("*");
                    sb.append(person.getName()).append("*");
                    sb.append(person.getAge()).append("*");
                    sb.append(person.getJob()).append("*");
                    sb.append(person.getSex()).append("*");
                    sb.append(person.isMarried());
                    sb.append("\n");
                }
                outTV.setText(sb.toString());
            } catch(SQLException e) {
                e.printStackTrace();
            }
            break;
        case R.id.btn4:
            try {
                List<Person> personList = personDao.queryForAll();
                if (personList != null && !personList.isEmpty()) {
                    int ret = personDao.delete(personList.get(personList.size() - 1));
                    long count = personDao.countOf();
                    outTV.setText("ret=" + ret + " " + count);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            break;
        default:
            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DbHelper.getHelper(this).close();
    }

    
}
