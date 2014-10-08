/**
 * scalaで書いたfizzbuzzのサンプル
 */
object FizzBuzz extends App {
  def fizzbuzz(n: Int) : String = {
    if (n % 15 == 0) {
      "FizzBuzz"
    } else if (n % 5 == 0) {
      "Buzz"
    } else if (n % 3 == 0) {
      "Fizz"
    } else {
      n.toString()
    }
  }

  // val fizzbuzz = (n:Int) => {
  //   if (n % 15 == 0) {
  //     "FizzBuzz"
  //   } else if (n % 5 == 0) {
  //     "Buzz"
  //   } else if (n % 3 == 0) {
  //     "Fizz"
  //   } else {
  //     n.toString()
  //   }
  // }:String

  (1 to 100).foreach { i =>
    println(fizzbuzz(i))
  }

}
