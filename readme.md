Renamer
===

Simple yet powerful file rename script. 
Rename batch of files as simple text lines.

 
 
This script doesn't provide much functionality by itself, but instead it
leverages the power of your favorite text editor or IDE.
 

Demo worth a thousand words
---

<img width=600 src=https://cloud.githubusercontent.com/assets/2865203/17645373/12d29ce0-6159-11e6-98f7-cf1bddd24e49.gif>

Requirements
---

* [java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

Usage
---

* Download the [latest release](https://github.com/Aivean/renamer/releases/tag/0.1.0)
* execute `java -jar path_to_renamer.jar --help` to see the usage:
    ```
    ~ $ renamer --help
    Usage: renamer [options] [glob]
    
      -o, --order <value>  File ordering (default is by name). Use one of these values (or prefix):
         * name (default)
         * extention
         * modification time
         * size
    
      --reverse            Reverse ordering
      -a, --arrow <value>  Arrow symbol. Default is '->'
      --help               print this usage text
      glob                 optional glob pattern (enclose in quotes)
    ```
* execute `java -jar path_to_renamer.jar` (without any args) 
    to rename files in current directory
* follow the interactive instructions

Consider creating [bash alias](http://tldp.org/LDP/abs/html/aliases.html) for 
the renamer. It's more convenient to type `renamer` instead of `java -jar ...`.   


Advanced usage
---

Script supports several parameters:
 
 * `--order` allows to override sort order of the files
 * `--reverse` reverse the selected file ordering (see `--order`)
 * `--arrow` use alternative arrow symbol (make output look more fancy)
 * trailing argument is a file filter in 
[glob](https://en.wikipedia.org/wiki/Glob_(programming)) format. Example:
    
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


Credit
---

* [Scala](http://www.scala-lang.org/) ❤️
* [Scopt](https://github.com/scopt/scopt) — simple scala command line options parsing
* [Better files](https://github.com/pathikrit/better-files) — Simple, safe and intuitive Scala I/O