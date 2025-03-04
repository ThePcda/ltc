//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.model;

import java.util.Map;
import java.util.function.Function;

import com.pcda.ltc.constant.WorkspaceConfigConstants;

public interface WorkspaceConfig {

    public static WorkspaceConfig of(final String liferayHomeName, final String tomcatHomeName, final String[] includeArguments) {
        return new WorkspaceConfigImpl(
            liferayHomeName,
            tomcatHomeName,
            includeArguments
        );
    }

    public static WorkspaceConfig ofDefaults() {
        return new WorkspaceConfigImpl(
            WorkspaceConfigConstants.Defaults.LIFERAY_HOME_NAME,
            WorkspaceConfigConstants.Defaults.TOMCAT_HOME_NAME,
            WorkspaceConfigConstants.Defaults.INCLUDE_ARGUMENTS
        );
    }

    public static final Map<String, Function<WorkspaceConfig, Object>> GETTERS = Map.of(
        WorkspaceConfigConstants.Keys.LIFERAY_HOME_NAME, WorkspaceConfig::getLiferayHomeName,
        WorkspaceConfigConstants.Keys.TOMCAT_HOME_NAME, WorkspaceConfig::getTomcatHomeName,
        WorkspaceConfigConstants.Keys.INCLUDE_ARGUMENTS, WorkspaceConfig::getIncludeArguments
    );

    String getLiferayHomeName();

    String getTomcatHomeName();

    String[] getIncludeArguments();

}
