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

    public static final char EOT = '\u0000';  // Символ конца текста
    public static final char EOL = '\n';      // Символ конца строки
    public static final char TAB = '\t';      // Символ табуляции
    public static final int TAB_SIZE = 3;        // Размер табуляции

    public static Lex Lex;      // Текущая лексема
    public static String Name;  // Строковое значение имени
    public static int Num;      // Значение числовых литералов
    public static int LexPos;   // Позиция начала лексемы

    public char Ch;     // Очередной символ
    public int Line;    // Номер строки
    public int Pos;     // Номер символа в строке

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

    // Поиск
    void Search(tChainHash T, tKey K, tItem res) {
        int h = ChainHash(K);
        tItem p = T.items[h];

        while (p != null && K.getKey() != p.key.getKey()) {
            p = p.next;
        }

        res = p;
    }

    public tData TestKW() {
        tItem p;
        Search(H, Name, p);
        if (p != null) {
            return p.data;
        } else {
            return Lex.lexName;
        }
    }

    void Ident() {
        int i = 0;
        Name = "";
        do {
            if (i < NAME_LEN) {
                i++;
                Name += Ch;
            } else {
                Error("Слишком длинное имя");
            }
            NextCh();
        } while (((Ch >= 'A') && (Ch <= 'Z')) || ((Ch >= 'a') && (Ch <= 'z')) || ((Ch >= '0') && (Ch <= '9')));
        Lex = TestKW(); // Проверка на ключевое слово
    }





    void NextCh() {
        if (f.hasNext()) {
            Ch = EOT;
        } else if (Ch == EOL) {
            f.skip(Long.MAX_VALUE); // переход к следующей строке
            print();
            Line++;
            Pos = 0;
            Ch = EOL;
        } else {
            int c = f.read();
            if (c == -1) { // достигнут конец файла
                Ch = EOT;
            } else {
                Ch = (char) c;
                if (Ch != TAB) {
                    System.out.print(Ch);
                    Pos++;
                } else {
                    do {
                        System.out.print(" ");
                        Pos++;
                    } while (Pos % TAB_SIZE != 0);
                }
            }
        }
    }

}