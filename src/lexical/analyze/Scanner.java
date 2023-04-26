package lexical.analyze;


/*
    Хлюпин Дмитрий, ПМ-21
    Использованная структура: Перемешанная таблица с цепочками
    Анализируемый язык: Си++
*/

//
class Scanner {

    static int NAMELEN = 31;    //
    static int N = 47;          //

    final static int
        lexNone = 0,
        lexName = 1,
        lexAlignas = 2,     lexAlignof = 28,            lexAnd = 54,        lexAnd_Eq = 79,         lexAsm = 104,
        lexAuto = 3,        lexBitand = 29,             lexBitor = 55,      lexBool = 80,           lexBreak = 105,
        lexCase = 4,        lexCatch = 30,              lexChar = 56,       lexChar16_t = 81,       lexChar32_t = 106,
        lexClass = 5,       lexComlp = 31,              lexConst = 57,      lexConstexpr = 82,      lexConst_cast = 107,
        lexContinue = 6,    lexDecltype = 32,           lexDefault = 58,    lexDelete = 83,         lexDo = 108,
        lexDouble = 7,      lexDynamic_cast = 33,       lexElse = 59,       lexEnum = 84,           lexExplicit = 109,
        lexExtern = 8,      lexFalse = 34,              lexFinal = 60,      lexFloat = 85,          lexFriend = 110,
        lexFor = 9,         lexGoto = 35,               lexIf = 61,         lexInline = 86,         lexInt = 111,
        lexLong = 10,       lexMutable = 36,            lexNamespace = 62,  lexNew = 87,            lexNoexcept = 112,
        lexNot = 11,        lexNot_eq = 37,             lexNullptr = 63,    lexOperator = 88,       lexOr = 113,
        lexOr_eq = 12,      lexOverride = 38,           lexPrivate = 64,    lexProtected = 89,      lexPublic = 114,
        lexRegister = 13,   lexReinterpret_cast = 39,   lexReturn = 65,     lexShort = 90,          lexSigned = 115,
        lexSizeof = 14,     lexStatic_assert = 40,      lexStatic = 66,     lexStatic_cast = 91,    lexStruct = 116,
        lexSwitch = 15,     lexTemplate = 41,           lexThis = 67,       lexThread_local = 92,   lexThrow = 117,
        lexTrue = 16,       lexTry = 42,                lexTypedef = 68,    lexTypeid = 93,         lexTypename = 118,
        lexUnion = 17,      lexUnsigned = 43,           lexUsing = 69,      lexVirtual = 94,        lexVoid = 119,
        lexVolatile = 18,   lexWchar_t = 44,            lexWhile = 70,      lexPlus = 95,           lexPlus_Eq = 120,
        lexMinus = 19,      lexMinus_Eq = 45,           lexStar = 71,       lexStar_Eq = 96,        lexSlash = 121,
        lexSlash_Eq = 20,   lexModulo = 46,             lexModulo_Eq = 72,  lexCaret = 97,          lexCaret_Eq = 122,
        lexAmpersand = 21,  lexAmpersand_Eq = 47,       lexPipe = 73,       lexPipe_Eq = 98,        lexTilde = 123,
        lexNot_Eq = 22,     lexLogical_And = 48,        lexAssign = 74,     lexLogical_Or = 99,     lexDouble_Colon = 124,
        lexEqual = 23,      lexShift_Left = 49,         lexShift_Right = 75,lexQuestion_Mark = 100, lexColon = 125,
        lexLess = 24,       lexLess_Eq = 50,            lexGreater = 76,    lexGreater_Eq = 101,    lexClose_Paren = 126,
        lexDot = 25,        lexArrow = 51,              lexEllipsis = 77,   lexSemicolon = 102,     lexOpen_Paren = 127,
        lexComma = 26,      lexOpen_Bracket = 52,       lexDot_Star = 78,   lexOpen_Brace = 103,    lexClose_Brace = 128,
        lexClose_Bracket = 27,  lexArrow_Star = 53;

