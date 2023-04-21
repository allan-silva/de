package firecalls

import org.apache.spark.sql.SparkSession

object FireCallsSchemaInfered {
  def main(args: Array[String]): Unit = {
    var spark = SparkSession
      .builder
      .appName("FireCallsSchemaInfered")
      .getOrCreate()

    var sampleDF = spark
      .read
      .option("samplingRatio", 0.001)
      .option("header", true) // primeira linha do arquivo é o header
      .csv("../../datasets/sf-fire-calls.csv")

    sampleDF.printSchema()
  }
}
