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
      val score = solve(p)
      println(s"Score: $score")
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

  /**
   * @param problem 問題の初期配置（リスト）
   * @return この配置で獲得できるスコア
   */
  def solve(problem: List[List[Int]]): Int = {
    // createProblems関数の仕様上、0が入力の時に空のリストが生成されるのでそれの対応。createProblemsを直した方がスマートという気も...
    if (problem.length <= 0) {
      return 0
    }

    // スコアを初期化
    var score = 0

    // フィールドを初期化
    var field = problem.map(_ toBuffer).toBuffer
    println("--- 初期フィールド")
    for (line <- field) {
      println(line)
    }

    /**
     * @param min いくつ並んでたら消すか的な値（整数）
     * @return 消せる石の座標の配列（リスト）
     */
    def searchLinedStones(min: Int): List[List[Int]] = {
      var linedStones = ListBuffer.empty[ListBuffer[Int]]

      for ((line, i) <- field.zipWithIndex) {
        linedStones.append(ListBuffer.empty[Int])

        var continuous = 1
        var previousNum = -1

        for ((n, j) <- line.zipWithIndex) {
          if (previousNum == n) {
            continuous += 1
          } else {
            if (continuous >= min) {
              linedStones(i) ++= Range(j-continuous, j).toList
            }
            continuous = 1
            previousNum = n
          }
        }

        if (continuous >= min) {
          linedStones(i) ++= Range(4-(continuous-1), 5).toList
        }
      }
      return linedStones.map(_ toList).toList
    }

    // 初期配置で3つ以上並んでる部分を探して、消す部分のindexをキューに入れる
    val erasableStones = searchLinedStones(3)
    println("--- 消せる石")
    for ((line,i) <- erasableStones.zipWithIndex) {
      println(s"$i 行目の $line 番目の石は消してOK")
    }

    // 削除する
    for (i <- 0 until erasableStones.length) {
      for (j <- erasableStones(i).reverse) {
        score += field(i)(j)
        field(i)(j) = -1
      }
    }

    println("--- 現在のフィールド")
    for (line <- field) {
      println(line)
    }

    // 石を落とす
    // 現在の配置でまた最初の処理に戻る
    // 消すものがなければ終了
    return score
  }
}
