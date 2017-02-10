package com.zchk.yunzichan.utils;


import android.util.Base64;

public class Encrypt
{
  //Base64加密
  public static String getBase64(String str)
  {
    if (str == null)
    {
      return null;
    }

    return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);

  }
  
  //Dase64解密
  public static String getFromBase64(String str)
  {
    if (str == null)
    {
      return null;
    }
    

    try
    {

      return new String(Base64.decode(str,Base64.DEFAULT));
    }
    catch (Exception e)
    {
      return null;
    }
  }
}
