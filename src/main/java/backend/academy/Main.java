package backend.academy;

import backend.academy.factory.InputCommandFactory;
import backend.academy.logic.LogController;
import backend.academy.logic.parser.ParseParams;
import backend.academy.service.InputCommand;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import lombok.experimental.UtilityClass;

@SuppressFBWarnings("LSC_LITERAL_STRING_COMPARISON")
@UtilityClass
public class Main {
    public static void main(String[] args) throws IOException {
        InputCommand inputCommand = InputCommandFactory.createInput("console");
        String[] in = inputCommand.read();
        ParseParams parseParams = new ParseParams();
        LogController logController = new LogController();
        while (!in[0].equals("стоп")) {
            parseParams.parseInput(in);
            logController.start(parseParams);
            in = inputCommand.read();
        }
    }
}
