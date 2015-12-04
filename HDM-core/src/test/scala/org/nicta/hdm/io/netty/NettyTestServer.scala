package org.nicta.hdm.io.netty

import org.nicta.wdy.hdm.executor.HDMContext
import org.nicta.wdy.hdm.io.netty.NettyBlockServer
import org.nicta.wdy.hdm.serializer.JavaSerializer
import org.nicta.wdy.hdm.storage.{Block, HDMBlockManager}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by tiantian on 27/05/15.
 */
object NettyTestServer {

  val blkSize = 160*8

  val serializer = new JavaSerializer(HDMContext.defaultConf).newInstance()
  val blockServer = new NettyBlockServer(9091,
    4,
    HDMBlockManager(),
    serializer)


  val text =
    """
        this is a word count text
        this is line 4
        this is line 5
        this is line 6
        this is line 7
    """.split("\\s+")

  val data = ArrayBuffer.empty[String] ++= text

  val data2 = ArrayBuffer.fill[(String, List[Double])](10000){
    ("0xb601998146d35e06", List(1D))
  }


  def main(args:Array[String]): Unit ={
    HDMBlockManager.initBlockServer()
    for(i <- 0 until blkSize){
      val id = s"blk-00$i"
      HDMBlockManager().add(id, Block(id, data2))
    }

  }
}
