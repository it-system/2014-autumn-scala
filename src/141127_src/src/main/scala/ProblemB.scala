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
    // while (_inputs.length > 0) {
    while (_inputs.length > 1) {
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
    //DEBUG
    // println("--- 初期フィールド")
    // for (line <- field) {
    //   println(line)
    // }

    /**
     * @param min いくつ並んでたら消すか的な値（整数）
     * @return 消せる石の座標の配列（リスト）
     */
    def searchLinedStones(min: Int): List[List[Int]] = {
      var linedStones = ListBuffer.empty[ListBuffer[Int]]

      //HACK 各セルごとに1つ隣の石だけ見て同じ数字だったらその次も見て...違う数字が出てきたらそいつからまた同じ処理するみたいな感じに書き直せそう
      for ((line, lineNum) <- field.zipWithIndex) {
        linedStones.append(ListBuffer.empty[Int])

        var continuous = 1
        var previousNum = -1

        for ((n, i) <- line.zipWithIndex) {
          if (previousNum == n && previousNum != 0) {
            continuous += 1
          } else {
            if (continuous >= min) {
              linedStones(lineNum) ++= Range(i-continuous, i).toList
            }
            continuous = 1
            previousNum = n
          }
        }

        if (continuous >= min) {
          linedStones(lineNum) ++= Range(4-(continuous-1), 5).toList
        }
      }
      return linedStones.map(_ toList).toList
    }

    // 初期配置で3つ以上並んでる部分を探して、消す部分のindexをキューに入れる
    val erasableStones = searchLinedStones(3)
    //DEBUG
    // println("--- 消せる石")
    // for ((line,i) <- erasableStones.zipWithIndex) {
    //   println(s"$i 行目の $line 番目の石は消してOK")
    // }

    // 削除する
    for (i <- 0 until erasableStones.length) {
      for (j <- erasableStones(i)) {
        score += field(i)(j)
        field(i)(j) = 0
      }
    }

    //DEBUG
    // println("--- 現在のフィールド")
    // for (line <- field) {
    //   println(line)
    // }

    // 石を落とす
    var fell = true
    while (fell) {
      fell = false
      for ((line, lineNum) <- field.zipWithIndex.reverse) {
        for ((n, i) <- line.zipWithIndex if lineNum != field.length-1) {
          if (field(lineNum+1)(i) == 0) {
            if (n != 0) {
              fell = true
            }
            field(lineNum+1)(i) = n
            field(lineNum)(i) = 0
          }
        }
      }

      //DEBUG
      // println("--- 落下中のフィールド")
      // for (line <- field) {
      //   println(line)
      // }

    }

    //DEBUG
    // println("--- 落下後のフィールド")
    // for (line <- field) {
    //   println(line)
    // }

    // 現在の配置でまた最初の処理に戻る
    var erase = true
    while(erase) {
      erase = false

      val erasableStones = searchLinedStones(3)

      //DEBUG
      // println("--- 消せる石")
      // for ((line,i) <- erasableStones.zipWithIndex) {
      //   println(s"$i 行目の $line 番目の石は消してOK")
      // }

      for (i <- 0 until erasableStones.length) {
        for (j <- erasableStones(i)) {
          score += field(i)(j)
          field(i)(j) = 0
          erase = true
        }
      }

      var fell = true
      while (fell) {
        fell = false
        for ((line, lineNum) <- field.zipWithIndex.reverse) {
          for ((n, i) <- line.zipWithIndex if lineNum != field.length-1) {
            if (field(lineNum+1)(i) == 0) {
              if (n != 0) {
                fell = true
              }
              field(lineNum+1)(i) = n
              field(lineNum)(i) = 0
            }
          }
        }

        //DEBUG
        // println("--- 落下中のフィールド")
        // for (line <- field) {
        //   println(line)
        // }

      }

      //DEBUG
      // println("--- 落下後のフィールド")
      // for (line <- field) {
      //   println(line)
      // }


    }

    // 消すものがなければ終了
    return score
  }
}
