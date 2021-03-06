object Kafkaconsumerstream {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("https://SparkByExamples.com")
      .getOrCreate()

    val df = spark
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("subscribe", "text_topic")
      .load()

    df.printSchema()

    val df2 = df.selectExpr("CAST(key AS STRING)",
      "CAST(value AS STRING)","topic")
    df2.show(false)
    df2.write.format("org.apache.spark.sql.cassandra").options(Map( "table" -> "words_copy", "keyspace" -> "test")).save()
  }

}

