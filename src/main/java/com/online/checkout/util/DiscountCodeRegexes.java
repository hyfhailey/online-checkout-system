package com.online.checkout.util;

import java.util.regex.Pattern;

public final class DiscountCodeRegexes {
    public static final Pattern BUY_NUM_GET_NUM_FREE = Pattern.compile("BUY\\s(\\d)\\sGET\\s(\\d)\\sFREE");
    public static final Pattern BUY_NUM_GET_PERCENT_OFF_THE_NUM = Pattern.compile("^BUY\\s(\\d)\\sGET\\s([1-9]|[1-9][0-9])%\\sOFF\\sTHE\\s([A-Z]+)$");
    public static final Pattern PERCENT_OFF = Pattern.compile("^([1-9]|[1-9][0-9])%\\sOFF$");
}
