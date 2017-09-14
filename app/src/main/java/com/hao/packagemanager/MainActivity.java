package com.hao.packagemanager;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String SP_NAME = "config";
    private static String KEY_NOTICE = "notice";

    private List<ApplicationInfo> mAppList;
    private RecyclerView mContentRv;
    private PackageAdapter mAdapter;
    private ImageView mCloseIv;
    private View mNoticeLl;

    private SharedPreferences mShareSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShareSp = getSharedPreferences(SP_NAME, MODE_APPEND);
        initView();
        initPackageInfo();
    }

    /**
     * 初始化
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mNoticeLl = findViewById(R.id.notice_ll);
        mCloseIv = (ImageView) findViewById(R.id.close_iv);
        mCloseIv.setOnClickListener(this);
        mContentRv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mContentRv.setLayoutManager(layoutManager);
        mContentRv.setItemAnimator(new DefaultItemAnimator());
        mContentRv.addItemDecoration(new LinearItemDecoration(this,
                LinearLayoutManager.VERTICAL, R.dimen.divider_height, R.color.divider));
        mAdapter = new PackageAdapter(this);
        mContentRv.setAdapter(mAdapter);
    }

    /**
     * 加载安装包信息及设置提示栏状态
     */
    private void initPackageInfo() {
        mAppList = getPackageManager().getInstalledApplications(0);
        if (mAppList != null && !mAppList.isEmpty()) {
            mAdapter.addAll(mAppList);
        }
        boolean notice = mShareSp.getBoolean(KEY_NOTICE, false);
        mNoticeLl.setVisibility(notice ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.copy:
                String packages = mAdapter.getPackageInfo();
                if (!TextUtils.isEmpty(packages)) {
                    StringUtils.copy(this, "packages", packages);
                    Toast.makeText(this, R.string.copy_to_clipboard, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.select_apk, Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_iv:
                mShareSp.edit().putBoolean(KEY_NOTICE, true).apply();
                mNoticeLl.setVisibility(View.GONE);
                break;
        }
    }
}
