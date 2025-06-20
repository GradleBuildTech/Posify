//
//import com.android.build.api.dsl.CommonExtension
//import org.gradle.api.Project
//import java.io.File
//import java.util.*
//
//fun Project.applyFlavorConfig(
//    androidExtension: CommonExtension<*, *, *, *, *>,
//    flavorDimension: String = "env"
//) {
//    androidExtension.apply {
//        flavorDimensions += flavorDimension
//
//        productFlavors {
//            Flavor.LIST.forEach { flavorName ->
//                create(flavorName) {
//                    dimension = flavorDimension
//
//                    val props = loadProperties(flavorName)
//                    props.forEach { key, value ->
//                        buildConfigField("String", key, "\"$value\"")
//                    }
//                }
//            }
//        }
//    }
//}
//
//private fun Project.loadProperties(flavor: String): Properties {
//    val props = Properties()
//    val file = File(rootDir, "$flavor.properties")
//    if (!file.exists()) error("Missing $flavor.properties in root dir")
//    props.load(file.inputStream())
//    return props
//}
