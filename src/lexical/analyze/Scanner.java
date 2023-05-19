package lexical.analyze;


/*
    Хлюпин Дмитрий, ПМ-21
    Использованная структура: Перемешанная таблица с цепочками
    Анализируемый язык: Си++
*/

// Лексический анализатор
class Scanner {

    static int NAMELEN = 100;    // Наибольшая длина имени
    static int N = 127;         // Объем таблицы, где 70% заполняется
    static int NMAX = 182;      // Количество всех лексем

    final static int
        lexNone = 0,
        lexName = 1,
        lexCharacter = 2,
        lexString = 3,

        lexNumInt = 4,

        lexNum = 5,             /* число-в10 */     lexNumOct = 17,         /* ч-в8 */      lexNumHex = 23,             /* ч-в16 */
        lexNumU = 6,            /* ч-в10-U */       lexNumOctU = 18,        /* ч-в8-U */    lexNumHexU = 24,            /* ч-в16-U */
        lexNumUL = 7,           /* ч-в10-UL */      lexNumOctUL = 19,       /* ч-в8-UL */   lexNumHexUL = 25,           /* ч-в16-UL */
        lexNumULL = 8,          /* ч-в10-ULL */     lexNumOctULL = 20,      /* ч-в8-ULL */  lexNumHexULL = 26,          /* ч-в16-ULL */
        lexNumL = 9,            /* ч-в10-L */       lexNumOctL = 21,        /* ч-в8-L */    lexNumHexL = 27,            /* ч-в16-L */
        lexNumLL = 10,          /* ч-в10-LL */      lexNumOctLL = 22,       /* ч-в8-LL */   lexNumHexLL = 28,           /* ч-в16-LL */
        lexNumBin = 11,         /* ч-вbin */        lexNumBinL = 15,        /* ч-вbin-L */  lexNumBinLL = 16,           /* ч-вbin-LL */
        lexNumBinU = 12,        /* ч-вbin-U */      lexNumBinUL = 13,       /* ч-вbin-UL */ lexNumBinULL = 14,          /* ч-вbin-ULL */

        lexNumReal = 29,

        lexNumFloat = 30,       /* ч-float */       lexNumDouble = 31,      /* ч-double */  lexNumDoubleL = 32,         /* ч-double-L */

        lexAlignas = 33,        /* alignas */       lexAlignof = 34,        /* alignof */   lexAnd = 35,                /* and */
        lexAnd_Eq = 36,         /* and_eq */        lexAsm = 37,            /* asm */       lexAuto = 38,               /* auto */
        lexBitand = 39,         /* bitand */        lexBitor = 40,          /* bitor */     lexBool = 41,               /* bool */
        lexBreak = 42,          /* break */         lexCase = 43,           /* case */      lexCatch = 44,              /* catch */
        lexChar = 45,           /* char */          lexChar16_t = 44,       /* char16_t */  lexChar32_t = 45,           /* char32_t */
        lexClass = 48,          /* class */         lexComlp = 47,          /* comlp */     lexConst = 48,              /* const */
        lexConst_cast = 51,     /* const_cast */    lexConstexpr = 52,      /* constexpr */ lexContinue = 53,           /* continue */
        lexDecltype = 54,       /* decltype */      lexDefault = 55,        /* default */   lexDelete = 56,             /* delete */
        lexDo = 57,             /* do */            lexDouble = 58,         /* double */    lexDynamic_cast = 59,       /* dynamic_cast */
        lexElse = 60,           /* else */          lexEnum = 61,           /* enum */      lexExplicit = 62,           /* explicit */
        lexExtern = 63,         /* extern */        lexFalse = 64,          /* false */     lexFinal = 65,              /* final */
        lexFloat = 66,          /* float */         lexFriend = 67,         /* friend */    lexFor = 68,                /* for */
        lexGoto = 69,           /* goto */          lexIf = 70,             /* if */        lexInline = 71,             /* inline */
        lexInt = 72,            /* int */           lexLong = 73,           /* long */      lexMutable = 74,            /* mutable */
        lexNamespace = 75,      /* namespace */     lexNew = 76,            /* new */       lexNoexcept = 77,           /* noexcept */
        lexNot = 78,            /* not */           lexNot_eq = 79,         /* not_eq */    lexNullptr = 80,            /* nullptr */
        lexOperator = 81,       /* operator */      lexOr = 82,             /* or */        lexOr_eq = 83,              /* or_eq */
        lexOverride = 84,       /* override */      lexPrivate = 85,        /* private */   lexProtected = 86,          /* protected */
        lexPublic = 87,         /* public */        lexRegister = 88,       /* register */  lexReinterpret_cast = 89,   /* reinterpret_cast */
        lexReturn = 90,         /* return */        lexShort = 91,          /* short */     lexSigned = 92,             /* signed */
        lexSizeof = 93,         /* sizeof */        lexStatic = 94,         /* static */    lexStatic_assert = 95,      /* static_assert */
        lexStatic_cast = 96,    /* static_cast */   lexStruct = 97,         /* struct */    lexSwitch = 98,             /* switch */
        lexTemplate = 99,       /* template */      lexThis = 100,           /* this */      lexThread_local = 101,       /* thread_local */
        lexThrow = 102,         /* throw */         lexTrue = 103,          /* true */      lexTry = 104,               /* try */
        lexTypedef = 105,       /* typedef */       lexTypeid = 106,        /* typeid */    lexTypename = 107,          /* typename */
        lexUnion = 108,         /* union */         lexUnsigned = 109,      /* unsigned */  lexUsing = 110,             /* using */
        lexVirtual = 111,       /* virtual */       lexVoid = 112,          /* void */      lexVolatile = 113,          /* volatile */
        lexWchar_t = 114,       /* wchar_t */       lexWhile = 115,         /* while */

