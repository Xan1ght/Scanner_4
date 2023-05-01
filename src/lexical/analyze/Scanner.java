package lexical.analyze;


/*
    Хлюпин Дмитрий, ПМ-21
    Использованная структура: Перемешанная таблица с цепочками
    Анализируемый язык: Си++
*/

//
class Scanner {

    static int NAMELEN = 140;   // Наибольшая длина имени (пока хз)
    static int N = 130;         // Объем таблицы, где 70% заполняется (пока хз)
    static int NMAX = 180;      // Количество всех лексем

    final static int
        lexNone = 0,
        lexName = 1,

        lexNum = 2,             /* число-в10 */     lexNumOct = 14,         /* ч-в8 */      lexNumHex = 20,             /* ч-в16 */
        lexNumU = 3,            /* ч-в10-U */       lexNumOctU = 15,        /* ч-в8-U */    lexNumHexU = 21,            /* ч-в16-U */
        lexNumUL = 4,           /* ч-в10-UL */      lexNumOctUL = 16,       /* ч-в8-UL */   lexNumHexUL = 22,           /* ч-в16-UL */
        lexNumULL = 5,          /* ч-в10-ULL */     lexNumOctULL = 17,      /* ч-в8-ULL */  lexNumHexULL = 23,          /* ч-в16-ULL */
        lexNumL = 6,            /* ч-в10-L */       lexNumOctL = 18,        /* ч-в8-L */    lexNumHexL = 24,            /* ч-в16-L */
        lexNumLL = 7,           /* ч-в10-LL */      lexNumOctLL = 19,       /* ч-в8-LL */   lexNumHexLL = 25,           /* ч-в16-LL */
        lexNumBin = 8,          /* ч-вbin */        lexNumBinL = 12,        /* ч-вbin-L */  lexNumBinLL = 13,           /* ч-вbin-LL */
        lexNumBinU = 9,         /* ч-вbin-U */      lexNumBinUL = 10,       /* ч-вbin-UL */ lexNumBinULL = 11,          /* ч-вbin-ULL */
        lexNumFloat = 26,       /* ч-float */       lexNumDouble = 27,      /* ч-double */  lexNumDoubleL = 28,         /* ч-double-L */

        lexAlignas = 29,        /* alignas */       lexAlignof = 30,        /* alignof */   lexAnd = 31,                /* and */
        lexAnd_Eq = 32,         /* and_eq */        lexAsm = 33,            /* asm */       lexAuto = 34,               /* auto */
        lexBitand = 35,         /* bitand */        lexBitor = 36,          /* bitor */     lexBool = 37,               /* bool */
        lexBreak = 38,          /* break */         lexCase = 39,           /* case */      lexCatch = 40,              /* catch */
        lexChar = 41,           /* char */          lexChar16_t = 42,       /* char16_t */  lexChar32_t = 43,           /* char32_t */
        lexClass = 44,          /* class */         lexComlp = 45,          /* comlp */     lexConst = 46,              /* const */
        lexConst_cast = 47,     /* const_cast */    lexConstexpr = 48,      /* constexpr */ lexContinue = 49,           /* continue */
        lexDecltype = 50,       /* decltype */      lexDefault = 51,        /* default */   lexDelete = 52,             /* delete */
        lexDo = 53,             /* do */            lexDouble = 54,         /* double */    lexDynamic_cast = 55,       /* dynamic_cast */
        lexElse = 56,           /* else */          lexEnum = 57,           /* enum */      lexExplicit = 58,           /* explicit */
        lexExtern = 59,         /* extern */        lexFalse = 60,          /* false */     lexFinal = 61,              /* final */
        lexFloat = 62,          /* float */         lexFriend = 63,         /* friend */    lexFor = 64,                /* for */
        lexGoto = 65,           /* goto */          lexIf = 66,             /* if */        lexInline = 67,             /* inline */
        lexInt = 68,            /* int */           lexLong = 69,           /* long */      lexMutable = 70,            /* mutable */
        lexNamespace = 71,      /* namespace */     lexNew = 72,            /* new */       lexNoexcept = 73,           /* noexcept */
        lexNot = 74,            /* not */           lexNot_eq = 75,         /* not_eq */    lexNullptr = 76,            /* nullptr */
        lexOperator = 77,       /* operator */      lexOr = 78,             /* or */        lexOr_eq = 79,              /* or_eq */
        lexOverride = 80,       /* override */      lexPrivate = 81,        /* private */   lexProtected = 82,          /* protected */
        lexPublic = 83,         /* public */        lexRegister = 84,       /* register */  lexReinterpret_cast = 85,   /* reinterpret_cast */
        lexReturn = 86,         /* return */        lexShort = 87,          /* short */     lexSigned = 88,             /* signed */
        lexSizeof = 89,         /* sizeof */        lexStatic = 90,         /* static */    lexStatic_assert = 91,      /* static_assert */
        lexStatic_cast = 92,    /* static_cast */   lexStruct = 93,         /* struct */    lexSwitch = 94,             /* switch */
        lexTemplate = 95,       /* template */      lexThis = 96,           /* this */      lexThread_local = 97,       /* thread_local */
        lexThrow = 98,          /* throw */         lexTrue = 99,           /* true */      lexTry = 100,               /* try */
        lexTypedef = 101,       /* typedef */       lexTypeid = 102,        /* typeid */    lexTypename = 103,          /* typename */
        lexUnion = 104,         /* union */         lexUnsigned = 105,      /* unsigned */  lexUsing = 106,             /* using */
        lexVirtual = 107,       /* virtual */       lexVoid = 108,          /* void */      lexVolatile = 109,          /* volatile */
        lexWchar_t = 110,       /* wchar_t */       lexWhile = 111,         /* while */

