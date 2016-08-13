package com.aivean.renamer

import better.files.File

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

/**
  *
  * @author <a href="mailto:ivan.zaytsev@webamg.com">Ivan Zaytsev</a>
  *         2016-08-12
  */
object Main extends App {

  val conf = ArgsParser.parser.parse(args, ArgsParser.Config()).getOrElse {
    System.exit(1)
    null // never happens
  }

  val root = File.currentWorkingDirectory

  val files = (conf.glob match {
    case Some(glob) => root.glob(glob)
    case _ => root.list
  }).filter(_.isRegularFile).toList.sorted(conf.order)

  println("Copy these file names to your favourite text editor:")
  files.foreach(f => println(f.name))

  println("---------------------------")

  def uuid = java.util.UUID.randomUUID.toString

  def getNames: List[(File, String)] = {
    println("Paste the new names in the same order (one per line):")
    val newNames = files.map(f => (f, StdIn.readLine())).filter { case (f, n) => f.name != n }

    Option(newNames.map(_._2).groupBy(identity).filter(_._2.size > 1).keys).filter(_.nonEmpty) match {
      case Some(dups) =>
        System.err.println("Error! Duplicate names found: " + dups.mkString(", "))
        getNames

      case _ =>
        println("---------------------------")
        println("Verify the rename:")

        newNames.map {
          case (f, name) =>
            def trunc(f: File) = f.pathAsString.drop(root.pathAsString.length)
            (trunc(f), trunc(f.parent / name))
        } match {
          case pairs if pairs.nonEmpty =>
            val maxW = pairs.map(_._1.length).max
            pairs.foreach {
              case (oldf, newf) =>
                print(oldf)
                print(" " * ((maxW - oldf.length) max 0))
                println(s" ${conf.arrow} $newf")
            }
          case _ =>
        }

        def confirm: Option[List[(File, String)]] = {
          println("Confirm? [y]es / [n]o / [a]bort ")

          StdIn.readLine().toLowerCase match {
            case "y" | "yes" => Some(newNames)
            case "n" | "no" => None
            case "a" | "abort" =>
              System.err.println("aborted")
              System.exit(0)
              None
            case x =>
              System.err.println(s"Command '$x' not recognized, try once again.")
              confirm
          }
        }

        confirm match {
          case Some(res) => res
          case None => getNames
        }
    }
  }

  val newNames = getNames

  newNames.map {
    case (f, newName) =>
      Try {
        val newF = f.renameTo(uuid)
        (newF, newName)
      }
  } match {
    case all =>
      val errors =
        all.collect {
          case Success((f, name)) => Try(f.renameTo(name))
          case x => x
        }.collect { case Failure(f) => f }

      Option(errors).filter(_.nonEmpty).foreach {
        fs =>
          System.err.println("\nErrors during file renaming:\n")
          fs.foreach(e => System.err.println(e.getMessage))
      }
  }
}
