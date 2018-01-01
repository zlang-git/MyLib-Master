package com.zlang.baselib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aries.ui.view.title.TitleBarView;
import com.google.gson.Gson;
import com.zlang.baselib.listener.BasicViewInterface;
import com.zlang.baselib.utils.AppPresences;
import com.zlang.baselib.widget.AppActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;

/**
 * Created by zlang on 2017/12/21.
 */

public abstract class MBaseTitleFragment extends Fragment implements BasicViewInterface{

    Unbinder unbinder;
    public View mView;
    protected OkHttpClient okHttpClient ;
    protected Activity mActivity;
    protected Context mContext;
    protected LayoutInflater mInflater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(getContentLayout(), container, false);
        AppPresences.init(getActivity());
        okHttpClient = new OkHttpClient();
        mContext = getActivity().getApplicationContext();
        mInflater = LayoutInflater.from(getActivity());
        mActivity = getActivity();
        unbinder = ButterKnife.bind(this,mView);//绑定fragment
        EventBus.getDefault().register(this);
        initTitle();
        initView(savedInstanceState);
        initData();
        return mView;
    }

    @Subscribe
    public void onEventMainThread(Object object){
        //接收到发布者发布的事件后，进行相应的处理操作
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 时间转换为long
     * @param date
     * @return
     */
    protected String dateFormartToLong(String date){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(new Date(Long.valueOf(date)));
    }

    /**
     * 将对象转换为JSON字符串(Object to json string)
     * @param object
     * @return
     */
    protected String objectToJson(Object object){
        return "["+new Gson().toJson(object)+"]" + "ζ";
    }

    /** start activity with left in anim */
    public  void startActivityWithAnim(Activity old,Intent intent){

        if(old == null || intent == null){
            return ;
        }

        // start activity
        old.startActivity(intent) ;
        // set adnim
        old.overridePendingTransition(getIdByName("anim", "push_left_in"),getIdByName("anim", "push_not_move"));
    }

    public  void startActivityWithAnim(Activity old, Intent intent, int requestCode){

        if(old == null || intent == null){
            return ;
        }

        // start activity
        old.startActivityForResult(intent, requestCode);
        // set adnim
        old.overridePendingTransition(getIdByName("anim", "push_left_in"),getIdByName("anim", "push_not_move"));
    }

    /** finish activity with left out anim */
    public  void finishWithAnim(Activity old){
        old.finish() ;
        old.overridePendingTransition(getIdByName("anim", "push_not_move"),getIdByName("anim", "push_left_out"));
    }

    public  void finishWithAnim(Activity old,int resultCode){
        old.setResult(resultCode);
        old.finish() ;
        old.overridePendingTransition(getIdByName("anim", "push_not_move"),getIdByName("anim", "push_left_out"));
    }

    public  int getIdByName(String className, String name) {
        String packageName = getActivity().getPackageName();
        Class r = null;
        int id = 0;
        try {
            r = Class.forName(packageName + ".R");

            Class[] classes = r.getClasses();
            Class desireClass = null;

            for (int i = 0; i < classes.length; ++i) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;
                }
            }

            if (desireClass != null)
                id = desireClass.getField(name).getInt(desireClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    protected void showToast(String content){
        Toast toast = Toast.makeText(mContext, content,Toast.LENGTH_SHORT);
//        if(isPad()){
//            toast.setGravity(Gravity.CENTER, 0, 0);
//        }

        toast.show();
    }

    protected void back(){
        getActivity().finish();
        AppActivityManager.getInstance().removeActivity(getActivity());
        if(AppActivityManager.getInstance().getActivityStack().size() != 0){
            getActivity().overridePendingTransition(getIdByName("anim", "push_not_move"),getIdByName("anim", "push_left_out"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

}
