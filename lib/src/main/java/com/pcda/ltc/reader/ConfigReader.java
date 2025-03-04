//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.pcda.ltc.constant.FileConstant;
import com.pcda.ltc.model.WorkspaceConfig;
import com.pcda.ltc.util.WorkspaceConfigMapperUtil;

public final class ConfigReader {

    private static final class InstanceHolder {

        public static final ConfigReader INSTANCE = new ConfigReader();

        private InstanceHolder() throws IllegalAccessException {
            throw new IllegalAccessException("Instance holder class");
        }

    }

    private static final Logger LOG = Logger.getLogger(ConfigReader.class.getName());

    private final Map<String, WorkspaceConfig> workspaceConfigs = new HashMap<>();

    private ConfigReader() {
        //empty constructor
    }

    public static final void read() {
        InstanceHolder.INSTANCE._read();
    }

    public static final Map<String, WorkspaceConfig> getWorkspaceConfigs() {
        return InstanceHolder.INSTANCE._getWorkspaceConfigs();
    }

    public static final boolean isConfigPresent(final String workspace) {
        return InstanceHolder.INSTANCE._isConfigPresent(workspace);
    }

    private final void _read() {
        try {
            String content = new String(Files.readAllBytes(FileConstant.CONFIG_FILE_PATH));
            final JSONObject jsonObject = new JSONObject(content);
            for (final String workspace : jsonObject.keySet()) {
                workspaceConfigs.put(workspace, WorkspaceConfigMapperUtil.fromJson(jsonObject.getJSONObject(workspace)));
            }
        } catch (IOException e) {
            LOG.info(FileConstant.CONFIG_FOLDER_NAME + "/" + FileConstant.CONFIG_FILE_NAME + " isn't present");
        }
    }

    private final Map<String, WorkspaceConfig> _getWorkspaceConfigs() {
        return workspaceConfigs;
    }

    private final boolean _isConfigPresent(final String workspace) {
        return workspaceConfigs.containsKey(workspace);
    }

}
