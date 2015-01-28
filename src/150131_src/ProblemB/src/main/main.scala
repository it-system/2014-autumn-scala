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
      var H = input.nextInt
      debug(H)

      if (H == 0) return

      // H * Wの可変な多次元配列
      // var field = Array.ofDim[Int](H, W).map(_.toBuffer).toBuffer
      // for ( y <- 0 until H) {
      //   field(y) = input.nextLine.split(" ").map(_.toInt).toBuffer
      // }

      var field = List.fill(H,W)(input.nextInt).map(_.toBuffer).toBuffer
      field.foreach(debug)
    }
  }

  def debug(args: Any): Unit = {
    println(args)
  }
}
