package com.example.calculatorapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun CalculatorView(modifier: Modifier) {

    var singleNum by remember{ mutableStateOf("") }
    var ckdDisplay by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var nums by remember { mutableStateOf(mutableListOf<String>()) }
    val opr by remember { mutableStateOf(mutableListOf<String>()) }
    var afterOpt by remember { mutableStateOf(false) }
    var pointTyped by remember{ mutableStateOf(false) }
    var negativeAllowed by remember { mutableStateOf(true) }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val signs = listOf("/","x","-","+")
    val numbers = listOf("0","1","2","3","4","5","6","7","8","9")

    fun liveResultUpdater(){
        if(opr.isNotEmpty() && nums.size>1 && ckdDisplay.last().toString() in numbers){
            nums = nums.filter { it != "-" }.toMutableList()
            result = operation(nums.toMutableList(),opr.toMutableList())
        }
    }

    fun onClickForNumbers(it:String){

        ckdDisplay += it
        singleNum += it
        if(nums.isNotEmpty() && !afterOpt)  nums.removeLast()
        afterOpt=false
        if(ckdDisplay.length == 2 && ckdDisplay.first().toString() == "-") nums.removeFirst()
        if(it=="-") {
            afterOpt = true
            negativeAllowed = false
        }

        nums.add(singleNum)
        if(it != "." && it != "-") liveResultUpdater()

        coroutineScope.launch {
            lazyListState.animateScrollToItem(index = ckdDisplay.length)
        }
        result = "$singleNum $nums $opr"
    }

    fun onClickForOpt(it:String){
        if(ckdDisplay != "" && nums.isNotEmpty()  && nums.last().toString() != "")
            if(afterOpt && ckdDisplay.length != 1){
                ckdDisplay=ckdDisplay.substring(0,ckdDisplay.length-1)
                opr.removeLast()
                ckdDisplay+=it
                singleNum=""
                opr.add(it)
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(index = ckdDisplay.length)
                }
                pointTyped=false
            }else if(!afterOpt){
                ckdDisplay+=it
                singleNum=""
                opr.add(it)
                afterOpt=true
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(index = ckdDisplay.length)
                }
                pointTyped=false
            }
        /*
        if(ckdDisplay == "" && it == "-"){
            ckdDisplay+=it
            singleNum+=it
            afterOpt=true
            nums.add(singleNum)
            coroutineScope.launch {
                lazyListState.animateScrollToItem(index = ckdDisplay.length)
            }
            negativeAllowed=false
        }
         */
        result = "$singleNum $nums $opr"
    }

    /*
    fun autoTextSizeMinimizer(it:String) : TextUnit{
        if(it.length<10) return 48.sp
        else if(it.length<15) return 36.sp
        else return 28.sp
    }*/

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
            ,
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    state = lazyListState
                ) {
                    items(ckdDisplay.toMutableList()){
                        Text(text=it.toString(),
                            fontSize=48.sp,
                            color = Color.Black
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    items(result.toMutableList()){
                        Text(text=it.toString(),
                            fontSize=36.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }



        }

        Row(modifier = Modifier
            .fillMaxSize()
            .weight(2f)) {

            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Red)
            ) {

                val numModifier= Modifier
                    .background(Color.DarkGray)
                    .weight(1f)
                    .fillMaxSize()

                ButtonItem(modifier = numModifier , num = "7", onClick = {
                    onClickForNumbers(it)
                })
                ButtonItem(modifier = numModifier , num = "4", onClick = {
                    onClickForNumbers(it)
                })
                ButtonItem(modifier = numModifier , num = "1", onClick = {
                    onClickForNumbers(it)
                })
                ButtonItem(modifier = numModifier , num = ".", onClick = {
                    if(!pointTyped){
                        onClickForNumbers(it)
                        pointTyped = true
                    }
                })
            }

            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Red)
            ) {

                val modifier1= Modifier
                    .background(Color.DarkGray)
                    .weight(1f)
                    .fillMaxSize()

                ButtonItem(modifier = modifier1 , num = "8", onClick = {
                    onClickForNumbers(it)
                })
                ButtonItem(modifier = modifier1 , num = "5", onClick = {
                    onClickForNumbers(it)
                })
                ButtonItem(modifier = modifier1 , num = "2", onClick = {
                    onClickForNumbers(it)
                })
                ButtonItem(modifier = modifier1 , num = "0", onClick = {
                    onClickForNumbers(it)
                })

            }

            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Red)
            ) {

                val modifier1= Modifier
                    .background(Color.DarkGray)
                    .weight(1f)
                    .fillMaxSize()

                ButtonItem(modifier = modifier1 , num = "9", onClick = {
                    onClickForNumbers(it)
                })
                ButtonItem(modifier = modifier1 , num = "6", onClick = {
                    onClickForNumbers(it)
                })
                ButtonItem(modifier = modifier1 , num = "3", onClick = {
                    onClickForNumbers(it)
                })

                ButtonItem(modifier = modifier1 , num = "=", onClick = {
                    if(ckdDisplay.isNotEmpty() &&
                        ckdDisplay.last().toString() !in signs && opr.isNotEmpty() &&
                        ckdDisplay.last().toString()!="."){
                        ckdDisplay = operation(nums.toMutableList(),opr.toMutableList())
                        opr.clear()
                        nums.clear()
                        singleNum = ""
                        result= ""
                        afterOpt = false
                        nums.add(ckdDisplay)
                        singleNum = ckdDisplay
                    }

                }
                )
            }


            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Blue)) {

                val optModifier= Modifier
                    .background(Color.Gray)
                    .weight(1f)
                    .fillMaxSize()

                ButtonItem(modifier = optModifier, num = "DEL", onClick = {

                    if(ckdDisplay.isNotEmpty()){
                        nums.filter { it != "" }

                        if(ckdDisplay.last().toString() in signs){
                            if(ckdDisplay.last().toString() == "-" && !negativeAllowed){
                                if(singleNum.isNotEmpty()){
                                    singleNum=singleNum.substring(0,singleNum.length-1)
                                }else{
                                    singleNum=nums.last()
                                    singleNum=singleNum.substring(0,singleNum.length-1)
                                }
                                nums.removeLast()
                                nums.add(singleNum)
                                negativeAllowed=true
                                afterOpt=false

                            }else if(negativeAllowed){
                                opr.removeLast()
                                if(nums.last() == "") nums.removeLast()
                                singleNum=nums.last()
                                afterOpt=false
                            }

                        }else{
                            if(singleNum.isNotEmpty()){
                                singleNum=singleNum.substring(0,singleNum.length-1)
                            }else{
                                singleNum=nums.last()
                                singleNum=singleNum.substring(0,singleNum.length-1)
                            }
                            nums.removeLast()
                            // if singleNum=="" error
                            nums.add(singleNum)
                        }
                        if(ckdDisplay.last().toString() == ".") pointTyped=false
                        ckdDisplay=ckdDisplay.substring(0, ckdDisplay.length-1)
                        if(ckdDisplay.isNotEmpty() &&
                            ckdDisplay.last().toString() in signs &&
                            ckdDisplay[ckdDisplay.length-2].toString() in signs){
                            negativeAllowed=false
                        }

                        if(ckdDisplay.isNotEmpty() &&
                            ckdDisplay.last().toString() !in signs &&
                            ckdDisplay.last().toString()!="."
                            ){
                            liveResultUpdater()
                        }
                    }else{
                        result=""
                    }
                    nums.filter { it != "" }
                    result = "$singleNum $nums $opr"

                },
                    onLongClick = {
                        ckdDisplay = ""
                        opr.clear()
                        nums.clear()
                        singleNum = ""
                        result= ""
                        afterOpt = false
                        pointTyped=false
                        negativeAllowed=true
                    }
                )

                ButtonItem(modifier = optModifier , num = "/", onClick = {
                    onClickForOpt(it)
                })
                ButtonItem(modifier = optModifier , num = "x", onClick =  {
                    onClickForOpt(it)
                })
                ButtonItem(modifier = optModifier , num = "-", onClick =  {
                    if(!afterOpt && ckdDisplay!=""){
                        onClickForOpt(it)
                    }else if(negativeAllowed){
                        onClickForNumbers(it)
                    }
                })
                ButtonItem(modifier = optModifier , num = "+", onClick =  {
                    onClickForOpt(it)
                })
            }
        }
    }
}

@Composable
fun ButtonItem(modifier: Modifier, num:String, onClick : (String)->Unit, onLongClick : ()->Unit = {}){
    var isClicked by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.pointerInput(Unit){
            detectTapGestures(
                onTap = {
                    onClick(num)
                },
                onLongPress = {
                    onLongClick()
                }
            )
        }
            .background(if (isClicked) Color.Gray else Color.Transparent)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = num, fontSize = 38.sp, color = Color.White, fontStyle = FontStyle.Normal)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorView(modifier = Modifier.fillMaxSize())
}
