package lambdaparallel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Urls {

    public static void main(String[] args) throws Exception {
        new Urls().findLongWords();
    }

    public void findLongWords() throws Exception {//lekerdezzuk az index.hu oldalat es ebben osszegyujtjuk a linkeket
        // . Majd vegigmegyunk a linkeken letoltjuk ayokat az oldalakat emelyekre link mutat es ezeket az oldalakat
        // feldolgozzuk es kikeressuk a 12 karakternel hosszabb szavakat
        String html = fetchUrl("https://www.index.hu");
        List<String> links = collectUrls(html);

        // URL-ek kiszűrése, hogy https://dex.hu címmel kezdődjön
        // Ne legyen duplikáció
        // Max. 10 db
        // URL-ek letöltése
        // Szavak kiválogatása
        // 12-nél hosszabbak leszűrése
        // Szavak, első

        //System.out.println(links);
        //https:dex.hu-s linkeket akarom kiszurni

        long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool(
            4);//egy ForkJoinPool-t kell peldanyositanom, amelynek atadom a szalak szamat

        List<String> words = pool.submit(
            () -> //meg kell hivnom a megfelelo submit metodusat aminek lambda kifejezesben adom at, hogy futtassa le
                // gyakorlatilag a a komplett belsejet
                links.parallelStream() //parhuzamositunk nem a links.stream() helyett a parallelStreamot hivjuk meg
                    //ha parallelStream metodust hasznalunk akkor a JVM a processorok szamatol vagy a processormagok
                    // magok szamatol fuggoen onmaga dont arrol, hogy hany szalon futtassa le. Viszont ezt szemelyre is
                    // lehet szabni
                    .filter(s -> s.startsWith("https://dex.hu"))
                    .distinct()//az ismetlodo url-ek kiszurese -> tehat a duplikatumokat kiszurtuk
                    .limit(10) //az elso 10-re van csak szuksegem
                    .peek(System.out::println)
                    .map(s -> fetchUrl(
                        s))//az url-hez (s) hozzarendelem a fetchUrl hivast -> ez a metodus az url-t lekeri es annak
                    // tartalmat visszaadja
                    //a kovetkezo lepes, hogy ezeket szeretnem szavakra felbontani es ezeket a szavakat szeretnem
                    // tovabbadni a streambe. De mivel minden egyes html oldalhoz tobb szo is tartozhat azokat streame
                    // alakitom es ezeket a streameket kell osszefuyni. Ezt a flatmap metodussal tudom megtenni
                    .flatMap(s -> words(
                        s).stream()) // itt annyit mondok, hogy a html tartalomhoz (s) hozzarendelem azt a streamet
                    // mikor
                    // elosszor is szavak listajava alakitom es annak meghivom a stream metodusat
                    .filter(s -> s.length() >
                        12)//kiszurni azokat amiknek a hossza nagyobb mint 12 karakter. Itt egy predicatet kell megadnom
                    // ami booleant ad vissza
                    .peek(System.out::println)
                    .collect(Collectors.toList())).get();

        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }

    //ez a seged metodus pedig szavakra bontja a html tartalmat. Meghozza ugy, hogz felspliteli nem betu karaktekek
    // alapjan
    private List<String> words(String content) {
        return Arrays.asList(content.split("[\\P{L}]+"));
    }

    //kovetkezo seged metodus egy html-bol kivalogatja az urleket. Egy rehularis kifejezessel a href ertekeket kigyujti
    private List<String> collectUrls(String html) {
        List<String> links = new ArrayList<>();
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(html);
        while (m.find()) {
            links.add(m.group(1));
        }
        return links;
    }

    //ez az egyik seged metodus. Ez kap egy url-t es letolti az adott oldalt es visszaadja stringkent
    private String fetchUrl(String url) {
        try {
            return new BufferedReader(new InputStreamReader(new URL(url).openStream()))
                .lines().collect(Collectors.joining("\n"));//Stringkent visszaadom egy html oldal tartalmat
        } catch (IOException e) {
            throw new IllegalStateException("Can not fetch url: " + url, e);
        }
    }
}
