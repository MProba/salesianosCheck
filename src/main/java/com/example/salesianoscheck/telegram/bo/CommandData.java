package com.example.salesianoscheck.telegram.bo;

import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

@Component
public class CommandData {
    private Options options;
    private String presentation;
    private String verb;

    public String getVerb() {
        return verb;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getPresentation() {
        return presentation;
    }


    public static class CommandDataBuilder
    {
        private final CommandData commandData;

        private CommandDataBuilder()
        {
            commandData = new CommandData();
        }

        public CommandDataBuilder withOptions(Options options)
        {
            commandData.options = options;
            return this;
        }

        public CommandDataBuilder withPresentation(String presentation)
        {
            commandData.presentation = presentation;
            return this;
        }

        public CommandDataBuilder withVerb(String verb)
        {
            commandData.verb = verb;
            return this;
        }

        public static CommandDataBuilder commandData()
        {
            return new CommandDataBuilder();
        }

        public CommandData build()
        {
            return commandData;
        }
    }
}
