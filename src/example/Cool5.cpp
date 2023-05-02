#include <iostream>
#include <string>

using namespace std;

int main() {
    int num1 = 5;
    int num2 = 10;
    int result;

    result = num1 + num2;

    cout << "The result of " << num1 << " + " << num2 << " = " << result << endl;

    string name;
    cout << "Enter your name: ";
    getline(cin, name);

    cout << "Hello, " << name << "!" << endl;

    if (result > 15) {
        cout << "The result is greater than 15." << endl;
    } else {
        cout << "The result is not greater than 15." << endl;
    }

    return 0;
}