package backend.academy.factory;

import backend.academy.service.InputCommand;
import backend.academy.service.impl.InputCommand.ReadConsole;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("LSC_LITERAL_STRING_COMPARISON")
public class InputCommandFactory {
    public static InputCommand createInput(String str) {
        return switch (str.toLowerCase()) {
            case "console" -> new ReadConsole();
            default -> throw new IllegalArgumentException("Unknown input type: " + str);
        };

    }

    private InputCommandFactory() {

    }
}
