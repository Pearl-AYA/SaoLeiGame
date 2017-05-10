package com.example.asus.game.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.asus.game.R;
import com.example.asus.game.entity.GameGroundEntity;
import com.example.asus.game.entity.GridEntity;




public class BoomAdapter extends BaseAdapter{
    private int level;
    private GridView gridView;
    private GameGroundEntity gameGround;
    private Context context;
    public BoomAdapter(int level,GridView gridView,Context context){
        this.level=level;
        this.gridView=gridView;
        this.context=context;
        this.gameGround=new GameGroundEntity(level);
    }
    @Override
    public int getCount() {
        return level*level;
    }
    /**
     * 方法：获取每个格子对象
     *  position 格子编号，位置下标
     *  格子类型的GameGroundEntity
     * */
    @Override
    public GridEntity getItem(int position) {
        //调用GameGroundEntity中的getEntity方法获取格子对象
        return gameGround.getEntity(position);
    }
    /**
     * 方法：通过适配器给每个格子对象编号或下标获取id值
     *  long类型，在java中，byte和short可自动转换为int，int可自动转换为long
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //判断适配器接收到的，用于显示游戏界面的控件是否为空，如果为空则指定用于显示游戏界面的xml文件
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.other,null);
        }
        ((ImageView)convertView).setImageResource(getRes(getItem(position)));
        AbsListView.LayoutParams params=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,gridView.getWidth()/level);
        convertView.setLayoutParams(params);
        return convertView;
    }
    /**
     * 方法：设置格子对象的背景图片
     * 不同状态下设置不同的背景图片
     *  grid :格子对象
     * */
    public int getRes(GridEntity grid){
        //设置格子对象的背景图片的ID为0
        int resID=0;
        //如果格子对象被标记了且标记正确
        if(grid.isFlag()&&!grid.isFlagWrong()){
            resID=R.drawable.i_flag;
        }
        //如果格子对象被标记了但标记不正确
        else if(grid.isFlag()&&grid.isFlagWrong()){
            resID=R.drawable.i14;
        }
        //如果格子对象没有被点击，isShow()属性为false
        else if(!grid.isShow()){
            resID=R.drawable.i00;
        }
        //格子对象是地雷且非自动显示
        else if(grid.isBoom()&&!grid.isAutoShow()){
            resID=R.drawable.i13;
        }
        //格子对象是地雷，自动显示
        else if(grid.isBoom()&&grid.isAutoShow()){
            resID=R.drawable.i12;
        }
        //格子周围没有地雷，是空白格
        else if(grid.getBoomsCount()==0){
            resID=R.drawable.i09;
        }
        //格子中卫有地雷，个数为1-8个
        else if(grid.getBoomsCount()!=0){
            //动态拼接图片名，格式为图片名称，图片类型，资源所在包名
            resID=context.getResources().getIdentifier("i0"+grid.getBoomsCount(),"drawable",context.getPackageName());
        }
        return resID;
    }
    public boolean isWin(){
        return gameGround.isWin();
    }
    //在游戏结束时展示所有没被猜中的雷
    public void showAllBooms(){
        gameGround.showAllBooms();
        //notify通知、提醒，Data数据，Set设置，让Changed改变
        notifyDataSetChanged();
    }
    //展示无雷区域
    public void showNotBoomsArea(int position){
        gameGround.showNotBoomArea(position);
        notifyDataSetChanged();
    }
    //调用GameGroundEntity实例中的获取格子对象方法
    public GridEntity getEntity(int position){
        return gameGround.getEntity(position);
    }
    //检查标记状态的方法
    public void checkFlag(){
        gameGround.checkFlag();
        notifyDataSetChanged();
    }
}

