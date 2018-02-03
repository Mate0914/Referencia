package szabadulosjatek;

import java.util.Random;
import java.util.Scanner;

public class Szereplok {

    private final int[] jatekos = new int[2];
    private final int[] robot1 = new int[2];
    private final int[] robot2 = new int[2];
    private final int[] robot3 = new int[2];
    private final int[] robot4 = new int[2];
    private final int[] robot5 = new int[2];

    public Szereplok() {
        this.jatekos[0] = 0;
        this.jatekos[1] = 0;
        Random generator = new Random();
        int sor = generator.nextInt(9) + 1;
        int oszlop = generator.nextInt(9) + 1;
        this.robot1[0] = sor;
        this.robot1[1] = oszlop;
        do {
            sor = generator.nextInt(9) + 1;
            oszlop = generator.nextInt(9) + 1;
        } while (sor == robot1[0] && oszlop == robot1[1]);
        this.robot2[0] = sor;
        this.robot2[1] = oszlop;
        do {
            sor = generator.nextInt(9) + 1;
            oszlop = generator.nextInt(9) + 1;
        } while (sor == robot1[0] && oszlop == robot1[1] || sor == robot2[0] && oszlop == robot2[1]);
        this.robot3[0] = sor;
        this.robot3[1] = oszlop;
        do {
            sor = generator.nextInt(9) + 1;
            oszlop = generator.nextInt(9) + 1;
        } while (sor == robot1[0] && oszlop == robot1[1] || sor == robot2[0] && oszlop == robot2[1] || sor == robot3[0] && oszlop == robot3[1]);
        this.robot4[0] = sor;
        this.robot4[1] = oszlop;
        do {
            sor = generator.nextInt(9) + 1;
            oszlop = generator.nextInt(9) + 1;
        } while (sor == robot1[0] && oszlop == robot1[1] || sor == robot2[0] && oszlop == robot2[1] || sor == robot3[0] && oszlop == robot3[1]
                || sor == robot4[0] && oszlop == robot4[1]);
        this.robot5[0] = sor;
        this.robot5[1] = oszlop;
    }

    public void jatekosLep() {
        char lepes;
        Scanner sc = new Scanner(System.in);
        do {
            lepes = sc.next().charAt(0);
            while (lepes != 'a' && lepes != 's' && lepes != 'd' && lepes != 'w') {
                System.out.println("Csak az 'a','s','d','w' gombok működnek!");
                lepes = sc.next().charAt(0);
            }
        } while (!jatekosHelyesLep(lepes));
    }

    public boolean jatekosHelyesLep(char lepes) {
        int sor = jatekos[0];
        int oszlop = jatekos[1];
        switch (lepes) {
            case 'a':
                oszlop -= 1;
                if (oszlop < 0) {
                    return false;
                }
                break;
            case 'd':
                oszlop += 1;
                if (oszlop > 9) {
                    return false;
                }
                break;
            case 'w':
                sor -= 1;
                if (sor < 0) {
                    return false;
                }
                break;
            case 's':
                sor += 1;
                if (sor > 9) {
                    return false;
                }
                break;
        }
        jatekos[0] = sor;
        jatekos[1] = oszlop;
        return true;
    }

    public void robotLep(int[] robot) {
        Random generator = new Random();
        int lep;
        do {
            lep = generator.nextInt(8) + 1;
        } while (!robotHelyesLep(lep, robot));
    }

    public boolean robotHelyesLep(int lep, int[] robot) {
        int sor = 0;
        int oszlop = 0;
        switch (lep) {
            case 1:
                if (robot[0] - 1 < 0 || robot[1] - 1 < 0) {
                    return false;
                }
                sor = -1;
                oszlop = -1;
                break;
            case 2:
                if (robot[0] - 1 < 0) {
                    return false;
                }
                sor = -1;
                break;
            case 3:
                if (robot[0] - 1 < 0 || robot[1] + 1 > 9) {
                    return false;
                }
                sor = -1;
                oszlop = 1;
                break;
            case 4:
                if (robot[1] + 1 > 9) {
                    return false;
                }
                oszlop = 1;
                break;
            case 5:
                if (robot[0] + 1 > 9 || robot[1] + 1 > 9) {
                    return false;
                }
                sor = 1;
                oszlop = 1;
                break;
            case 6:
                if (robot[0] + 1 > 9) {
                    return false;
                }
                sor = 1;
                break;
            case 7:
                if (robot[0] + 1 > 9 || robot[1] - 1 < 0) {
                    return false;
                }
                sor = 1;
                oszlop = -1;
                break;
            case 8:
                if (robot[1] - 1 < 0) {
                    return false;
                }
                oszlop = -1;
                break;
        }
        if (robot[0] == robot1[0] && robot[1] == robot1[1]) {
            robot1[0] += sor;
            robot1[1] += oszlop;
        } else if (robot[0] == robot2[0] && robot[1] == robot2[1]) {
            robot2[0] += sor;
            robot2[1] += oszlop;
        } else if (robot[0] == robot3[0] && robot[1] == robot3[1]) {
            robot3[0] += sor;
            robot3[1] += oszlop;
        } else if (robot[0] == robot4[0] && robot[1] == robot4[1]) {
            robot4[0] += sor;
            robot4[1] += oszlop;
        } else {
            robot5[0] += sor;
            robot5[1] += oszlop;
        }
        return true;
    }

    public int[] getJatekos() {
        return this.jatekos;
    }

    public int[] getRobot1() {
        return this.robot1;
    }

    public int[] getRobot2() {
        return this.robot2;
    }

    public int[] getRobot3() {
        return this.robot3;
    }

    public int[] getRobot4() {
        return this.robot4;
    }

    public int[] getRobot5() {
        return this.robot5;
    }
}