        lexPlus = 116,          /* + */             lexPlus_Plus = 117,     /* ++ */        lexPlus_Eq = 118,           /* += */
        lexMinus = 119,         /* - */             lexMinus_Minus = 120,   /* -- */        lexMinus_Eq = 121,          /* -= */
        lexStar = 122,          /* * */             lexStar_Eq = 123,       /* *= */
        lexSlash = 124,         /* / */             lexSlash_Eq = 125,      /* /= */
        lexModulo = 126,        /* % */             lexModulo_Eq = 127,     /* %= */
        lexCaret = 128,         /* ^ */             lexCaret_Eq = 129,      /* ^= */
        lexAmpersand = 130,     /* & */             lexLogical_And = 131,   /* && */        lexAmpersand_Eq = 132,      /* &= */
        lexPipe = 133,          /* | */             lexLogical_Or = 134,    /* || */        lexPipe_Eq = 135,           /* |= */
        lexQuestion_Mark = 136, /* ? */             lexExclaim = 137,       /* ! */         lexNot_Eq = 138,            /* != */
        lexAssign = 139,        /* = */             lexEqual = 140,         /* == */
        lexColon = 141,         /* : */             lexDouble_Colon = 142,  /* :: */        lexSemicolon = 143,         /* ; */
        lexDot = 144,           /* . */             lexDot_Star = 145,      /* .* */        lexEllipsis = 146,          /* ... */
        lexComma = 147,         /* , */
        lexLess = 148,          /* < */             lexLess_Eq = 149,       /* <= */
        lexShift_Left = 150,    /* << */            lexShift_Left_Eq = 151, /* <<= */
        lexGreater = 152,       /* > */             lexGreater_Eq = 153,    /* >= */
        lexShift_Right = 154,   /* >> */            lexShift_Right_Eq = 155,/* >>= */
        lexArrow = 156,         /* -> */            lexArrow_Star = 157,    /* ->* */
        lexHash = 158,          /* # */             lexHash_Hash = 159,     /* ## */
        lexTilde = 160,         /* ~ */
        lexOpen_Paren = 161,    /* ( */             lexClose_Paren = 162,   /* ) */
        lexOpen_Brace = 163,    /* { */             lexClose_Brace = 164,   /* } */
        lexOpen_Bracket = 165,  /* [ */             lexClose_Bracket = 166, /* ] */

        lexBackslash_Alert_Or_Bell = 167,       /* \a */
        lexBackslash_Backspace = 168,           /* \b */
        lexBackslash_Escape = 169,              /* \e */
        lexBackslash_Form_Feed = 170,           /* \f */
        lexBackslash_Newline = 171,             /* \n */
        lexBackslash_Carriage_Return = 172,     /* \r */
        lexBackslash_Tab = 173,                 /* \t */
        lexBackslash_Vertical_Tab = 174,        /* \v */
        lexBackslash_Backslash = 175,           /* \\ */
        lexBackslash_Question_Mark = 176,       /* \? */
        lexBackslash_Single_Quote =177,         /* \' */
        lexBackslash_Double_Quote = 178,        /* \" */
        lexBackslash_Octal_Value = 179,         /* \ooo */
        lexBackslash_Hexadecimal_Value = 180,   /* \hhh */
        lexBackslash_Null_Character = 181,      /* \0 */

