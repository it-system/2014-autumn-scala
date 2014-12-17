/**
 * ACM2014国内予選のProblem Bを解くプログラム
 *
 * cf. http://icpc.iisf.or.jp/past-icpc/domestic2014/#section_B
 */

import scala.io._
import math._
import scala.collection.mutable._

object ProblemB {
  def main(args: Array[String]): Unit = {
    val inputs = readTextFileIntoList("src/main/resources/problem.txt")

    val problems = createProblems(inputs)

    for((p, i) <- problems.zipWithIndex) {
      println(s"----- problem $i -----")
      println(p)
    }

  }

  /**
   * @param filePath 入力例のサンプルファイルのパス
   * @return テキストファイルを1行ずつ格納した配列
   */
  def readTextFileIntoList(filePath: String): List[String] = {
    Source.fromFile(filePath, "UTF-8").getLines.toList
  }

  /**
   * @param inputs 問題のテキストを1行ずつ格納した配列
   * @return 問題ごとに分割された配列
   */
  def createProblems(inputs: List[String]): List[List[List[Int]]]= {
    var _inputs = inputs.to[ListBuffer]
    var problems = ListBuffer.empty[List[List[Int]]]

    //REVIEW これだと最後の0も読み込んでしまうので、残り行数が1になったら終わってもいい？
    while (_inputs.length > 0) {
      val height = _inputs.remove(0) toInt
      var problem = ListBuffer.empty[List[Int]]
      for (i <- 0 until height) {
        val line = _inputs.remove(0)
        problem.append((line split " ").toList.map(_ toInt))
      }
      problems.append(problem.toList)
    }

    return problems.toList
  }
}
