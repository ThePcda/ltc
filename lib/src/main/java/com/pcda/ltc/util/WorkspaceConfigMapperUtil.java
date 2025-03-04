//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pcda.ltc.constant.WorkspaceConfigConstants;
import com.pcda.ltc.enums.TargetDirectory;
import com.pcda.ltc.model.WorkspaceConfig;

public final class WorkspaceConfigMapperUtil {

    private WorkspaceConfigMapperUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static final WorkspaceConfig fromJson(final JSONObject jsonObject) {
        final String liferayHomeName = getLiferayHomeName(jsonObject);
        final String tomcatHomeName = getTomcatHomeName(jsonObject);
        final String[] executeArguments = getIncludeArguments(jsonObject);
        return WorkspaceConfig.of(liferayHomeName, tomcatHomeName, executeArguments);
    }

    private static final String getLiferayHomeName(final JSONObject jsonObject) {
        final String liferayHomeName;
        if (jsonObject.keySet().contains(WorkspaceConfigConstants.Keys.LIFERAY_HOME_NAME)) {
            liferayHomeName = jsonObject.getString(WorkspaceConfigConstants.Keys.LIFERAY_HOME_NAME);
        } else {
            liferayHomeName = WorkspaceConfigConstants.Defaults.LIFERAY_HOME_NAME;
        }
        return liferayHomeName;
    }

    private static final String getTomcatHomeName(final JSONObject jsonObject) {
        final String tomcatHomeName;
        if (jsonObject.keySet().contains(WorkspaceConfigConstants.Keys.TOMCAT_HOME_NAME)) {
            tomcatHomeName = jsonObject.getString(WorkspaceConfigConstants.Keys.TOMCAT_HOME_NAME);
        } else {
            tomcatHomeName = WorkspaceConfigConstants.Defaults.TOMCAT_HOME_NAME;
        }
        return tomcatHomeName;
    }

    private static final String[] getIncludeArguments(final JSONObject jsonObject) {
        if (!jsonObject.keySet().contains(WorkspaceConfigConstants.Keys.INCLUDE_ARGUMENTS)) {
            return WorkspaceConfigConstants.Defaults.INCLUDE_ARGUMENTS;
        }
        final JSONArray args = jsonObject.getJSONArray(WorkspaceConfigConstants.Keys.INCLUDE_ARGUMENTS);
        final int length = args.length();
        final List<String> validArgs = new ArrayList<>();
        final List<String> invalidArgs = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            final String arg = args.getString(i);
            List<String> list = (TargetDirectory.contains(arg)) ? validArgs : invalidArgs;
            list.add(arg);
        }
        final int invalidArgsLength = invalidArgs.size();
        if (invalidArgsLength > 0) {
            System.out.println("Found " + invalidArgsLength + " invalid keys: " + invalidArgs.toString());
            if (length == invalidArgsLength) {
                return WorkspaceConfigConstants.Defaults.INCLUDE_ARGUMENTS;
            }
        }
        return validArgs.toArray(String[]::new);
    }

}
