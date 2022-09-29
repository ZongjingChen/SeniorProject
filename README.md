# *<span style = "color:#ab4642">CR Documents</span>*
CR language is a programming language designed for virtual robot control.

In this document, you will learn how to write a simple CR program.

# **<span style = "color:#ab4642">Basic Structure </span>**
Let's look at an example of simple CR code:
<pre>
MODEL = YBOT;
function exampleFunction(a, b) {
    LHand.raise(a, b); 
}

BEGIN

// This is a comment
Head.rotate(90, 1);

END
</pre>
> In CR language, all the whitespaces(spaces, tabs, enters, etc) are ignored. The code are seperated by a semi colon **;** .

I will explain this code line by line.
<br><br>
    
## **Model Declaration**
The first line of code is called model declaration. CR language offers two different kind of robots, XBOT and YBOT
	<p>XBOT:  <img src="res/images/xbot.png" width="211" height="367">YBOT:  <img src="res/images/ybot.png" width="211" height="367"></p>
> Every CR program must start with a model declaration, otherwise it will cause an error.</p>

## **Function Declaration**

<p>The next section is called function declaration. You can define a function that has several <a href="#statements"><b>statements</b></a> by yourself. A function declaration can be seperated into several parts:</p>
<pre>function</pre>
<p> A function declaration should always start with a <b>function</b> keyword, followed by the name of that function.
<pre>exampleFunction</pre>
A function name must start with a letter, and followed by a sequence of letters or numbers. The function name is case-sensitive and it cannot be a <a href="#keyword"><b>keyword</b></a>.</p>
<pre>(a, b)</pre>
<p>You can input parameters to a function. They should be seperated by a comma and enclosed by parentheses. If there are no parameters, you still need to use empty parentheses.</p>
<pre>{ LHand.raise(a, b); }</pre>
<p>The body of a function is several <a href="#statements"><b>statements</b></a> enclosed by curly braces. When this function is called, the code here will be executed in sequence.</p>
<p>In this example, the only statement is a <a href="#pfCall"><b>Primitive Function Call</b></a>.

## **BEGIN and END**
<p>The body of CR codes must start with a <b>BEGIN</b> key word and end with an <b>END</b> key word.</p><br>

## **Body of the program**

<p>The body of the program is between BEGIN and END. The code here will be executed in sequence. In this example, the only statement is a <a href="#pfCall"><b>Primitive Function Call</b></a>
<pre>Head.rotate(90, 1);</pre>

## **Comments**

<p>You may have noticed that there is a line of comment, which start with two slash <pre>//This is a comment</pre> These comments do not have any effects. They are usually used to explain your code.</p>

> You can add a comment anywhere in your code.

<br>
 
# **<span style = "color:#ab4642" id="statements">Statements </span>**
<p> There are several types of statement: </p>
	<ul>
		<li><a href="#assignment">Assignment Call</a></li>
		<li><a href="#fCall">Function Call</a></li>
		<li><a href="#pfCall">Primitive Function Call</a></li>
		<li><a href="#seq">Seq Block</a></li>
		<li><a href="#sim">Sim Block</a></li>
	</ul>
<br>


## **<span id="assignment">Assignment Call</h3>**
<br>
<p>You can assign a real number value to a variable using an assignment call</p>
<pre>a = 10 * 2;</pre>
<p>The left-hand side, <b>a</b>, is the variable name. The variable name must start with a letter, and followed by a sequence of letters or numbers. The function name is case-sensitive and it cannot be a <a href="#keyword"><b>keyword</b></a>. </p>
<p>The right-hand side should be a math expression. Supported math operations are: <pre>+, -, *, /, mod</pre> </p>
<p>This example assignment will assign 20 (the result of 10 * 2) to the variable <b>a</b>. You can assign another value to <b>a</b> using the same syntax. </p>
<p>You can also use variables in another assignment call like</p>
<pre>b = a + 15;</pre>
<p>which assigns value 35 to <b>b.</b></p>
<p> You can assign a new value to the same variable using its old value:</p>
<pre>a = a + 5;</pre>
<p>This assignment call calculates the right-hand side expression using the old value of <b>a</b>, which is 20, and assign the new value 25 to <b>a.</b></p>
<br>



## **<span id="fCall">Function Call</span>**
<br>
<p>This example Function Call calls the previously defined function <b>exampleFunction</b>:</p>
<pre>exampleFunction(45, 2);</pre>
<p>The function call executes the body code of the function, in this example, is a <a href="#pfCall"><b>Primitive Function Call</b></a></p>
<pre>LHand.raise(a, b);</pre>
<p>However, because we input two values, 45 and 2, into this function, two assignment call is added to the function body before this Primitive Function Call</p>
<pre>a = 45;
b = 2;</pre>
<p>This example Function Call rotates the head for 45 degrees in two seconds.</p>
<p>You can call a function using defined variables as input, for example: </p>
<pre>a = 45;
b = 2;
exampleFunction(a, b);
</pre>

