package com.example.huanwensdk.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HWFixHelper {
	private static final int DRAWABLE_PADDING = 13;
	private static final int HEIGHT = 7;
	private static final int MARGIN = 8;
	private static final int MARGIN_BOTTOM = 12;
	private static final int MARGIN_LEFT = 9;
	private static final int MARGIN_RIGHT = 11;
	private static final int MARGIN_TOP = 10;
	private static final String MATCH_PARENT = "-1";
	private static final int PADDING = 1;
	private static final int PADDING_BOTTOM = 5;
	private static final int PADDING_LEFT = 2;
	private static final int PADDING_RIGHT = 4;
	private static final int PADDING_TOP = 3;
	private static final int TEXT_SIZE = 0;
	private static final int WIDTH = 6;
	private static final String WRAP_CONTENT = "-2";
	private static Context context;
	private static final int[] designAttrs;
	public static final ViewType WINDOWS = null;
	public static float designPad = 1080.0F;
	private static View dynamicView;
	private static HWFixHelper instance;
	private int bottomStatusHeight;
	public float designHeight = 1920.0F;
	public float designInnerHeight = 1020.0F;
	public float designInnerWidth = 1020.0F;
	public float designWidth = 1080.0F;
	public List<HWFixAttributeSet> fixAttrsList;
	public int height;
	public int innerHeight;
	public int innerWidth;
	public boolean isDesign = true;
	private boolean isPad;
	private boolean isStandardPx = true;
	private boolean screenOrientation;
	private double textRate = 1.0D;
	public int width;

	static {
		designAttrs = new int[] { 16842901, 16842965, 16842966, 16842967,
				16842968, 16842969, 16842996, 16842997, 16842998, 16842999,
				16843000, 16843001, 16843002, 16843121 };
	}

	private int getComplexUnit(int paramInt) {
		return 0xF & paramInt >> 0;
	}

	public static float getFixHeight(Context paramContext, float paramFloat) {
		getInstance().init(paramContext);
		return getInstance().getHeightSize(paramFloat);
	}

	public static float getFixInnerHeight(Context paramContext, float paramFloat) {
		getInstance().init(paramContext);
		return getInstance().getInnerHeightSize(paramFloat);
	}

	public static float getFixInnerWidth(Context paramContext, float paramFloat) {
		getInstance().init(paramContext);
		return getInstance().getInnerWidthSize(paramFloat);
	}

	public static float getFixWidth(Context paramContext, float paramFloat) {
		getInstance().init(paramContext);
		return getInstance().getWidthSize(paramFloat);
	}

	private float getHeight(ViewType paramViewType, float paramFloat) {
		if (paramViewType == HWFixHelper.ViewType.WINDOWS)
			return getInnerHeightSize(paramFloat);
		return getHeightSize(paramFloat);
	}

	private static HWFixHelper getInstance() {
		if (instance == null)
			instance = new HWFixHelper();
		return instance;
	}

	private float getWidth(ViewType paramViewType, float paramFloat) {
		if (paramViewType == ViewType.WINDOWS)
			return getInnerWidthSize(paramFloat);
		return getWidthSize(paramFloat);
	}

	public static HWFixHelper newInstance(Context paramContext) {
		HWFixHelper localHWFixHelper = new HWFixHelper();
		localHWFixHelper.init(paramContext);
		localHWFixHelper.initAttrsList();
		return localHWFixHelper;
	}

	public Builder on(View paramView) {
		dynamicView = paramView;
		getInstance().init(paramView.getContext());
		return new Builder();
	}

	private void setAttrs(final ViewType paramViewType, final View paramView,
			final HWFixAttributeSet paramFGFixAttributeSet) {
		
		paramView.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				setTextSize(paramViewType, paramView,
						paramFGFixAttributeSet);
				setViewParams(paramViewType, paramView,
						paramFGFixAttributeSet);
				setViewPadding(paramViewType, paramView,
						paramFGFixAttributeSet);
				setViewDrawablePadding(paramViewType,
						paramView, paramFGFixAttributeSet);
			}
		});
		
	}

	private void setTextSize(ViewType paramViewType, View paramView,
			HWFixAttributeSet paramFGFixAttributeSet) {
		if (((paramView instanceof TextView))
				&& (paramFGFixAttributeSet != null)) {
			float f = getWidth(paramViewType,
					paramFGFixAttributeSet.getTextSize());
			if (f != 0.0F)
				((TextView) paramView).setTextSize(0,
						(float) (f * this.textRate));
		}
	}

	private void setViewPadding(ViewType paramViewType, View paramView,
			HWFixAttributeSet paramFGFixAttributeSet) {
		int i = paramView.getPaddingLeft();
		int j = paramView.getPaddingRight();
		int k = paramView.getPaddingTop();
		int m = paramView.getPaddingBottom();
		boolean bool = paramFGFixAttributeSet.getPadding() < 0.0F;
		int n = 0;
		if (bool) {
			n = 1;
			j = (int) getWidth(paramViewType,
					paramFGFixAttributeSet.getPadding());
			i = j;
			m = (int) getHeight(paramViewType,
					paramFGFixAttributeSet.getPadding());
			k = m;
		}
		if (paramFGFixAttributeSet.getPaddingLeft() != 0.0F) {
			n = 1;
			i = (int) getWidth(paramViewType,
					paramFGFixAttributeSet.getPaddingLeft());
		}
		if (paramFGFixAttributeSet.getPaddingTop() != 0.0F) {
			n = 1;
			k = (int) getWidth(paramViewType,
					paramFGFixAttributeSet.getPaddingTop());
		}
		if (paramFGFixAttributeSet.getPaddingRight() != 0.0F) {
			n = 1;
			j = (int) getWidth(paramViewType,
					paramFGFixAttributeSet.getPaddingRight());
		}
		if (paramFGFixAttributeSet.getPaddingBottom() != 0.0F) {
			n = 1;
			m = (int) getWidth(paramViewType,
					paramFGFixAttributeSet.getPaddingBottom());
		}
		if (n != 0)
			paramView.setPadding(i, k, j, m);
	}

	private void setViewParams(ViewType paramViewType, View paramView,
			HWFixAttributeSet paramFGFixAttributeSet) {
		ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
		boolean bool = paramFGFixAttributeSet.getWidth() < 0.0F;
		int i = 0;
		if (bool) {
			i = 1;
			localLayoutParams.width = (int) getWidth(paramViewType,
					paramFGFixAttributeSet.getWidth());
		}
		if (paramFGFixAttributeSet.getHeight() != 0.0F) {
			i = 1;
			localLayoutParams.height = (int) getHeight(paramViewType,
					paramFGFixAttributeSet.getHeight());
		}
		if ((localLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
			ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) localLayoutParams;
			if (paramFGFixAttributeSet.getMargin() != 0.0F) {
				i = 1;
				int j = (int) getWidth(paramViewType,
						paramFGFixAttributeSet.getMargin());
				localMarginLayoutParams.rightMargin = j;
				localMarginLayoutParams.leftMargin = j;
				int k = (int) getHeight(paramViewType,
						paramFGFixAttributeSet.getMargin());
				localMarginLayoutParams.bottomMargin = k;
				localMarginLayoutParams.topMargin = k;
			}
			if (paramFGFixAttributeSet.getMarginLeft() != 0.0F) {
				i = 1;
				localMarginLayoutParams.leftMargin = (int) getWidth(
						paramViewType, paramFGFixAttributeSet.getMarginLeft());
			}
			if (paramFGFixAttributeSet.getMarginTop() != 0.0F) {
				i = 1;
				localMarginLayoutParams.topMargin = (int) getHeight(
						paramViewType, paramFGFixAttributeSet.getMarginTop());
			}
			if (paramFGFixAttributeSet.getMarginRight() != 0.0F) {
				i = 1;
				localMarginLayoutParams.rightMargin = (int) getWidth(
						paramViewType, paramFGFixAttributeSet.getMarginRight());
			}
			if (paramFGFixAttributeSet.getMarginBottom() != 0.0F) {
				i = 1;
				localMarginLayoutParams.bottomMargin = (int) getHeight(
						paramViewType, paramFGFixAttributeSet.getMarginBottom());
			}
		}
		if (i != 0)
			paramView.setLayoutParams(localLayoutParams);
	}

	public void getAttrs(AttributeSet paramAttributeSet) {
		if (!this.isDesign)
			return;
		TypedArray localTypedArray = context.obtainStyledAttributes(
				paramAttributeSet, designAttrs);
		HWFixAttributeSet localFGFixAttributeSet = new HWFixAttributeSet();
		int i = 0;
		if (i < localTypedArray.getIndexCount()) {
			int j = localTypedArray.getIndex(i);
			if (!isPxVal(localTypedArray.peekValue(j)))
				;
				switch (j) {
				default:
					break;
				case 0:
					localFGFixAttributeSet.setTextSize(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 1:
					localFGFixAttributeSet.setPadding(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 3:
					localFGFixAttributeSet.setPaddingTop(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 4:
					localFGFixAttributeSet.setPaddingRight(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 2:
					localFGFixAttributeSet.setPaddingLeft(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 5:
					localFGFixAttributeSet.setPaddingBottom(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 6:
					localFGFixAttributeSet.setWidth(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 7:
					localFGFixAttributeSet.setHeight(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 8:
					localFGFixAttributeSet.setMargin(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 10:
					localFGFixAttributeSet.setMarginTop(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 11:
					localFGFixAttributeSet.setMarginRight(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 9:
					localFGFixAttributeSet.setMarginLeft(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 12:
					localFGFixAttributeSet.setMarginBottom(localTypedArray
							.getDimension(j, 0.0F));
					break;
				case 13:
					localFGFixAttributeSet.setDrawablePadding(localTypedArray
							.getDimension(j, 0.0F));
				}
			}
		this.fixAttrsList.add(localFGFixAttributeSet);
		localTypedArray.recycle();
	}

	public float getHeightSize(float paramFloat) {
		if (this.screenOrientation) {
			if (!this.isStandardPx) {
				if (this.isPad)
					return paramFloat / designPad
							* (this.width - this.bottomStatusHeight);
				return paramFloat / designPad * this.width;
			}
			return paramFloat / this.designHeight * this.height;
		}
		if (!this.isStandardPx)
			return paramFloat / designPad * this.height;
		return paramFloat / this.designWidth * this.height;
	}

	public float getInnerHeightSize(float paramFloat) {
		if (this.screenOrientation) {
			if (!this.isStandardPx) {
				if (this.isPad)
					;
				return paramFloat / designPad
						* (this.innerWidth - this.bottomStatusHeight);
			}
			return paramFloat / this.designInnerHeight * this.innerHeight;
		}
		if (!this.isStandardPx)
			return paramFloat / designPad * this.innerHeight;
		return paramFloat / this.designInnerWidth * this.innerHeight;
	}

	public float getInnerWidthSize(float paramFloat) {
		if (this.screenOrientation) {
			if (!this.isStandardPx) {
				if (this.isPad)
					return paramFloat / designPad
							* (this.innerWidth - this.bottomStatusHeight);
				return paramFloat / designPad * this.innerWidth;
			}
			return paramFloat / this.designInnerWidth * this.innerWidth;
		}
		if (!this.isStandardPx)
			return paramFloat / designPad * this.innerHeight;
		return paramFloat / this.designInnerHeight * this.innerWidth;
	}

	public float getWidthSize(float paramFloat) {
		if (this.screenOrientation) {
			if (!this.isStandardPx) {
				if (this.isPad)
					return paramFloat / designPad
							* (this.width - this.bottomStatusHeight);
				return paramFloat / designPad * this.width;
			}
			return paramFloat / this.designWidth * this.width;
		}
		if (!this.isStandardPx)
			return paramFloat / designPad * this.height;
		return paramFloat / this.designHeight * this.width;
	}

	public void init(Context paramContext) {
		context = paramContext;
		
//		HWViewConfig localFGViewConfig = HWFixView.getInstance().getViewConfig(
//				paramContext);
		HWViewConfig localFGViewConfig = new HWViewConfig();
		localFGViewConfig.setWidth(400);
		localFGViewConfig.setHeight(600);
		LogUtils.e("context--->"+context);
		LogUtils.e("localFGView--->"+localFGViewConfig);
		this.designWidth = localFGViewConfig.getDesignWidth();
		this.designHeight = localFGViewConfig.getDesignHeight();
		this.designInnerWidth = localFGViewConfig.getDesignInnerWidth();
		this.designInnerHeight = localFGViewConfig.getDesignInnerHeight();
		this.width = localFGViewConfig.getWidth();
		this.height = localFGViewConfig.getHeight();
		this.screenOrientation = localFGViewConfig.isScreenOrientation();
		this.isStandardPx = localFGViewConfig.isStandardPx();
		if (!this.isStandardPx)
			this.bottomStatusHeight = localFGViewConfig.getBottomStatusHeight();
		this.isPad = localFGViewConfig.isPad();
		this.textRate = localFGViewConfig.getTextRate();
		if (this.screenOrientation) {
			this.innerWidth = (int) getWidthSize(this.designInnerWidth);
			this.innerHeight = (int) getHeightSize(this.designInnerHeight);
			return;
		}
		this.innerWidth = (int) getHeightSize(this.designInnerHeight);
		this.innerHeight = (int) getWidthSize(this.designInnerWidth);
	}

	public void initAttrsList() {
		this.fixAttrsList = new ArrayList();
	}

	public boolean isPxVal(TypedValue paramTypedValue) {
		return (paramTypedValue != null) && (paramTypedValue.type == 5)
				&& (getComplexUnit(paramTypedValue.data) == 0);
	}

	public void setViewAttr(HWFixHelper paramHWFixHelper,
			AttributeSet paramAttributeSet) {
		if (paramAttributeSet != null)
			paramHWFixHelper.getAttrs(paramAttributeSet);
	}

	public void setViewDrawablePadding(ViewType paramViewType, View paramView,
			HWFixAttributeSet paramFGFixAttributeSet) {
		if (((paramView instanceof TextView))
				&& (paramFGFixAttributeSet.getDrawablePadding() != 0.0F))
			((TextView) paramView)
					.setCompoundDrawablePadding((int) getWidth(paramViewType,
							paramFGFixAttributeSet.getDrawablePadding()));
	}

	public void setViews(ViewType paramViewType, View paramView,
			HWFixAttributeSet paramFGFixAttributeSet) {
		setAttrs(paramViewType, paramView, paramFGFixAttributeSet);
	}

	public class Builder {
		private HWFixAttributeSet fixAttrs = new HWFixAttributeSet();

		public void builder(HWFixHelper.ViewType paramViewType) {
			if (HWFixHelper.dynamicView != null)
				setViews(paramViewType,
						HWFixHelper.dynamicView, this.fixAttrs);
		}

		public Builder setHeight(float paramFloat) {
			this.fixAttrs.setHeight(paramFloat);
			return this;
		}

		public Builder setMargin(float paramFloat) {
			this.fixAttrs.setMargin(paramFloat);
			return this;
		}

		public Builder setMarginBottom(float paramFloat) {
			this.fixAttrs.setMarginBottom(paramFloat);
			return this;
		}

		public Builder setMarginLeft(float paramFloat) {
			this.fixAttrs.setMarginLeft(paramFloat);
			return this;
		}

		public Builder setMarginRight(float paramFloat) {
			this.fixAttrs.setMarginRight(paramFloat);
			return this;
		}

		public Builder setMarginTop(float paramFloat) {
			this.fixAttrs.setMarginTop(paramFloat);
			return this;
		}

		public Builder setPadding(float paramFloat) {
			this.fixAttrs.setPadding(paramFloat);
			return this;
		}

		public Builder setPaddingBottom(float paramFloat) {
			this.fixAttrs.setPaddingBottom(paramFloat);
			return this;
		}

		public Builder setPaddingLeft(float paramFloat) {
			this.fixAttrs.setPaddingLeft(paramFloat);
			return this;
		}

		public Builder setPaddingRight(float paramFloat) {
			this.fixAttrs.setPaddingRight(paramFloat);
			return this;
		}

		public Builder setPaddingTop(float paramFloat) {
			this.fixAttrs.setPaddingTop(paramFloat);
			return this;
		}

		public Builder setTextSize(float paramFloat) {
			this.fixAttrs.setTextSize(paramFloat);
			return this;
		}

		public Builder setWidth(float paramFloat) {
			this.fixAttrs.setWidth(paramFloat);
			return this;
		}
	}

	public static enum ViewType {
		SCREEN,
		WINDOWS;
		static {
			ViewType[] arrayOfViewType = new ViewType[2];
			arrayOfViewType[0] = SCREEN;
			arrayOfViewType[1] = WINDOWS;
		}
	}
}
