package com.aivean.renamer

import better.files.File
import scopt.OptionParser

/**
  *
  * @author <a href="mailto:ivan.zaytsev@webamg.com">Ivan Zaytsev</a>
  *         2016-08-13
  */
object ArgsParser {


  case class Config(glob: Option[String] = None, arrow: String = "->", order: File.Order = File.Order.byName)


  private object Orderings {
    val map = Map(
      "name (default)" -> File.Order.byName,
      "extention" -> Ordering.by((f: File) => (f.extension, f.name)),
      "modification time" -> File.Order.byModificationTime,
      "size" -> File.Order.bySize
    )

    val list = map.keys.map(" " * 5 + "* " + _ + "\n").mkString

    def find(prefix: String) = map.find(_._1.startsWith(prefix.toLowerCase)).map(_._2)
  }

  val parser = new OptionParser[Config]("renamer") {

    opt[String]('o', "order").optional()
      .validate(x => Orderings.find(x).map(_ => success)
        .getOrElse(failure("Ordering not found. Use one of these values (or prefix):\n" + Orderings.list

        ))
      ).action((x, conf) => conf.copy(order = Orderings.find(x).get))
      .text("File ordering (default is by name). " +
        "Use one of these values (or prefix):\n" + Orderings.list
      )

    opt[Unit]("reverse").optional()
      .action((r, conf) => conf.copy(order = conf.order.reverse))
      .text("Reverse ordering")

    opt[String]('a', "arrow").optional()
      .action((x, conf) => conf.copy(arrow = x))
      .text("Arrow symbol. Default is '->'")

    help("help").text("print this usage text")

    arg[String]("glob").unbounded().optional()
      .action((g, conf) => conf.copy(glob = Some(g)))
      .text("optional glob pattern (enclose in quotes)")
  }
}
