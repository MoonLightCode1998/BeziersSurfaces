import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Scanner;


class Punkt {
    double x;
    double y;
    double z;

    Punkt(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

public class BeziersSurface {


    public static int silnia(int i) {
        if (i == 0)
            return 1;
        if (i == 1)
            return 1;
        return i * silnia(i - 1);
    }

    public static int dwumianNewtona(int n, int k) {

        if (k != 0)
            return silnia(n) / (silnia(k) * silnia(n - k));
        else {
            return 1;
        }
    }

    public static void bezier(LinkedList<Punkt[][]> macierzPunktow, String nazwaPlikuDocelowego) {

        try {
            Writer a = new FileWriter(nazwaPlikuDocelowego);
            a.write("x,y,z" + System.lineSeparator());
            double sumax = 0;
            double sumay = 0;
            double sumaz = 0;
            for (int z = 0; z < macierzPunktow.size(); z++) {
                for (double v = 0; v <= 1; v += 0.001) {
                    for (double w = 0; w <= 1; w += 0.001) {
                        for (int i = 0; i <= 3; i++) {
                            for (int j = 0; j <= 3; j++) {
                                sumax += dwumianNewtona(3, i) * Math.pow(1 - v, 3 - i) * Math.pow(v, i) * dwumianNewtona(3, j) * Math.pow(1 - w, 3 - j) * Math.pow(w, j) * macierzPunktow.get(z)[i][j].x;
                                sumay += dwumianNewtona(3, i) * Math.pow(1 - v, 3 - i) * Math.pow(v, i) * dwumianNewtona(3, j) * Math.pow(1 - w, 3 - j) * Math.pow(w, j) * macierzPunktow.get(z)[i][j].y;
                                sumaz += dwumianNewtona(3, i) * Math.pow(1 - v, 3 - i) * Math.pow(v, i) * dwumianNewtona(3, j) * Math.pow(1 - w, 3 - j) * Math.pow(w, j) * macierzPunktow.get(z)[i][j].z;
                            }
                        }


                        a.write(sumax + "," + sumay + "," + sumaz + System.lineSeparator());


                        sumax = 0;
                        sumay = 0;
                        sumaz = 0;
                    }
                }

            }
            a.close();
        } catch (Exception e) {
            System.out.println("może kiedyś");
        }

    }
    public static void load(LinkedList<Punkt[][]> a,String nazwaPlik){
        try {
            Scanner one = new Scanner(new File(nazwaPlik));
            one.useDelimiter(" |" + System.lineSeparator());
            while (one.hasNextLine()) {
                // TODO: 25.03.2021 to był jakiś pomysł, ale ponizej lepszy
                // punkty.add(new Punkt(Double.parseDouble(one.next()),Double.parseDouble(one.next()),Double.parseDouble(one.next())));
                a.add(new Punkt[4][4]);
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        a.getLast()[x][y] = new Punkt(Double.parseDouble(one.next()), Double.parseDouble(one.next()), Double.parseDouble(one.next()));
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("elo");
        }

    }

    public static void main(String[] args) {
        //LinkedList<Punkt> punkty = new LinkedList<>();
        LinkedList<Punkt[][]> macierzPunktow = new LinkedList<>();
        LinkedList<Punkt[][]> macierzPunktowszklanka = new LinkedList<>();
        LinkedList<Punkt[][]> macierzPunktowszSpoon = new LinkedList<>();

        load(macierzPunktow,"czajnik.txt");
        load(macierzPunktowszklanka,"kubek.txt");
        load(macierzPunktowszSpoon,"spoon.txt");

        // TODO: 25.03.2021 wyznaczanie punktów czajnika
        //bezier(macierzPunktow,"dane5.txt");
        //bezier(macierzPunktowszklanka,"dane2.txt");
        //bezier(macierzPunktowszSpoon, "dane3.txt");

        System.out.println(macierzPunktow.size());
        System.out.println(macierzPunktowszklanka.size());
        System.out.println(macierzPunktowszSpoon.size());
    }
}
