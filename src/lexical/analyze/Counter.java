package lexical.analyze;

public class Counter {
    static int score;
    static int[] scoreLex = new int[Scanner.N];

    static void Add() {
        if (Scanner.Lex == Scanner.lexEllipsis) {
            score--;
            scoreLex[Scanner.lexDot]--;
        }

        score++;
        scoreLex[Scanner.Lex]++;
    }

    static void InfoAllLex() {
        System.out.println("Общее количество лексем: " + score + ".");
    }

    static void InfoLex() {
        System.out.println("Количество лексем по отдельности: ");
        for (int i = 0; i < Scanner.N; i++) {
            Scanner.Item p = Scanner.H[i].items;
//            System.out.println(i + " " + scoreLex[i]);
            while (p != null) {
                if (scoreLex[p.data] != 0) {
                    System.out.println(p.key + (" ").repeat(29 - p.key.length()) +"<< " + scoreLex[p.data]);
                }
                p = p.next;
            }

        }
    }
}