        lexPlus = 112,          /* + */             lexPlus_Plus = 113,     /* ++ */        lexPlus_Eq = 114,           /* += */
        lexMinus = 115,         /* - */             lexMinus_Minus = 116,   /* -- */        lexMinus_Eq = 117,          /* -= */
        lexStar = 118,          /* * */             lexStar_Eq = 119,       /* *= */
        lexSlash = 120,         /* / */             lexSlash_Eq = 121,      /* /= */
        lexModulo = 122,        /* % */             lexModulo_Eq = 123,     /* %= */
        lexCaret = 124,         /* ^ */             lexCaret_Eq = 125,      /* ^= */
        lexAmpersand = 126,     /* & */             lexLogical_And = 127,   /* && */        lexAmpersand_Eq = 128,      /* &= */
        lexPipe = 129,          /* | */             lexLogical_Or = 130,    /* || */        lexPipe_Eq = 131,           /* |= */
        lexQuestion_Mark = 132, /* ? */             lexExclaim = 133,       /* ! */         lexNot_Eq = 134,            /* != */
        lexAssign = 135,        /* = */             lexEqual = 136,         /* == */
        lexColon = 137,         /* : */             lexDouble_Colon = 138,  /* :: */        lexSemicolon = 139,         /* ; */
        lexDot = 140,           /* . */             lexDot_Star = 141,      /* .* */        lexEllipsis = 142,          /* ... */
        lexComma = 143,         /* , */
        lexLess = 144,          /* < */             lexLess_Eq = 146,       /* <= */
        lexShift_Left = 145,    /* << */            lexShift_Left_Eq = 147, /* <<= */
        lexGreater = 148,       /* > */             lexGreater_Eq = 150,    /* >= */
        lexShift_Right = 149,   /* >> */            lexShift_Right_Eq = 151,/* >>= */
        lexArrow = 152,         /* -> */            lexArrow_Star = 153,    /* ->* */
        lexHash = 154,          /* # */             lexHash_Hash = 155,     /* ## */
        lexTilde = 156,         /* ~ */
        lexOpen_Paren = 157,    /* ( */             lexClose_Paren = 158,   /* ) */
        lexOpen_Brace = 159,    /* { */             lexClose_Brace = 160,   /* } */
        lexOpen_Bracket = 161,  /* [ */             lexClose_Bracket = 162, /* ] */

        lexCharacter = 164,
        lexString = 165,

        lexBackslash_Newline = 166,             /* \n */
        lexBackslash_Tab = 167,                 /* \t */
        lexBackslash_Vertical_Tab = 168,        /* \v */
        lexBackslash_Backspace = 169,           /* \b */
        lexBackslash_Carriage_Return = 170,     /* \r */
        lexBackslash_Form_Feed = 171,           /* \f */
        lexBackslash_Alert_Or_Bell = 172,       /* \a */
        lexBackslash_Backslash = 173,           /* \\ */
        lexBackslash_Question_Mark = 174,       /* \? */
        lexBackslash_Single_Quote =175,         /* \' */
        lexBackslash_Double_Quote = 176,        /* \" */
        lexBackslash_Octal_Value = 177,         /* \ooo */
        lexBackslash_Hexadecimal_Value = 178,   /* \hhh */
        lexBackslash_Null_Character = 179,      /* \0 */

        lexEOT = 180;               /* \0 */


    //
    static int Lex;
    //
    private static final StringBuffer Buf = new StringBuffer(NAMELEN);
    static String Name;

    //
    static int Num;

    private static int KWNUM = 34;
    private static int nkw = 0;



    static public class Item {
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

    static public class tChainHash {
        Item items;
    }

    // private static Item[] KWTable = new Item[KWNUM];
    public static tChainHash[] H = new tChainHash[N];


    private static int TestKW() {
        Item p = Search(H, Name);

        if (p != null) {
            return p.data;
        } else {
            return lexName;
        }
    }


    private static void InitChainHash() {
        for (int i = 0; i < N; i++) {
            H[i] = new tChainHash();
        }
    }


    /* Плохая функция */
    private static int ChainHash(String K) {
        final int BASE = 17;
        long m = 0;

        for (int i = 0; i < K.length(); i++) {
            m = BASE * m + Character.toUpperCase(K.charAt(i));
        }

        return Math.abs((int)(m % N));
    }


