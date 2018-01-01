package com.zlang.baselib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import com.google.gson.Gson;
import com.zlang.baselib.utils.AppPresences;
import com.zlang.baselib.widget.AppActivityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import okhttp3.OkHttpClient;

/**
 * Created by zlang on 2017/12/14.
 * activity基类
 */

public class MBaseFragment extends Fragment{

//    protected Unbinder unbinder;
    protected String IMEI;
    protected OkHttpClient okHttpClient ;
    protected Activity mActivity;
    protected Context mContext;
    protected LayoutInflater mInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppPresences.init(getActivity());
        okHttpClient = new OkHttpClient();
        mContext = getActivity().getApplicationContext();
        mInflater = LayoutInflater.from(getActivity());
        mActivity = getActivity();
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

    protected void back(){
        getActivity().finish();
        AppActivityManager.getInstance().removeActivity(getActivity());
        if(AppActivityManager.getInstance().getActivityStack().size() != 0){
            getActivity().overridePendingTransition(getIdByName("anim", "push_not_move"),getIdByName("anim", "push_left_out"));
        }
    }

}
