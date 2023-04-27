package lexical.analyze;


/*
    Хлюпин Дмитрий, ПМ-21
    Использованная структура: Перемешанная таблица с цепочками
    Анализируемый язык: Си++
*/

//
class Scanner {

    static int NAMELEN = 129;   //
    static int N = 113;         //

    final static int
        lexNone = 0,
        lexName = 1,
        lexNum = 2,
        lexAlignas = 3,         /* alignas */       lexAlignof = 46,        /* alignof */   lexAnd = 88,                /* and */
        lexAnd_Eq = 4,          /* and_eq */        lexAsm = 47,            /* asm */       lexAuto = 89,               /* auto */
        lexBitand = 5,          /* bitand */        lexBitor = 48,          /* bitor */     lexBool = 90,               /* bool */
        lexBreak = 6,           /* break */         lexCase = 49,           /* case */      lexCatch = 91,              /* catch */
        lexChar = 7,            /* char */          lexChar16_t = 50,       /* char16_t */  lexChar32_t = 92,           /* char32_t */
        lexClass = 8,           /* class */         lexComlp = 51,          /* comlp */     lexConst = 93,              /* const */
        lexConst_cast = 9,      /* const_cast */    lexConstexpr = 52,      /* constexpr */ lexContinue = 94,           /* continue */
        lexDecltype = 10,       /* decltype */      lexDefault = 53,        /* default */   lexDelete = 95,             /* delete */
        lexDo = 11,             /* do */            lexDouble = 54,         /* double */    lexDynamic_cast = 96,       /* dynamic_cast */
        lexElse = 12,           /* else */          lexEnum = 55,           /* enum */      lexExplicit = 97,           /* explicit */
        lexExtern = 13,         /* extern */        lexFalse = 56,          /* false */     lexFinal = 98,              /* final */
        lexFloat = 14,          /* float */         lexFriend = 57,         /* friend */    lexFor = 99,                /* for */
        lexGoto = 15,           /* goto */          lexIf = 58,             /* if */        lexInline = 100,            /* inline */
        lexInt = 16,            /* int */           lexLong = 59,           /* long */      lexMutable = 101,           /* mutable */
        lexNamespace = 17,      /* namespace */     lexNew = 60,            /* new */       lexNoexcept = 102,          /* noexcept */
        lexNot = 18,            /* not */           lexNot_eq = 61,         /* not_eq */    lexNullptr = 103,           /* nullptr */
        lexOperator = 19,       /* operator */      lexOr = 62,             /* or */        lexOr_eq = 104,             /* or_eq */
        lexOverride = 20,       /* override */      lexPrivate = 63,        /* private */   lexProtected = 105,         /* protected */
        lexPublic = 21,         /* public */        lexRegister = 64,       /* register */  lexReinterpret_cast = 106,  /* reinterpret_cast */
        lexReturn = 22,         /* return */        lexShort = 65,          /* short */     lexSigned = 107,            /* signed */
        lexSizeof = 23,         /* sizeof */        lexStatic = 66,         /* static */    lexStatic_assert = 108,     /* static_assert */
        lexStatic_cast = 24,    /* static_cast */   lexStruct = 67,         /* struct */    lexSwitch = 109,            /* switch */
        lexTemplate = 25,       /* template */      lexThis = 68,           /* this */      lexThread_local = 110,      /* thread_local */
        lexThrow = 26,          /* throw */         lexTrue = 69,           /* true */      lexTry = 111,               /* try */
        lexTypedef = 27,        /* typedef */       lexTypeid = 70,         /* typeid */    lexTypename = 112,          /* typename */
        lexUnion = 28,          /* union */         lexUnsigned = 71,       /* unsigned */  lexUsing = 113,             /* using */
        lexVirtual = 29,        /* virtual */       lexVoid = 72,           /* void */      lexVolatile = 114,          /* volatile */
        lexWchar_t = 30,        /* wchar_t */       lexWhile = 73,          /* while */     lexPlus = 115,              /* + */
        lexPlus_Eq = 31,        /* += */            lexMinus = 74,          /* - */         lexMinus_Eq = 116,          /* -= */
        lexStar = 32,           /* * */             lexStar_Eq = 75,        /* *= */        lexSlash = 117,             /* / */
        lexSlash_Eq = 33,       /* /= */            lexModulo = 76,         /* % */         lexModulo_Eq = 118,         /* %= */
        lexCaret = 34,          /* ^ */             lexCaret_Eq = 77,       /* ^= */        lexAmpersand = 119,         /* & */
        lexAmpersand_Eq = 35,   /* &= */            lexPipe = 78,           /* | */         lexPipe_Eq = 120,           /* |= */
        lexTilde = 36,          /* ~ */             lexNot_Eq = 79,         /* != */        lexLogical_And = 121,       /* && */
        lexAssign = 37,         /* = */             lexLogical_Or = 80,     /* || */        lexDouble_Colon = 122,      /* :: */
        lexEqual = 38,          /* == */            lexShift_Left = 81,     /* << */        lexShift_Right = 123,       /* >> */
        lexQuestion_Mark = 39,  /* ? */             lexColon = 82,          /* : */         lexLess = 124,              /* < */
        lexLess_Eq = 40,        /* <= */            lexGreater = 83,        /* > */         lexGreater_Eq = 125,        /* >= */
        lexClose_Paren = 41,    /* ) */             lexDot = 84,            /* . */         lexArrow = 126,             /* -> */
        lexEllipsis = 42,       /* ... */           lexSemicolon = 85,      /* ; */         lexOpen_Paren = 127,        /* ( */
        lexComma = 43,          /* , */             lexOpen_Bracket = 86,   /* [ */         lexDot_Star = 128,          /* .* */
        lexOpen_Brace = 44,     /* { */             lexClose_Brace = 87,    /* } */         lexClose_Bracket = 129,     /* ] */
        lexArrow_Star = 45,     /* ->* */           lexShift_Left_Eq = 88,  /* <<= */       lexShift_Right_Eq = 130,    /* >>= */
        lexPlus_Plus = 46,      /* ++ */            lexMinus_Minus = 89,    /* -- */        lexExclaim = 131,           /* ! */
        lexHash = 47,           /* # */             lexHash_Hash = 90,      /* ## */
        lexEOT = 150;           /* /0 */


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


