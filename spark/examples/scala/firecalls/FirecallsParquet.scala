package firecalls

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{
  avg,
  min,
  max,
  sum,
  col,
  countDistinct,
  to_timestamp,
  year,
  desc,
  expr
}

// sbt package
// spark-submit --class=firecalls.FireCallsParquet target/scala-2.12/firecalls_2.12-1.0.jar fire-calls
object FireCallsParquet {
  def main(args: Array[String]): Unit = {
    if (args.length <= 0) {
      println("No input file found.")
      System.exit(1)
    }

    val inputFile = args(0)

    val spark = SparkSession.builder
      .appName("FireCallsParquet")
      .getOrCreate()
    import spark.implicits._

    val fireCallsDF = spark.read.parquet(inputFile)

    // Projections and Filters
    fireCallsDF
      .select("IncidentNumber", "AvailableDtTm", "CallType")
      .where(col("CallType") =!= "Medical Incident")
      .show(5, true)

    // Count Distinct call types
    fireCallsDF
      .select("CallType")
      .where(col("CallType").isNotNull)
      .agg(countDistinct("CallType") as "DistinctCallTypes")
      .show(false)

    // List Distinct call types
    fireCallsDF
      .select("CallType")
      .where(col("CallType").isNotNull)
      .distinct()
      .show(5, false)

    // Rename Column
    val newDF = fireCallsDF.withColumnRenamed("Delay", "ResponseDelayedInMins")
    newDF
      .select("ResponseDelayedInMins")
      .where($"ResponseDelayedInMins" > 5)
      .show(5, false)

    // Change data types. Strings to timestamp
    val newDFTS = fireCallsDF
      .withColumn("IncidentDate", to_timestamp(col("CallDate"), "MM/dd/yyyy"))
      .drop("CallDate")
      .withColumn("OnWatchDate", to_timestamp(col("WatchDate"), "MM/dd/yyyy"))
      .drop("WatchDate")
      .withColumn(
        "AvailableDtTS",
        to_timestamp(col("AvailableDtTm"), "MM/dd/yyyy hh:mm:ss a")
      )
      .drop("AvailableDtTm")

    newDFTS.select("IncidentDate", "OnWatchDate", "AvailableDtTS").show(5)
    newDFTS.printSchema()

    // Perform queries on new data columns
    newDFTS
      .select(year($"IncidentDate"))
      .distinct()
      .orderBy(year($"IncidentDate").desc)
      // .orderBy(desc("year(IncidentDate)"))
      .show()

    // Aggregations
    newDFTS
      .select("CallType")
      .where(col("CallType").isNotNull)
      .groupBy("CallType")
      .count()
      .orderBy(desc("count"))
      .show(10, false)

    newDFTS
      .select(sum("NumAlarms"), avg("Delay"), min("Delay"), max("Delay"))
      .show()
    newDFTS
      .select("CallNumber", "CallType", "IncidentNumber", "IncidentDate", "NumAlarms", "Delay")
      .where($"Delay" >= 1844)
      .show()
  }
}
