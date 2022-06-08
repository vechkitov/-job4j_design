package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class);

    public static void main(String[] args) {
        byte b = 1;
        short s = 2;
        int i = 3;
        long l = 4L;
        float f = 5f;
        double d = 6d;
        char ch = '7';
        boolean bool = true;
        LOG.info("byte b = {};\nshort s = {};\nint i = {};\nlong l = {};\nfloat f = {};\n"
                + "double d = {};\nchar ch = {};\nboolean bool = {}", b, s, i, l, f, d, ch, bool);
    }
}