    //
    static int Lex;
    //
    private static StringBuffer Buf = new StringBuffer(NAMELEN);
    static String Name;

    //
    static int Num;

    private static int KWNUM = 34;
    private static int nkw = 0;



    static private class Item {
        String key;
        int data;
        Item next;
        Item() {}
        Item(String key, int data, Item next) {
            this.key = key;
            this.data = data;
            this.next = next;
        }
    }

    static private class tChainHash {
        Item items;
    }

    private static Item[] KWTable = new Item[KWNUM];
    private static tChainHash[] H;


    private static void EnterKW(String Name, int Lex) {
        (KWTable[nkw] = new Item()).key = new String(Name); // !!
        KWTable[nkw++].data = Lex;
    }

    private static int TestKW() {
        Item p = Search(H, Name);
        if (p != null) {
            return p.data;
        } else {
            return lexName;
        }
    }

    void InitChainHash() {
        for (int i = 0; i < N; i++) {
            H[i] = null;
        }
    }

    /* Плохая функция */
    static int ChainHash(String K) {
        final int BASE = 17;
        long m = 0;

        for (int i = 0; i < K.length(); i++) {
            m = BASE * m + Character.toUpperCase(K.charAt(i));
        }

        return Math.abs((int)(m % N));
    }

    void Add2ChainHash(tChainHash[] T, String K, int D) {
        int h = ChainHash(K);
        Item p = T[h].items;

        while (p != null && K != p.key) {
            p = p.next;
        }

        if (p == null) {
            Item q = new Item(K, D, T[h].items);
            T[h].items = q;
        }
    }

    // Поиск
    static Item Search(tChainHash[] T, String K) {
        int h = ChainHash(K);
        Item p = T[h].items;

        while (p != null && K != p.key) {
            p = p.next;
        }

        return p;
    }

    private static void Ident() {
        int i = 0;
        Buf.setLength(0);
        do {
            if (i++ < NAMELEN) {
                Buf.append((char) Text.Ch);
            } else {
                Error.Message("Слишком длинное имя");
            }
            Text.NextCh();
        } while (Character.isLetterOrDigit((char)Text.Ch));
        Name = Buf.toString();
        Lex = TestKW(); //
    }

    private static void Number() {
        Lex = lexNum;
        Num = 0;

        do {
            int d = Text.Ch - '0';
            if ((Integer.MAX_VALUE - d)/10 >= Num) {
                Num = 10 * Num + d;
            } else {
                Error.Message("Слишком большое число");
            }
            Text.NextCh();
        } while (Character.isDigit((char)Text.Ch));
    }

    private static void Comment() {
        Text.NextCh();
        do {
            while (Text.Ch != '*' && Text.Ch != Text.chEOT) {
                if (Text.Ch == '(') {
                    Text.NextCh();
                    if (Text.Ch == '*') {
                        Comment();
                    }
                } else {
                    Text.NextCh();
                }
                if (Text.Ch == '*') {
                    Text.NextCh();
                }
            }
        } while (Text.Ch != ')' && Text.Ch != Text.chEOT);
        if (Text.Ch == ')') {
            Text.NextCh();
        } else {
            Location.LexPos = Location.Pos;
            Error.Message("ЌҐ § Є®­зҐ­ Є®¬¬Ґ­в аЁ©");
        }
    }

/*
private static void Comment() {
   int Level = 1;
   Text.NextCh();
   do
      if( Text.Ch == '*' ) {
         Text.NextCh();
         if( Text.Ch == ')' )
            { Level--; Text.NextCh(); }
         }
      else if( Text.Ch == '(' ) {
         Text.NextCh();
         if( Text.Ch == '*' )
            { Level++; Text.NextCh(); }
         }
      else //if ( Text.Ch <> chEOT )
         Text.NextCh();
   while( Level != 0 && Text.Ch != Text.chEOT );
   if( Level != 0 ) {
      Location.LexPos = Location.Pos;
      Error.Message("ЌҐ § Є®­зҐ­ Є®¬¬Ґ­в аЁ©");
   }
}
*/

