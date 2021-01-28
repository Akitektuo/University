import repository.Repository;
import view.MainView;

public class Main {

    public static void main(String[] args) {
        var repository = new Repository();
        var mainView = new MainView(repository);

        mainView.start();
    }
}
