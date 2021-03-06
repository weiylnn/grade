package com.leap.grade.widget.validator.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * NumberInputFilter : 小数点位数限制
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class NumberInputFilter implements InputFilter {
  private int precision;

  public NumberInputFilter(int precision) {
    this.precision = precision;
  }

  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    try {
      // 删除等特殊字符，直接返回
      if ("".equals(source.toString())) {
        return null;
      }
      if (!source.toString().matches("^[0-9\\.]*$")) {
        return "";
      }
      String dValue = dest.toString();
      String[] splitArray = dValue.split("\\.");
      int dotIndex = dValue.indexOf(".");
      if (splitArray.length > 1) {
        if (source.toString().startsWith(".")) {
          return "";// 输入多个点号
        }
        if (precision >= 0 && dstart > dotIndex) {
          String dotValue = splitArray[1];
          int diff = dotValue.length() + 1 - precision;
          if (diff > 0) {
            return source.subSequence(start, end - diff);
          }
        }
      } else {
        if (precision == 0 && source.toString().equals(".")) {
          return "";
        }
        if (dotIndex > 0 && source.toString().equals(".")) {
          return "";
        }
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}
