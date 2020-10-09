import repository.MemoryRepository;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        var repository = new MemoryRepository(10);
        var application = new MainView(repository);

        application.start();
    }
}
