package com.example.huanwensdk.utils;

public class HWViewConfig {
	private int bottomStatusHeight = 0;
	private float designHeight = 1920.0F;
	private float designInnerHeight = 1020.0F;
	private float designInnerWidth = 1020.0F;
	private float designWidth = 1080.0F;
	private int height;
	private boolean isPad;
	private boolean isStandardPx = true;
	private boolean screenOrientation;
	private double textRate = 1.0D;
	private int width;

	public int getBottomStatusHeight() {
		return this.bottomStatusHeight;
	}

	public float getDesignHeight() {
		return this.designHeight;
	}

	public float getDesignInnerHeight() {
		return this.designInnerHeight;
	}

	public float getDesignInnerWidth() {
		return this.designInnerWidth;
	}

	public float getDesignWidth() {
		return this.designWidth;
	}

	public int getHeight() {
		return this.height;
	}

	public double getTextRate() {
		return this.textRate;
	}

	public int getWidth() {
		return this.width;
	}

	public boolean isPad() {
		return this.isPad;
	}

	public boolean isScreenOrientation() {
		return this.screenOrientation;
	}

	public boolean isStandardPx() {
		return this.isStandardPx;
	}

	public void setBottomStatusHeight(int paramInt) {
		this.bottomStatusHeight = paramInt;
	}

	public void setDesignHeight(float paramFloat) {
		this.designHeight = paramFloat;
	}

	public void setDesignInnerHeight(float paramFloat) {
		this.designInnerHeight = paramFloat;
	}

	public void setDesignInnerWidth(float paramFloat) {
		this.designInnerWidth = paramFloat;
	}

	public void setDesignWidth(float paramFloat) {
		this.designWidth = paramFloat;
	}

	public void setHeight(int paramInt) {
		this.height = paramInt;
	}

	public void setPad(boolean paramBoolean) {
		this.isPad = paramBoolean;
	}

	public void setScreenOrientation(boolean paramBoolean) {
		this.screenOrientation = paramBoolean;
	}

	public void setStandardPx(boolean paramBoolean) {
		this.isStandardPx = paramBoolean;
	}

	public void setTextRate(double paramDouble) {
		this.textRate = paramDouble;
	}

	public void setWidth(int paramInt) {
		this.width = paramInt;
	}

	public String toString() {
		return "FGViewConfig{width=" + this.width + ", height=" + this.height
				+ ", screenOrientation=" + this.screenOrientation
				+ ", isStandardPx=" + this.isStandardPx + ", isPad="
				+ this.isPad + ", bottomStatusHeight="
				+ this.bottomStatusHeight + ", designWidth=" + this.designWidth
				+ ", designHeight=" + this.designHeight + ", designInnerWidth="
				+ this.designInnerWidth + ", designInnerHeight="
				+ this.designInnerHeight + ", textRate=" + this.textRate + '}';
	}
}
