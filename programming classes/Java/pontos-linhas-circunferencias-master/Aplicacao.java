/**
 * @author Fernando Fonseca, Jolie Caldarone, Rafael Ferrari
 */

import janela.Janela;
import javafx.application.Application;
import javafx.stage.Stage;

public class Aplicacao extends Application
{
    public static void main (String args[])
    {
        launch(args);
    }

    @Override
    public void start(Stage palco) throws Exception
    {
        new Janela(palco);
    }
}
