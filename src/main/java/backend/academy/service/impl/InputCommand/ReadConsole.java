package backend.academy.service.impl.InputCommand;

import backend.academy.service.InputCommand;
import backend.academy.utils.ScannerUtils;
import java.util.function.Function;

public class ReadConsole implements InputCommand {
    @Override
    public String[] read() {
        return ScannerUtils.getInput("", Function.identity()).split(" ");
    }
}
