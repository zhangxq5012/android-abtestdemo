package com.example.adhoc.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adhoc.adhocsdk.AdhocTracker;
import com.adhoc.adhocsdk.ExperimentFlags;
import com.example.adhoc.abtestdemo.MainActivity;
import com.example.adhoc.abtestdemo.R;
import com.example.adhoc.base.AdhocActivity;


public class FlagTestActivity extends AdhocActivity {

    private Button btn01;
    private TextView tv01;
    TextView tv_tracking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flag_test);
        tv01 = (TextView) findViewById(R.id.tv01);
        btn01 = (Button) findViewById(R.id.btn01);
        tv_tracking = (TextView) findViewById(R.id.tv_tracking);
        tv_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 统计key：‘payment’ value：100. "Payment" 为ADHOC 网站后台定义。
                AdhocTracker.incrementStat(FlagTestActivity.this, "revenue", 100);
                Toast.makeText(FlagTestActivity.this,"上报统计信息到ADHOC后台",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获取模块开关
        ExperimentFlags flags = AdhocTracker.getExperimentFlags(FlagTestActivity.this);
        if(flags!=null){
            // 'model01' 对应网站添加的产品模块名称
            boolean flag = flags.getBooleanFlag("module01",false);
            // 根据获取模块的值，开发不同的业务逻辑
            if(flag){
                Toast.makeText(FlagTestActivity.this,"has net flags is true" ,Toast.LENGTH_LONG).show();
                btn01.setBackgroundColor(getResources().getColor(android.R.color.black));
                btn01.setTextColor(getResources().getColor(android.R.color.white));
                btn01.setTextSize(getResources().getDimension(R.dimen.textsize_small));
                btn01.setText("实验版本B");
                tv_tracking.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(FlagTestActivity.this,"has net flags is false" ,Toast.LENGTH_LONG).show();
                btn01.setBackgroundColor(getResources().getColor(android.R.color.white));
                btn01.setTextColor(getResources().getColor(android.R.color.black));
                btn01.setTextSize(getResources().getDimension(R.dimen.textsize));
                btn01.setText("实验版本A");
                tv_tracking.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
