//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.pcda.lib.util.ArrayUtil;
import com.pcda.lib.util.SetUtil;
import com.pcda.ltc.constant.LTCCommandConstant;
import com.pcda.ltc.enums.TargetDirectory;
import com.pcda.ltc.model.WorkspaceConfig;
import com.pcda.ltc.reader.ConfigReader;
import com.pcda.ltc.util.LogUtil;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "ltcc",
    header = "<>_Liferay Temp Cleaner_<>",
    description = "Your tiny clean up fairy <3",
    version = "1.0.0",
    mixinStandardHelpOptions = true,
    requiredOptionMarker = '*',
    optionListHeading = "%nFollowing commands at your disposal:%n"
)
public final class LTCCommand implements Runnable {

    @Option(
        names = {
            LTCCommandConstant.LOCATION_OPTION_KEY,
            LTCCommandConstant.LOCATION_OPTION_KEY_LONG
        },
        description = {
            "The path to the workspace",
            "A '.' can be set to select the current directory"
        },
        required = true
    )
    private String location;

    @Option(
        names = {
            LTCCommandConstant.NO_CONF_KEY,
            LTCCommandConstant.NO_CONF_KEY_LONG
        },
        description = "Set this argument to ignore the configurations in conf.json"
    )
    private boolean noConf;

    @Option(
        names = {
            LTCCommandConstant.CLEAR_ALL_KEY,
            LTCCommandConstant.CLEAR_ALL_KEY_LONG
        },
        description = "Set this argument to clear all temp directories"
    )
    private boolean clearAll;

    @Option(
        names = {
            LTCCommandConstant.NO_INCLUDE_ARGUMENTS_KEY_LONG
        },
        description = "Set this argument to ignore the included arguments (inc-args) in conf.json"
    )
    private boolean noIncludes;

    @Option(
        names = {
            LTCCommandConstant.CLEAR_WORK_OPTION_KEY_LONG
        },
        description = "Set this argument to add the work directory for clean up"
    )
    private boolean clearWork;

    @Option(
        names = {
            LTCCommandConstant.CLEAR_OSGI_STATE_OPTION_KEY_LONG
        },
        description = "Set this argument to add the osgi/state directory for clean up"
    )
    private boolean clearOsgiState;

    @Option(
        names = {
            LTCCommandConstant.CLEAR_TOMCAT_TEMP_OPTION_KEY_LONG
        },
        description = "Set this argument to add the tomcat/temp directory for clean up"
    )
    private boolean clearTomcatTemp;

    @Option(
        names = {
            LTCCommandConstant.CLEAR_TOMCAT_WORK_OPTION_KEY_LONG
        },
        description = "Set this argument to add the tomcat/work directory for clean up"
    )
    private boolean clearTomcatWork;

    @Override
    public final void run() {
        if (location.equals(".")) {
            location = System.getProperty("user.dir");
        }
        Path path = Path.of(location);
        if (!Files.exists(path)) {
            System.out.println(location + " doesn't exist");
            return;
        }
        if (!Files.isDirectory(path)) {
            System.out.println(location + " isn't a directory");
            return;
        }

        final WorkspaceConfig workspaceConfig;
        if (!noConf) {
            ConfigReader.read();
            final String workspaceName = path.getFileName().toString();

            if (ConfigReader.isConfigPresent(workspaceName)) {
                System.out.println("Found configs for: " + workspaceName);
                workspaceConfig = ConfigReader.getWorkspaceConfigs().get(workspaceName);
            } else {
                System.out.println("Found no configs for: " + workspaceName);
                workspaceConfig = WorkspaceConfig.ofDefaults();
            }
            if (!noIncludes) {
                applyWorkspaceIncludeArguments(workspaceConfig);
            }
        } else {
            workspaceConfig = WorkspaceConfig.ofDefaults();
        }

        final Set<TargetDirectory> targetDirectorySet = getTargetDirectories();

        System.out.println("Selected: " + targetDirectorySet.size() + " target directories for clean up");
        for (final TargetDirectory targetDirectory : targetDirectorySet) {
            System.out.println(targetDirectory + ": " + targetDirectory.getPathCreater().apply(location, workspaceConfig).toString());
        }

        System.out.println();

        final List<Exception> es = new ArrayList<>();
        for (final TargetDirectory targetDirectory : targetDirectorySet) {
            Path dirPath = targetDirectory.getPathCreater().apply(location, workspaceConfig);
            try {
                FileUtils.cleanDirectory(dirPath.toFile());
                System.out.println("Cleaned: " + dirPath.toString());
            } catch (IOException e) {
                System.out.println("Couldn't clear: " + dirPath.toString());
                e.printStackTrace();
                es.add(e);
            }
        }
        if (!es.isEmpty()) {
            LogUtil.createLog(es);
        }
    }

    private final void applyWorkspaceIncludeArguments(final WorkspaceConfig workspaceConfig) {
        final String[] includeArguments = workspaceConfig.getIncludeArguments();
        if (ArrayUtil.containsAny(includeArguments, new String[] {LTCCommandConstant.CLEAR_ALL_KEY, LTCCommandConstant.CLEAR_ALL_KEY_LONG})) {
            clearAll = true;
        } else {
            if (ArrayUtil.contains(includeArguments, LTCCommandConstant.CLEAR_WORK_OPTION_KEY_LONG)) {
                clearWork = true;
            }
            if (ArrayUtil.contains(includeArguments, LTCCommandConstant.CLEAR_OSGI_STATE_OPTION_KEY_LONG)) {
                clearOsgiState = true;
            }
            if (ArrayUtil.contains(includeArguments, LTCCommandConstant.CLEAR_TOMCAT_TEMP_OPTION_KEY_LONG)) {
                clearTomcatTemp = true;
            }
            if (ArrayUtil.contains(includeArguments, LTCCommandConstant.CLEAR_TOMCAT_WORK_OPTION_KEY_LONG)) {
                clearTomcatWork = true;
            }
        }
    }

    private final Set<TargetDirectory> getTargetDirectories() {
        if (clearAll) {
            return SetUtil.of(TargetDirectory.values());
        }
        final Set<TargetDirectory> set = new HashSet<>();
        if (clearWork) {
            set.add(TargetDirectory.WORK);
        }
        if (clearOsgiState) {
            set.add(TargetDirectory.OSGI_STATE);
        }
        if (clearTomcatTemp) {
            set.add(TargetDirectory.TOMCAT_TEMP);
        }
        if (clearTomcatWork) {
            set.add(TargetDirectory.TOMCAT_WORK);
        }
        return set;
    }

}
