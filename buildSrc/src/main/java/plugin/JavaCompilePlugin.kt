package plugin

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.android.build.gradle.*

class JavaCompilePlugin: Plugin<Project> {

    override fun apply(project: Project) {

        val androidExtension = project.extensions
            .getByName("android")

        if (androidExtension !is BaseExtension)
            return

        androidExtension.apply {
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            project.tasks
                .withType(KotlinCompile::class.java)
                .configureEach {
                    kotlinOptions {
                        jvmTarget = JavaVersion.VERSION_17.toString()
                    }
                }
        }
    }
}