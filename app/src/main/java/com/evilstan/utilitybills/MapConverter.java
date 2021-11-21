package com.evilstan.utilitybills;

import androidx.room.TypeConverter;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Map;

public class MapConverter {
    @TypeConverter
    public static Map<String, Double> toUtilityPayments(String value) {
        Type mapType = new TypeToken<Map<String, Double>>() {}.getType();
        return new Gson().fromJson(value, mapType);
    }

    @TypeConverter
    public static String fromUtilityPayments(Map<String, Double> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
