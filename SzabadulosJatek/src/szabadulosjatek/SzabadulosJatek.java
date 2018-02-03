package szabadulosjatek;

public class SzabadulosJatek {

    public static void main(String[] args) {
        Szereplok szer = new Szereplok();
        Tabla tabla = new Tabla(szer);
        tabla.tablaKirajzolas();
        do {
            System.out.println("Merre mész tovább? ");
            System.out.println("'a' balra | 's' lefele | 'd' jobbra | 'w' felfele");
            szer.jatekosLep();
            szer.robotLep(szer.getRobot1());
            szer.robotLep(szer.getRobot2());
            szer.robotLep(szer.getRobot3());
            szer.robotLep(szer.getRobot4());
            szer.robotLep(szer.getRobot5());
            tabla.tablaRendez(szer);
            tabla.lepesSzamNovel();
            tabla.tablaKirajzolas();
            System.out.println("                                              Lépések száma: " + tabla.getLepesSzam());
        } while (!tabla.gyozelem(szer) && !tabla.vesztes(szer));
        if (tabla.gyozelem(szer)) {
            System.out.println("Sikeresen kijutottál!");
        } else {
            System.out.println("Elkaptak!");
        }
    }
}