        lexEOT = 182;               /* \0 */


    // Текущая лексема
    static int Lex;
    // Строковое значение имени
    private static final StringBuffer Buf = new StringBuffer(NAMELEN);
    static String Name;

    // Значение числовых литералов
    static int Num;

    // Класс для хэш-таблицы
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

    // Клас хэш-таблица
    static public class tChainHash {
        Item items;
    }

    // Создание хэш-таблицы
    public static tChainHash[] H = new tChainHash[N];



    // Проверка лексемы на ключевое слово
    private static int TestKW() {
        Item p = Search(H, Name);

        if (p != null) {
            return p.data;
        } else {
            return lexName;
        }
    }


    // Иниацилизация хэщ-таблицы
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


    // Добавление ключевого слова в хэш-таблицу
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


    // Нахождение идентификатора(имени/ключевого слова)
    private static void Ident() {
        int i = 0;
        Buf.setLength(0);

        do {
            if (Text.Ch != '_') {
                if (i++ < NAMELEN) {
                    Buf.append((char) Text.Ch);
                } else {
                    Error.Message("Слишком длинное имя");
                }

                Text.NextCh();
                BackSlash();
            }

            if (Text.Ch == '_') {
                if (i++ < NAMELEN) {
                    Buf.append((char) Text.Ch);
                } else {
                    Error.Message("Слишком длинное имя");
                }
                Text.NextCh();
                BackSlash();
                if (Text.Ch == '_') {
                    if (i++ < NAMELEN) {
                        Buf.append((char) Text.Ch);
                    } else {
                        Error.Message("Слишком длинное имя");
                    }
                    Error.Warning("Двойное подчеркивание в идентификаторах зарезервировано в С++");
                    Text.NextCh();
                    BackSlash();
                }
            }

            BackSlash();
        } while (Character.isLetterOrDigit((char)Text.Ch));


        Name = Buf.toString();  // Полученное слово из строки
        Lex = TestKW();         // Проверка на ключевое слово
    }


