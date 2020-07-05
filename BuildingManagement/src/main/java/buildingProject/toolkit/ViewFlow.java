package buildingProject.toolkit;

import buildingProject.controllers.MainViewController;
import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Stack;

@Component
public final class ViewFlow {
    private final ApplicationContext applicationContext;
    private final Stack<Resource> resourceStack = new Stack<>();

    public ViewFlow(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Stack<Resource> getResourceStack() {
        return resourceStack;
    }

    public void goBack() throws IOException {
        Resource previousView = resourceStack.pop();
        FXMLLoader loader = new FXMLLoader(previousView.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }

    public void clear() {
        resourceStack.clear();
    }

    public void add(Resource resource) {
        resourceStack.add(resource);
    }

    public void loadResource(Resource previous, Resource next) throws IOException {
        resourceStack.add(previous);
        FXMLLoader loader = new FXMLLoader(next.getURL());
        loader.setControllerFactory(applicationContext::getBean);
        MainViewController.getGlobalMainPage().setCenter(loader.load());
    }
}
