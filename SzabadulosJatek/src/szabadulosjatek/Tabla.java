package szabadulosjatek;

public final class Tabla {

    private final char[][] tabla = new char[10][10];
    private int lepesSzam;

    public Tabla(Szereplok szereplok) {
        this.lepesSzam = 0;
        tablaRendez(szereplok);
    }

    public void lepesSzamNovel() {
        this.lepesSzam += 1;
    }

    public int getLepesSzam() {
        return this.lepesSzam;
    }

    public void tablaRendez(Szereplok szereplok) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tabla[i][j] = ' ';
                if (szereplok.getJatekos()[0] == i && szereplok.getJatekos()[1] == j) {
                    tabla[i][j] = 'J';
                }
                if (szereplok.getRobot1()[0] == i && szereplok.getRobot1()[1] == j) {
                    tabla[i][j] = 'R';
                }
                if (szereplok.getRobot2()[0] == i && szereplok.getRobot2()[1] == j) {
                    tabla[i][j] = 'R';
                }
                if (szereplok.getRobot3()[0] == i && szereplok.getRobot3()[1] == j) {
                    tabla[i][j] = 'R';
                }
                if (szereplok.getRobot4()[0] == i && szereplok.getRobot4()[1] == j) {
                    tabla[i][j] = 'R';
                }
                if (szereplok.getRobot5()[0] == i && szereplok.getRobot5()[1] == j) {
                    tabla[i][j] = 'R';
                }
            }
        }
    }

    public boolean gyozelem(Szereplok jatekos) {
        return jatekos.getJatekos()[0] == 9 && jatekos.getJatekos()[1] == 9;
    }

    public boolean vesztes(Szereplok jatekos) {
        return jatekos.getJatekos()[0] == jatekos.getRobot1()[0] && jatekos.getJatekos()[1] == jatekos.getRobot1()[1]
                || jatekos.getJatekos()[0] == jatekos.getRobot2()[0] && jatekos.getJatekos()[1] == jatekos.getRobot2()[1]
                || jatekos.getJatekos()[0] == jatekos.getRobot3()[0] && jatekos.getJatekos()[1] == jatekos.getRobot3()[1]
                || jatekos.getJatekos()[0] == jatekos.getRobot4()[0] && jatekos.getJatekos()[1] == jatekos.getRobot4()[1]
                || jatekos.getJatekos()[0] == jatekos.getRobot5()[0] && jatekos.getJatekos()[1] == jatekos.getRobot5()[1];
    }

    public void tablaKirajzolas() {
        System.out.println(" |" + tabla[0][0] + "|" + tabla[0][1] + "|" + tabla[0][2] + "|" + tabla[0][3] + "|" + tabla[0][4] + "|" + tabla[0][5] + "|" + tabla[0][6] + "|" + tabla[0][7] + "|" + tabla[0][8] + "|" + tabla[0][9] + "|");
        System.out.println(" |" + tabla[1][0] + "|" + tabla[1][1] + "|" + tabla[1][2] + "|" + tabla[1][3] + "|" + tabla[1][4] + "|" + tabla[1][5] + "|" + tabla[1][6] + "|" + tabla[1][7] + "|" + tabla[1][8] + "|" + tabla[1][9] + "|");
        System.out.println(" |" + tabla[2][0] + "|" + tabla[2][1] + "|" + tabla[2][2] + "|" + tabla[2][3] + "|" + tabla[2][4] + "|" + tabla[2][5] + "|" + tabla[2][6] + "|" + tabla[2][7] + "|" + tabla[2][8] + "|" + tabla[2][9] + "|");
        System.out.println(" |" + tabla[3][0] + "|" + tabla[3][1] + "|" + tabla[3][2] + "|" + tabla[3][3] + "|" + tabla[3][4] + "|" + tabla[3][5] + "|" + tabla[3][6] + "|" + tabla[3][7] + "|" + tabla[3][8] + "|" + tabla[3][9] + "|");
        System.out.println(" |" + tabla[4][0] + "|" + tabla[4][1] + "|" + tabla[4][2] + "|" + tabla[4][3] + "|" + tabla[4][4] + "|" + tabla[4][5] + "|" + tabla[4][6] + "|" + tabla[4][7] + "|" + tabla[4][8] + "|" + tabla[4][9] + "|");
        System.out.println(" |" + tabla[5][0] + "|" + tabla[5][1] + "|" + tabla[5][2] + "|" + tabla[5][3] + "|" + tabla[5][4] + "|" + tabla[5][5] + "|" + tabla[5][6] + "|" + tabla[5][7] + "|" + tabla[5][8] + "|" + tabla[5][9] + "|");
        System.out.println(" |" + tabla[6][0] + "|" + tabla[6][1] + "|" + tabla[6][2] + "|" + tabla[6][3] + "|" + tabla[6][4] + "|" + tabla[6][5] + "|" + tabla[6][6] + "|" + tabla[6][7] + "|" + tabla[6][8] + "|" + tabla[6][9] + "|");
        System.out.println(" |" + tabla[7][0] + "|" + tabla[7][1] + "|" + tabla[7][2] + "|" + tabla[7][3] + "|" + tabla[7][4] + "|" + tabla[7][5] + "|" + tabla[7][6] + "|" + tabla[7][7] + "|" + tabla[7][8] + "|" + tabla[7][9] + "|");
        System.out.println(" |" + tabla[8][0] + "|" + tabla[8][1] + "|" + tabla[8][2] + "|" + tabla[8][3] + "|" + tabla[8][4] + "|" + tabla[8][5] + "|" + tabla[8][6] + "|" + tabla[8][7] + "|" + tabla[8][8] + "|" + tabla[8][9] + "|");
        System.out.println(" |" + tabla[9][0] + "|" + tabla[9][1] + "|" + tabla[9][2] + "|" + tabla[9][3] + "|" + tabla[9][4] + "|" + tabla[9][5] + "|" + tabla[9][6] + "|" + tabla[9][7] + "|" + tabla[9][8] + "|" + tabla[9][9] + "|<---Cél");
    }
}
