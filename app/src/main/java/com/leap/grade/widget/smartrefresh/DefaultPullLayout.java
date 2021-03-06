package com.leap.grade.widget.smartrefresh;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.leap.grade.widget.smartrefresh.base.support.impl.Pullable;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class DefaultPullLayout extends NestedScrollView implements Pullable {
  public DefaultPullLayout(Context context) {
    super(context);
  }

  public DefaultPullLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DefaultPullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean isGetBottom() {
    return false;
  }

  @Override
  public boolean isGetTop() {
    return true;
  }
}
