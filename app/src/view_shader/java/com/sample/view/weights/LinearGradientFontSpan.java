package com.sample.view.weights;

/**
 * @date: 2020/9/19
 * <p>
 * @author: ice_coffee
 * <p>
 * @remark:
 */

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * @ClassName: LinearGradientFontSpan
 * @Description: 文字渐变的Span类
 * @Author: ZL
 * @CreateDate: 2019/09/23 09:58
 */
public class LinearGradientFontSpan extends CharacterStyle implements UpdateAppearance {

    private String mText;
    private int mViewWidth;
    private int[] colors = new int[]{Color.RED, Color.GREEN};

    public LinearGradientFontSpan(String text, int viewWidth) {
        this.mText = text;
        this.mViewWidth = viewWidth;
    }

    @Override
    public void updateDrawState(TextPaint paint) {

        Shader shader = new LinearGradient(0, 0, mViewWidth, 0, colors, null, Shader.TileMode.REPEAT);
        paint.setShader(shader);
    }
}