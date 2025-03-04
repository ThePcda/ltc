//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.constant;

public final class WorkspaceConfigConstants {

    public static final class Keys {

        public static final String LIFERAY_HOME_NAME = "liferay-home-name";

        public static final String TOMCAT_HOME_NAME = "tomcat-home-name";

        public static final String INCLUDE_ARGUMENTS = "inc-args";

        private Keys() throws IllegalAccessException {
            throw new IllegalAccessException("Utility class");
        }

    }

    public static final class Defaults {

        public static final String LIFERAY_HOME_NAME = "liferay";

        public static final String TOMCAT_HOME_NAME = "tomcat";

        public static final String[] INCLUDE_ARGUMENTS = new String[0];

        private Defaults() throws IllegalAccessException {
            throw new IllegalAccessException("Utility class");
        }

    }

    private WorkspaceConfigConstants() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

}
