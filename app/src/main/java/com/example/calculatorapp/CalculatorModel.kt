package com.example.calculatorapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


fun operation(nums:MutableList<String>, opr:MutableList<String>): String{
    val cpyNums=nums
    val cpyOpr=opr
    if(cpyNums.last()=="")cpyNums.removeLast()
    if(cpyOpr.last()=="") cpyOpr.removeLast()

    var rtn = ""

    while(cpyOpr.isNotEmpty())
    {
        if ("/" in cpyOpr){
            rtn = (cpyNums[cpyOpr.indexOf("/")].toDouble() / cpyNums[cpyOpr.indexOf("/") + 1].toDouble()).toString()
            cpyNums.removeAt(cpyOpr.indexOf("/"))
            cpyNums.removeAt(cpyOpr.indexOf("/"))
            cpyNums.add(cpyOpr.indexOf("/"), rtn)
            cpyOpr.removeAt(cpyOpr.indexOf("/"))

        }else if("x" in cpyOpr){
            rtn = (cpyNums[cpyOpr.indexOf("x")].toDouble() * cpyNums[cpyOpr.indexOf("x") + 1].toDouble()).toString()
            cpyNums.removeAt(cpyOpr.indexOf("x"))
            cpyNums.removeAt(cpyOpr.indexOf("x"))
            cpyNums.add(cpyOpr.indexOf("x"), rtn)
            cpyOpr.removeAt(cpyOpr.indexOf("x"))

        }else if("-" in cpyOpr){
            rtn = (cpyNums[cpyOpr.indexOf("-")].toDouble() - cpyNums[cpyOpr.indexOf("-") + 1].toDouble()).toString()
            cpyNums.removeAt(cpyOpr.indexOf("-"))
            cpyNums.removeAt(cpyOpr.indexOf("-"))
            cpyNums.add(cpyOpr.indexOf("-"), rtn)
            cpyOpr.removeAt(cpyOpr.indexOf("-"))

        }else{
            rtn = (cpyNums[cpyOpr.indexOf("+")].toDouble() + cpyNums[cpyOpr.indexOf("+") + 1].toDouble()).toString()
            cpyNums.removeAt(cpyOpr.indexOf("+"))
            cpyNums.removeAt(cpyOpr.indexOf("+"))
            cpyNums.add(cpyOpr.indexOf("+"), rtn)
            cpyOpr.removeAt(cpyOpr.indexOf("+"))

        }

    }
    val split = rtn.split(".")

    if(split[1].toDouble() == 0.0) return split[0]
    else return rtn
}


fun main(){

    //ckdDisplay.last().toString() in signs

    val numList = mutableListOf("2","3")
    val opr = mutableListOf("+","-","x")

    val signs = listOf("/","x","-","+")
    opr.removeLast()
    println(opr)



    //println(Operation(numList,opr))

}



