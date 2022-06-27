package dvdLibrary;

import controller.DvdLibraryController;
import dao.Dao;
import dao.DaoImpl;
import ui.DvdLibraryView;
import ui.UserIO;
import ui.UserIOConsoleImpl;

public class App {
    public static void main (String[] args) {
            UserIO myIo = new UserIOConsoleImpl();
            DvdLibraryView myView = new DvdLibraryView(myIo);
            Dao myDao = new DaoImpl();
            DvdLibraryController controller =
                    new DvdLibraryController(myDao, myView);
            controller.run();
        }
}
