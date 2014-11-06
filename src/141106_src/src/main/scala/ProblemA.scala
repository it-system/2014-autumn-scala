/**
 * ACM2014国内予選のProblem Aを解くプログラム
 *
 * cf. http://icpc.iisf.or.jp/past-icpc/domestic2014/#section_A
 */

import scala.io._
import math._
import scala.collection.mutable._

object ProblemA {
  def main(args: Array[String]): Unit = {
    val inputs = readTextFileIntoList("src/main/resources/problem.txt")
    for (line <- inputs) {
      val buf = line.split(" ") //e.g. ["5", "8", "105"]
      val max = calcMaxPrice(buf(0).toInt, buf(1).toInt, buf(2).toInt)
      println(max)
    }
  }

  /**
   * ファイルを読み込む関数
   * @param filePath 入力例のサンプルファイルのパス
   * @return テキストファイルを1行ずつ格納した配列
   */
  def readTextFileIntoList(filePath: String): List[String] = {
    Source.fromFile(filePath, "UTF-8").getLines.toList
  }

  /**
   * 税込合計価格の最大値を計算する関数
   * @param oldTaxRate 旧税率
   * @param newTaxRate 新税率
   * @param oldTotalPrice 税率変更前の税込み合計価格
   * @return 税率変更後の税込合計価格の最大値
   */
  def calcMaxPrice(oldTaxRate: Int, newTaxRate: Int, oldTotalPrice: Int): Int = {
    if (oldTotalPrice <= 0) {
      return 0
    }
    // 税込み合計価格がoldTotalPriceになる価格の組み合わせを全通り求める
    val priceCombinationWithoutTax = enumPriceCombinationWithoutTax(oldTotalPrice, oldTaxRate)

    // 全組み合わせに対して, 新税率での税込合計価格がいくらになるか求める
    val totalPrices = enumTotalPrices(priceCombinationWithoutTax, newTaxRate)

    // 最大値を返す
    totalPrices.max
  }

  /**
   * 税込み合計価格が引数になるような、税抜き価格の組み合わせを求める関数
   * @param taxIncludedTotalPrice 税込み合計価格
   * @return 税抜き価格の組み合わせの配列
   */
  def enumPriceCombinationWithoutTax(taxIncludedTotalPrice: Int, oldTaxRate: Int): List[List[Int]] = {
    //TOOD: 別のメソッドに切り分ける
    val combTaxIncluded = {(n: Int) => {
      var combination = ListBuffer.empty[List[Int]]
      for (i <- (1 to n/2)) {
        combination.append(List(i, n-i))
      }
      combination.toList
    }: List[List[Int]] }.apply(taxIncludedTotalPrice)

    //TOOD: 別のメソッドに切り分ける
    val combPriceWithoutTax = {(list: List[List[Int]], taxRate: Int) => {
      // この計算は別のメソッドでも使うかもしれない、使う場合は外に出す
      def calcTaxIncludedPrice(p: Int): Int = {
        p * (100 + taxRate) / 100
      }

      def calcPriceWithoutTax(p: Int): Int = {
        //TODO: 税抜き価格を計算する式で浮動小数点を使わないようにする
        ceil(p * 100.0 / (100 + taxRate)).toInt
      }

      var combination = ListBuffer.empty[List[Int]]
      for (c <- list) {
        //TODO: cのlengthが2以外だった場合のエラー処理を入れる
        val item1 = calcPriceWithoutTax(c(0))
        val item2 = calcPriceWithoutTax(c(1))

        if (calcTaxIncludedPrice(item1) == c(0) && calcTaxIncludedPrice(item2) == c(1)) {
          combination.append(List(item1, item2))
        }
      }
      // ここでreturnを書くと即時関数の中じゃなくて enumPriceCombinationWithoutTax のreturnとして解釈される(要調査)
      combination.toList
    }: List[List[Int]] }.apply(combTaxIncluded, oldTaxRate)

    combPriceWithoutTax
  }

  /**
   * 税抜き金額の組み合わせから、各組み合わせの合計金額の配列を返す関数
   * @param priceCombination 税抜き金額の組み合わせ
   * @return 税込み合計金額の配列
   */
  def enumTotalPrices(priceCombination: List[List[Int]], taxRate: Int): List[Int] = {
    var prices = List.empty[Int]
    for (p <- priceCombination) {
      prices = (p(0) * (100 + taxRate) / 100) + (p(1) * (100 + taxRate) / 100) :: prices
    }
    return prices
  }
}
