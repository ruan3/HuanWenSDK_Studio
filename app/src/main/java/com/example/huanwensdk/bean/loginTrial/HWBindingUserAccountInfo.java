package com.example.huanwensdk.bean.loginTrial;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 
 * @Title:  HWBindingUserAccountInfo.java   
 * @Package bean.loginTrial   
 * @Description:    试玩游戏登录返回的绑定字段
 * @author: Android_ruan     
 * @date:   2018-3-27 上午10:35:29   
 * @version V1.0
 */
public class HWBindingUserAccountInfo
implements Parcelable
{
public static final Parcelable.Creator<HWBindingUserAccountInfo> CREATOR = new Parcelable.Creator()
{
  public HWBindingUserAccountInfo createFromParcel(Parcel paramParcel)
  {
    return new HWBindingUserAccountInfo(paramParcel);
  }

  public HWBindingUserAccountInfo[] newArray(int paramInt)
  {
    return new HWBindingUserAccountInfo[paramInt];
  }
};
private int type;
private String username;

public HWBindingUserAccountInfo()
{
}

public HWBindingUserAccountInfo(Parcel paramParcel)
{
  this.username = paramParcel.readString();
  this.type = paramParcel.readInt();
}

public int describeContents()
{
  return 0;
}

public int getType()
{
  return this.type;
}

public String getUsername()
{
  return this.username;
}

public void setType(int paramInt)
{
  this.type = paramInt;
}

public void setUsername(String paramString)
{
  this.username = paramString;
}

public String toString()
{
  return "FGBindingUserAccountInfo{type=" + this.type + ", username='" + this.username + '\'' + '}';
}

public void writeToParcel(Parcel paramParcel, int paramInt)
{
  paramParcel.writeString(this.username);
  paramParcel.writeInt(this.type);
}
}