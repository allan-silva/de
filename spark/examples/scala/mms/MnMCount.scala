package mms

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import java.nio.file.{FileSystems, Files}
import scala.collection.JavaConverters._


object MnMCount {
    def main(args: Array[String]) {
        val spark = SparkSession
            .builder
            .appName("MnMCount")
            .getOrCreate()

        val mnmFile = "file:///spark_shared/input_data/mnm_dataset.csv"

        val mnmDF = spark.read.format("csv")
            .option("header", "true")
            .option("inferSchema", "true")
            .load(mnmFile)

        val countMnMDF = mnmDF
            .select("State", "Color", "Count")
            .groupBy("State", "Color")
            .sum("Count")
            .orderBy(desc("sum(Count)"))

        countMnMDF
            .write
            .option("header", "true")
            .csv("file:///spark_shared/output_data/mnm_result.csv")

        spark.stop()
    }
}
