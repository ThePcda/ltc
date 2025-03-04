//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.constant;

public final class LTCCommandConstant {

    private LTCCommandConstant() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static final String LOCATION_OPTION_KEY = "-l";
    public static final String LOCATION_OPTION_KEY_LONG = "--location";

    public static final String NO_CONF_KEY = "-c";
    public static final String NO_CONF_KEY_LONG = "--no-conf";

    public static final String CLEAR_ALL_KEY = "-a";
    public static final String CLEAR_ALL_KEY_LONG = "--all";

    public static final String NO_INCLUDE_ARGUMENTS_KEY_LONG = "--no-includes";

    public static final String CLEAR_WORK_OPTION_KEY_LONG = "--c-work";

    public static final String CLEAR_OSGI_STATE_OPTION_KEY_LONG = "--c-osgi-state";

    public static final String CLEAR_TOMCAT_TEMP_OPTION_KEY_LONG = "--c-tomcat-temp";

    public static final String CLEAR_TOMCAT_WORK_OPTION_KEY_LONG = "--c-tomcat-work";

}
