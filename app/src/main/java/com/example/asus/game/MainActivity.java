package com.example.asus.game;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {
    private Button toPlay;
    private Button setDifficulty;
    private Button Rules;
    private Button exidGame;
    public String level="10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开始游戏
        toPlay=(Button)findViewById(R.id.toPlay);
        toPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("level",level);//用于将设置的难度等级传给GameActivity中的level
                intent.setClass(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
        //设置难度
        setDifficulty=(Button)findViewById(R.id.setDifficulty);
        setDifficulty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                final EditText editText=new EditText(view.getContext());
                new Builder(view.getContext()).setTitle("请选择游戏难度(10-20 初始难度为10):").setView(editText).setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        level=editText.getText().toString().trim();
                        if (level.compareTo("10")<0||level.compareTo("20")>0){      //当设置的难度不在10-20内时，弹出提示窗口并将难度重新设置为10
                            new Builder(view.getContext()).setMessage("错误，请重新选择难度").setNegativeButton("确定",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    level="10";
                                }
                            }).show();
                            }
                    }
                }).setNegativeButton("取消",null).show();
            }
        });
        //规则说明
        Rules=(Button)findViewById(R.id.Rules);
        Rules.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new Builder(view.getContext()).setMessage("点击揭开格子，长按标记格子；把所有非地雷的格子揭开即胜利，若揭开有地雷格子则失败").setPositiveButton("确定",null).show();
            }
        });
        //退出程序
        exidGame=(Button)findViewById(R.id.exidGame);
        exidGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
