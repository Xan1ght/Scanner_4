package lexical.analyze;

public class Counter {
    static int score;

    static void Add() {
        score++;
    }

    static void Info() {
        System.out.println("Общее количество лексем: " + score + ".");
    }
}