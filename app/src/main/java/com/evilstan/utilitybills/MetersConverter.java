package com.evilstan.utilitybills;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiresApi(api = VERSION_CODES.O)
public class MetersConverter {

    private final String REGEX = ",";

    @TypeConverter
    public String fromMetersId(List<Integer> metersIdList) {
        List<String> s = new ArrayList<>();
        metersIdList.forEach(i -> s.add(i.toString()));

        return String.join(REGEX, s);
    }


    public List<Integer> toMetersId(String text) {
        List<String> resultString = Arrays.asList(text.split(REGEX));
        List<Integer> result = new ArrayList<>();

        resultString.forEach(s -> result.add(Integer.valueOf(s)));
        return result;
    }

}
