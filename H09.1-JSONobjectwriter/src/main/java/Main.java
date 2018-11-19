import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.h9.jsonobjectwriter.JsonObjectWriter;
import ru.otus.h9.UserObjects;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {

        try {
            log.info("{}", JsonObjectWriter.writeObject(1000));


            log.info("{}", JsonObjectWriter.writeObject("Велосипед JSON'а"));


            Map<Integer, String> mapOfIntToStr = new HashMap<>();

            mapOfIntToStr.put(10, "десять");
            mapOfIntToStr.put(20, "двадцать");
            mapOfIntToStr.put(30, "тридцать");

            log.info("{}", JsonObjectWriter.writeObject(mapOfIntToStr));


            log.info("{}", JsonObjectWriter.writeObject(new UserObjects()));


        } catch (Exception e) {
            log.error("Ошибка - о ужас!!!", e);
        }
    }
}
