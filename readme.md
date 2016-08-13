Renamer
===

Simple yet powerful file rename script. 
Rename batch of files as simple text lines.

 
 
This script doesn't much functionality by itself, but instead it 
 allows you to leverage the power of your favorite text editor or IDE.
 

Demo worth a thousand words
---

<img width=600 src=https://cloud.githubusercontent.com/assets/2865203/17642245/08c6d790-60f3-11e6-81a0-472c6f7ea55c.gif>

Requirements
---

* [java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

Usage
---

* Download the latest release
* execute `java -jar path_to_renamer.jar` to rename files in current directory
* follow the interactive instructions

Consider creating [bash alias](http://tldp.org/LDP/abs/html/aliases.html) for 
the renamer. It's more convenient to type `renamer` instead of `java -jar ...`.   


Advanced usage
---

Script supports a single parameter — a file filter in 
[glob](https://en.wikipedia.org/wiki/Glob_(programming)) format.

Example:
```
java -jar renamer.jar "**/*.png"
```


Two things to keep in mind when providing glob parameter:
 
* glob parameter must be enclosed in quotes (`"**"`), as otherwise
    bash supplies actual file names as an argument instead of the pattern
* search becomes recursive (from current directory) 
    and pattern is tested against the absolute file path,
    so in order to match anything pattern must start from the wildcard (`**/*.ext`)


Development
---

* install [sbt](http://www.scala-sbt.org/)
* in project directory:
    * `sbt compile` — to compile project
    * `sbt assembly` — to create a fat jar
