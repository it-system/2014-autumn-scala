/**
 * Scalaで最終制作のひな形
 */

import scala.io._
import scala.collection.mutable._

object Sample {

  val input = new java.util.Scanner(System.in)
  val W = 5

  def main(args: Array[String]): Unit = {
    while(true) {
      val H: Int = input.nextInt
      if (H == 0) return

      // H * Wの可変な多次元配列
      // var field = Array.ofDim[Int](H, W).map(_.toBuffer).toBuffer
      // for ( y <- 0 until H) {
      //   field(y) = input.nextLine.split(" ").map(_.toInt).toBuffer
      // }

      val field: List[List[Int]] = List.fill(H,W)(input.nextInt)
      field.foreach(debug)

      val ans: Int = solve(field)
      println(ans)
    }
  }

  def solve(f: List[List[Int]]): Int = {
    var ans = 0

    // 3個以上の水平に並んだ石の消滅処理
    val replacedField  = f.map({ line =>
      var continuous: Int = 1
      var previousNum: Int = 0
      var replacedLine: Buffer[Int] = line.toBuffer

      for ((n, i) <- line.zipWithIndex) {
        if (previousNum == n) {
          continuous += 1
        } else if (continuous >= 3) {
          for (j <- (i-continuous until i)) {
            ans += line(j)
            replacedLine(j) = -1
          }
          continuous = 1
        } else {
          continuous = 1
        }
        previousNum = n
      }

      if (continuous >= 3) {
        for (j <- (W-continuous until W)) {
          ans += line(j)
          replacedLine(j) = -1
        }
      }

      replacedLine.toList
    })

    // 石の落下処理

    return ans
  }

  def debug(args: Any): Unit = {
    println(args)
  }
}
