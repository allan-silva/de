package blogs

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Row

// spark-submit --class blogs.Example3_7 target/scala-2.12/blogs_2.12-1.0.jar ../../datasets/blogs.json

object Example3_7 {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("Example3_7")
      .getOrCreate()
    import spark.implicits._

    if(args.length <= 0) {
      println("No input json file found")
      System.exit(1)
    }

    val jsonFile = args(0)

    val schema = StructType(
      Array(
        StructField("Id", IntegerType, false),
        StructField("First", StringType, false),
        StructField("Last", StringType, false),
        StructField("Url", StringType, false),
        StructField("Published", StringType, false),
        StructField("Hits", IntegerType, false),
        StructField("Campaings", ArrayType(StringType), false)
      )
    )

    val blogsDF = spark.read.schema(schema).json(jsonFile)

    blogsDF.show(false)

    println(blogsDF.printSchema)
    println(blogsDF.schema)

    // Compute with expression
    blogsDF.select(expr("Hits * 2")).show(2)

    //Compute with column type
    blogsDF.select(col("Hits") * 2).show(2)

    //Create new column based on expression value
    blogsDF.withColumn("Big Hitters", (expr("Hits > 10000"))).show()

    // Next statements results in same value
    blogsDF.select(col("Hits")).show(2)
    blogsDF.select(expr("Hits")).show(2)
    blogsDF.select("Hits").show(2 )

    blogsDF
      .withColumn(
        "AuthorId",
        (concat(expr("First"), col("Last"), expr("Id")))
      )
      .select(col("AuthorId"))
      .show(4)

    //Sorting
    blogsDF.sort(col("Id").desc).show()
    blogsDF.sort($"Id".desc).show()

    val blogRow = Row(42, "Allan", "Silva")
    println(blogRow(1))

    val rows = Seq(("Chico", "Doguíneo"), ("Paloma", "Doguíneo"))
    val petsDF = rows.toDF("Nome", "Espécie")
    petsDF.show()

  }
}