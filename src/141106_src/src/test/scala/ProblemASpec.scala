import org.scalatest.FunSpec

class ProblemASpec extends FunSpec {
  describe("ProblemA") {
    describe("#readTextFileIntoList") {
      val filePath = ""
      it("should return list") {
        val response = ProblemA.readTextFileIntoList()
        assert(response.isInstanceOf[List[String]])
      }
    }
  }
}
