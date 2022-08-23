package com.lyentech.use;

import android.app.Application;

import com.lyentech.sdk.GreeNp;

public class NUApp extends Application {
   @Override
   public void onCreate() {
      super.onCreate();
      GreeNp.init(this,"02EBA73C32A2AF47552FA411135E2CBA",true);
   }
}
