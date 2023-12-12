package lambdaparallel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatesMain {

    public static void main(String[] args) {
        /*List<Integer> list = new ArrayList<>();
        IntStream.range(0, 100000).parallel().forEach(list::add);
        System.out.println(list.size());//ez igy nem determinisztikus*/
        //Itt gyakorlatilag azok a szalak amik a streamot dolgozzak fel parhuzamosan fernek hozza az ArrayList-hez.
        // Itt egy race condition van es mivel az ArrayList az nem szalbiztos ezert rosszul fog mukodni, bizonyos
        // elemek ebbol a listabol el fognak veszni
        //megoldas lehet a Collections.synchronizedList(new ArrayList<>()) ez egy olyan burkolo peldanyt hoz letre
        // (synchronizedList), amelyik mar szalbiztos. Azonban ilyent soha ne hasznaljunk. Egyreszt ellentetes a
        // funkcionalis programozassal, amikor a fuggveny az alapveto elemi egyseg es a fuggveny csak a bemeneti
        // parameterekkel dolgozhat. Itt latszik hogz nem csak a parameterekkel dolgoyik hanem van egz globalis
        // parameter (list) a fuggvenz szamara dekralalva raadasul ebbe ir is
        List<Integer> list = IntStream.range(0, 100_000)
            .parallel()
            .boxed()//autoboxing -> int-bol Integer .mapToObj(i -> i)
            .collect(
                Collectors.toList());//jo megoldas, hogy valamilzen kollektort hasznalunk ami fel van keszitve a
        // parhuzamos futasra
        System.out.println(list.size());
    }
}
