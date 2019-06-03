package com.guiji.botsentence.util;

public class TtsUtil {

    public static int getIndexFromSeq(String seq){
        return Integer.valueOf(seq.substring(seq.lastIndexOf("_") + 1));
    }
}
