import controllers.AbonatController;
import controllers.BibliotecarController;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IBibliotecaServices;

public class StartClientFX extends Application {

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");

        try{
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IBibliotecaServices server=(IBibliotecaServices)factory.getBean("bibliotecaService");
            System.out.println("Obtained a reference to remote biblioteca server");

            //login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
            Parent root=loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setContext(server);





            //ctrl.setControllerBibliotecar(bibliotecarController);
           // ctrl.setControllerAbonat(abonatController);

           // ctrl.setParents(bRoot, aRoot);

            primaryStage.setTitle("Biblioteca");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        }catch (Exception e) {
            System.err.println("Biblioteca Initialization exception:"+e);
            e.printStackTrace();
        }
    }

}