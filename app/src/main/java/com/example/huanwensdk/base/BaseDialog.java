package com.example.huanwensdk.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.huanwensdk.utils.ResLoader;

/**
 * @ClassName: BaseDialog
 * @Description: TODO(���жԻ���Ļ���)
 * @author ruan
 * @date 2017-6-13 ����10:05:42
 * 
 */
public class BaseDialog extends Dialog {

	Context context;
	
	public BaseDialog(Context context) {
//		super(context, R.style.dialog);
		super(context, ResLoader.getStyle(context, "dialog"));
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public BaseDialog(Context context, int style) {
		super(context, style);
		this.context = context;
	}

	/**
	 * @Description: TODO(����Ĳ����ļ�)
	 * @param @return �趨�ļ�
	 * @return ��View��
	 * @throws
	 */
	public FrameLayout getContentView() {
//		View v = this.findViewById(R.id.base_dialog_layout);
		View v = this.findViewById(ResLoader.getId(context, "base_dialog_layout"));
		if (v != null) {
			return (FrameLayout) v;
		}
		return null;
	}

	Drawable drawable = new ColorDrawable() {
		Paint paint = new Paint();

		@Override
		public void draw(Canvas canvas) {
			Rect rect = canvas.getClipBounds();
			paint.setStyle(Paint.Style.STROKE);

			paint.setStrokeWidth(0.5f);
			paint.setColor(Color.argb(200, 0, 0, 40));
			canvas.drawRoundRect(new RectF(0, 0, rect.width(), rect.height()),
					10, 10, paint);

			paint.setStrokeWidth(1.0f);
			paint.setColor(Color.argb(200, 200, 200, 200));
			for (int i = 1; i < 2; i++) {
				canvas.drawRoundRect(
						new RectF(i, i, rect.width() - i * 2,
								rect.height() - 1 * 2), 10, 10, paint);
			}

			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.argb(200, 0, 0, 40));

			canvas.drawRoundRect(
					new RectF(2, 2, rect.width() - 4, rect.height() - 4), 10,
					10, paint);

			paint.setStyle(Paint.Style.STROKE);

			float height = rect.height() * 0.2f;

			for (int i = 1; i <= height; i++) {
				paint.setColor(Color.argb(255 - i * 3, 200, 200, 200));
				canvas.drawCircle(rect.width() / 2, i - rect.width() * 2,
						rect.width() * 2, paint);
			}

		}
	};

	/**
	 * @Description: TODO(�����ⲿ�����ļ�)
	 * @param �趨�ļ�
	 * @return ���������ͣ�
	 * @throws
	 */
	@SuppressLint("NewApi") public void setContentView(int layoutResID) {

		/*
		 * LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		 * ViewGroup view = getContentView(); if(view != null){
		 * layoutInflater.inflate(layoutResID, view); super.setContentView(view,
		 * new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
		 * ViewGroup.LayoutParams.WRAP_CONTENT)); }
		 */
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//		FrameLayout contentView = (FrameLayout) layoutInflater.inflate(
//				R.layout.dialog_layout_base, null);
		FrameLayout contentView = (FrameLayout) layoutInflater.inflate(
				ResLoader.getLayout(context, "dialog_layout_base"), null);

//		contentView.setBackground(drawable);
		layoutInflater.inflate(layoutResID, contentView);
		super.setContentView(contentView, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
	}

	/**
	 * ����Ի������Ͻǿ��ԹرնԻ���
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getWindow().getDecorView();
			float disX = v.getWidth() - event.getX();
			float disY = event.getY();
			if ((disX > 0 && disX < v.getWidth() * 0.1)
					&& (disY > 0 && disY < v.getHeight() * 0.2)) {
				if (beforeDismiss()) {
					this.dismiss();
				}
				return true;
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * ����Ի������Ͻ�ʱ���Ƿ�رնԻ���Ĭ��Ϊ���ر�
	 * 
	 * @return
	 */
	public boolean beforeDismiss() {
		return false;
	}

}
