/**
 * ACM2014国内予選のProblem Aを解くプログラム
 *
 * cf. http://icpc.iisf.or.jp/past-icpc/domestic2014/#section_A
 */

import scala.io._

object ProblemA {
  def main(args: Array[String]): Unit = {
    val inputs = readTextFileIntoList("src/main/resources/problem.txt")
    for (line <- inputs) {
      println(line)
    }
  }

  /**
   * ファイルを読み込む関数
   * @param filePath 入力例のサンプル
   * @return テキストファイルを1行ずつ格納した配列
   */
  def readTextFileIntoList(filePath: String): List[String] = {
    val list = Source.fromFile(filePath, "UTF-8").getLines.toList
    return list
  }
}
