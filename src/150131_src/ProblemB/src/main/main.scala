/**
 * Scalaで最終制作のひな形
 */

import scala.io._
import scala.collection.mutable._
import scala.util.control.Breaks

object Sample {

  val b = new Breaks
  val input = new java.util.Scanner(System.in)
  val W = 5

  def main(args: Array[String]): Unit = {
    while(true) {
      val H: Int = input.nextInt
      if (H == 0) return

      val field: List[List[Int]] = List.fill(H,W)(input.nextInt)

      val ans: Int = solve(field)
      println(ans)
    }
  }

  def solve(f: List[List[Int]]): Int = {
    var ans = 0

    // 各行に対して石を消す処理
    // この中で合計得点を加算...他に良い書き方あるかな
    val fieldAfterErasingStones = f.map({ line =>
      val (tmpScore, newLine) = eraseLinedStones(line)
      ans += tmpScore
      newLine
    })

    // 左に90度回転 → 各行ごと（つまり元の配列の各列ごと）に石の落下処理 → 右に90度回転
    val fieldAfterfallingStones = fieldAfterErasingStones.map(_.reverse).transpose.map(fallStones).transpose.map(_.reverse)

    val fell = !fieldAfterErasingStones.sameElements(fieldAfterfallingStones)
    if (fell) {
      return ans + solve(fieldAfterfallingStones)
    }
    return ans
  }

  def eraseLinedStones(line: List[Int]): (Int, List[Int]) = {
    var stack = ListBuffer(0)
    // TODO: breakを使わずに書き直したい
    b.breakable {
      for (i <- 1 until W) {
        if (line(stack(0)) == line(i)) {
          stack.append(i)
        } else if (stack.size >= 3) {
          b.break
        } else {
          stack.clear
          stack.append(i)
        }
      }
    }

    if (stack.size >= 3) {
      var l = line.toBuffer
      var tmpScore = 0
      stack.foreach({ i =>
        l(i) = -1
        tmpScore += line(i)
      })
      return (tmpScore, l.toList)
    }
    return (0, line)
  }

  def fallStones(line: List[Int]): List[Int] = {
    return line
  }

  def debug(args: Any): Unit = {
    println(args)
  }
}
