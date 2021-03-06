package com.leap.grade.util;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.leap.base.util.IsEmpty;
import com.leap.base.util.KeyBoardUtil;
import com.leap.base.widget.LoadingLayout;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * BaseBarActivity : 基础Activity
 * <p>
 * </> Created by ylwei on 2018/3/26.
 */
public abstract class BaseBarActivity extends RxAppCompatActivity {
  private boolean keyboardAutoHide = true;
  protected ImmersionBar mStatusBar;
  protected Context context;
  protected LifecycleProvider provider;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    provider = this;
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    initComponent();
    createEventHandlers();
    loadData(savedInstanceState);
    if (useStatusBar())
      initStatusBar();
  }

  protected void initStatusBar() {
    mStatusBar = ImmersionBar.with(this);
    if (statusBarView() != null)
      mStatusBar.statusBarView(statusBarView());
    if (isDarkFont())
      mStatusBar.statusBarDarkFont(true, 0.2f);
    if (keyboardEnable())
      mStatusBar.keyboardEnable(true);
    mStatusBar.init();
  }

  protected boolean useStatusBar() {
    return true;
  }

  protected View statusBarView() {
    return null;
  }

  protected boolean isDarkFont() {
    return true;
  }

  protected boolean keyboardEnable() {
    return false;
  }

  /**
   * 初始化界面控件
   */
  protected abstract void initComponent();

  /**
   * 初次加载数据
   */
  protected abstract void loadData(Bundle savedInstanceState);

  /**
   * 界面事件响应
   */
  protected void createEventHandlers() {
  }

  /**
   * 设置软键盘是否自动隐藏
   */
  protected void setKeyboardAutoHide(boolean b) {
    this.keyboardAutoHide = b;
  }

  /**
   * 点击空白处隐藏软键盘
   */
  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      View v = getCurrentFocus();
      if (isEdt(v, ev) && keyboardAutoHide)
        KeyBoardUtil.keyShow(v, false);
      return super.dispatchTouchEvent(ev);
    }
    if (getWindow().superDispatchTouchEvent(ev))
      return getWindow().superDispatchTouchEvent(ev);
    return onTouchEvent(ev);
  }

  /**
   * 判断当前焦点是否是输入框
   */
  public boolean isEdt(View v, MotionEvent event) {
    if (v != null && (v instanceof EditText)) {
      int[] leftTop = {
          0, 0 };
      v.getLocationInWindow(leftTop);
      int left = leftTop[0];
      int top = leftTop[1];
      int bottom = top + v.getHeight();
      int right = left + v.getWidth();
      return !(event.getX() > left && event.getX() < right && event.getY() > top
          && event.getY() < bottom);
    }
    return false;
  }

  @Override
  protected void onDestroy() {
    if (mStatusBar != null)
      mStatusBar.destroy();
    super.onDestroy();
  }

  protected LoadingLayout getLoadingLayout() {
    return null;
  }

  protected void startLoading() {
    if (!IsEmpty.object(getLoadingLayout()))
      getLoadingLayout().startLoading();
  }

  protected void stopLoading() {
    if (!IsEmpty.object(getLoadingLayout()))
      getLoadingLayout().stopLoading();
  }
}
