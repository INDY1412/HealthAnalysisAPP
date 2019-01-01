package me.inspalgo.healthanaylsisapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnGestureListener {
    // 定义手势检测器实例
    GestureDetector detector;

    private Button mButton;

    private double mHeight;
    private double mWeight;
    private int mAge;
    private int mSelectSex;
    private int mSelectSmoke;
    private int mSelectDrinking;
    private int mSelectTea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化手势检测器
        detector = new GestureDetector(this, this);

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 输入检查
                if (!inputCheck())
                    return;

                // 传值
                // mHeight mWeight mAge
                // mSelectSex mSelectSmoke mSelectDrinking mSelectTea


                // 接收来自远端的分析，此时出现等待
                for (int i = 0; i < 5; i++)
                    TransferData.sRadarChartData[i] = (i * 10 + i * 2 + i) * 0.01;

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                startActivity(intent);

                // 设置切换动画，从右边进入，左边退出
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


    }

    /**
     * 输入检查
     * 报错提示也直接在这里写
     */
    private boolean inputCheck() {
        EditText heightEt = findViewById(R.id.height);
        String inputHeight = heightEt.getText().toString();

        if (inputHeight.isEmpty()) {
            Toast.makeText(MainActivity.this, "请输入身高", Toast.LENGTH_SHORT).show();
            return false;
        }

        mHeight = .0;
        mHeight = Double.valueOf(inputHeight);

        if (mHeight < 0.0 || mHeight > 200.0) {
            Toast.makeText(MainActivity.this, "身高不合法", Toast.LENGTH_SHORT).show();
            return false;
        }

        EditText weightEt = findViewById(R.id.weight);
        String inputWeight = weightEt.getText().toString();

        if (inputWeight.isEmpty()) {
            Toast.makeText(MainActivity.this, "请输入体重", Toast.LENGTH_SHORT).show();
            return false;
        }

        mWeight = .0;
        mWeight = Double.valueOf(inputWeight);

        if (mWeight < 3.0 || mWeight > 150.0) {
            Toast.makeText(MainActivity.this, "体重不合法", Toast.LENGTH_SHORT).show();
            return false;
        }

        EditText ageEt = findViewById(R.id.age);
        String inputAge = ageEt.getText().toString();

        if (inputAge.isEmpty()) {
            Toast.makeText(MainActivity.this, "请输入年龄", Toast.LENGTH_SHORT).show();
            return false;
        }

        mAge = 0;
        mAge = Integer.valueOf(inputAge);

        if (mAge < 0 || mAge > 120) {
            Toast.makeText(MainActivity.this, "年龄不合法", Toast.LENGTH_SHORT).show();
            return false;
        }

        RadioButton manRb = findViewById(R.id.man);
        RadioButton womanRb = findViewById(R.id.woman);
        mSelectSex = 0;

        if (manRb.isChecked())
            mSelectSex = 1;
        if (womanRb.isChecked())
            mSelectSex = 2;
        if (mSelectSex == 0) {
            Toast.makeText(MainActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
            return false;
        }

        RadioButton smokeYesRb = findViewById(R.id.smoke_yes);
        RadioButton smokeNoRb = findViewById(R.id.smoke_no);
        mSelectSmoke = 0;

        if (smokeYesRb.isChecked())
            mSelectSmoke = 1;
        if (smokeNoRb.isChecked())
            mSelectSmoke = 2;
        if (mSelectSmoke == 0) {
            Toast.makeText(MainActivity.this, "请选择是否抽烟", Toast.LENGTH_SHORT).show();
            return false;
        }

        RadioButton drinkingYesRb = findViewById(R.id.drinking_yes);
        RadioButton drinkingNoRb = findViewById(R.id.drinking_no);
        mSelectDrinking = 0;

        if (drinkingYesRb.isChecked())
            mSelectDrinking = 1;
        if (drinkingNoRb.isChecked())
            mSelectDrinking = 2;
        if (mSelectDrinking == 0) {
            Toast.makeText(MainActivity.this, "请选择是否饮酒", Toast.LENGTH_SHORT).show();
            return false;
        }

        RadioButton teaYesRb = findViewById(R.id.tea_yes);
        RadioButton teaNoRb = findViewById(R.id.tea_no);
        mSelectTea = 0;

        if (teaYesRb.isChecked())
            mSelectTea = 1;
        if (teaNoRb.isChecked())
            mSelectTea = 2;
        if (mSelectTea == 0) {
            Toast.makeText(MainActivity.this, "请选择是否喝茶", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /*
     *
     *   以下是页面切换使用
     *
     * */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        // 如果向右滑动的距离大于 50mm
//        if (e1.getX() - e2.getX() > 50) {
//            Intent intent = new Intent(this, Main2Activity.class);
//            startActivity(intent);
//
//            // 设置切换动画，从右边进入，左边退出
//            overridePendingTransition(R.anim.right_in, R.anim.left_out);
//        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}
