package ru.otus.H051;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 ДЗ 05. Измерение активности GC
 Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа (young, old) и время которое ушло на сборки в минуту.
 Добиться OutOfMemory в этом приложении через медленное подтекание по памяти (например добавлять элементы в List и удалять только половину).
 Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало с OOM примерно через 5 минут после начала работы.
 Собрать статистику (количество сборок, время на сборрки) по разным типам GC.
 */



public class Main {
    public static void main(String[] args) {
        System.out.println("PID:"+ ManagementFactory.getRuntimeMXBean().getName());
        ExecutorService exserv = Executors.newFixedThreadPool(2);
        System.out.println("запустим поток использование памяти");
        exserv.submit(new MemoryAllocationClass());
        System.out.println("соберём статистику");
        exserv.submit(new MemoryStat());
    }
 }
