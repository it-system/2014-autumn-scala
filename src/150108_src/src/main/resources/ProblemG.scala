import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.actors.threadpool.Arrays

object ProblemG {

  def main(args: Array[String]): Unit = {
	//ファイル読み込み
    var datasetList:ListBuffer[Dataset] = readTextFileIntoList("ProblemG.txt")
	
	//データセットの数分、繰り返す
	for(dataset <- datasetList){
	  //PとQのペアの数分、繰り返す
	  for(pair <- dataset.pairList){
	     //PとQがどの円弧とも接触せず結ばれるか判定し、YES or NOを出力する
	   	 if(!intersect(dataset.circleList, pair)) print("YES ") else  print("NO ")
	  }
	  println()
	}
  }
  
  def readTextFileIntoList(filePath: String): ListBuffer[Dataset]= {
    val inputList = Source.fromFile(filePath, "UTF-8").getLines.toList //テキストからリスト
    var returnValue:ListBuffer[Dataset] = ListBuffer.empty[Dataset] //戻り値となるリスト
    var dataset:Dataset = null
    var i:Int =0
    var n:Int = 0 
    var m:Int = 0
    var bufi =0
    
    //リストから１行ずつ取り出す
    while (i < inputList.length) {
      var buf = inputList(i).split(" ")
      
      if(buf.length == 2){
    	  n = buf(0).toInt
    	  m = buf(1).toInt
    	  dataset = new Dataset
    	  bufi = i
    	  i = i + 1
      }else{
          //n回繰り返す
          while(i <= n +bufi ){
            var cx = buf(0).toInt
            var cy = buf(1).toInt
            var r = buf(2).toInt
            dataset.circleList.+=(new Circle(new Point(cx,cy),r))
            
            i = i + 1
            buf = inputList(i).split(" ")
          }
          
          //m回繰り返す
          while(i <= m + n +bufi ){
            var px = buf(0).toInt
            var py = buf(1).toInt
            var qx = buf(2).toInt
            var qy = buf(3).toInt
            dataset.pairList.+=((new Point(px,py),new Point(qx,qy)))
            i = i + 1
            buf = inputList(i).split(" ")
          }
          returnValue.+=(dataset)
      }
    }
    returnValue
  }
  
   
  def intersect(circleList:ListBuffer[Circle],pair:(Point,Point) ):Boolean={
     for(i<-0 until circleList.length){ 
        if((circleList(i).containPoint(pair._1)) != (circleList(i).containPoint(pair._2))){
          return true
        }
     }	
     false
  }
  class Dataset{ //データセットオブジェクト
    var circleList:ListBuffer[Circle] = ListBuffer.empty[Circle]
    var pairList:ListBuffer[(Point,Point)] = ListBuffer.empty[(Point,Point)]
  }
  
  class Circle(var centerPoint:Point,var r:Int ){ //円オブジェクト
    def containPoint(point:Point):Boolean = {
      //(x - a)^2 + (y - b)^2 < r^2
      var pointR:Double = Math.pow((point.x - centerPoint.x),2) + Math.pow((point.y - centerPoint.y),2)
      
       //println (pointR +"<="+Math.pow(r,2))
      //円の内側(境界線含む)領域に含まれているか
      if(pointR <= Math.pow(r,2)) true else false
    }
 //	    for(circle <- dataset.circleList){
//	      println(circle.centerPoint.x +" " +circle.centerPoint.y +" "+circle.r )
//	    }
//	    for((point1,point2) <- dataset.pairList ){
//	      println(point1.x +" " +point1.y+" " +point2.x+" " +point2.y)
//	    }
//	    println() 
  }
  
  class Point(var x:Int,var y:Int ) //点オブジェクト
}

