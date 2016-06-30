package com.qm.common.utils;

public class RandUtils {

    private static final long MOD_4 = 10000L;
    private static final long MOD_6 = 1000000L;

    protected RandUtils() {

    }

    private static final long getMod(int len) {
        long mod = 1L;
        switch (len) {
        case 4:
            mod = MOD_4;
            break;
        case 6:
            mod = MOD_6;
            break;
        default:
            while (len-- > 0) {
                mod *= 10L;
            }
            break;
        }
        return mod;
    }

    public static final String randNum(int len) {
        if (len <= 0) {
            return "";
        }
        long mod = getMod(len);
        String fmt = String.format("%%0%dd", len);
        long value = System.nanoTime() % mod;
        return String.format(fmt, value);
    }
}
