package com.tmt.tmdt.util;

import java.util.Random;

public class GeneratedId {
    public static String generateRandomPassword(int len) {
        String chars = "0123456789abcdefghijk"
                + "lmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateRandomPassword(3));
    }

}
