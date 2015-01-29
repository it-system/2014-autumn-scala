/**
 * Scalaで最終制作のひな形
 */

import scala.io._
import scala.collection.mutable._
import scala.util.control.Breaks

object ProblemB {

  val b = new Breaks
  val input = new java.util.Scanner(System.in)
  val W = 5

  def main(args: Array[String]): Unit = {
    while(true) {
      val H: Int = input.nextInt
      if (H == 0) return

      val field: List[List[Int]] = List.fill(H,W)(input.nextInt)
      display(field)

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
    display(fieldAfterErasingStones)

    // 左に90度回転 → 各行ごと（つまり元の配列の各列ごと）に石の落下処理 → 右に90度回転
    val fieldAfterfallingStones = fieldAfterErasingStones.map(_.reverse).transpose.map(fallStones).transpose.map(_.reverse)
    display(fieldAfterfallingStones)

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
        if (line(i) != -1 && line(stack(0)) == line(i)) {
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

  // TODO: whileを使わずに書き直したい
  def fallStones(line: List[Int]): List[Int] = {
    var fell = true
    var l = line.toBuffer

    while (fell) {
      fell = false
      for (i <- 0 until l.size-1 if l(i) != -1) {
        if (l(i+1) == -1) {
          l(i+1) = l(i)
          l(i) = -1
          fell = true
        }
      }
    }
    return l.toList
  }

  /**
   * @param field 表示するフィールド
   * @param score 表示する得点
   */
  def display(field: List[List[Int]]) ={
    print("\033[2J") //コンソールクリア
    println("\033[2J") //コンソールクリア
    print("\033[93m"); //フォントを標準色に戻す
    print("\033[1m"); //フォントをボールド設定

     for (line <- field) {
       for(num <- line){
         num match {
           case -1 => print("\033[37m");print(" 　")
           case 1 => print("\033[31m");print(" ① ")
           case 2 => print("\033[32m");print(" ② ")
           case 3 => print("\033[33m");print(" ③ ")
           case 4 => print("\033[34m");print(" ④ ")
           case 5 => print("\033[35m");print(" ⑤ ")
           case 6 => print("\033[36m");print(" ⑥ ")
           case 7 => print("\033[37m");print(" ⑦ ")
           case 8 => print("\033[31m");print(" ⑧ ")
           case 9 => print("\033[32m");print(" ⑨ ")
         }
       }
       println()
     }
    // print("\033[36m");
    // println(s"Score: $score")
    println("================")
    Thread.sleep(1000)
    print("\033[93m");
  }
}
