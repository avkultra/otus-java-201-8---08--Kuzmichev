package ru.otus.h9.jsonobjectwriter;

public class Helper {
    static Boolean[] hlpArray(boolean[] inBool) {
        if (inBool == null) return null;
        Boolean[] res = new Boolean[inBool.length];
        for (int i = 0; i < inBool.length; i++) {
            res[i] = inBool[i];
        }
        return res;
    }

    static Byte[] hlpArray(byte[] inByte) {
        if (inByte == null) return null;

        Byte[] res = new Byte[inByte.length];
        for (int i = 0; i < inByte.length; i++) {
            res[i] = inByte[i];
        }
        return res;
    }

    static Short[] hlpArray(short[] inShort) {
        if (inShort == null) return null;

        Short[] res = new Short[inShort.length];
        for (int i = 0; i < inShort.length; i++) {
            res[i] = inShort[i];
        }
        return res;
    }

    static Integer[] hlpArray(int[] inInteger) {
        if (inInteger == null) return null;

        Integer[] res = new Integer[inInteger.length];
        for (int i = 0; i < inInteger.length; i++) {
            res[i] = inInteger[i];
        }
        return res;
    }

    static Long[] hlpArray(long[] inLong) {
        if (inLong == null) return null;

        Long[] res = new Long[inLong.length];
        for (int i = 0; i < inLong.length; i++) {
            res[i] = inLong[i];
        }
        return res;
    }

    static Float[] hlpArray(float[] inFloat) {
        if (inFloat == null) return null;

        Float[] res = new Float[inFloat.length];
        for (int i = 0; i < inFloat.length; i++) {
            res[i] = inFloat[i];
        }
        return res;
    }

    static Double[] hlpArray(double[] inDouble) {
        if (inDouble == null) return null;

        Double[] res = new Double[inDouble.length];
        for (int i = 0; i < inDouble.length; i++) {
            res[i] = inDouble[i];
        }
        return res;
    }
}