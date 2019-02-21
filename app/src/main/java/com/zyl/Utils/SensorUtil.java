package com.zyl.Utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.List;

public class SensorUtil {

   public void getAllSensor(Context context) {
       SensorManager mSmanager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
      // List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);
   }
}
