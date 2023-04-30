package lexical.analyze;


/*
    Хлюпин Дмитрий, ПМ-21
    Использованная структура: Перемешанная таблица с цепочками
    Анализируемый язык: Си++
*/

//
class Scanner {

    static int NAMELEN = 140;   // Наибольшая длина имени (пока хз)
    static int N = 175;         // Объем таблицы, где 70% заполняется (пока хз)

    final static int
        lexNone = 0,
        lexName = 1,
        lexNum = 2,             /* число-в10 */     lexNumOct = 56,          /* ч-в8 */      lexNumHex = 110,              /* ч-в16 */
        lexNumU = 3,            /* ч-в10-U */       lexNumOctU = 57,         /* ч-в8-U */   lexNumHexU = 111,             /* ч-в16-U */
        lexNumUL = 4,           /* ч-в10-UL */      lexNumOctUL = 58,        /* ч-в8-UL */  lexNumHexUL = 112,            /* ч-в16-UL */
        lexNumULL = 5,          /* ч-в10-ULL */     lexNumOctULL = 59,       /* ч-в8-ULL */ lexNumHexULL = 113,           /* ч-в16-ULL */
        lexNumL = 6,            /* ч-в10-L */       lexNumOctL = 60,         /* ч-в8-L */   lexNumHexL = 114,             /* ч-в16-L */
        lexNumLL = 7,           /* ч-в10-LL */      lexNumOctLL = 61,        /* ч-в8-LL */  lexNumHexLL = 115,            /* ч-в16-LL */
        lexNumBin = 8,          /* ч-вbin */        lexNumBinL = 62,         /* ч-вbin-L */ lexNumBinLL = 116,            /* ч-вbin-LL */
        lexNumBinU = 9,         /* ч-вbin-U */      lexNumBinUL = 63,        /* ч-вbin-UL */lexNumBinULL = 117,           /* ч-вbin-ULL */
        lexNumFloat = 10,       /* ч-float */       lexNumDouble = 64,      /* ч-double */ lexNumDoubleL = 118,         /* ч-double-L */
        lexAlignas = 11,         /* alignas */      lexAlignof = 65,        /* alignof */   lexAnd = 119,               /* and */
        lexAnd_Eq = 12,          /* and_eq */       lexAsm = 66,            /* asm */       lexAuto = 120,               /* auto */
        lexBitand = 13,          /* bitand */       lexBitor = 67,          /* bitor */     lexBool = 121,               /* bool */
        lexBreak = 14,           /* break */        lexCase = 68,           /* case */      lexCatch = 122,              /* catch */
        lexChar = 15,            /* char */         lexChar16_t = 69,       /* char16_t */  lexChar32_t = 123,           /* char32_t */
        lexClass = 16,           /* class */        lexComlp = 70,          /* comlp */     lexConst = 124,              /* const */
        lexConst_cast = 17,      /* const_cast */   lexConstexpr = 71,      /* constexpr */ lexContinue = 125,           /* continue */
        lexDecltype = 18,       /* decltype */      lexDefault = 72,        /* default */   lexDelete = 126,             /* delete */
        lexDo = 19,             /* do */            lexDouble = 73,         /* double */    lexDynamic_cast = 127,       /* dynamic_cast */
        lexElse = 20,           /* else */          lexEnum = 74,           /* enum */      lexExplicit = 128,           /* explicit */
        lexExtern = 21,         /* extern */        lexFalse = 75,          /* false */     lexFinal = 129,              /* final */
        lexFloat = 22,          /* float */         lexFriend = 76,         /* friend */    lexFor = 130,                /* for */
        lexGoto = 23,           /* goto */          lexIf = 77,             /* if */        lexInline = 131,            /* inline */
        lexInt = 24,            /* int */           lexLong = 78,           /* long */      lexMutable = 132,           /* mutable */
        lexNamespace = 25,      /* namespace */     lexNew = 79,            /* new */       lexNoexcept = 133,          /* noexcept */
        lexNot = 26,            /* not */           lexNot_eq = 80,         /* not_eq */    lexNullptr = 134,           /* nullptr */
        lexOperator = 27,       /* operator */      lexOr = 81,             /* or */        lexOr_eq = 135,             /* or_eq */
        lexOverride = 28,       /* override */      lexPrivate = 82,        /* private */   lexProtected = 136,         /* protected */
        lexPublic = 29,         /* public */        lexRegister = 83,       /* register */  lexReinterpret_cast = 137,  /* reinterpret_cast */
        lexReturn = 30,         /* return */        lexShort = 84,          /* short */     lexSigned = 138,            /* signed */
        lexSizeof = 31,         /* sizeof */        lexStatic = 85,         /* static */    lexStatic_assert = 139,     /* static_assert */
        lexStatic_cast = 32,    /* static_cast */   lexStruct = 86,         /* struct */    lexSwitch = 140,            /* switch */
        lexTemplate = 33,       /* template */      lexThis = 87,           /* this */      lexThread_local = 141,      /* thread_local */
        lexThrow = 34,          /* throw */         lexTrue = 88,           /* true */      lexTry = 142,               /* try */
        lexTypedef = 35,        /* typedef */       lexTypeid = 89,         /* typeid */    lexTypename = 143,          /* typename */
        lexUnion = 36,          /* union */         lexUnsigned = 90,       /* unsigned */  lexUsing = 144,             /* using */
        lexVirtual = 37,        /* virtual */       lexVoid = 91,           /* void */      lexVolatile = 145,          /* volatile */
        lexWchar_t = 38,        /* wchar_t */       lexWhile = 92,          /* while */     lexPlus = 146,              /* + */
        lexPlus_Eq = 39,        /* += */            lexMinus = 93,          /* - */         lexMinus_Eq = 147,          /* -= */
        lexStar = 40,           /* * */             lexStar_Eq = 94,        /* *= */        lexSlash = 148,             /* / */
        lexSlash_Eq = 41,       /* /= */            lexModulo = 95,         /* % */         lexModulo_Eq = 149,         /* %= */
        lexCaret = 42,          /* ^ */             lexCaret_Eq = 96,       /* ^= */        lexAmpersand = 150,         /* & */
        lexAmpersand_Eq = 43,   /* &= */            lexPipe = 97,           /* | */         lexPipe_Eq = 151,           /* |= */
        lexTilde = 44,          /* ~ */             lexNot_Eq = 98,         /* != */        lexLogical_And = 152,       /* && */
        lexAssign = 45,         /* = */             lexLogical_Or = 99,     /* || */        lexDouble_Colon = 153,      /* :: */
        lexEqual = 46,          /* == */            lexShift_Left = 100,     /* << */       lexShift_Right = 154,       /* >> */
        lexQuestion_Mark = 47,  /* ? */             lexColon = 101,          /* : */        lexLess = 155,              /* < */
        lexLess_Eq = 48,        /* <= */            lexGreater = 102,        /* > */        lexGreater_Eq = 156,        /* >= */
        lexClose_Paren = 49,    /* ) */             lexDot = 103,            /* . */        lexArrow = 157,             /* -> */
        lexEllipsis = 50,       /* ... */           lexSemicolon = 104,      /* ; */        lexOpen_Paren = 158,        /* ( */
        lexComma = 51,          /* , */             lexOpen_Bracket = 105,   /* [ */        lexDot_Star = 159,          /* .* */
        lexOpen_Brace = 52,     /* { */             lexClose_Brace = 106,    /* } */        lexClose_Bracket = 160,     /* ] */
        lexArrow_Star = 53,     /* ->* */           lexShift_Left_Eq = 107,  /* <<= */      lexShift_Right_Eq = 161,    /* >>= */
        lexPlus_Plus = 54,      /* ++ */            lexMinus_Minus = 108,    /* -- */       lexExclaim = 162,           /* ! */
        lexHash = 55,           /* # */             lexHash_Hash = 109,      /* ## */



        lexEOT = 170;           /* /0 */


    //
    static int Lex;
    //
    private static StringBuffer Buf = new StringBuffer(NAMELEN);
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


    private static void Add2ChainHash(tChainHash[] T, String K, int D) {
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
                    System.out.println("Error");
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
                    System.out.println("Error");
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
                DecNumber();
            } else if (Text.Ch == '+') {
                DecNumber();
            } else if (Character.isDigit((char)Text.Ch)) {
                DecNumber();
            } else {
                System.out.println("Error");
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
        }
    }


    private static void Comment() {
        while (Text.Ch != Text.chEOL && Text.Ch != Text.chEOT) {
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
    }
}