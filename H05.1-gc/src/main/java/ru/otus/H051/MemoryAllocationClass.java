package ru.otus.H051;

import java.util.ArrayList;
import java.util.List;

public class MemoryAllocationClass implements Runnable {
        @Override
        public void run() {
            long clcount = 0;
            List<String> ListStrings = new ArrayList<>();
            for(;;) {

                //Выделим память
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 100000; i++) {

                    ListStrings.add(new String(""));

                    list.add(new String(""));

                }


                for (int i = 0; i < 50000; i++) {
                    ListStrings.add(new String(""));
                }

                // Освободим
                if (clcount % 100 == 0) {
                    ListStrings.clear();
                }
                ++clcount;

                try {

                    Thread.sleep(10);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                }
            }
        }
}
