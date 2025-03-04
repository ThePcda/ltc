//(C) Adrian Suslik (klauen ist ehrenlos, aber als Polacke kann ich das verstehen)
package com.pcda.ltc.main;

import com.pcda.ltc.command.LTCCommand;

import picocli.CommandLine;

public final class Main {

    public static final void main(String[] args) {
        new CommandLine(new LTCCommand()).execute(args);
    }

}
