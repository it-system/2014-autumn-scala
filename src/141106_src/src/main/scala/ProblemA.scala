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
   * @param filePath 入力例のサンプルファイルのパス
   * @return テキストファイルを1行ずつ格納した配列
   */
  def readTextFileIntoList(filePath: String): List[String] = {
    val list = Source.fromFile(filePath, "UTF-8").getLines.toList
    return list
  }

  /**
   * 税込合計価格の最大値を計算する関数
   * @param oldTaxRate 旧税率
   * @param newTaxRate 新税率
   * @param oldTotalPrice 税率変更前の税込み合計価格
   * @return 税率変更後の税込合計価格の最大値
   */
  def calcMaxPrice(oldTaxRate: Int, newTaxRate: Int, oldTotalPrice: Int): Int = {
    return 109
  }
}
