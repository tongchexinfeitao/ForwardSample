package com.bw.forwardsample.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bw.forwardsample.R;
import com.bw.forwardsample.bean.StudentBean;
import com.bw.forwardsample.database.DaoMaster;
import com.bw.forwardsample.database.DaoSession;
import com.bw.forwardsample.database.StudentBeanDao;

import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {


    private StudentBeanDao studentBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        //根据 DaoMaster 去拿  DaoSession
        DaoSession daoSession = DaoMaster.newDevSession(this, "app.db");
        //根据 DaoSession 去拿  StudentBeanDao  ,增删改查 在 StudentBeanDao 中
        studentBeanDao = daoSession.getStudentBeanDao();
    }

    public void query(View view) {
        List<StudentBean> list = studentBeanDao.queryBuilder()
                //查询大于10岁的人
                .where(StudentBeanDao.Properties.AgeXxxx.ge(10))
                //根据年龄升序    Asc是升序 desc是降序
                .orderAsc(StudentBeanDao.Properties.AgeXxxx)
                .list();
        for (int i = 0; i < list.size(); i++) {
            Toast.makeText(GreenDaoActivity.this, list.get(i).getName(), Toast.LENGTH_SHORT).show();
        }
    }

    public void add(View view) {
        StudentBean studentBean = new StudentBean(null, "wang", 15);
        studentBeanDao.insert(studentBean);
    }

    public void update(View view) {
        List<StudentBean> list = studentBeanDao.queryBuilder()
                .where(StudentBeanDao.Properties.Name.eq("wang"))
                .list();
        for (int i = 0; i < list.size(); i++) {
            StudentBean studentBean = list.get(i);
            studentBean.setAgeXxxx(100);
            studentBeanDao.update(studentBean);
        }
    }

    public void delete(View view) {
        List<StudentBean> list = studentBeanDao.queryBuilder()
                .where(StudentBeanDao.Properties.Name.eq("wang"))
                .list();
        for (int i = 0; i < list.size(); i++) {
            StudentBean studentBean = list.get(i);
            studentBeanDao.delete(studentBean);
        }
    }
}
