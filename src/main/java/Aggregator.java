import controller.Controller;
import model.HHStrategy;
import model.Model;
import model.Provider;
import view.HtmlView;


public class Aggregator {
    public static void main(String[] args) {

        HtmlView view = new HtmlView();
        Model model = new Model(view, new Provider(new HHStrategy()));
        Controller controller = new Controller(model);

        view.setController(controller);

        view.userCitySelectEmulationMethod();
    }
}
