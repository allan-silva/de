package firecalls

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.{StringType, IntegerType, BooleanType, FloatType}

// sbt package
// spark-submit --class=firecalls.Firecalls target/scala-2.12/firecalls_2.12-1.0.jar ../../datasets/sf-fire-calls.csv
object Firecalls {
  def main(args: Array[String]): Unit = {
    if (args.length <= 1) {
      println("No input file found.")
      System.exit(1)
    }
    
    val inputFile = args(0)

    val spark = SparkSession.builder
      .appName("Firecalls")
      .getOrCreate()

    val fireDF = spark.
      read
      .schema(originSchema())
      .option("header", true)
      .csv(inputFile)

    fireDF.write.format("parquet").save("fire-calls")
    // df.write.parquet("fire-calls.parquet") //shortway

    fireDF.write.format("parquet").saveAsTable("firecalls_table")
  }


  def originSchema(): StructType =
    StructType(
      Array(
        StructField("CallNumber", IntegerType, true),
        StructField("UnitID", StringType, true),
        StructField("IncidentNumber", IntegerType, true),
        StructField("CallType", StringType, true),
        StructField("CallDate", StringType, true),
        StructField("WatchDate", StringType, true),
        StructField("CallFinalDisposition", StringType, true),
        StructField("AvailableDtTm", StringType, true),
        StructField("Address", StringType, true),
        StructField("City", StringType, true),
        StructField("Zipcode", StringType, true),
        StructField("Battalion", StringType, true),
        StructField("StationArea", StringType, true),
        StructField("Box", StringType, true),
        StructField("OriginalPriority", StringType, true),
        StructField("Priority", StringType, true),
        StructField("FinalPriority", IntegerType, true),
        StructField("ALSUnit", BooleanType, true),
        StructField("CallTypegroup", StringType, true),
        StructField("NumAlarms", IntegerType, true),
        StructField("UnitType", StringType, true),
        StructField("UnitSequenceInCallDispatch", IntegerType, true),
        StructField("FirePreventionDistrict", StringType, true),
        StructField("SupervisorDistrict", StringType, true),
        StructField("Neighborhood", StringType, true),
        StructField("Location", StringType, true),
        StructField("RowID", StringType, true),
        StructField("Delay", FloatType, true),
      )
    )
}
