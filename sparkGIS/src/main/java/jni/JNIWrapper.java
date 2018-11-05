package jni;
import java.util.List;
import java.util.ArrayList;

public class JNIWrapper {    
    
    // Join Map
    // public static native String[] partitionMapperJoin(
    // 						      String line, 
    // 						      int geomID,
    // 						      String[] partfile
    // 						      );
    
    /**
     * Called for native spatial join calculation
     * Contains additional statistics like jaccard and dice indices for intersecting 
     * objects in result. These statistics can be used for heatmap computations.
     * NOTE: Tile-Dice statistics not included in result becasue tile-dice cannot be
     *       computed object wise. Instead it has to be calculated per tile.
     *       Use 'resqueTileDice' for tile-dice computaion instead
     * Corresponding c++ function calls in JNI and their functionalities are as follows
     *       gis.cpp             Java_jni_JNIWrapper_resque()
     *             - For all objects in this tile (data), resq.populate() is called
     *             - Performs the actual join operation using resq.join_bucket()
     *       native/resque.cpp   populate()
     *             - Extracts geometries from input string and stores two seperate 
     *               lists of geometries belonging to algorithm-1 and algorithm-2 respectively
     *       native/resque.cpp   join_bucket()
     *             - Performs the actual join operation on the two sets of geometries for this tile
     *             - Returns list of hits i.e. objects fulfilling join criteria
     *             - Also appends statistics (Jaccard, Dice) to the results
     *       include/statistics.h
     *             - Jaccard can Dice calculation
     *
     * @param data      All spatial objects belonging to this tile
     *                  String: tile-id TAB algo-id TAB geometry-information
     * @param predicate Spatial join predicate such as intersects, within etc. Defined in sparkgis.enums.Predicate.java
     * @param geomID1   Geometry index of algorithm-1 in input string
     * @param geomID2   Geometry index of algorithm-2 in input string
     * @return Spatial join results based on predicate
     *         String: Object-1 TAB Object-2 TAB JaccardIndex TAB DiceIndex
     */
    public static // synchronized
	native String[] resqueSPJ(
			       String[] data, 
			       int predicate, 
			       int geomID1, 
			       int geomID2
			       );
    /**
     * Called for heatmap calculation involving tile-dice
     * Tile-Dice: For all object in given tile, calculate
     *            (intersection_area/union_area) 
     * Unlike Jaccard and Dice, all calculation done natively by RESQUE
     */
    public native double resqueTileDice(
					String[] data, 
					int predicate, 
					int geomID1, 
					int geomID2
					);

    public static // synchronized
	native String[] resqueKNN(
				  String[] data, 
				  int predicate,
				  int k,
				  int geomID1, 
				  int geomID2
				  );
    
    // Method-2
    /* 
     * Alternate approach 
     * Initialize Resque class object and return back its pointer. Use class pointer to populate data
     * For each string in java, call a jni function to populate the data in Resque class variables
     * when key changes call join_bucket() similar to original Hadoop-GIS
     * Disadvantage: Too many JNI calls
     */
    // public static native long initResque(String predicate, int geomID1, int geomID2);
    // public static synchronized native String[] resque2(long objPtr, String line);
    

    static {
	try{
	    System.loadLibrary("gis");
	}catch(Exception e){
	    System.out.println("Error during static initialization");
	}
    }        
    
    public JNIWrapper(){
	// try{
	//     System.loadLibrary("gis");
	// }catch(Exception e){
	//     System.out.println("Error during static initialization");
	// }
    }
}

//public static synchronized native long buildIndex(String[] partfile, int geomid);    
//public static native void nativeFoo();    
//public static native void pmjGarbageCollection(long idxPtr);
// public void print() {
// 	//nativeFoo();
// 	List<String> param = new ArrayList<String>();
// 	param.add("Hi");
// 	param.add("There");
// 	String[] arg = new String[param.size()];
// 	buildIndex(param.toArray(arg));
// }


// /* DO NOT EDIT THIS FILE - it is machine generated */
// #include <jni.h>
// /* Header for class jni_JNIWrapper */

// #ifndef _Included_jni_JNIWrapper
// #define _Included_jni_JNIWrapper
// #ifdef __cplusplus
// extern "C" {
// #endif
// /*
//  * Class:     jni_JNIWrapper
//  * Method:    partitionMapperJoin
//  * Signature: (Ljava/lang/String;I[Ljava/lang/String;)[Ljava/lang/String;
//  */
// JNIEXPORT jobjectArray JNICALL Java_jni_JNIWrapper_partitionMapperJoin
//   (JNIEnv *, jclass, jstring, jint, jobjectArray);

// /*
//  * Class:     jni_JNIWrapper
//  * Method:    resque
//  * Signature: ([Ljava/lang/String;Ljava/lang/String;II)[Ljava/lang/String;
//  */
// JNIEXPORT jobjectArray JNICALL Java_jni_JNIWrapper_resque
//   (JNIEnv *, jclass, jobjectArray, jstring, jint, jint);

// #ifdef __cplusplus
// }
// #endif
// #endif

// /********************** CODE BACKUP *******************/

// /* /\* */
// /*  * Class:     jni_JNIWrapper */
// /*  * Method:    pmjGarbageCollection */
// /*  * Signature: (J)V */
// /*  *\/ */
// /* JNIEXPORT void JNICALL Java_jni_JNIWrapper_pmjGarbageCollection */
// /*   (JNIEnv *, jclass, jlong); */
// /* /\* */
// /*  * Class:     jni_JNIWrapper */
// /*  * Method:    nativeFoo */
// /*  * Signature: ()V */
// /*  *\/ */
// /* JNIEXPORT void JNICALL Java_jni_JNIWrapper_nativeFoo */
// /*   (JNIEnv *, jclass); */
