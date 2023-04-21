package boilerplate


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

// sbt package
// spark-submit --class=boilerplate.Boilerplate target/scala-2.12/boilerplate_2.12-1.0.jar path-to-file
object Boilerplate {
  def main(args: Array[String]): Unit = {
    if (args.length <= 0) {
      println("No input file found.")
      System.exit(1)
    }

    val inputFile = args(0)

    println(s"OK ${inputFile}")
  }
}
