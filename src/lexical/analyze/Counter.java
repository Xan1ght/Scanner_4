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



    static void AllLexAdd2ChainHash() {
        Scanner.Add2ChainHash(Scanner.H,    "Имя",                                  Scanner.lexName);

        Scanner.Add2ChainHash(Scanner.H,    "Число",                                Scanner.lexNum);
        Scanner.Add2ChainHash(Scanner.H,    "Число (полож.)",                       Scanner.lexNumU);
        Scanner.Add2ChainHash(Scanner.H,    "Число (полож. + диапозон)",            Scanner.lexNumUL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (полож. + 2х диапозон)",         Scanner.lexNumULL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (+ диапозон)",                   Scanner.lexNumL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (+ 2x диапозон)",                Scanner.lexNumLL);

        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е)",                        Scanner.lexNumBin);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е полож.)",                 Scanner.lexNumBinU);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е полож. + диапозон)",      Scanner.lexNumBinUL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е полож. + 2х диапозон)",   Scanner.lexNumBinULL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е + диапозон)",             Scanner.lexNumBinL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (bin-е + 2x диапозон)",          Scanner.lexNumBinLL);

        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е)",                          Scanner.lexNumOct);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е полож.)",                   Scanner.lexNumOctU);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е полож. + диапозон)",        Scanner.lexNumOctUL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е полож. + 2х диапозон)",     Scanner.lexNumOctULL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е + диапозон)",               Scanner.lexNumOctL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (8-е + 2x диапозон)",            Scanner.lexNumOctLL);

        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е)",                         Scanner.lexNumHex);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е полож.)",                  Scanner.lexNumHexU);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е полож. + диапозон)",       Scanner.lexNumHexUL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е полож. + 2х диапозон)",    Scanner.lexNumHexULL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е + диапозон)",              Scanner.lexNumHexL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (16-е + 2x диапозон)",           Scanner.lexNumHexLL);

        Scanner.Add2ChainHash(Scanner.H,    "Число (двойная)",                      Scanner.lexNumDouble);
        Scanner.Add2ChainHash(Scanner.H,    "Число (двойная + диапозон)",           Scanner.lexNumDoubleL);
        Scanner.Add2ChainHash(Scanner.H,    "Число (одинарная)",                    Scanner.lexNumFloat);


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
    }

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



    static void InfoLex() {
        int j = 0;
        String[] nameLex = new String[Scanner.N];
        int[] dataLex = new int[Scanner.N];

        AllLexAdd2ChainHash();

        for (int i = 0; i < Scanner.N; i++) {
            Scanner.Item p = Scanner.H[i].items;
//            System.out.println(i + " " + scoreLex[i]);
            while (p != null) {
                if (scoreLex[p.data] != 0 && j < Scanner.N) {
                    nameLex[j] = p.key;
                    dataLex[j] = p.data;
                    j++;
                }
                p = p.next;
            }
        }

        insertionSort(nameLex, dataLex, j);

        System.out.println("Количество лексем по отдельности: ");
        for (int i = 0; i < j; i++) {
            System.out.println(nameLex[i] + (" ").repeat(40 - nameLex[i].length()) + "<< " + scoreLex[dataLex[i]]);
        }
    }
}