    private static int TestKW() {
        Item p = Search(H, Name);
        if (p != null) {
            return p.data;
        } else {
            return lexName;
        }
    }

    static void InitChainHash() {
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

    static void Add2ChainHash(tChainHash[] T, String K, int D) {
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
                    Lex = lexDot;
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
                    } else {
                        Lex = lexLess;
                    }
                    break;
                case '>':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexGreater_Eq;
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
                    } else {
                        Lex = lexPlus;
                    }
                    break;
                case '-':
                    Text.NextCh();
                    if (Text.Ch == '=') {
                        Text.NextCh();
                        Lex = lexMinus_Eq;
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

        Add2ChainHash(H, "alignas", lexAlignas);
        Add2ChainHash(H, "alignof", lexAlignof);
        Add2ChainHash(H, "and", lexAnd);
        Add2ChainHash(H, "and_eq", lexAnd_Eq);
        Add2ChainHash(H, "asm", lexAsm);
        Add2ChainHash(H, "auto", lexAuto);

        Add2ChainHash(H, "bitand", lexBitand);
        Add2ChainHash(H, "bitor", lexBitor);
        Add2ChainHash(H, "bool", lexBool);
        Add2ChainHash(H, "break", lexBreak);

        Add2ChainHash(H, "case", lexCase);
        Add2ChainHash(H, "catch", lexCatch);
        Add2ChainHash(H, "char", lexChar);
        Add2ChainHash(H, "char16_t", lexChar16_t);
        Add2ChainHash(H, "char32_t", lexChar32_t);
        Add2ChainHash(H, "class", lexClass);
        Add2ChainHash(H, "comlp", lexComlp);
        Add2ChainHash(H, "const", lexConst);
        Add2ChainHash(H, "const_cast", lexConst_cast);
        Add2ChainHash(H, "constexpr", lexConstexpr);
        Add2ChainHash(H, "continue", lexContinue);

        Add2ChainHash(H, "decltype", lexDecltype);
        Add2ChainHash(H, "default", lexDefault);
        Add2ChainHash(H, "delete", lexDelete);
        Add2ChainHash(H, "do", lexDo);
        Add2ChainHash(H, "double", lexDouble);
        Add2ChainHash(H, "dynamic_cast", lexDynamic_cast);

        Add2ChainHash(H, "else", lexElse);
        Add2ChainHash(H, "enum", lexEnum);
        Add2ChainHash(H, "explicit", lexExplicit);
        Add2ChainHash(H, "extern", lexExtern);

        Add2ChainHash(H, "false", lexFalse);
        Add2ChainHash(H, "final", lexFinal);
        Add2ChainHash(H, "float", lexFloat);
        Add2ChainHash(H, "friend", lexFriend);
        Add2ChainHash(H, "for", lexFor);

        Add2ChainHash(H, "goto", lexGoto);

        Add2ChainHash(H, "if", lexIf);
        Add2ChainHash(H, "inline", lexInline);
        Add2ChainHash(H, "int", lexInt);

        Add2ChainHash(H, "long", lexLong);

        Add2ChainHash(H, "mutable", lexMutable);

        Add2ChainHash(H, "namespace", lexNamespace);
        Add2ChainHash(H, "new", lexNew);
        Add2ChainHash(H, "noexcept", lexNoexcept);
        Add2ChainHash(H, "not", lexNot);
        Add2ChainHash(H, "not_eq", lexNot_eq);
        Add2ChainHash(H, "nullptr", lexNullptr);

        Add2ChainHash(H, "operator", lexOperator);
        Add2ChainHash(H, "or", lexOr);
        Add2ChainHash(H, "or_eq", lexOr_eq);
        Add2ChainHash(H, "override", lexOverride);

        Add2ChainHash(H, "private", lexPrivate);
        Add2ChainHash(H, "protected", lexProtected);
        Add2ChainHash(H, "public", lexPublic);

        Add2ChainHash(H, "register", lexRegister);
        Add2ChainHash(H, "reinterpret_cast", lexReinterpret_cast);
        Add2ChainHash(H, "return", lexReturn);

        Add2ChainHash(H, "short", lexShort);
        Add2ChainHash(H, "signed", lexSigned);
        Add2ChainHash(H, "sizeof", lexSizeof);
        Add2ChainHash(H, "static", lexStatic);
        Add2ChainHash(H, "static_assert", lexStatic_assert);
        Add2ChainHash(H, "static_cast", lexStatic_cast);
        Add2ChainHash(H, "struct", lexStruct);
        Add2ChainHash(H, "switch", lexSwitch);

        Add2ChainHash(H, "template", lexTemplate);
        Add2ChainHash(H, "this", lexThis);
        Add2ChainHash(H, "thread_local", lexThread_local);
        Add2ChainHash(H, "throw", lexThrow);
        Add2ChainHash(H, "true", lexTrue);
        Add2ChainHash(H, "try", lexTry);
        Add2ChainHash(H, "typedef", lexTypedef);
        Add2ChainHash(H, "typeid", lexTypeid);
        Add2ChainHash(H, "typename", lexTypename);

        Add2ChainHash(H, "union", lexUnion);
        Add2ChainHash(H, "unsigned", lexUnsigned);
        Add2ChainHash(H, "using", lexUsing);

        Add2ChainHash(H, "virtual", lexVirtual);
        Add2ChainHash(H, "void", lexVoid);
        Add2ChainHash(H, "volatile", lexVolatile);

        Add2ChainHash(H, "wchar_t", lexWchar_t);
        Add2ChainHash(H, "while", lexWhile);

        NextLex();
    }

}