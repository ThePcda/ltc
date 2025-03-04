//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.pcda.ltc.constant.FileConstant;

public final class LogUtil {

    private LogUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static final void createLog(final Exception e) {
        try {
            Path logsFolder = Files.createDirectories(FileConstant.LOGS_FOLDER_PATH);

            Path logFile = logsFolder.resolve("ltc" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss")) + ".log");

            Files.createFile(logFile);
            StringWriter sw = new StringWriter();
            PrintWriter  pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            Files.write(logFile, sw.toString().getBytes());
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }

    public static final void createLog(final Exception[] es) {
        try {
            Path logsFolder = Files.createDirectories(FileConstant.LOGS_FOLDER_PATH);

            Path logFile = logsFolder.resolve("ltc" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss")) + ".log");

            Files.createFile(logFile);
            StringWriter sw = new StringWriter();
            PrintWriter  pw = new PrintWriter(sw);
            int counter = 1;
            for (final Exception e : es) {
                pw.append("============================================================\n");
                pw.append("===Error: " + counter + "=================================================\n");
                pw.append("============================================================\n\n");
                e.printStackTrace(pw);
                pw.append("\n");
                counter++;
            }

            Files.write(logFile, sw.toString().getBytes());
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }

    public static final void createLog(final List<Exception> es) {
        try {
            Path logsFolder = Files.createDirectories(FileConstant.LOGS_FOLDER_PATH);

            Path logFile = logsFolder.resolve("ltc" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss")) + ".log");

            Files.createFile(logFile);
            StringWriter sw = new StringWriter();
            PrintWriter  pw = new PrintWriter(sw);
            int counter = 1;
            for (final Exception e : es) {
                pw.append("============================================================\n");
                pw.append("===Error: " + counter + "=================================================\n");
                pw.append("============================================================\n\n");
                e.printStackTrace(pw);
                pw.append("\n");
                counter++;
            }

            Files.write(logFile, sw.toString().getBytes());
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }

}
