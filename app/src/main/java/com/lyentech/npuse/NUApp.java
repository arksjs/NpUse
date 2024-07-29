package com.lyentech.npuse;

import android.app.Application;

import com.lyentech.sdk.GreeNp;

import java.util.ArrayList;

public class NUApp extends Application {
   @Override
   public void onCreate() {
      super.onCreate();
//      GreeNp.initSdk(this,"02EBA73C32A2AF47552FA411135E2CBA",true);


      String appKey = "02EBA73C32A2AF47552FA411135E2CBA";
      ArrayList<String> appKeyArr = new ArrayList<>();
      appKeyArr.add("0D511ECC570BAA9BDAE6075B9F0336FC");
      ArrayList<String> eventArr = new ArrayList<>();
      eventArr.add("first_data_download");
      eventArr.add("second_data_download");
      //多项目统计
      GreeNp.initSdk(this, appKey, appKeyArr, eventArr,true);
   }
}
