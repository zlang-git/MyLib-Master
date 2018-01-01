package com.zlang.baselib.listener;

import android.os.Bundle;

/**
 * Created by zlang on 2017/12/22.
 */

public interface BasicViewInterface {

    /**
     * 资源文件xml
     * @return
     */
    int getContentLayout();

    /**
     * 如果有titleBar   使用titleBar
     */
    void initTitle();

    /**
     * 加载view
     * @param savedInstanceState
     */
    void initView(Bundle savedInstanceState);

    /**
     * 加载数据
     */
    void initData();

}
