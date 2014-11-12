import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.collection.immutable.Range
import scala.util.control.Breaks.{ break, breakable }


/**
 * ACM2014国内予選のProblem Cを解くプログラム
 *
 * cf. http://icpc.iisf.or.jp/past-icpc/domestic2014/#section_C
 */
object ProblemC {

  def main(args: Array[String]): Unit = {
    //ファイルから入力データを取り出し、リストで扱う
    val inputs:List[(Sun,Silhouette)] = readTextFileIntoList("ProblemC.txt").toList

    //入力データ分繰り返す
    for(data <- inputs){
        var sun:Sun = data._1  //円
        var silhouette:Silhouette = data._2 //シルエット 
        var finalTime = 0.0000d //最後の瞬間の時刻
        
        //0秒から20秒まで0.0001秒ずつカウントアップし、円を動かす
        breakable {
	      	for(time<-0d to 20d by 0.0001d){
	      	   //シルエットに円が含まれている場合
	    	   if(silhouette.contain(sun)){
	    	      finalTime = time
	    	   }else{
	    	      break
	    	   }
	    	   
	    	   //円をy方向へ移動
	      	   sun.move(0.0001d)
	    	}
        }
        println("%.4f".format(finalTime))
     }

  }

  /**
   * ファイルを読み込む関数
   * @param filePath 入力例のサンプルファイルのパス
   * @return 太陽と建物のシルエットの情報を保持したリスト
   */
  def readTextFileIntoList(filePath: String): ListBuffer[(Sun,Silhouette)]= {
    val inputList = Source.fromFile(filePath, "UTF-8").getLines.toList //テキストからリスト
    var flg:Boolean = false
    var returnValue:ListBuffer[(Sun,Silhouette)] = ListBuffer.empty[(Sun,Silhouette)] //戻り値となるリスト
    var silhouette:Silhouette = null //長方形
    var sun:Sun = null //太陽
    
    //リストから１行ずつ取り出す
    for (line <- inputList) {
      val buf = line.split(" ")
      if(buf.length == 2 && flg){
        returnValue.append((sun,silhouette))
        flg = false
      }
      if(buf.length == 2 && !flg){
         flg = true
         sun = new Sun(buf(0).toShort,buf(1).toShort,-2)
         silhouette = new Silhouette
      }else if(buf.length == 3 && flg){
        silhouette.addXl(buf(0).toShort)
        silhouette.addXr(buf(1).toShort)
        silhouette.addH(buf(2).toShort)
      }else{
        throw new IllegalArgumentException
      }
    }
    returnValue
  }
  
  //建物のシルエットを表現するクラス
  class Silhouette {
    var xl:ListBuffer[Short] = ListBuffer.empty[Short] //長方形x軸の位置(左)
    var xr:ListBuffer[Short] = ListBuffer.empty[Short] //長方形x軸の位置(右)
    var h:ListBuffer[Short] = ListBuffer.empty[Short] //長方形の高さ
    
    def addXl(xl:Short):Unit = this.xl+=xl
    def addXr(xr:Short):Unit = this.xr+=xr
    def addH(h:Short):Unit = this.h+=h
    def getXl(index:Int):Double = this.xl(index)
    def getXr(index:Int):Double = this.xr(index)
    def geth(index:Int):Double = this.h(index)
    /**
     * シルエットが円を含まれているか確認するメソッド
     * @param 太陽
     * @return　太陽がシルエットに含まれていた場合true、そうでない場合はfalseを返す
     */
    def contain(sun:Sun):Boolean = {
    	var result:Boolean = false
	    //円上の座標がシルエットに含まれているか
	    //太陽のx-y座標テーブル分、繰り返す
	    for(i<-0 until sun.xyPositionTbl.size by 1){
	        //シルエット分、繰り返す
	       breakable{
		        for(j<-0 until xl.length by 1){
		          if( sun.xyPositionTbl(i)._2 <=0 ||
		              (xl(j) <= sun.xyPositionTbl(i)._1 &&
		              sun.xyPositionTbl(i)._1 <= xr(j) &&
		              sun.xyPositionTbl(i)._2 + sun.time  <= h(j))){
		              result = true
		              break
		          }else{
		              result = false
		          }
		        }
	       }
	       if(!result){
	          println("x="+sun.xyPositionTbl(i)._1 +" y="+(sun.xyPositionTbl(i)._2+sun.time))
	          return false
	       }
	    }
	    result
    }
    
  }
  
  //太陽を表現するクラス
  class Sun(r:Short, n:Short,var time:Double) {
    //x-yの座標が入ったリスト
    var xyPositionTbl:ListBuffer[(BigDecimal,BigDecimal)] = ListBuffer.empty[(BigDecimal,BigDecimal)] 
    //コンストラクタ xとyの座標をリストに入れる
    for(i<- -90d to 90d by 1d){
       var xPosition = BigDecimal(Math.cos(Math.toRadians(i))) * BigDecimal(r.toDouble)
       var yPosition = BigDecimal(Math.sin(Math.toRadians(i))) * BigDecimal(r.toDouble) 
       xyPositionTbl.append((xPosition,yPosition))
       xyPositionTbl.append((-1 * xPosition,yPosition))
    }
    
    def getR = this.r
    def getN = this.n
    def getTime = this.time 
    def move(time:Double) = this.time = this.time+time
  }
}