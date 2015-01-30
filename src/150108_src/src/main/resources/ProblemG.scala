import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.actors.threadpool.Arrays
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ListBuffer

object ProblemG {

  //とりあえず定数で
  val CIRCLE_INSIDE_INSIDE = 1
  val CIRCLE_INSIDE_OUTSIDE = 2
  val CIRCLE_OUTSIDE_OUTSIDE = 3
  
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
	     var p = pair._1
	     var q = pair._2
	     println("P:"+p.printPoint+" Q:"+q.printPoint +"  ") //PとQの座標
	     
	     //PとQがどの円弧とも接触せず結ばれるか判定し、YES or NOを出力する
	     //PとQが両方内側の場合
	   	 if(intersect(dataset.circleList, pair) == CIRCLE_INSIDE_INSIDE) {
	   		 print("YES ") 
	   	 }
	     //PとQのどちらかが円の外側の場合
	   	 else if(intersect(dataset.circleList, pair) == CIRCLE_INSIDE_OUTSIDE){ 
	   		 print("NO ")
	   	 }
	   	 //PとQどちらも円の外側の場合
	   	 else{
	   	   var polygonList = createPolygon(dataset.circleList ) //多角形を作る
	   	   var pLine = new Line(p,new Point(10000,p.y+2),0,0) //PとXの最大で線分を作る
	   	   var qLine = new Line(q,new Point(10000,q.y+2),0,0) //QとXの最大で線分を作る
	   	   //多角形ができない場合は、結ばれるのでYES
	   	   if(polygonList == null){
	   	     print("YES ")
	   	   }else{
	   	     var pResult = new Array[Boolean](polygonList.length)
	   	     var qResult = new Array[Boolean](polygonList.length)
	   	     var mm = 0;
	   	     
	   	     for(polygon <- polygonList){
	   	       var pCount = 0 //Pと多角形の交差回数
	   	       var qCount = 0 //Qと多角形の交差回数
	   	       for(line <- polygon.lineList ){
	   	         if(pLine.intersect2(line)){ 
	   	          // print("P:"+pLine.point1.printPoint+" "+pLine.point2.printPoint+" 線分:"+line.point1 .printPoint+" "+line.point2 .printPoint+" ◯ ")
	   	          // print("P:◯ ")
	   	           pCount = pCount + 1
	   	         }else{
	   	           //print("P:"+pLine.point1.printPoint+" "+pLine.point2.printPoint+" 線分:"+line.point1 .printPoint+" "+line.point2 .printPoint+" ☓ ")
	   	           //print("P:☓ ")
	   	         }
	   	         if(qLine.intersect2(line)){
	   	           //print("Q:"+pLine.point1.printPoint+" "+pLine.point2.printPoint+" 線分:"+line.point1 .printPoint+" "+line.point2 .printPoint+" ◯ ")
	   	           //print("Q:◯ ")
	   	           qCount = qCount + 1
	   	         }else{
	   	           //print("Q:"+pLine.point1.printPoint+" "+pLine.point2.printPoint+" 線分:"+line.point1 .printPoint+" "+line.point2 .printPoint+" ☓ ")
	   	           //print("Q:☓ ")
	   	         }
	   	         
	   	       }
	   	       //println
	   	       //交差回数が奇数の場合は内側、偶数の場合は外側
	   	       if(pCount % 2 != 0){
	   	         pResult(mm) = true 
	   	       }
	   	       if(qCount % 2 != 0){
	   	         qResult(mm) = true 
	   	       }
	   	       mm = mm +1
	   	     }
	   	     
	   	     if(checkOrder(pResult, qResult)){
	   	       print("YES ")
	   	     }else{
	   	       print("NO ")
	   	     }
	   	     
	   	   }
	   	   
	   	 }
	   	 println()
	  }
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
   * 点のペアPとQが円と交差判定
   * @param 円の情報が入ったリスト
   * @param 点のペアPとQ
   */
  def intersect(circleList:ListBuffer[Circle],pair:(Point,Point) ):Int={
    //PとQの円内外判定配列
    var pArray = new Array[Boolean](circleList.length)
    var qArray = new Array[Boolean](circleList.length)
    //結果変数
    var result = 0
    
    //円それぞれに、PとQが存在するかチェックする
     for(i<-0 until circleList.length){ 
        if(circleList(i).containPoint(pair._1)){
          pArray(i) = true
        }
        if(circleList(i).containPoint(pair._2)){
          qArray(i) = true
        }
     }	

     //PとQが円の外側にある場合
     if(checkInside(pArray)==false && checkInside(qArray)==false) {
       result = CIRCLE_OUTSIDE_OUTSIDE 
     }//PとQどちらも円の内側の場合
     else if(checkInside(pArray)==true && checkInside(qArray)==true
             && checkOrder(pArray,qArray)){
       result =  CIRCLE_INSIDE_INSIDE 
     }//PとQどちらかが円の外側にある場合
     else{
       result = CIRCLE_INSIDE_OUTSIDE 
     }
     
     //PとQの結果が内側にあるか
     def checkInside(array:Array[Boolean]):Boolean={
       for(item <- array){
         if(item != false){
           return true
         }
       }
       false
     }
     
     return result
  }
  
	   //PとQの配列の並びが同じか
	 def checkOrder(array1:Array[Boolean],array2:Array[Boolean]):Boolean={
	   for(i<-0 until array1.length){
	     if(array1(i) != array2(i)){
	       return false
	     }
	   }
	   true
	 }
  
  /**
   * 円と円の交差判定
   * @param 円１
   * @param 円2
   */
  def intersect(circle1:Circle,circle2:Circle):Boolean={
    var p = (Math.pow((circle2.centerPoint.x - circle1.centerPoint.x ),2).toInt + Math.pow((circle2.centerPoint.y - circle1.centerPoint.y ),2).toInt)
    var n = Math.pow((circle2.r  + circle1.r ), 2).toInt
    if(p <= n)  true else false
    
  }
  
  /**
   * 円リストから交差するものを見つけ、多角形を生成する
   */
  def createPolygon(circleList:ListBuffer[Circle]):ListBuffer[Polygon]={
    var lineList:ListBuffer[Line] = ListBuffer.empty[Line] //線分リスト
    var polygonList:ListBuffer[Polygon] = ListBuffer.empty[Polygon] //多角形リスト
    
    //円が２つ以下だと、多角形ができないのでnullでリターン
    if(circleList.length <= 2){
       println("多角形はできない")
       return null
    }
    
    //交差している円どうしで、線分をまず作る
     for(i<-0 until circleList.length ){
        for(j<-0 until circleList.length){
           if(i!=j && intersect(circleList(i),circleList(j))){
              //print((i+1)+"と"+(j+1)+" ")
              lineList.+=(new Line(circleList(i).centerPoint ,circleList(j).centerPoint ,i+1 ,j+1) )
              //println()
           }
        }
     }

     var polygon:Polygon = new Polygon() //多角形オブジェクト
     var templineList = polygon.lineList.clone     //再帰呼び出しで一時的に保存する線分リスト
     //線分から多角形を生成する
     for(i<-0 until  lineList.length){
        polygon = new Polygon()
        findPolygon(lineList(i))
        templineList = polygon.lineList.clone 
     }
     
     def findPolygon(line:Line):Unit={
       polygon.lineList.+=(line)
    	for(line2<-lineList){
    	   //線分１の終点＝＝線分２の始点 かつ　線分１と線分２の 
    	   if(line.circleNum2 == line2.circleNum1  && line.circleNum1 != line2.circleNum2 && !checkNum(line2)){
    		   templineList = polygon.lineList.clone
    		   findPolygon(line2)
    	   }
    	   else if(line.circleNum2 == line2.circleNum1 && line.circleNum1 != line2.circleNum2 &&checkNum(line2)){
	            polygon.lineList.+=(line2)
	            if(polygon.lineList(0).circleNum1 == polygon.lineList(polygon.lineList.length-1).circleNum2 && checkPolygonOrder(polygon) ){
	            	for(poly <- polygon.lineList ){
	                 // print("円"+poly.circleNum1  +":"+poly.point1.printPoint+" 円"+poly.circleNum2+":"+poly.point2.printPoint+" ")
	                 }
	            	//println()
	            	polygonList.+=(polygon) //多角形リストに格納
	            } 
	            
	            polygon = new Polygon() 
	            polygon.lineList = templineList
    	   }
    	}
     }
     
     //ポイントが重ならないように
     def checkNum(line:Line):Boolean={
       for(poly <- polygon.lineList ){
         if(poly.circleNum1 == line.circleNum2  || poly.circleNum2 == line.circleNum2){
           return true
         }
       }
       false
     }
     
     //多角形の点が正しい順番かをチェックする
     def checkPolygonOrder(poygon:Polygon):Boolean={
        for(i<-0 until poygon.lineList.length-1){
          if(poygon.lineList(i).circleNum2 != poygon.lineList(i+1).circleNum1 ){
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
  
  /**
   * 線分を表現するクラス
   */
  class Line(var point1:Point,var point2:Point,var circleNum1:Int, var circleNum2:Int){
	  /**
	   * 線分と線分の交差判定
	   * @param 線分
	   */
	  def intersect(line:Line):Boolean ={
	    var point3 = line.point1
	    var point4 = line.point2
	    
	    //X座標のチェック
	    if(point1.x >= point2.x){
	    	if((point1.x < point3.x && point1.x < point4.x) || (point2.x > point3.x && point2.x > point4.x)){
	    	  return false
	    	}
	    }else{
	       if((point2.x < point3.x && point2.x < point4.x) || (point1.x > point3.x && point1.x > point4.x)){
	    	  return false
	    	}
	    }
	    //Y座標のチェック
	    if(point1.y >= point2.y){
	    	if((point1.y <  point3.y && point1.y < point4.y) || (point2.y > point3.y && point2.y > point4.y)){
	    	  return false
	    	}
	    }else{
	       if((point2.y < point3.y && point2.y < point4.y) || (point1.y > point3.y && point1.y > point4.y)){
	    	  return false
	    	}
	    }
	    //方程式によるチェック
	    if(((point1.x.toDouble - point2.x.toDouble)*(point3.y.toDouble - point1.y.toDouble )+(point1.y.toDouble - point2.y.toDouble)*(point1.x.toDouble - point3.x.toDouble )) *
	        ((point1.x.toDouble - point2.x.toDouble)*(point4.y.toDouble - point1.y.toDouble )+(point1.y.toDouble - point2.y.toDouble)*(point1.x.toDouble - point4.x.toDouble ))>0){
	       return false
	    }
	    if(((point3.x.toDouble - point4.x.toDouble)*(point1.y.toDouble - point3.y.toDouble )+(point3.y.toDouble - point4.y.toDouble)*(point3.x.toDouble - point1.x.toDouble )) *
	        ((point3.x.toDouble - point4.x.toDouble)*(point2.y.toDouble - point3.y.toDouble )+(point3.y.toDouble - point4.y.toDouble)*(point3.x.toDouble - point2.x.toDouble ))>0){
	       return false
	    }
	    true
	  }
	  	  /**
	   * 線分と線分の交差判定
	   * @param 線分
	   */
	  def intersect2(line:Line):Boolean ={
	    var point3 = line.point1
	    var point4 = line.point2
	    
	    //If (((p1.x - p2.x) * (p3.y - p1.y) + (p1.y - p2.y) * (p1.x - p3.x)) * _
	    if(((point1.x.toDouble - point2.x.toDouble) * (point3.y.toDouble-point1.y.toDouble) + (point1.y.toDouble-point2.y.toDouble)*(point1.x.toDouble - point3.x.toDouble)) *
	        // ((p1.x - p2.x) * (p4.y - p1.y) + (p1.y - p2.y) * (p1.x - p4.x)) < 0#) Then
	        ((point1.x.toDouble - point2.x.toDouble) * ( point4.y.toDouble- point1.y.toDouble)+(point1.y.toDouble -point2.y.toDouble)*(point1.x.toDouble - point4.x.toDouble)) < 0){
	      //  If (((p3.x - p4.x) * (p1.y - p3.y) + (p3.y - p4.y) * (p3.x - p1.x)) * _
	      if(((point3.x.toDouble - point4.x.toDouble) * (point1.y.toDouble - point3.y.toDouble) + (point3.y.toDouble - point4.y.toDouble) * (point3.x.toDouble - point1.x.toDouble)) *
	          // ((p3.x - p4.x) * (p2.y - p3.y) + (p3.y - p4.y) * (p3.x - p2.x)) < 0#) Then
	          ((point3.x.toDouble - point4.x.toDouble) * (point2.y.toDouble - point3.y.toDouble) + (point3.y.toDouble - point4.y.toDouble) * (point3.x.toDouble - point2.x.toDouble)) <0){
	         return true
	      }
	    }
	    
	    false
	  }
  } 
  class Polygon(){
    var lineList:ListBuffer[Line] = ListBuffer.empty[Line]
  } //多角形オブジェクト
  
}

