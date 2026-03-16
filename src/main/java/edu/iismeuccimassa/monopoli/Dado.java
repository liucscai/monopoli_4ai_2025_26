package edu.iismeuccimassa.monopoli;
/**
 *
 * @author AleMarza08
 */
import java.util.Random;

public class Dado {

    Random ran = new Random();
    int result1;
    int result2;

    public int lancia() {
        result1 = ran.nextInt(6) + 1;
        result2 = ran.nextInt(6) + 1;
        return result1 + result2;
    }

    public boolean doppio() {
        return result1 == result2;
    }
}
