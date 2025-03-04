//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.enums;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import com.pcda.lib.contract.Keyed;
import com.pcda.ltc.constant.LTCCommandConstant;
import com.pcda.ltc.model.WorkspaceConfig;

public enum TargetDirectory implements Keyed {

    WORK(
        LTCCommandConstant.CLEAR_WORK_OPTION_KEY_LONG,
        (wsPath, workspaceConfig) -> Path.of(wsPath).resolve("bundles").resolve(workspaceConfig.getLiferayHomeName()).resolve("work")
    ),
    OSGI_STATE(
        LTCCommandConstant.CLEAR_OSGI_STATE_OPTION_KEY_LONG,
        (wsPath, workspaceConfig) -> Path.of(wsPath).resolve("bundles").resolve(workspaceConfig.getLiferayHomeName()).resolve("osgi").resolve("state")
    ),
    TOMCAT_TEMP(
        LTCCommandConstant.CLEAR_TOMCAT_TEMP_OPTION_KEY_LONG,
        (wsPath, workspaceConfig) -> Path.of(wsPath).resolve("bundles").resolve(workspaceConfig.getLiferayHomeName()).resolve(workspaceConfig.getTomcatHomeName()).resolve("temp")
    ),
    TOMCAT_WORK(
        LTCCommandConstant.CLEAR_TOMCAT_WORK_OPTION_KEY_LONG,
        (wsPath, workspaceConfig) -> Path.of(wsPath).resolve("bundles").resolve(workspaceConfig.getLiferayHomeName()).resolve(workspaceConfig.getTomcatHomeName()).resolve("work")
    );

    private static final Map<String, TargetDirectory> ALL_VALUES_KEY = new HashMap<>();

    private final String key;
    private final BiFunction<String, WorkspaceConfig, Path> pathCreator;

    static {
        for (final TargetDirectory commandExecution : values()) {
            ALL_VALUES_KEY.put(commandExecution.key, commandExecution);
        }
    }

    private TargetDirectory(final String key, final BiFunction<String, WorkspaceConfig, Path> pathCreator) {
        this.key = key;
        this.pathCreator = pathCreator;
    }

    public static final TargetDirectory get(final String key) {
        return ALL_VALUES_KEY.get(key);
    }

    public static final boolean contains(final String key) {
        return ALL_VALUES_KEY.containsKey(key);
    }

    @Override
    public final String getKey() {
        return key;
    }

    public final BiFunction<String, WorkspaceConfig, Path> getPathCreater() {
        return pathCreator;
    }

}