    public static void Add2ChainHash(tChainHash[] T, String K, int D) {
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
    public static Item Search(tChainHash[] T, String K) {
        int h = ChainHash(K);
        Item p = T[h].items;

        while (p != null && !K.equals(p.key)) {
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

    // Идентификатор-Число
    private static void Number() {
        Lex = lexNum;
        Num = 0;

        if (Text.Ch == '0') {                                   // Проверка наличия у первого числа "0":
            Text.NextCh();
            if (Text.Ch == 'b' || Text.Ch == 'B') {                 // (1) Если "0b", значит число "должно быть" в bin
                Text.NextCh();
                if (Text.Ch == '0' || Text.Ch == '1') {             // "Должно быть"
                    BinNumber();
                    if (SearchSuffixU()) {                          // Если есть в конце U, то unsigned +
                        if (SearchSuffixL()) {                      // Если есть в конце L, то unsigned long +
                            if (SearchSuffixL()) {                  // Если есть в конце L, то unsigned long long.
                                Lex = lexNumBinULL;
                            } else {
                                Lex = lexNumBinUL;
                            }
                        } else {
                            Lex = lexNumBinU;
                        }
                    } else if (SearchSuffixL()) {                   // Если нет  в конце U, но есть L, то long +
                        if (SearchSuffixL()) {                      // Если есть в конце L, то long long.
                            Lex = lexNumBinLL;
                        } else {
                            Lex = lexNumBinL;
                        }
                    } else {                                        // Если нет  в конце U и L, то просто в bin.
                        Lex = lexNumBin;
                    }
                } else {
                    Error.Expected("цифра из бинарного числа");
                }
            } else if (Text.Ch == 'x' || Text.Ch == 'X') {          // (2) Если "0х", значит число "должно быть" в 16
                Text.NextCh();
                if ((Text.Ch >= '0' && Text.Ch <= '9') ||           // "Должно быть"
                        (Text.Ch >= 'A' && Text.Ch <= 'F') ||
                        (Text.Ch >= 'a' && Text.Ch <= 'f')) {
                    HexNumber();
                    if (SearchSuffixU()) {                          // Если есть в конце U, то unsigned +
                        if (SearchSuffixL()) {                      // Если есть в конце L, то unsigned long +
                            if (SearchSuffixL()) {                  // Если есть в конце L, то unsigned long long.
                                Lex = lexNumHexULL;
                            } else {
                                Lex = lexNumHexUL;
                            }
                        } else {
                            Lex = lexNumHexU;
                        }
                    } else if (SearchSuffixL()) {                   // Если нет  в конце U, но есть L, то long +
                        if (SearchSuffixL()) {                      // Если есть в конце L, то long long.
                            Lex = lexNumHexLL;
                        } else {
                            Lex = lexNumHexL;
                        }
                    } else {                                        // Если нет  в конце U и L, то просто в 16.
                        Lex = lexNumHex;
                    }
                } else {
                    Error.Expected("цифра или буква из шестнадцатеричного числа");
                }
            } else if (Character.isDigit((char)Text.Ch)) {          // (3) Если "0 с цифрой", значит число "должно быть" в 8
                OctNumber();                                        // Просто в 8
                if (SearchSuffixU()) {                              // Если есть в конце U, то unsigned +
                    if (SearchSuffixL()) {                          // Если есть в конце L, то unsigned long +
                        if (SearchSuffixL()) {                      // Если есть в конце L, то unsigned long long.
                            Lex = lexNumOctULL;
                        } else {
                            Lex = lexNumOctUL;
                        }
                    } else {
                        Lex = lexNumOctU;
                    }
                } else if (SearchSuffixL()) {                       // Если нет  в конце U, но есть L, то long +
                    if (SearchSuffixL()) {                          // Если есть в конце L, то long long.
                        Lex =lexNumOctLL;
                    } else {
                        Lex = lexNumOctL;
                    }
                } else {                                            // Если нет  в конце U и L, то просто в 8.
                    Lex = lexNumOct;
                }
            } else if (Text.Ch == '.') {                            // (4) Если "0.", значит число должно быть в double/float
                DecNumber();                                        // Просто в 10
                if (SearchSuffixE()) {                              // Если есть E, то 100% double/float + "должно быть"
                    if (SearchSuffixL()) {                          // Если есть в конце L, то long.
                        Lex = lexNumDoubleL;
                    } else if (SearchSuffixF()) {                   // Если нет  в конце L, но есть F, то float.
                        Lex = lexNumFloat;
                    } else {                                        // Если нет  в конце L и F, то просто в double.
                        Lex = lexNumDouble;
                    }
                } else if (SearchSuffixL()) {                       // Если нет E, но есть в конце L, то long.
                    Lex = lexNumDoubleL;
                } else if (SearchSuffixF()) {                       // Если нет E, но есть в конце F, то float.
                    Lex = lexNumFloat;
                } else {                                            // Если нет E, и нет   в конце L и F, то просто в double.
                    Lex = lexNumDouble;
                }
            } else {                                                // (5) Если "0" и только, значит число должно быть в 10\double\float
                if (SearchSuffixE()) {                              // Если есть E, то 100% double/float + проверка ошибки
                    if (SearchSuffixL()) {                          // Если есть в конце L, то long.
                        Lex = lexNumDoubleL;
                    } else if (SearchSuffixF()) {                   // Если нет  в конце L, но есть F, то float.
                        Lex = lexNumFloat;
                    } else {                                        // Если нет  в конце L и F, то просто в double.
                        Lex = lexNumDouble;
                    }
                } else if (SearchSuffixU()) {                       // Если есть в конце U, то unsigned +
                    if (SearchSuffixL()) {                          // Если есть в конце L, то unsigned long +
                        if (SearchSuffixL()) {                      // Если есть в конце L, то unsigned long long.
                            Lex = lexNumULL;
                        } else {
                            Lex = lexNumUL;
                        }
                    } else {
                        Lex = lexNumU;
                    }
                } else if (SearchSuffixL()) {                       // Если нет  в конце U, но есть L, то long +
                    if (SearchSuffixL()) {                          // Если есть в конце L, то long long.
                        Lex =lexNumLL;
                    } else {
                        Lex = lexNumL;
                    }
                } else {                                            // Если нет  в конце U и L, то просто в 10.
                    Lex = lexNum;
                }
            }
        } else {                                                // Проверка если будет любое число:
            DecNumber();                                            // "Должно быть" в 10
            if (SearchSuffixE()) {                                  // Если есть E, то 100% double/float + проверка ошибки
                if (SearchSuffixL()) {                              // Если есть в конце L, то long.
                    Lex = lexNumDoubleL;
                } else if (SearchSuffixF()) {                       // Если нет  в конце L, но есть F, то float.
                    Lex = lexNumFloat;
                } else {                                            // Если нет  в конце L и F, то просто в double.
                    Lex = lexNumDouble;
                }
            } else if (SearchSuffixU()) {                           // Если есть в конце U, то unsigned +
                if (SearchSuffixL()) {                              // Если есть в конце L, то unsigned long +
                    if (SearchSuffixL()) {                          // Если есть в конце L, то unsigned long long.
                        Lex = lexNumULL;
                    } else {
                        Lex = lexNumUL;
                    }
                } else {
                    Lex = lexNumU;
                }
            } else if (SearchSuffixL()) {                           // Если нет  в конце U, но есть L, то long +
                if (SearchSuffixL()) {                              // Если есть в конце L, то long long.
                    Lex =lexNumLL;
                } else {
                    Lex = lexNumL;
                }
            } else if (Text.Ch == '.') {
                DecNumber();
                if (SearchSuffixE()) {                              // Если есть E, то 100% double/float + проверка ошибки
                    if (SearchSuffixL()) {                          // Если есть в конце L, то long.
                        Lex = lexNumDoubleL;
                    } else if (SearchSuffixF()) {                   // Если нет  в конце L, но есть F, то float.
                        Lex = lexNumFloat;
                    } else {                                        // Если нет  в конце L и F, то просто в double.
                        Lex = lexNumDouble;
                    }
                } else if (SearchSuffixL()) {                       // Если нет E, но есть в конце L, то long.
                    Lex = lexNumDoubleL;
                } else if (SearchSuffixF()) {                       // Если нет E, но есть в конце F, то float.
                    Lex = lexNumFloat;
                } else {                                            // Если нет E, и нет   в конце L и F, то просто в double.
                    Lex = lexNumDouble;
                }
            } else {                                            // Если нет  в конце U и L, то просто в 10.
                Lex = lexNum;
            }
        }
    }

    private static void BinNumber() {
        do {
            Text.NextCh();
        } while (Text.Ch == '0' || Text.Ch == '1');
    }

    private static void OctNumber() {
        do {
            Text.NextCh();
        } while (Text.Ch >= '0' && Text.Ch <= '7');
    }

    private static void HexNumber() {
        do {
            Text.NextCh();
        } while ((Text.Ch >= '0' && Text.Ch <= '9') || (Text.Ch >= 'A' && Text.Ch <= 'F') || (Text.Ch >= 'a' && Text.Ch <= 'f'));
    }

    private static void DecNumber() {
        do {
            Text.NextCh();
        } while (Character.isDigit((char)Text.Ch));
    }

//        do {
//            int d = Text.Ch - '0';
//            if ((Integer.MAX_VALUE - d)/10 >= Num) {
//                Num = 10 * Num + d;
//            } else {
//                Error.Message("Слишком большое число");
//            }
//            Text.NextCh();
//        } while (Character.isDigit((char)Text.Ch));



    private static boolean SearchSuffixE() {
        if (Text.Ch == 'E' || Text.Ch == 'e') {
            Text.NextCh();
            if (Text.Ch == '-') {
                Text.NextCh();
            } else if (Text.Ch == '+') {
                Text.NextCh();
            }
            if (Character.isDigit((char)Text.Ch)) {
                DecNumber();
            } else {
                Error.Expected("цифра");
            }
            return true;
        } else {
            return false;
        }
    }


    private static boolean SearchSuffixF() {
        if (Text.Ch == 'F' || Text.Ch == 'f') {
            Text.NextCh();
            return true;
        } else {
            return false;
        }
    }

    private static boolean SearchSuffixU() {
        if (Text.Ch == 'U' || Text.Ch == 'u') {
            Text.NextCh();
            return true;
        } else {
            return false;
        }
    }

    private static boolean SearchSuffixL() {
        if (Text.Ch == 'L' || Text.Ch == 'l') {
            Text.NextCh();
            return true;
        } else {
            return false;
        }
    }


    private static void CommentStar() {
        Text.NextCh();

        do {
            while (Text.Ch != '*' && Text.Ch != Text.chEOT) {
                Text.NextCh();
            }
            if (Text.Ch == '*') {
                Text.NextCh();
            }
        } while (Text.Ch != '/' && Text.Ch != Text.chEOT);

        if (Text.Ch == Text.chEOT) {
            Location.LexPos = Location.Pos;
            Error.Message("Не закончен комментарий");
        } else {
            Text.NextCh();
        }
    }


    private static void Comment() {
        while (Text.Ch != Text.chEOL && Text.Ch != Text.chEOT) {
            Text.NextCh();
        }
    }

    private static void String() {
        do {
            Text.NextCh();
        } while (Text.Ch != '\"' && Text.Ch != '\\' && Text.Ch != Text.chEOL && Text.Ch != Text.chEOT);

        if (Text.Ch == Text.chEOL || Text.Ch == Text.chEOT) {
            Error.Message("Не закончена строка");
        } else if (Text.Ch == '\\') {
            Text.NextCh();
            if (Text.Ch == Text.chEOT) {
                Error.Message("Не закончена строка");
            } else {
                String();
            }
        } else {
            Text.NextCh();
        }
    }


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
                    Lex = lexSemicolon;
                    break;
                case ':':
                    Text.NextCh();
                    if (Text.Ch == ':') {
                        Text.NextCh();
                        Lex = lexDouble_Colon;
                    } else {
                        Lex = lexColon;
                    }
                    break;
                case '.':
                    Text.NextCh();
                    if (Text.Ch == '*') {
                        Text.NextCh();
                        Lex = lexDot_Star;
                    } else if (Text.Ch == '.') {
                        if (Lex == lexDot) {
                            Text.NextCh();
                            Lex = lexEllipsis;
                        } else {
                            Lex = lexDot;
                        }
                    } else if (Character.isDigit((char)Text.Ch)) {
                        DecNumber();
                        if (SearchSuffixE()) {                              // Если есть E, то 100% double/float + проверка ошибки
                            if (SearchSuffixL()) {                          // Если есть в конце L, то long.
                                Lex = lexNumDoubleL;
                            } else if (SearchSuffixF()) {                   // Если нет  в конце L, но есть F, то float.
                                Lex = lexNumFloat;
                            } else {                                        // Если нет  в конце L и F, то просто в double.
                                Lex = lexNumDouble;
                            }
                        } else if (SearchSuffixL()) {                       // Если нет E, но есть в конце L, то long.
                            Lex = lexNumDoubleL;
                        } else if (SearchSuffixF()) {                       // Если нет E, но есть в конце F, то float.
                            Lex = lexNumFloat;
                        } else {                                            // Если нет E, и нет   в конце L и F, то просто в double.
                            Lex = lexNumDouble;
                        }
                    } else {
                        Lex = lexDot;
                    }
                    break;
                case ',':
                    Text.NextCh();
                    Lex = lexComma;
                    break;
                case '=':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexEqual;
                    } else {
                        Lex = lexAssign;
                    }
                    break;
                case '<':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexLess_Eq;
                    } else if (Text.Ch == '<') {
                        Text.NextCh();
                        if (Text.Ch == '=') {
                            Text.NextCh();
                            Lex = lexShift_Left_Eq;
                        } else {
                            Lex = lexShift_Left;
                        }
                    } else {
                        Lex = lexLess;
                    }
                    break;
                case '>':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexGreater_Eq;
                    } else if (Text.Ch == '>') {
                        Text.NextCh();
                        if (Text.Ch == '=') {
                            Text.NextCh();
                            Lex = lexShift_Right_Eq;
                        } else {
                            Lex = lexShift_Right;
                        }
                    } else {
                        Lex = lexGreater;
                    }
                    break;
                case '(':
                    Text.NextCh();
                    Lex = lexOpen_Paren;
                    break;
                case ')':
                    Text.NextCh();
                    Lex = lexClose_Paren;
                    break;
                case '{':
                    Text.NextCh();
                    Lex = lexOpen_Brace;
                    break;
                case '}':
                    Text.NextCh();
                    Lex = lexClose_Brace;
                    break;
                case '[':
                    Text.NextCh();
                    Lex = lexOpen_Bracket;
                    break;
                case ']':
                    Text.NextCh();
                    Lex = lexClose_Bracket;
                    break;
                case '+':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexPlus_Eq;
                    } else if (Text.Ch == '+') {
                        Text.NextCh();
                        Lex = lexPlus_Plus;
                    } else {
                        Lex = lexPlus;
                    }
                    break;
                case '-':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexMinus_Eq;
                    } else if (Text.Ch == '>') {
                        Text.NextCh();
                        if (Text.Ch == '*') {
                            Text.NextCh();
                            Lex = lexArrow_Star;
                        } else {
                            Lex = lexArrow;
                        }
                    } else if (Text.Ch == '-') {
                        Text.NextCh();
                        Lex = lexMinus_Minus;
                    } else {
                        Lex = lexMinus;
                    }
                    break;
                case '*':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexStar_Eq;
                    } else {
                        Lex = lexStar;
                    }
                    break;
                case '/':
                    Text.NextCh();
                    if (Text.Ch == '*' ) {
                        CommentStar();
                        NextLex();
                    } else if (Text.Ch == '/') {
                        Comment();
                        NextLex();
                    } else if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexSlash_Eq;
                    } else {
                        Lex = lexSlash;
                    }
                    break;
                case '%':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexModulo_Eq;
                    } else {
                        Lex = lexModulo;
                    }
                    break;
                case '&':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexAmpersand_Eq;
                    } else if (Text.Ch == '&') {
                        Text.NextCh();
                        Lex = lexLogical_And;
                    } else {
                        Lex = lexAmpersand;
                    }
                    break;
                case '|':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexPipe_Eq;
                    } else if (Text.Ch == '|') {
                        Text.NextCh();
                        Lex = lexLogical_Or;
                    } else {
                        Lex = lexPipe;
                    }
                    break;
                case '^':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexCaret_Eq;
                    } else {
                        Lex = lexCaret;
                    }
                    break;
                case '?':
                    Text.NextCh();
                    Lex = lexQuestion_Mark;
                    break;
                case '!':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexNot_Eq;
                    } else {
                        Lex = lexExclaim;
                    }
                    break;
                case '~':
                    Text.NextCh();
                    Lex = lexTilde;
                    break;
                case '#':
                    Text.NextCh();
                    if (Text.Ch == '#') {
                        Text.NextCh();
                        Lex = lexHash_Hash;
                    } else {
                        Lex = lexHash;
                    }
                    break;
                case '\"':
                    String();
                    Lex = lexString;
                    break;
                case '\\':
                    Text.NextCh();
                    if (Text.Ch == 'n') {
                        Text.NextCh();
                        Lex = lexBackslash_Newline;
                    } else if (Text.Ch == 't') {
                        Text.NextCh();
                        Lex = lexBackslash_Tab;
                    } else if (Text.Ch == 'v') {
                        Text.NextCh();
                        Lex = lexBackslash_Vertical_Tab;
                    } else if (Text.Ch == 'b') {
                        Text.NextCh();
                        Lex = lexBackslash_Backspace;
                    } else if (Text.Ch == 'r') {
                        Text.NextCh();
                        Lex = lexBackslash_Carriage_Return;
                    } else if (Text.Ch == 'f') {
                        Text.NextCh();
                        Lex = lexBackslash_Form_Feed;
                    } else if (Text.Ch == 'a') {
                        Text.NextCh();
                        Lex = lexBackslash_Alert_Or_Bell;
                    } else if (Text.Ch == '\\') {
                        Text.NextCh();
                        Lex = lexBackslash_Backslash;
                    } else if (Text.Ch == '?') {
                        Text.NextCh();
                        Lex = lexBackslash_Question_Mark;
                    } else if (Text.Ch == '\'') {
                        Text.NextCh();
                        Lex = lexBackslash_Single_Quote;
                    } else if (Text.Ch == '\"') {
                        Text.NextCh();
                        Lex = lexBackslash_Double_Quote;
//                    } else if (Text.Ch == '?') {
//
//                    } else if (Text.Ch == '?') {

                    } else if (Text.Ch == '0') {
                        Text.NextCh();
                        Lex = lexBackslash_Null_Character;
                    } else {
                        Error.Expected("буква/цифра/символ для \\");
                    }
                    break;
                case Text.chEOT:
                    Lex = lexEOT;
                    break;
                default:
                    Error.Message("Недопустимый символ");
            }
        }
    }


    static void Init() {
        InitChainHash();

        Add2ChainHash(H,    "alignas",             lexAlignas);
        Add2ChainHash(H,    "alignof",             lexAlignof);
        Add2ChainHash(H,    "and",                 lexAnd);
        Add2ChainHash(H,    "and_eq",              lexAnd_Eq);
        Add2ChainHash(H,    "asm",                 lexAsm);
        Add2ChainHash(H,    "auto",                lexAuto);

        Add2ChainHash(H,    "bitand",              lexBitand);
        Add2ChainHash(H,    "bitor",               lexBitor);
        Add2ChainHash(H,    "bool",                lexBool);
        Add2ChainHash(H,    "break",               lexBreak);

        Add2ChainHash(H,    "case",                lexCase);
        Add2ChainHash(H,    "catch",               lexCatch);
        Add2ChainHash(H,    "char",                lexChar);
        Add2ChainHash(H,    "char16_t",            lexChar16_t);
        Add2ChainHash(H,    "char32_t",            lexChar32_t);
        Add2ChainHash(H,    "class",               lexClass);
        Add2ChainHash(H,    "comlp",               lexComlp);
        Add2ChainHash(H,    "const",               lexConst);
        Add2ChainHash(H,    "const_cast",          lexConst_cast);
        Add2ChainHash(H,    "constexpr",           lexConstexpr);
        Add2ChainHash(H,    "continue",            lexContinue);

        Add2ChainHash(H,    "decltype",            lexDecltype);
        Add2ChainHash(H,    "default",             lexDefault);
        Add2ChainHash(H,    "delete",              lexDelete);
        Add2ChainHash(H,    "do",                  lexDo);
        Add2ChainHash(H,    "double",              lexDouble);
        Add2ChainHash(H,    "dynamic_cast",        lexDynamic_cast);

        Add2ChainHash(H,    "else",                lexElse);
        Add2ChainHash(H,    "enum",                lexEnum);
        Add2ChainHash(H,    "explicit",            lexExplicit);
        Add2ChainHash(H,    "extern",              lexExtern);

        Add2ChainHash(H,    "false",               lexFalse);
        Add2ChainHash(H,    "final",               lexFinal);
        Add2ChainHash(H,    "float",               lexFloat);
        Add2ChainHash(H,    "friend",              lexFriend);
        Add2ChainHash(H,    "for",                 lexFor);

        Add2ChainHash(H,    "goto",                lexGoto);

        Add2ChainHash(H,    "if",                  lexIf);
        Add2ChainHash(H,    "inline",              lexInline);
        Add2ChainHash(H,    "int",                 lexInt);

        Add2ChainHash(H,    "long",                lexLong);

        Add2ChainHash(H,    "mutable",             lexMutable);

        Add2ChainHash(H,    "namespace",           lexNamespace);
        Add2ChainHash(H,    "new",                 lexNew);
        Add2ChainHash(H,    "noexcept",            lexNoexcept);
        Add2ChainHash(H,    "not",                 lexNot);
        Add2ChainHash(H,    "not_eq",              lexNot_eq);
        Add2ChainHash(H,    "nullptr",             lexNullptr);

        Add2ChainHash(H,    "operator",            lexOperator);
        Add2ChainHash(H,    "or",                  lexOr);
        Add2ChainHash(H,    "or_eq",               lexOr_eq);
        Add2ChainHash(H,    "override",            lexOverride);

        Add2ChainHash(H,    "private",             lexPrivate);
        Add2ChainHash(H,    "protected",           lexProtected);
        Add2ChainHash(H,    "public",              lexPublic);

        Add2ChainHash(H,    "register",            lexRegister);
        Add2ChainHash(H,    "reinterpret_cast",    lexReinterpret_cast);
        Add2ChainHash(H,    "return",              lexReturn);

        Add2ChainHash(H,    "short",               lexShort);
        Add2ChainHash(H,    "signed",              lexSigned);
        Add2ChainHash(H,    "sizeof",              lexSizeof);
        Add2ChainHash(H,    "static",              lexStatic);
        Add2ChainHash(H,    "static_assert",       lexStatic_assert);
        Add2ChainHash(H,    "static_cast",         lexStatic_cast);
        Add2ChainHash(H,    "struct",              lexStruct);
        Add2ChainHash(H,    "switch",              lexSwitch);

        Add2ChainHash(H,    "template",            lexTemplate);
        Add2ChainHash(H,    "this",                lexThis);
        Add2ChainHash(H,    "thread_local",        lexThread_local);
        Add2ChainHash(H,    "throw",               lexThrow);
        Add2ChainHash(H,    "true",                lexTrue);
        Add2ChainHash(H,    "try",                 lexTry);
        Add2ChainHash(H,    "typedef",             lexTypedef);
        Add2ChainHash(H,    "typeid",              lexTypeid);
        Add2ChainHash(H,    "typename",            lexTypename);

        Add2ChainHash(H,    "union",               lexUnion);
        Add2ChainHash(H,    "unsigned",            lexUnsigned);
        Add2ChainHash(H,    "using",               lexUsing);

        Add2ChainHash(H,    "virtual",             lexVirtual);
        Add2ChainHash(H,    "void",                lexVoid);
        Add2ChainHash(H,    "volatile",            lexVolatile);

        Add2ChainHash(H,    "wchar_t",             lexWchar_t);
        Add2ChainHash(H,    "while",               lexWhile);


//        Add2ChainHash(H,    "Имя",                                  lexName);
//
//        Add2ChainHash(H,    "Число",                                lexNum);
//        Add2ChainHash(H,    "Число (полож.)",                       lexNumU);
//        Add2ChainHash(H,    "Число (полож. + диапозон)",            lexNumUL);
//        Add2ChainHash(H,    "Число (полож. + 2х диапозон)",         lexNumULL);
//        Add2ChainHash(H,    "Число (+ диапозон)",                   lexNumL);
//        Add2ChainHash(H,    "Число (+ 2x диапозон)",                lexNumLL);
//
//        Add2ChainHash(H,    "Число (bin-е)",                        lexNumBin);
//        Add2ChainHash(H,    "Число (bin-е полож.)",                 lexNumBinU);
//        Add2ChainHash(H,    "Число (bin-е полож. + диапозон)",      lexNumBinUL);
//        Add2ChainHash(H,    "Число (bin-е полож. + 2х диапозон)",   lexNumBinULL);
//        Add2ChainHash(H,    "Число (bin-е + диапозон)",             lexNumBinL);
//        Add2ChainHash(H,    "Число (bin-е + 2x диапозон)",          lexNumBinLL);
//
//        Add2ChainHash(H,    "Число (8-е)",                          lexNumOct);
//        Add2ChainHash(H,    "Число (8-е полож.)",                   lexNumOctU);
//        Add2ChainHash(H,    "Число (8-е полож. + диапозон)",        lexNumOctUL);
//        Add2ChainHash(H,    "Число (8-е полож. + 2х диапозон)",     lexNumOctULL);
//        Add2ChainHash(H,    "Число (8-е + диапозон)",               lexNumOctL);
//        Add2ChainHash(H,    "Число (8-е + 2x диапозон)",            lexNumOctLL);
//
//        Add2ChainHash(H,    "Число (16-е)",                         lexNumHex);
//        Add2ChainHash(H,    "Число (16-е полож.)",                  lexNumHexU);
//        Add2ChainHash(H,    "Число (16-е полож. + диапозон)",       lexNumHexUL);
//        Add2ChainHash(H,    "Число (16-е полож. + 2х диапозон)",    lexNumHexULL);
//        Add2ChainHash(H,    "Число (16-е + диапозон)",              lexNumHexL);
//        Add2ChainHash(H,    "Число (16-е + 2x диапозон)",           lexNumHexLL);
//
//        Add2ChainHash(H,    "Число (двойная)",                      lexNumDouble);
//        Add2ChainHash(H,    "Число (двойная + диапозон)",           lexNumDoubleL);
//        Add2ChainHash(H,    "Число (одинарная)",                    lexNumFloat);
//
//
//        Add2ChainHash(H,    "+",                lexPlus);
//        Add2ChainHash(H,    "++",               lexPlus_Plus);
//        Add2ChainHash(H,    "+=",               lexPlus_Eq);
//
//        Add2ChainHash(H,    "-",                lexMinus);
//        Add2ChainHash(H,    "--",               lexMinus_Minus);
//        Add2ChainHash(H,    "-=",               lexMinus_Eq);
//
//        Add2ChainHash(H,    "*",                lexStar);
//        Add2ChainHash(H,    "*=",               lexStar_Eq);
//
//        Add2ChainHash(H,    "/",                lexSlash);
//        Add2ChainHash(H,    "/=",               lexSlash_Eq);
//
//        Add2ChainHash(H,    "/",                lexModulo);
//        Add2ChainHash(H,    "/=",               lexModulo_Eq);
//
//        Add2ChainHash(H,    "^",                lexCaret);
//        Add2ChainHash(H,    "^=",               lexCaret_Eq);
//
//        Add2ChainHash(H,    "&",                lexAmpersand);
//        Add2ChainHash(H,    "&&",               lexLogical_And);
//        Add2ChainHash(H,    "&=",               lexAmpersand_Eq);
//
//        Add2ChainHash(H,    "|",                lexPipe);
//        Add2ChainHash(H,    "||",               lexLogical_Or);
//        Add2ChainHash(H,    "|=",               lexPipe_Eq);
//
//        Add2ChainHash(H,    "?",                lexQuestion_Mark);
//        Add2ChainHash(H,    "!",                lexExclaim);
//        Add2ChainHash(H,    "!=",               lexNot_Eq);
//
//        Add2ChainHash(H,    "=",                lexAssign);
//        Add2ChainHash(H,    "==",               lexEqual);
//
//        Add2ChainHash(H,    ":",                lexColon);
//        Add2ChainHash(H,    "::",               lexDouble_Colon);
//        Add2ChainHash(H,    ";",                lexSemicolon);
//
//        Add2ChainHash(H,    ".",                lexDot);
//        Add2ChainHash(H,    ".*",               lexDot_Star);
//        Add2ChainHash(H,    "...",              lexEllipsis);
//        Add2ChainHash(H,    ",",                lexComma);
//
//        Add2ChainHash(H,    "<",                lexLess);
//        Add2ChainHash(H,    "<<",               lexShift_Left);
//        Add2ChainHash(H,    "<=",               lexLess_Eq);
//        Add2ChainHash(H,    "<<=",              lexShift_Left_Eq);
//
//        Add2ChainHash(H,    ">",                lexGreater);
//        Add2ChainHash(H,    ">>",               lexShift_Right);
//        Add2ChainHash(H,    ">=",               lexGreater_Eq);
//        Add2ChainHash(H,    ">>=",              lexShift_Right_Eq);
//
//        Add2ChainHash(H,    "->",               lexArrow);
//        Add2ChainHash(H,    "->*",              lexArrow_Star);
//
//        Add2ChainHash(H,    "#",                lexHash);
//        Add2ChainHash(H,    "##",               lexHash_Hash);
//
//        Add2ChainHash(H,    "~",                lexTilde);
//
//        Add2ChainHash(H,    "(",                lexOpen_Paren);
//        Add2ChainHash(H,    ")",                lexClose_Paren);
//
//        Add2ChainHash(H,    "{",                lexOpen_Brace);
//        Add2ChainHash(H,    "}",                lexClose_Brace);
//
//        Add2ChainHash(H,    "[",                lexOpen_Bracket);
//        Add2ChainHash(H,    "]",                lexClose_Bracket);


    }
}