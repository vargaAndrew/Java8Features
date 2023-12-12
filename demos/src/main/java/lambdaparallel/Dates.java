package lambdaparallel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Dates {

    public static void main(String[] args) {
        new Dates().generateDates();
    }
    //parhuzamositas csak nagy elemszam eseten jar elonnyel mivel kis elemszamnal nagyobb overhead-el jar a szalak
    //parhuzamos streameknel fontos, hogy ne hasznaljunk kulso allapotot, amibe irunk. Raadasul parhuzamos modon
    // adminisztralasa

    //szemleltetni a nagy processor kapacitast/eroforras igenyes muveletekkel lehet. tipikusan ilzen a formazasok
    // illetve a parsolasok. Pl egy datum leformazasa Stringge viszonylag eroforras igenyes muvelet
    private void generateDates() {//legeneralunk 1M db datumot. Ezt megformazzuk es osszegyujtjuk egy String
        // listaba
        long start = System.currentTimeMillis();
        List<String> dates = IntStream.range(0,
                1_000_000)//letrehozok egy olzan int streamot ami 0 tol 1m ig adja az egesz szamokat
            .parallel()//streamek elonye, hogy nagyon gyorsan lehet parhuzamositani gzakorlatilag csak ezt a paralelt
            // kellett megadnom (enelkul a single Thread speed merheto )
            .mapToObj(i -> LocalDateTime.now()
                .format(
                    DateTimeFormatter.ISO_DATE_TIME))//parameterkent atadom az int functiont (i). A szambol egy uj
            // localdate timeot, megformazom
            .collect(Collectors.toList());
        System.out.println(dates.get(0));
        System.out.println("Duration: " + (System.currentTimeMillis() - start));//mennyi ido telt el
        //a teljesitmeny meresehez le kell kerni ay indulasi idot es hogy mennyi ido alatt futott le
    }
}
