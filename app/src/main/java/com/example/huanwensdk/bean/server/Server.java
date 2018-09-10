package com.example.huanwensdk.bean.server;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * 
 * @Title:  Server.java   
 * @Package bean.server   
 * @Description:    服务器保存类
 * @author: Android_ruan     
 * @date:   2018-4-13 下午3:16:13   
 * @version V1.0
 */
@Table(name = "hw_server")
public class Server
  implements Parcelable
{
  public static final Parcelable.Creator<Server> CREATOR = new Parcelable.Creator()
  {
    public Server createFromParcel(Parcel paramParcel)
    {
      return new Server(paramParcel);
    }

    public Server[] newArray(int paramInt)
    {
      return new Server[paramInt];
    }
  };
  @Column(name = "address")//注释列名
  private String address;
  
  @Column(name = "gamecode")//注释列名
  private String gamecode;
  
  @Column(name = "port")//注释列名
  private String port;
  
  @Column(name = "repairNoticeContext")//注释列名
  private String repairNoticeContext;
  
  @Column(name = "serverName")//注释列名
  private String serverName;
  
  @Column(name = "servercode",isId = true)//注释列名
  private String servercode;
  
  @Column(name = "status")//注释列名
  private int status;

  public Server()
  {
  }

  protected Server(Parcel paramParcel)
  {
    this.servercode = paramParcel.readString();
    this.serverName = paramParcel.readString();
    this.address = paramParcel.readString();
    this.port = paramParcel.readString();
    this.status = paramParcel.readInt();
    this.gamecode = paramParcel.readString();
    this.repairNoticeContext = paramParcel.readString();
  }

  public int describeContents()
  {
    return 0;
  }

  public String getAddress()
  {
    return this.address;
  }

  public String getGamecode()
  {
    return this.gamecode;
  }

  public String getPort()
  {
    return this.port;
  }

  public String getRepairNoticeContext()
  {
    return this.repairNoticeContext;
  }

  public String getServercode()
  {
    return this.servercode;
  }

  public String getServername()
  {
    return this.serverName;
  }

  public int getStatus()
  {
    return this.status;
  }

  public void setAddress(String paramString)
  {
    this.address = paramString;
  }

  public void setGamecode(String paramString)
  {
    this.gamecode = paramString;
  }

  public void setPort(String paramString)
  {
    this.port = paramString;
  }

  public void setRepairNoticeContext(String paramString)
  {
    this.repairNoticeContext = paramString;
  }

  public void setServercode(String paramString)
  {
    this.servercode = paramString;
  }

  public void setServername(String paramString)
  {
    this.serverName = paramString;
  }

  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }

  public String toString()
  {
    return "Server{servercode='" + this.servercode + '\'' + ", serverName='" + this.serverName + '\'' + ", address='" + this.address + '\'' + ", port='" + this.port + '\'' + ", status=" + this.status + ", gamecode='" + this.gamecode + '\'' + ", repairNoticeContext='" + this.repairNoticeContext + '\'' + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.servercode);
    paramParcel.writeString(this.serverName);
    paramParcel.writeString(this.address);
    paramParcel.writeString(this.port);
    paramParcel.writeInt(this.status);
    paramParcel.writeString(this.gamecode);
    paramParcel.writeString(this.repairNoticeContext);
  }
}