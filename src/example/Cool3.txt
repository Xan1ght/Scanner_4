#include <iostream>

int main() {
  int a = 10;
  int b = 5;
  char c = 'A';
  double d = 3.14;
  long double e = 3.e2L;
  float f = 3.1f;

  std::cout << "Hello World!"               << std::endl;
  std::cout << "The value of a is: "        << a            << std::endl;
  std::cout << "The value of b is: "        << b            << std::endl;
  std::cout << "The ASCII value of c is: "  << (int)c       << std::endl;
  std::cout << "The value of d is: "        << d            << std::endl;
  std::cout << "The value of e is: "        << e            << std::endl;
  std::cout << "The value of f is: "        << f            << std::endl;

  return 0;
}