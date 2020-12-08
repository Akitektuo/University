package view.command;

public class ExitCommand extends BaseCommand {
    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.out.println("Closing application...");
        System.exit(0);
    }
}
