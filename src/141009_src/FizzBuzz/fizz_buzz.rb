def fizz_buzz(n)
  return "FizzBuzz" if n % 15 == 0
  return "Buzz" if n % 5 == 0
  return "Fizz" if n % 3 == 0
  n
end

(1..100).each do |num|
  puts fizz_buzz(num)
end
