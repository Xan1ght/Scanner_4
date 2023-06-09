package lexical.analyze;

// Счетчик
public class Counter {
    static int score;
    static int[] scoreLex = new int[Scanner.NMAX];

    // Добавление лексемы в счетчик
    static void Add() {
        if (Scanner.Lex == Scanner.lexEllipsis) {           // Частный случай лексемы: ...
            score--;
            scoreLex[Scanner.lexDot]--;
        }

        score++;
        scoreLex[Scanner.Lex]++;
    }

    // Вывод общего количества лексем
    static void InfoAllLex() {
        System.out.println("Общее количество лексем: " + score + ".");
    }


    // Добавление лексем помимо ключевых слов для вывода счетчика отдельных лексем
    static void AllLexAdd2ChainHash() {
        Scanner.Add2ChainHash(Scanner.H,    "Имя",                                  Scanner.lexName);

        Scanner.Add2ChainHash(Scanner.H,    "Число (int)",                          Scanner.lexNumInt);

        Scanner.Add2ChainHash(Scanner.H,    "Число (10-е)",                         Scanner.lexNum);
        Scanner.Add2ChainHash(Scanner.H,    "Число (10-е + U)",                     Scanner.lexNumU);
        Scanner.Add2ChainHash(Scanner.H,    "Число (10-е + U + L)",                 Scanner.lexNumUL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (10-е + U + L + L)",             Scanner.lexNumULL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (10-е + L)",                     Scanner.lexNumL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (10-е + L + L)",                 Scanner.lexNumLL);

        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е)",                        Scanner.lexNumBin);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е + U)",                    Scanner.lexNumBinU);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е + U + L)",                Scanner.lexNumBinUL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е + U + L + L)",            Scanner.lexNumBinULL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е + L)",                    Scanner.lexNumBinL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е + L + L)",                Scanner.lexNumBinLL);

        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е)",                          Scanner.lexNumOct);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е + U)",                      Scanner.lexNumOctU);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е + U + L)",                  Scanner.lexNumOctUL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е + U + L + L)",              Scanner.lexNumOctULL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е + L)",                      Scanner.lexNumOctL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е + L + L)",                  Scanner.lexNumOctLL);

        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е)",                         Scanner.lexNumHex);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е + U)",                     Scanner.lexNumHexU);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е + U + L)",                 Scanner.lexNumHexUL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е + U + L + L)",             Scanner.lexNumHexULL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е + L)",                     Scanner.lexNumHexL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е + L + L)",                 Scanner.lexNumHexLL);

        Scanner.Add2ChainHash(Scanner.H,    "Число (real)",                         Scanner.lexNumReal);

        Scanner.Add2ChainHash(Scanner.H,    "Число (Double)",                       Scanner.lexNumDouble);
        Scanner.Add2ChainHash(Scanner.H,    "Число (Long Double)",                  Scanner.lexNumDoubleL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (Float)",                        Scanner.lexNumFloat);


        Scanner.Add2ChainHash(Scanner.H,    "+",                Scanner.lexPlus);
        Scanner.Add2ChainHash(Scanner.H,    "++",               Scanner.lexPlus_Plus);
        Scanner.Add2ChainHash(Scanner.H,    "+=",               Scanner.lexPlus_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "-",                Scanner.lexMinus);
        Scanner.Add2ChainHash(Scanner.H,    "--",               Scanner.lexMinus_Minus);
        Scanner.Add2ChainHash(Scanner.H,    "-=",               Scanner.lexMinus_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "*",                Scanner.lexStar);
        Scanner.Add2ChainHash(Scanner.H,    "*=",               Scanner.lexStar_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "/",                Scanner.lexSlash);
        Scanner.Add2ChainHash(Scanner.H,    "/=",               Scanner.lexSlash_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "%",                Scanner.lexModulo);
        Scanner.Add2ChainHash(Scanner.H,    "%=",               Scanner.lexModulo_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "^",                Scanner.lexCaret);
        Scanner.Add2ChainHash(Scanner.H,    "^=",               Scanner.lexCaret_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "&",                Scanner.lexAmpersand);
        Scanner.Add2ChainHash(Scanner.H,    "&&",               Scanner.lexLogical_And);
        Scanner.Add2ChainHash(Scanner.H,    "&=",               Scanner.lexAmpersand_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "|",                Scanner.lexPipe);
        Scanner.Add2ChainHash(Scanner.H,    "||",               Scanner.lexLogical_Or);
        Scanner.Add2ChainHash(Scanner.H,    "|=",               Scanner.lexPipe_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "?",                Scanner.lexQuestion_Mark);
        Scanner.Add2ChainHash(Scanner.H,    "!",                Scanner.lexExclaim);
        Scanner.Add2ChainHash(Scanner.H,    "!=",               Scanner.lexNot_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "=",                Scanner.lexAssign);
        Scanner.Add2ChainHash(Scanner.H,    "==",               Scanner.lexEqual);

        Scanner.Add2ChainHash(Scanner.H,    ":",                Scanner.lexColon);
        Scanner.Add2ChainHash(Scanner.H,    "::",               Scanner.lexDouble_Colon);
        Scanner.Add2ChainHash(Scanner.H,    ";",                Scanner.lexSemicolon);

        Scanner.Add2ChainHash(Scanner.H,    ".",                Scanner.lexDot);
        Scanner.Add2ChainHash(Scanner.H,    ".*",               Scanner.lexDot_Star);
        Scanner.Add2ChainHash(Scanner.H,    "...",              Scanner.lexEllipsis);
        Scanner.Add2ChainHash(Scanner.H,    ",",                Scanner.lexComma);

        Scanner.Add2ChainHash(Scanner.H,    "<",                Scanner.lexLess);
        Scanner.Add2ChainHash(Scanner.H,    "<<",               Scanner.lexShift_Left);
        Scanner.Add2ChainHash(Scanner.H,    "<=",               Scanner.lexLess_Eq);
        Scanner.Add2ChainHash(Scanner.H,    "<<=",              Scanner.lexShift_Left_Eq);

        Scanner.Add2ChainHash(Scanner.H,    ">",                Scanner.lexGreater);
        Scanner.Add2ChainHash(Scanner.H,    ">>",               Scanner.lexShift_Right);
        Scanner.Add2ChainHash(Scanner.H,    ">=",               Scanner.lexGreater_Eq);
        Scanner.Add2ChainHash(Scanner.H,    ">>=",              Scanner.lexShift_Right_Eq);

        Scanner.Add2ChainHash(Scanner.H,    "->",               Scanner.lexArrow);
        Scanner.Add2ChainHash(Scanner.H,    "->*",              Scanner.lexArrow_Star);

        Scanner.Add2ChainHash(Scanner.H,    "#",                Scanner.lexHash);
        Scanner.Add2ChainHash(Scanner.H,    "##",               Scanner.lexHash_Hash);

        Scanner.Add2ChainHash(Scanner.H,    "~",                Scanner.lexTilde);

        Scanner.Add2ChainHash(Scanner.H,    "(",                Scanner.lexOpen_Paren);
        Scanner.Add2ChainHash(Scanner.H,    ")",                Scanner.lexClose_Paren);

        Scanner.Add2ChainHash(Scanner.H,    "{",                Scanner.lexOpen_Brace);
        Scanner.Add2ChainHash(Scanner.H,    "}",                Scanner.lexClose_Brace);

        Scanner.Add2ChainHash(Scanner.H,    "[",                Scanner.lexOpen_Bracket);
        Scanner.Add2ChainHash(Scanner.H,    "]",                Scanner.lexClose_Bracket);


        Scanner.Add2ChainHash(Scanner.H,    "Строка",                Scanner.lexString);
        Scanner.Add2ChainHash(Scanner.H,    "Символ",                Scanner.lexCharacter);


        Scanner.Add2ChainHash(Scanner.H,    "\\n",              Scanner.lexBackslash_Newline);
        Scanner.Add2ChainHash(Scanner.H,    "\\t",              Scanner.lexBackslash_Tab);
        Scanner.Add2ChainHash(Scanner.H,    "\\e",              Scanner.lexBackslash_Escape);
        Scanner.Add2ChainHash(Scanner.H,    "\\v",              Scanner.lexBackslash_Vertical_Tab);
        Scanner.Add2ChainHash(Scanner.H,    "\\b",              Scanner.lexBackslash_Backspace);
        Scanner.Add2ChainHash(Scanner.H,    "\\r",              Scanner.lexBackslash_Carriage_Return);
        Scanner.Add2ChainHash(Scanner.H,    "\\f",              Scanner.lexBackslash_Form_Feed);
        Scanner.Add2ChainHash(Scanner.H,    "\\a",              Scanner.lexBackslash_Alert_Or_Bell);
        Scanner.Add2ChainHash(Scanner.H,    "\\\\",             Scanner.lexBackslash_Backslash);
        Scanner.Add2ChainHash(Scanner.H,    "\\?",              Scanner.lexBackslash_Question_Mark);
        Scanner.Add2ChainHash(Scanner.H,    "\\'",              Scanner.lexBackslash_Single_Quote);
        Scanner.Add2ChainHash(Scanner.H,    "\\\"",             Scanner.lexBackslash_Double_Quote);
        Scanner.Add2ChainHash(Scanner.H,    "\\ooo",            Scanner.lexBackslash_Octal_Value);
        Scanner.Add2ChainHash(Scanner.H,    "\\hhh",            Scanner.lexBackslash_Hexadecimal_Value);
        Scanner.Add2ChainHash(Scanner.H,    "\\0",              Scanner.lexBackslash_Null_Character);
    }

    // Сортировка вставками, чтобы +\- по алфавиту и красоте смотрелось
    public static void insertionSort(String[] name, int[] data, int length) {
        for (int i = 0; i < length; i++) {
            int j = i - 1;
            int key1 = data[i];
            String key2 = name[i];
            while (j >= 0 && data[j] > key1) {
                data[j + 1] = data[j];
                name[j + 1] = name[j];
                j--;
            }
            data[j + 1] = key1;
            name[j + 1] = key2;
        }
    }


    // Вывод количества отдельных лексем, для нормальной проверки с общей
    static void InfoLex() {
        int j = 0;
        String[] nameLex = new String[Scanner.NMAX];
        int[] dataLex = new int[Scanner.NMAX];

        AllLexAdd2ChainHash();

        for (int i = 0; i < Scanner.N; i++) {
            Scanner.Item p = Scanner.H[i].items;
            while (p != null) {
                if (scoreLex[p.data] != 0 && j < Scanner.N) {
                    nameLex[j] = p.key;
                    dataLex[j] = p.data;
                    j++;
                }
                p = p.next;
            }
        }

        nameLex[j] = "Число (int)";
        dataLex[j] = Scanner.lexNumInt;
        int k = Scanner.lexNum;
        while (k <= Scanner.lexNumHexLL) {
            scoreLex[dataLex[j]] += scoreLex[k];
            k++;
        }
        if (scoreLex[dataLex[j]] != 0) {
            j++;
        }

        nameLex[j] = "Число (real)";
        dataLex[j] = Scanner.lexNumReal;
        while (k <= Scanner.lexNumDoubleL) {
            scoreLex[dataLex[j]] += scoreLex[k];
            k++;
        }
        if (scoreLex[dataLex[j]] != 0) {
            j++;
        }

        insertionSort(nameLex, dataLex, j);

        System.out.println("Количество лексем по отдельности: ");
        for (int i = 0; i < j; i++) {
            if (dataLex[i] >= Scanner.lexNum && dataLex[i] <= Scanner.lexNumHexLL) {
                System.out.println("\033[37m" + (" ").repeat(6) + nameLex[i] + (" ").repeat(44 - nameLex[i].length()) +
                        "<< " + scoreLex[dataLex[i]] + "\033[0m");
            } else if (dataLex[i] >= Scanner.lexNumFloat && dataLex[i] <= Scanner.lexNumDoubleL) {
                System.out.println("\033[37m" + (" ").repeat(6) + nameLex[i] + (" ").repeat(44 - nameLex[i].length()) +
                        "<< " + scoreLex[dataLex[i]] + "\033[0m");
            } else if (dataLex[i] == Scanner.lexNumInt || dataLex[i] == Scanner.lexNumReal) {
                System.out.println("\033[33m" + nameLex[i] + (" ").repeat(50 - nameLex[i].length()) +
                        "<< " + scoreLex[dataLex[i]] + "\033[0m");
            } else if (dataLex[i] == Scanner.lexCharacter || dataLex[i] == Scanner.lexString){
                System.out.println("\033[32m" + nameLex[i] + (" ").repeat(50 - nameLex[i].length()) +
                        "<< " + scoreLex[dataLex[i]] + "\033[0m");
            } else if (dataLex[i] == Scanner.lexName) {
                System.out.println("\033[35m" + nameLex[i] + (" ").repeat(50 - nameLex[i].length()) +
                        "<< " + scoreLex[dataLex[i]] + "\033[0m");
            } else {
                System.out.println(nameLex[i] + (" ").repeat(50 - nameLex[i].length()) +
                        "<< " + scoreLex[dataLex[i]]);
            }
        }
    }
}