package org.hdm.core.executor

import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory
import org.hdm.core.context.{HDMContext, HDMServerContext, HDMAppContext, AppContext}
import org.hdm.core.server.HDMEngine
import org.junit.{After, Before}

import scala.concurrent.duration.Duration

/**
 * Created by Tiantian on 2014/12/19.
 */
trait ClusterTestSuite {

  val testMasterConf = ConfigFactory.parseString("""
		akka{
		  actor {
		    provider = "akka.remote.RemoteActorRefProvider"
		    serialize-messages = off
		    serializers {
		        java = "akka.serialization.JavaSerializer"
		        proto = "akka.remote.serialization.ProtobufSerializer"
		     }
		   }
		  remote {
		    enabled-transports = ["akka.remote.netty.tcp"]
		    netty.tcp {
		        hostname = "127.0.0.1"
		  		port = "8999"
		    }
		  }
		}
                                                 """)

  val testSlaveConf = ConfigFactory.parseString("""
		akka{
		  actor {
		    provider = "akka.remote.RemoteActorRefProvider"
		    serialize-messages = off
		    serializers {
		        java = "akka.serialization.JavaSerializer"
		        proto = "akka.remote.serialization.ProtobufSerializer"
		     }
		   }
		  remote {
		    enabled-transports = ["akka.remote.netty.tcp"]
		    netty.tcp {
		        hostname = "127.0.0.1"
    		   	port = "10010"
		    }
		  }
		}
                                                """)

  implicit val maxWaitResponseTime = Duration(20, TimeUnit.SECONDS)

  val hDMContext = HDMServerContext.defaultContext

  val appContext = new AppContext()

  hDMContext.clusterExecution.set(false)

  implicit val hDMEntry = HDMEngine()


}