    static void NextLex() {
        while (Text.Ch == Text.chSPACE || Text.Ch == Text.chTAB || Text.Ch == Text.chEOL) {
            Text.NextCh();
        }

        Location.LexPos = Location.Pos;

        if (Character.isLetter((char)Text.Ch)) {
            Ident();
        } else if (Character.isDigit((char)Text.Ch)) {
            Number();
        } else {
            switch (Text.Ch) {
                case ';':
                    Text.NextCh();
                    Lex = lexSemi;
                    break;
                case ':':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexAss;
                    } else {
                        Lex = lexColon;
                    }
                    break;
                case '.':
                    Text.NextCh();
                    Lex = lexDot;
                    break;
                case ',':
                    Text.NextCh();
                    Lex = lexComma;
                    break;
                case '=':
                    Text.NextCh();
                    Lex = lexEQ;
                    break;
                case '#':
                    Text.NextCh();
                    Lex = lexNE;
                    break;
                case '<':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexLE;
                    } else {
                        Lex = lexLT;
                    }
                    break;
                case '>':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexGE;
                    } else {
                        Lex = lexGT;
                    }
                    break;
                case '(':
                    Text.NextCh();
                    if (Text.Ch == '*') {
                        Comment();
                        NextLex();
                    } else {
                        Lex = lexLPar;
                    }
                    break;
                case ')':
                    Text.NextCh();
                    Lex = lexRPar;
                    break;
                case '+':
                    Text.NextCh();
                    Lex = lexPlus;
                    break;
                case '-':
                    Text.NextCh();
                    Lex = lexMinus;
                    break;
                case '*':
                    Text.NextCh();
                    Lex = lexMult;
                    break;
                case Text.chEOT:
                    Lex = lexEOT;
                    break;
                default:
                    Error.Message("ЌҐ¤®ЇгбвЁ¬л© бЁ¬ў®«");
            }
        }
    }

    static void Init() {
        EnterKW("ARRAY",     lexNone);
        EnterKW("BY",        lexNone);
        EnterKW("BEGIN",     lexBEGIN);
        EnterKW("CASE",      lexNone);
        EnterKW("CONST",     lexCONST);
        EnterKW("DIV",       lexDIV);
        EnterKW("DO",        lexDO);
        EnterKW("ELSE",      lexELSE);
        EnterKW("ELSIF",     lexELSIF);
        EnterKW("END",       lexEND);
        EnterKW("EXIT",      lexNone);
        EnterKW("FOR",       lexNone);
        EnterKW("IF",        lexIF);
        EnterKW("IMPORT",    lexIMPORT);
        EnterKW("IN",        lexNone);
        EnterKW("IS",        lexNone);
        EnterKW("LOOP",      lexNone);
        EnterKW("MOD",       lexMOD);
        EnterKW("MODULE",    lexMODULE);
        EnterKW("NIL",       lexNone);
        EnterKW("OF",        lexNone);
        EnterKW("OR",        lexNone);
        EnterKW("POINTER",   lexNone);
        EnterKW("PROCEDURE", lexNone);
        EnterKW("RECORD",    lexNone);
        EnterKW("REPEAT",    lexNone);
        EnterKW("RETURN",    lexNone);
        EnterKW("THEN",      lexTHEN);
        EnterKW("TO",        lexNone);
        EnterKW("TYPE",      lexNone);
        EnterKW("UNTIL",     lexNone);
        EnterKW("VAR",       lexVAR);
        EnterKW("WHILE",     lexWHILE);
        EnterKW("WITH",      lexNone);

        NextLex();
    }

}