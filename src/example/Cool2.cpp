#include <iostream>

int main() {
    int num1 = 10;
    int num2 = 5;
    int sum = num1 + num2;

    std::cout << "The sum of " << num1 << " and " << num2 << " is: " << sum << std::endl;

    if (sum > 10) {
        std::cout << "The sum is greater than 10." << std::endl;
    } else {
        std::cout << "The sum is less than or equal to 10." << std::endl;
    }

    return 0;
}