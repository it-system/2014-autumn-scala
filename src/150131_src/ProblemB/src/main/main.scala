/**
 * Scalaで最終制作のひな形
 */

import scala.io._

object Sample {

  val input = new java.util.Scanner(System.in)
  val W = 5

  def main(args: Array[String]): Unit = {
    while(true) {
      var H = input.nextInt
      println(H)

      if (H == 0) return

      for ( y <- 0 until H) {
        for (x <- 0 until W) {
          print(input.nextInt)
        }
        println()
      }
    }
  }
}
