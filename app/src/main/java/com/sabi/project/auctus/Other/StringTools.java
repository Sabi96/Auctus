package com.sabi.project.auctus.Other;

/**
 * Created by Sabi on 4.6.2017..
 */

public class StringTools {

    public static String getShort(String original){
        if (original.length()>150){
            return original.substring(0,150)+"...";
        }else return original;
    }

    public static String getShorter(String original){
        if (original.length()>75){
            return original.substring(0,75)+"...";
        }else return original;
    }
}
