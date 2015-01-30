import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.actors.threadpool.Arrays
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ListBuffer

object ProblemG {

  def main(args: Array[String]): Unit = {
	//ファイル読み込み
    var datasetList:ListBuffer[Dataset] = readTextFileIntoList("ProblemG.txt")
	var num:Int = 1
	//データセット分、繰り返す
	for(dataset <- datasetList){
	  //PとQのペア分、繰り返す
	  println("---------------------------No:"+num+"---------------------------")
	  println("円の数:"+dataset.circleList.length)
	  for(pair <- dataset.pairList){
	     print("P:"+pair._1.printPoint+" Q:"+pair._2.printPoint +"  ")
	     //PとQがどの円弧とも接触せず結ばれるか判定し、YES or NOを出力する
	   	 if(!intersect(dataset.circleList, pair)) print("YES ") else  print("NO ")
	   	 println()
	  }
	  println()
	  
	  createPolygon(dataset.circleList)
	  println()
	  num = num + 1
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
  
  
  /**
   * 点のペアPとQが円と交差するか
   * @param 円の情報が入ったリスト
   * @param 点のペアPとQ
   */
  def intersect(circleList:ListBuffer[Circle],pair:(Point,Point) ):Boolean={
     for(i<-0 until circleList.length){ 
        if((circleList(i).containPoint(pair._1)) != (circleList(i).containPoint(pair._2))){
          return true
        }
     }	
     false
  }
  /**
   * 円と円が交差するか
   * @param 円１
   * @param 円2
   */
  def intersect(circle1:Circle,circle2:Circle):Boolean={
    // (C2)
    var p = (Math.pow((circle2.centerPoint.x - circle1.centerPoint.x ),2).toInt + Math.pow((circle2.centerPoint.y - circle1.centerPoint.y ),2).toInt)
    var n = Math.pow((circle2.r  + circle1.r ), 2).toInt
    if(p <= n)  true else false
    
  }
  
  /**
   * 円リストから交差するものを見つけ、多角形を生成する
   */
  def createPolygon(circleList:ListBuffer[Circle]):ListBuffer[Polygon]={
    var lineList:ListBuffer[Line] = ListBuffer.empty[Line]
    var polygonList:ListBuffer[Polygon] = ListBuffer.empty[Polygon]
    
    //円が２つ以下だと、多角形ができないのでnullでリターン
    if(circleList.length <= 2){
       println("多角形はできない")
       return null
    }
    
    //交差している円で、線分をまず作る
     for(i<-0 until circleList.length ){
        for(j<-0 until circleList.length){
           if(i!=j && intersect(circleList(i),circleList(j))){
              //print((i+1)+"と"+(j+1)+" ")
              lineList.+=(new Line(circleList(i).centerPoint ,circleList(j).centerPoint ,i+1 ,j+1) )
              //println()
           }
        }
     }

     var polygon:Polygon = new Polygon()
     var z = polygon.pointList.clone     
     //線分から多角形を生成する
     for(i<-0 until  lineList.length){
        polygon = new Polygon()
        findPolygon(lineList(i))
        z = polygon.pointList.clone 
     }
     
     def findPolygon(line:Line):Unit={
       
       polygon.pointList.+=(line)
       
    	for(line2<-lineList){
    	  
    	   //引数の線分の終点と、線分の始点が同じ場合 かつ  
    	   if(line.circleNum2 == line2.circleNum1 
    	       && line.circleNum1 != line2.circleNum2
    	        && !checkNum(line2)){
    		    z = polygon.pointList.clone
    		   findPolygon(line2)
    	   }
    	   else if(line.circleNum2 == line2.circleNum1 
    	       && line.circleNum1 != line2.circleNum2
    	       &&checkNum(line2)){
    		   		
    	            polygon.pointList.+=(line2)
    	            if(polygon.pointList(0).circleNum1 == polygon.pointList(polygon.pointList.length-1).circleNum2 
    	                && checkPolygonOrder(polygon)
    	                ){
    	            	for(poly <- polygon.pointList ){
    	                  print("円"+poly.circleNum1  +":"+poly.point1.printPoint+" 円"+poly.circleNum2+":"+poly.point2.printPoint+" ")
    	            	  //print("円"+poly.circleNum1  +":"+poly.point1.printPoint)
    	            }
    	            println()
    	            }
    	                  
    	            polygon = new Polygon()
    	            polygon.pointList =z
    	            
    	   }
    	}
       
     }
     
     //ポイントが重ならないように
     def checkNum(line:Line):Boolean={
       for(poly <- polygon.pointList ){
         if(poly.circleNum1 == line.circleNum2  || poly.circleNum2 == line.circleNum2){
           return true
         }
       }
       false
     }
     
     //多角形の点が正しい順番かをチェックする
     def checkPolygonOrder(poygon:Polygon):Boolean={
        for(i<-0 until poygon.pointList.length-1){
          if(poygon.pointList(i).circleNum2 != poygon.pointList(i+1).circleNum1 ){
           return  false
          }
        }
        true
     }
     
     return polygonList
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
    
  }
  
  class Point(var x:Int,var y:Int ) //点オブジェクト
  {
    def printPoint():String={
      return ("("+this.x +","+this.y +")")
    }
  }
  
  
  class Line(var point1:Point,var point2:Point,var circleNum1:Int, var circleNum2:Int) //線分オブジェクト
  class Polygon(){
    var pointList:ListBuffer[Line] = ListBuffer.empty[Line]
  } //多角形オブジェクト
  
}

