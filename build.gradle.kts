/*
LGPL v3.0
Copyright (C) 2019 Pedro Agfullo Soliveres
p.agullo.soliveres@gmail.com

KStructs is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.

KStructs is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val supportEclipseJUnitWorkaround = false
val supportEclipseJUnitRunners = true

group = "com.softwarementors.${rootProject.name}"
version = "0.2-alpha-SNAPSHOT"

val log4j2Ver = "2.11.2"
val log4jKotlinApiVer = "1.0.0"
val junitVer = "5.4.0"
val junitPlatformLauncherVer = "1.4.0"
val kotlinJunitTestRunnerVer = "3.3.2"

val kpointersVer = "alpha-1-SNAPSHOT"

buildscript {
   repositories {   
      jcenter()
      mavenCentral()
   }
}

repositories {
   mavenLocal() // To be able to access other project components that are 
   jcenter()
   mavenCentral()
}

plugins {
   val kotlinVer = "1.3.41"

   `maven-publish`
   java
   application
   id("eclipse")   
   kotlin("jvm") version kotlinVer
   kotlin("kapt") version kotlinVer
}


val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
   repositories {
      mavenLocal()
   }
   publications {
      register("mavenJava", MavenPublication::class) {
         from(components["java"])
         artifact(sourcesJar.get())
      }
   }
}

dependencies {
   implementation(kotlin("stdlib-jdk8"))
   implementation(kotlin("reflect"))
   implementation(kotlin("stdlib-common"))

   // implementation("org.apache.logging.log4j:log4j-api:$log4j2Ver")
   // implementation("org.apache.logging.log4j:log4j-api-kotlin:$log4jKotlinApiVer")

   // ***********************************************
   // *** Testing
   testCompile("org.junit.jupiter:junit-jupiter-api:$junitVer")
   testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVer")
   testRuntimeOnly("io.kotlintest:kotlintest-runner-junit5:$kotlinJunitTestRunnerVer")
   if( supportEclipseJUnitWorkaround) {
      // This might not be necessary, but we are having trouble with Eclipse + JUnit
      testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVer") 
   }
   if( supportEclipseJUnitRunners) {
      // Eclipse needs this -maybe for JUnit?
      testRuntimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Ver")
   }

   implementation("com.softwarementors.kpointers:kpointers:$kpointersVer")
}

if( supportEclipseJUnitWorkaround ) {
   eclipse {
      classpath {
         file {
            // This might not be necessary, but we are having trouble with Gradle + JUnit in Eclipse :(
            containers( "org.eclipse.jdt.junit.JUNIT_CONTAINER/5")
         }
      }
   }
}

// List of examples: we create a runXxx task to run every example
// and set the default run task to run the first example in the list
// See usage of exampleMainClass()
val examples = listOf("Simple", "Offsets")   

fun exampleMainClass( exampleName : String) : String {
   return "com.softwarementors.kstructs.examples.${exampleName}ExampleKt"
}

application {
   if( examples.size > 0 ) {
      // First example in examples list will be the one to be run by 'run' taks 
      mainClassName = exampleMainClass( examples[0])
   }
}

tasks {
   // Generate a 'runXxx' for every 'xxx' example class
   examples.forEach {
      val example = it
      register<JavaExec>("run${example}Example") {
         group = "Application"
         val mainClass = exampleMainClass(example)
         description = "Run example class $mainClass"
         classpath = sourceSets["main"].runtimeClasspath
         main = mainClass
      }
   }
   
   withType<KotlinCompile> {
      // Add arg to remove the '"inline classes" is experimental' warning
      var args = mutableListOf("-XXLanguage:+InlineClasses")
      args.addAll(kotlinOptions.freeCompilerArgs)
      kotlinOptions.freeCompilerArgs = args
   }

   withType<Wrapper> {
      gradleVersion = "5.5.1"
   }

   withType(Test::class) {
      useJUnitPlatform() // This is needed, just adding the JUnit jars is not enough
      testLogging {
         // outputs.upToDateWhen {true} // Forces test execution even if last time they worked *and* project is up to date since then
         events("PASSED", "FAILED", "SKIPPED"
                // ,"STARTED"       // Show log when test starts
                // ,"STANDARD_ERROR" // Show System.err output inside tests
                // ,"STANDARD_OUT"    // Show System.out output inside tests
         )
         // Must follow the events property assignment!
         showStandardStreams = false
         showStackTraces = true
         exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL // FULL for complete stack trace
      }
   }
}