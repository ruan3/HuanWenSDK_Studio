package com.example.huanwensdk.utils;

import java.security.MessageDigest;

public class MD5
{
  public static String getMD5(String paramString)
  {
    if (paramString == null)
      paramString = "";
    return getMD5(paramString.getBytes());
  }

  public static String getMD5(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      char[] arrayOfChar2 = new char[32];
      int i = 0;
      int j = 0;
      while (i < 16)
      {
        int k = arrayOfByte[i];
        int m = j + 1;
        arrayOfChar2[j] = arrayOfChar1[(0xF & k >>> 4)];
        j = m + 1;
        arrayOfChar2[m] = arrayOfChar1[(k & 0xF)];
        i++;
      }
      String str = new String(arrayOfChar2);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}