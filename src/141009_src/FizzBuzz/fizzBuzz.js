var fizzBuzz = function(n) {
  if (n % 15 === 0) {
    return "FizzBuzz";
  } else if (n % 5 === 0) {
    return "Buzz";
  } else if (n % 3 === 0) {
    return "Fizz";
  } else {
    return n
  }
};

// var fizzBuzz = function(n) {
//   return n % 3 === 0 ? (n % 5 === 0 ? "FizzBuzz" : "Fizz") : (n % 5 === 0 ? "Buzz" : n);
// };
//

for (var i = 1; i <= 100; i++) {
  console.log(fizzBuzz(i));
}