> <p>Notice that when calling a function, the input is the value, not the variable. Any changes made in function call to a variable will not be made to the variable outside that function.</p>
> <p>In this case, if the <b>exampleFunction</b> changes the value of <b>a</b> or <b>b</b> in its body, it will not affect the value of a and b outside this Function Call.</p>
> <p>Similarly, all variables defined in the function body cannot be used outside that function.</p>

> <p>If the function has no input, you still need to use empty parentheses to call it.</p>



## **<span id="pfCall">Primitive Function Call</span>**
<br>
<p>A Primitive Function Call is a Function Call that controls the robot to perform an action. You cannot declare a Primitive Function Call by yourself. </p>
<p>Generally, the syntax of a Primitive Function Call is:</p>
<pre>BodyPart.action(degree, duration);</pre>
<p>A <a href="#body"><b>BodyPart</b></a> is a controllable body part of the robot, and an <b><a href="#action">action</b></a> is the action you want this body part to perform.</p>
<p>Most actions that a robot can perform are just rotations of a body part, so the first parameter decides the degree of this rotation. The second parameter decides the time of this action.</p>
<p>The example code</p>
<pre>Head.rotate(90, 1);</pre>
<p>controls the robot to <b>rotate</b> its <b>head</b> for <b>90</b> degrees in <b>one</b> second.</p>
<p>Same as a Function Call, you can use defined variables as input to a Primitive Function Call.</p>
<p>Notice that the <b>dance</b> function's input parameters are different from others:</p>
<pre>Body.dance(speed, duration);</pre>
<p>The first parameter of dance function is the speed of the pre-recorded dance, 1 means the default speed. The second parameter is the time you want your robot to dance.</p>



## **<span id="seq">Seq Block</span>**
<br>
<p> The syntax of a Seq Block is</p>
<pre>
seq{
	// Statements
	Head.rotate(90, 2);
	Body.dance(1, 10);
};
</pre>
<p>A Seq Block must start with the keyword <b>seq</b>. The body of Seq Block is enclosed by curly braces, like a function declaration.</p>
<p>Same as a function, the body of a Seq Block is executed in sequence. In this example, when the code is executed, the robot will rotate its head for 90 degrees in the first two seconds, then dance in default speed for 10 seconds.</p>


## **<span id="sim">Sim Block</span>**
<br>
<p>The syntax of a Sim Block is</p>
<pre>
sim{
	// Statements
	LArm.raise(90, 1);
	RArm.raise(90, 2);
};
</pre>
<p>A Sim Block must start with the keyword <b>sim</b>. The body of Sim Block is enclosed by curly braces, like a function declaration.</p>
<p>Different with a Seq Block, the body of a Sim Block is executed <b>simultaneously</b>. In this example, when the code is executed, the robot will raise its left arm <b>and</b> right arm together, but in different speed.</p>
<p>The robot will raise its left arm for 90 degrees in 1 second, and its right arm in two seconds. The code after a Sim Block will be executed when the action has the longest duration in the Sim Block is finished.</p>
<br>

> The changes made to a defined variable will not change the value of that variable outside the Seq or Sim Block, and you cannot use the variables which are defined in the Seq or Sim Block outside that Block.

> You can write any statement in a Seq or Sim Block, even a Sim Block or a Seq Block.<br>


# **<span style = "color:#ab4642" id="keyword">Key Word</span>**
<p>There are some reserved words in CR language. You cannot use the words listed here as a variable name or function name.</p>

## **Common**
<ul>
    <li>MODEL </li>
    <li>XBOT</li>
    <li>YBOT</li>
    <li>BEGIN</li>
    <li>END</li>
    <li>mod</li>
    <li>function</li>
    <li>sim</li>
    <li>seq</li>
</ul>

## **<span id="body">Body Part</span>**
<ul>
    <li>LArm</li>
    <li>RArm</li>
    <li>LForeArm</li>
    <li>RForeArm</li>
    <li>LHand</li>
    <li>RHand</li>
    <li>LLeg</li>
    <li>RLeg</li>
    <li>LUpLeg</li>
    <li>RUpLeg</li>
    <li>LFoot</li>
    <li>RFoot</li>
    <li>Head</li>
    <li>Body</li>
</ul>

## **<span id="action">Action</span>**
<p> (BodyParts that can perform the action)</p>
<ul>
    <li>raise (LArm, RArm, LForeArm, RForeArm, LLeg, RLeg, LUpLeg, RUpLeg, Head)</li>
    <li>lateralRaise (LArm, RArm, LUpLeg, RUpLeg)</li>
    <li>rotate (Head, Body, LHand, RHand, LFoot, RFoot, LForeArm, RForeArm)</li>
    <li>tilt (Head, Body)</li>
    <li>dance (Body)</li>
    <li>bow (Body)</li>
</ul>
<br><br>








