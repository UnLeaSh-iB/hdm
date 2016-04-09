package org.nicta.wdy.hdm.server.provenance

import org.nicta.wdy.hdm.model.{HDMPoJo, AbstractHDM, HDM}

/**
 * Created by tiantian on 3/03/16.
 */

trait Provenance extends Serializable

case class ApplicationTrace (name:String,
                             version:String,
                             author:String,
                             createTime:Long,
                             dependencies:Seq[String]) extends Provenance {
  def id = s"$name#$version"

}

case class ExecutionInstance(exeId: String,
                             appName: String,
                             version: String,
                             source: AbstractHDM[_],
                             logicalPlan:Seq[AbstractHDM[_]] = Seq.empty[AbstractHDM[_]],
                             logicalPlanOpt: Seq[AbstractHDM[_]] = Seq.empty[AbstractHDM[_]],
                             physicalPlan: Seq[AbstractHDM[_]] = Seq.empty[AbstractHDM[_]],
                             state: String = "REGISTERED") extends Provenance

case class ExecutionTrace(taskId:String,
                          appName:String,
                          version:String,
                          instanceID:String,
                          funcName:String,
                          function:String,
                          inputPath:Seq[String],
                          outputPath:Seq[String],
                          location:String,
                          dependency:String,
                          partitioner:String,
                          startTime:Long,
                          endTime:Long,
                          status:String) extends Provenance {

  def uuid = s"$appName#$version#$instanceID#$taskId"

}


case class ExecutionDAG(nodes:Seq[ExecutionTrace],
                        links:Seq[(String, String)]) extends Provenance