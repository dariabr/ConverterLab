package com.joyfullkiwi.converterlab.Detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.joyfullkiwi.converterlab.Models.CurrencyModel;
import com.joyfullkiwi.converterlab.R;
import java.util.List;

public class BankView extends View {

    private Paint bitmapPaint;
    private Paint textPaint;
    private int width;
    private String title;
    private String region;
    private String city;
    private Bitmap resultBitmap;
    private List<CurrencyModel> currencyModel;

    public void setData(String title, String region, String city, List<CurrencyModel> currencyModel) {
        this.title = title;
        this.region = region;
        this.city = city;
        this.currencyModel = currencyModel;
        postInvalidate();
    }

    public BankView(Context context) {
        super(context);
        init();
        setClickable(false);
    }

    public BankView(Context context,
                    @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = getWidth();
            }
        });

    }

    private float getSize(int size) {
        return TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, size, getResources().getDisplayMetrics());
    }

    private Bitmap createBitmapInfo() {
        Bitmap bitmap = Bitmap.createBitmap(width, getBitmapHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        canvas.drawColor(Color.WHITE);

        textPaint.setTextSize(getSize(25));
        textPaint.setTextAlign(Align.LEFT);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.primary_text));
        textPaint.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        canvas.drawText(title, 30, 100, textPaint);

        textPaint.setTextSize(getSize(18));
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        textPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

        canvas.drawText(region, 30, 160, textPaint);
        canvas.drawText(city, 30, 210, textPaint);

        int positionY = 350;

        for (CurrencyModel model : currencyModel) {
            textPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            textPaint.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            textPaint.setTextSize(getSize(25));
            textPaint.setTextAlign(Align.LEFT);
            canvas.drawText(model.getCurrencyId(), 80, positionY, textPaint);

            textPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            textPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
            textPaint.setTextAlign(Align.RIGHT);
            textPaint.setTextSize(getSize(25));
            String rate = model.getPrices().get(0).getAsk() + "/" + model.getPrices().get(0).getBid();
            canvas.drawText(rate, width - 80, positionY, textPaint);

            positionY += 100;
        }
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, getBitmapHeight());
    }

    public int getBitmapHeight() {
        //Расчет размера вью
        int positionY = 350;
        for (int i = 0; i < currencyModel.size(); i++) {
            positionY += i != currencyModel.size() - 1 ? 100 : 50;
        }
        return positionY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        resultBitmap = createBitmapInfo();

        canvas.drawBitmap(resultBitmap, 0, 0, bitmapPaint);

    }

    public Bitmap getResultBitmap() {
        return resultBitmap;
    }
}
