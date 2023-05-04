package lexical.analyze;

// Текущая позиция в исходном тексте
class Location {
    static int Line;    // Номен строки
    static int Pos;     // Номер символа в строке
    static int LexPos;  // Позиция начала лексемы
    static String Path; // Путь к файлу
}