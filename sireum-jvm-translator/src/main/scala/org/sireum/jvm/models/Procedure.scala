package org.sireum.jvm.models

import org.sireum.jvm.util.Util
import scala.tools.asm.Type
import java.util.HashMap
import java.util.ArrayList
import java.util.HashSet

class Procedure(val access:Int,val name:String,val desc:String,val signature: String,val exceptions: Array[String],val owner: Record) extends BaseModel{
    val parameters = new ArrayList[String]()
    val code = new ArrayList[String]()
    val locals = new ArrayList[String]()
    val catchExceptions = new ArrayList[CatchException]()
    
    // Getters for StringTemplate
	val getName = Util.getPilarMethod(owner.getClassName+"."+name)
	val getReturnType = Util.getTypeString(Type.getReturnType(desc).getDescriptor())
	val getCode = code
	val getLocals = locals
	
	val getParameters = {
      var i = 0
      if (!Util.isStatic(access)) {
        parameters.add(owner.getName +" "+Util.getVarName(i)+" @type this")
        i+=1
      }
      for (argument <- Type.getArgumentTypes(desc)) {
        parameters.add(Util.getTypeString(argument.getDescriptor())+" "+Util.getVarName(i))
        i+=1
      }
      parameters
	}
    
    val getAnnotations = {
      annotations.put("Owner", owner.getName)
      annotations.put("Access", Util.getAccessFlag(access, name.equals("<init>")))
      annotations.put("Signature", Util.getFunctionSignature(owner.getClassName, name, desc))
      if (exceptions!=null) {
    	  annotations.put("Throws", (exceptions map (x=>{Util.getPilarName(x)})).mkString(","))
      }
      annotations
    }
    
    def getCatch() = catchExceptions
    def addCatch(typ: String, start: String, end: String, handler: String) = 
      catchExceptions.add(new CatchException(typ, start, end, handler))
    
    override def toString() = {
      getReturnType + getName
    }
    
    class CatchException(typ: String, start: String, end: String, handler: String)  {
      val getTyp = typ
      val getStart = start
      val getEnd = end
      val getHandler = handler
    }
}