    // Нахождение числа (бинарное, восьмеричное, десятичное, шестнадцатеричное и все суффиксы)
    private static void Number() {
        Lex = lexNum;
        Num = 0;

        if (Text.Ch == '0') {                                   // Проверка наличия у первого числа "0":
            Text.NextCh();
            BackSlash();
            if (Text.Ch == 'b' || Text.Ch == 'B') {                 // (1) Если "0b", значит число "должно быть" в bin
                Text.NextCh();
                BackSlash();
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
                        if (SearchSuffixU()) {                      // Если есть в конце U, то то unsigned long +
                            Lex = lexNumBinUL;
                        } else if (SearchSuffixL()) {               // Если есть в конце L, то long long +
                            if (SearchSuffixU()) {                  // Если есть в конце U, то то unsigned long long.
                                Lex = lexNumBinULL;
                            } else {
                                Lex = lexNumBinLL;
                            }
                        } else {
                            Lex = lexNumBinL;
                        }
                    } else {                                        // Если нет  в конце U и L, то просто в bin.
                        Lex = lexNumBin;
                    }
                } else {
                    Error.Expected("символ бинарного числа");
                }
            } else if (Text.Ch == 'x' || Text.Ch == 'X') {          // (2) Если "0х", значит число "должно быть" в 16
                Text.NextCh();
                BackSlash();
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
                        if (SearchSuffixU()) {                      // Если есть в конце U, то то unsigned long +
                            Lex = lexNumHexUL;
                        } else if (SearchSuffixL()) {               // Если есть в конце L, то long long +
                            if (SearchSuffixU()) {                  // Если есть в конце U, то то unsigned long long.
                                Lex = lexNumHexULL;
                            } else {
                                Lex = lexNumHexLL;
                            }
                        } else {
                            Lex = lexNumHexL;
                        }
                    } else {                                        // Если нет  в конце U и L, то просто в 16.
                        Lex = lexNumHex;
                    }
                } else {
                    Error.Expected("символ шестнадцатеричного числа");
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
                    if (SearchSuffixU()) {                          // Если есть в конце U, то то unsigned long +
                        Lex = lexNumOctUL;
                    } else if (SearchSuffixL()) {                   // Если есть в конце L, то long long +
                        if (SearchSuffixU()) {                      // Если есть в конце U, то то unsigned long long.
                            Lex = lexNumOctULL;
                        } else {
                            Lex = lexNumOctLL;
                        }
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
                if (SearchSuffixU()) {                              // Если есть в конце U, то то unsigned long +
                    Lex = lexNumUL;
                } else if (SearchSuffixL()) {                       // Если есть в конце L, то long long +
                    if (SearchSuffixU()) {                          // Если есть в конце U, то то unsigned long long.
                        Lex = lexNumULL;
                    } else {
                        Lex = lexNumLL;
                    }
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


    // Бинарное число
    private static void BinNumber() {
        do {
            Text.NextCh();
            BackSlash();
        } while (Text.Ch == '0' || Text.Ch == '1');
    }

    // Восьмеричное число
    private static void OctNumber() {
        do {
            Text.NextCh();
            BackSlash();
        } while (Text.Ch >= '0' && Text.Ch <= '7');
    }

    // Шестнадцатеричное число
    private static void HexNumber() {
        do {
            Text.NextCh();
            BackSlash();
        } while ((Text.Ch >= '0' && Text.Ch <= '9') || (Text.Ch >= 'A' && Text.Ch <= 'F') || (Text.Ch >= 'a' && Text.Ch <= 'f'));
    }

    // Десятичное число
    private static void DecNumber() {
        do {
            Text.NextCh();
            BackSlash();
        } while (Character.isDigit((char)Text.Ch));
    }


    // Поиск суффикса Е
    private static boolean SearchSuffixE() {
        if (Text.Ch == 'E' || Text.Ch == 'e') {
            Text.NextCh();
            BackSlash();

            if (Text.Ch == '-') {
                Text.NextCh();
            } else if (Text.Ch == '+') {
                Text.NextCh();
            }

            BackSlash();

            if (Character.isDigit((char)Text.Ch)) {
                DecNumber();
            } else {
                Error.Expected("символ после суффикса");
            }
            return true;
        } else {
            return false;
        }
    }

    // Поиск суффикса F
    private static boolean SearchSuffixF() {
        if (Text.Ch == 'F' || Text.Ch == 'f') {
            Text.NextCh();
            BackSlash();
            return true;
        } else {
            return false;
        }
    }

    // Поиск суффикса U
    private static boolean SearchSuffixU() {
        if (Text.Ch == 'U' || Text.Ch == 'u') {
            Text.NextCh();
            BackSlash();
            return true;
        } else {
            return false;
        }
    }

    // Поиск суффикса L
    private static boolean SearchSuffixL() {
        if (Text.Ch == 'L' || Text.Ch == 'l') {
            Text.NextCh();
            BackSlash();
            return true;
        } else {
            return false;
        }
    }


    // Комментарий со звездочкой
    private static void CommentStar() {
        Text.NextCh();
        BackSlash();

        do {
            while (Text.Ch != '*' && Text.Ch != Text.chEOT) {
                Text.NextCh();
            }
            if (Text.Ch == '*') {
                Text.NextCh();
                BackSlash();
            }
        } while (Text.Ch != '/' && Text.Ch != Text.chEOT);

        if (Text.Ch == Text.chEOT) {
            Location.LexPos = Location.Pos;
            Error.Message("Не закончен комментарий");
        } else {
            Text.NextCh();
        }
    }

    // Комментарий
    private static void Comment() {
        while (Text.Ch != Text.chEOL && Text.Ch != Text.chEOT) {
            Text.NextCh();
            BackSlash();
        }
    }

    // Нахождение символа (не более 1 байта)
    private static void Character() {
        Text.NextCh();

        if (Text.Ch == '\\') {
            Text.NextCh();
            if (Text.Ch == 'n') {
                Text.NextCh();
            } else if (Text.Ch == 't') {
                Text.NextCh();
            } else if (Text.Ch == 'v') {
                Text.NextCh();
            } else if (Text.Ch == 'b') {
                Text.NextCh();
            } else if (Text.Ch == 'r') {
                Text.NextCh();
            } else if (Text.Ch == 'f') {
                Text.NextCh();
            } else if (Text.Ch == 'a') {
                Text.NextCh();
            } else if (Text.Ch == '\\') {
                Text.NextCh();
            } else if (Text.Ch == '?') {
                Text.NextCh();
            } else if (Text.Ch == '\'') {
                Text.NextCh();
            } else if (Text.Ch == '\"') {
                Text.NextCh();
            } else if (Text.Ch >= '0' && Text.Ch <= '3') {
                Text.NextCh();
                BackSlash();
                if (Text.Ch >= '0' && Text.Ch <= '7') {
                    Text.NextCh();
                    BackSlash();
                    if (Text.Ch >= '0' && Text.Ch <= '7') {
                        Text.NextCh();
                        BackSlash();
                    }
                }
            } else if (Text.Ch >= '4' && Text.Ch <= '7') {
                Text.NextCh();
                BackSlash();
                if (Text.Ch >= '0' && Text.Ch <= '7') {
                    Text.NextCh();
                    BackSlash();
                }
            } else if (Text.Ch == 'x') {
                Text.NextCh();
                BackSlash();
                if (!((Text.Ch >= '0' && Text.Ch <= '9') || (Text.Ch >= 'A' && Text.Ch <= 'F') || (Text.Ch >= 'a' && Text.Ch <= 'f'))) {
                    Error.Expected("символ шестнадцатеричного числа");
                }

                while (Text.Ch == '0') {
                    Text.NextCh();
                    BackSlash();
                }
                if ((Text.Ch >= '1' && Text.Ch <= '9') || (Text.Ch >= 'A' && Text.Ch <= 'F') || (Text.Ch >= 'a' && Text.Ch <= 'f')) {
                    Text.NextCh();
                    BackSlash();
                    if ((Text.Ch >= '0' && Text.Ch <= '9') || (Text.Ch >= 'A' && Text.Ch <= 'F') || (Text.Ch >= 'a' && Text.Ch <= 'f')) {
                        Text.NextCh();
                        BackSlash();
                    }
                }
            } else if (Text.Ch == '\n') {
                Text.NextCh();
                BackSlash();
                Text.NextCh();
            }
        } else if (Text.Ch == '\'') {
            Error.Expected("символ");
        } else {
            Text.NextCh();
        }

        BackSlash();

        if (Text.Ch != '\'') {
            Error.Expected("символ '");
        } else {
            Text.NextCh();
        }
    }


    // Нахождение строки
    private static void String() {
        do {
            Text.NextCh();
            if (Text.Ch == '\\') {
                Text.NextCh();
                if (Text.Ch == '\"') {
                    Text.NextCh();
                } else if (Text.Ch == Text.chEOL) {
                    Text.NextCh();
                    BackSlash();
                }
            }
        } while (Text.Ch != '\"' /*&& Text.Ch != '\\'*/ && Text.Ch != Text.chEOL && Text.Ch != Text.chEOT);

        if (Text.Ch == Text.chEOL || Text.Ch == Text.chEOT) {
            Error.Expected("символ строки");
//        } else if (Text.Ch == '\\') {
//            Text.NextCh();
//            if (Text.Ch == Text.chEOT) {
//                Error.Message("Не закончена строка");
//            } else {
//                String();
//            }
        } else {
            Text.NextCh();
        }
    }


    private static void BackSlash() {
        while (Text.Ch == '\\') {
            Text.NextCh();
            if (Text.Ch == Text.chEOL) {
                Text.NextCh();
            } else {
                Error.Expected("продолжение в следующей строке");
            }
        }
    }


    // Следующая лексема
    static void NextLex() {
        while (Text.Ch == Text.chSPACE || Text.Ch == Text.chTAB || Text.Ch == Text.chEOL) {
            Text.NextCh();
        }

        Location.LexPos = Location.Pos;

        if (Character.isLetter((char)Text.Ch)) {
            Ident();
        } else if (Text.Ch == '_') {
            Error.Warning("Не рекомендуется использовать подчеркивание в начале идентификатора в С++");
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
                    BackSlash();
                    if (Text.Ch == ':') {
                        Text.NextCh();
                        Lex = lexDouble_Colon;
                    } else {
                        Lex = lexColon;
                    }
                    break;
                case '.':
                    Text.NextCh();
                    BackSlash();
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
                    BackSlash();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexEqual;
                    } else {
                        Lex = lexAssign;
                    }
                    break;
                case '<':
                    Text.NextCh();
                    BackSlash();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexLess_Eq;
                    } else if (Text.Ch == '<') {
                        Text.NextCh();
                        BackSlash();
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
                    BackSlash();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexGreater_Eq;
                    } else if (Text.Ch == '>') {
                        Text.NextCh();
                        BackSlash();
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
                    BackSlash();
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
                    BackSlash();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexMinus_Eq;
                    } else if (Text.Ch == '>') {
                        Text.NextCh();
                        BackSlash();
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
                    BackSlash();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexStar_Eq;
                    } else {
                        Lex = lexStar;
                    }
                    break;
                case '/':
                    Text.NextCh();
                    BackSlash();
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
                    BackSlash();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexModulo_Eq;
                    } else {
                        Lex = lexModulo;
                    }
                    break;
                case '&':
                    Text.NextCh();
                    BackSlash();
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
                    BackSlash();
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
                    BackSlash();
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
                    BackSlash();
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
                    BackSlash();
                    if (Text.Ch == '#') {
                        Text.NextCh();
                        Lex = lexHash_Hash;
                    } else {
                        Lex = lexHash;
                    }
                    break;
                case '\'':
                    Character();
                    Lex = lexCharacter;
                    break;
                case '\"':
                    if (Lex == lexString) {
                        String();
                        NextLex();
                    } else {
                        String();
                        Lex = lexString;
                    }
                    break;
                case '\\':
                    BackSlash();
                    NextLex();
                    break;
//                case '\\':
//                    Text.NextCh();
//                    if (Text.Ch == 'n') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Newline;
//                    } else if (Text.Ch == 't') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Tab;
//                    } else if (Text.Ch == 'e') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Escape;
//                    } else if (Text.Ch == 'v') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Vertical_Tab;
//                    } else if (Text.Ch == 'b') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Backspace;
//                    } else if (Text.Ch == 'r') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Carriage_Return;
//                    } else if (Text.Ch == 'f') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Form_Feed;
//                    } else if (Text.Ch == 'a') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Alert_Or_Bell;
//                    } else if (Text.Ch == '\\') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Backslash;
//                    } else if (Text.Ch == '?') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Question_Mark;
//                    } else if (Text.Ch == '\'') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Single_Quote;
//                    } else if (Text.Ch == '\"') {
//                        Text.NextCh();
//                        Lex = lexBackslash_Double_Quote;
//                    } else if (Text.Ch == '0') {
//                        Text.NextCh();
//                        if (Text.Ch >= '0' && Text.Ch <= '7') {
//                            Text.NextCh();
//                            if (Text.Ch >= '0' && Text.Ch <= '7') {
//                                Text.NextCh();
//                            }
//                            Lex = lexBackslash_Octal_Value;
//                        } else {
//                            Lex = lexBackslash_Null_Character;
//                        }
//                    } else if (Text.Ch >= '1' && Text.Ch <= '3') {
//                        Text.NextCh();
//                        if (Text.Ch >= '0' && Text.Ch <= '7') {
//                            Text.NextCh();
//                            if (Text.Ch >= '0' && Text.Ch <= '7') {
//                                Text.NextCh();
//                            }
//                        }
//                        Lex = lexBackslash_Octal_Value;
//                    } else if (Text.Ch >= '4' && Text.Ch <= '7') {
//                        Text.NextCh();
//                        if (Text.Ch >= '0' && Text.Ch <= '7') {
//                            Text.NextCh();
//                        }
//                        Lex = lexBackslash_Octal_Value;
//                    } else if (Text.Ch == 'x') {
//                        do {
//                            Text.NextCh();
//                        } while (Text.Ch == '0');
//                        if ((Text.Ch >= '1' && Text.Ch <= '9') || (Text.Ch >= 'A' && Text.Ch <= 'F') || (Text.Ch >= 'a' && Text.Ch <= 'f')) {
//                            Text.NextCh();
//                            if ((Text.Ch >= '0' && Text.Ch <= '9') || (Text.Ch >= 'A' && Text.Ch <= 'F') || (Text.Ch >= 'a' && Text.Ch <= 'f')) {
//                                Text.NextCh();
//                            }
//                        }
//                        Lex = lexBackslash_Hexadecimal_Value;
//                    } else {
//                        Error.Expected("буква/цифра/символ для \\");
//                    }
//                    break;
//                case '_':
//                    Text.NextCh();
//                    if (Character.isLetterOrDigit((char)Text.Ch)) {
//                        Error.Warning("Не следует использовать идентификаторы, начинающиеся с одного символа подчеркивания в С++");
//                        Ident();
//                        Name = "_" + Name;
//                        Lex = TestKW();
//                    } else {
//                        Error.Expected("цифра или буква");
//                    }
//                    break;
                case Text.chEOT:
                    Lex = lexEOT;
                    break;
                default:
                    Error.Message("Недопустимый символ");
            }
        }
    }


    // Инициализация сканнера
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