package com.tmt.tmdt.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class TextUtil {
    public static String generateCode(String text) {
       text=text.toLowerCase(Locale.ROOT);
        String result = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        result = pattern.matcher(result).replaceAll("");
        result = result.replaceAll("đ", "d");
        result = result.replaceAll(" ", "-");

        return result;
    }


  
}
