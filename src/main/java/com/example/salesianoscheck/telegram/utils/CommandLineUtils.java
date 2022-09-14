package com.example.salesianoscheck.telegram.utils;

import org.apache.commons.cli.*;
import org.apache.tools.ant.types.Commandline;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CommandLineUtils {

    private CommandLineUtils() {
    }

    public static CommandLine getCommandLine(String strCommands, Options options) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, Commandline.translateCommandline(strCommands));
    }

    public static String getHelpCommandLine(String commandVerb, Options options, String header){
        StringWriter buff = new StringWriter();
        PrintWriter out = new PrintWriter(buff);
        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp(out,160, commandVerb, header, (options==null?new Options():options),10,10,"");
        return buff.toString();
    }
}
