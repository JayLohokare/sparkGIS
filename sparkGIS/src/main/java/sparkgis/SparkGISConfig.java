package sparkgis;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class SparkGISConfig
{
    // // Jar path
    // public static String jarPath;
    // HDFS configurations
    public static String hdfsNameNodeIP;
    public static String hdfsAlgoData; // OPTIONAL
    public static String hdfsHMResults; // OPTIONAL
    // MongoDB configurations
    // public static String mongoHost;
    // public static int mongoPort;
    // public static String input_mongoDB;
    // public static String input_collection_name;
    // public static String output_mongoDB;
    // public static String output_collection_name;
    // public static String temp_mongoDB;
    // public static String temp_collection_name;

    // public static int partition_size  ;

    static{
	InputStream inputStream = null;
	try {
	    Properties prop = new Properties();
	    inputStream =
	    	SparkGISConfig.class.getClassLoader().getResourceAsStream("sparkgis.properties");
	    prop.load(inputStream);

	    // HDFS configurations
	    hdfsNameNodeIP = prop.getProperty("hdfs-name-node-ip");
	    hdfsAlgoData = prop.getProperty("hdfs-algo-data");
	    hdfsHMResults = prop.getProperty("hdfs-hm-results");
	    // // MongoDB configurations
	    // mongoHost = prop.getProperty("mongo-host");
	    // mongoPort = Integer.parseInt(prop.getProperty("mongo-port"));

	    // temp_mongoDB = prop.getProperty("mongo-tempdb");
	    // temp_collection_name = prop.getProperty("mongo-tempcollection");
	    inputStream.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(-1);
	} finally{
	    try{
		inputStream.close();
	    }catch(Exception e){
		System.out.println("No stream to close");
	    }
	}
    }
}
