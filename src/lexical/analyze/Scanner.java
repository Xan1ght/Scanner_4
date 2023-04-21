package lexical.analyze;

import java.util.Arrays;

/*
    Хлюпин Дмитрий, ПМ-21
    Использованная структура: Перемешанная таблица с цепочками
    Анализируемый язык: Си++
*/

public class Scanner {
    public static final int NAME_LEN = 31;      // Наибольшая длина имени
    public static final int N = 47;             // Количество лексем
    public static final int MAX_LENGTH_KEY = 9; // Длина максимальной лексемы Procedure

    public static Lex Lex;      // Текущая лексема
    public static String Name;  // Строковое значение имени
    public static int Num;      // Значение числовых литералов
    public static int LexPos;   // Позиция начала лексемы

    // Класс Item
    public class tItem {
        tKey key;
        tData data;
        tItem next;
        tItem(tKey key, tData data, tItem next) {
            this.key = key;
            this.data = data;
            this.next = next;
        }
    }

    public int nkw;
    public tChainHash H;


    // Инициализация Hash-таблицы с цепочками
    void InitChainHash() {
        H.init();
    }

    /* Плохая функция */
    int ChainHash(tKey K) {
        final int BASE = 17;
        long m = 0;

        for (int i = 0; i < K.getKey().length(); i++) {
            m = BASE * m + Character.toUpperCase(K.getKey().charAt(i));
        }

        return Math.abs((int)(m % N));
    }

    void Add2ChainHash(tChainHash T, tKey K, tData D) {
        int h = ChainHash(K);
        tItem p = T.items[h];

        while (p != null && K != p.key) {
            p = p.next;
        }

        if (p == null) {
            tItem q = new tItem(K, D, T.items[h]);
            T.items[h] = q;
        }
    }







}