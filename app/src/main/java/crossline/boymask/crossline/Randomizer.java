package crossline.boymask.crossline;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {
    private List<Integer> lista = new ArrayList();

    public Randomizer(int size) {

        for (int i = 1; i <= size/2; i++)
            lista.add(i);
        for (int i = 1; i <= size/2; i++)
            lista.add(i);

    }

    public Integer getNextRandom(){
        if(lista.size()==0)return null;
        Random rand = new Random();

        int  n = rand.nextInt(lista.size()) ;

        int val=lista.get(n);
        lista.remove(n);
        return val;
    }

}
