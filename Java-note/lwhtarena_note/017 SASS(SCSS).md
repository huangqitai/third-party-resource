# 前端之Sass/Scss实战笔记

Sass 有两种语法规则：

- Sassy CSS `它是css3语法的的拓展级,SCSS文件使用.scss作为拓展名`
 - 使用大括号和分号，和css语法格式差不多

- 缩进语法 `.sass 为拓展名`  
 - 不使用大括号和分号



注释

有三种形式：

（1）`//comment`：该注释只是在.scss源文件中有，编译后的css文件中没有。

（2）`/! /`：重要注释，任何style的css文件中都会有，一般放置css文件版权说明等信息。

（3）`/ /`：该注释在compressed的style的css中没有，其他style的css文件都会含有。


#### 变量与选择器

- 变量 
	
	变量的定义一般以$开头，某个变量的作用域仅限于他们定义的层级以及子层级。如果变量是定义在所有嵌套选择器之外的，那么他们可以在各处被调用。(旧版是使用`！`)

```
	$color1:#aeaeae;
	.div1{
	    background-color:$color1;
	}
```

编译后：

```
	.div1 {
	  background-color: #aeaeae;
	}
	/*# sourceMappingURL=test.css.map */
```

如果希望某个在子选择器中定义的变量能够成为全局变量，可以使用<font size=3 face="黑体" color=red> !global </font>关键字：

```
	#main {
	  $width: 5em !global;
	  width: $width;
	}
	
	#sidebar {
	  width: $width;
	}
```

嵌套引用:

嵌套引用在其他编程语言中即是字符串插值，需要用<font size=3 face="黑体" color=red> #{ }  </font>进行包裹：
	
```
	$left:left;
	.div1{
	    border-#{$left}-width:5px;
	}
```

变量计算

Sass中也是支持对于变量进行简单的计算：

```
	$left:20px;
	.div1{
	    margin-left:$left+12px;
	}
```

变量可以支持计算的类型，还是比较多的：

```
	p {
	  font: 10px/8px;             // Plain CSS, no division
	  $width: 1000px;
	  width: $width/2;            // Uses a variable, does division
	  width: round(1.5)/2;        // Uses a function, does division
	  height: (500px/2);          // Uses parentheses, does division
	  margin-left: 5px + 8px/2px; // Uses +, does division
	  font: (italic bold 10px/8px); // In a list, parentheses don't count
	}
```

- 选择器

嵌套 ( 嵌套不要超过三层，否则产生难以理解的代码 )

```
	.div1{
	    .span1{
	        height: 12px;
	    }
	    .div2{
	        width: 16px;
	    }
	}
```

属性也可以嵌套，比如border-color属性，可以写成：

```
	　　p {
	　　　　border: {
	　　　　　　color: red;
	　　　　}
	　　}
```

注意，border后面必须加上冒号。

父元素引用
在嵌套的子层级中，允许使用<font size=3 face="黑体" color=red> & </font>引用父元素：

```
	.div1{
	    &:hover{
	        cursor: hand;
	    }
	}
```

代码重用

继承

SASS允许一个选择器，继承另一个选择器。比如，现有class1：

```
	.class1{
	    font-size:19px;
	}
	.class2{
	    @extend .class1;
	    color:black;
	}
```
注意：如果在class2后面有设置了class1的属性，那么也会影响class2，如下：

```
	.class1{
	    font-size:19px;
	}
	.class2{
	    @extend .class1;
	    color:black;
	}
	.class1{
	    font-weight:bold;
	}
```

由此可以看出Scss也是递归编译的。

引用外部css文件（Partials）
有时网页的不同部分会分成多个文件来写样式，或者引用通用的一些样式，那么可以使用@import。

```
@import "_test1.scss";
@import "_test2.scss";
@import "_test3.scss";
```
Mixin & Include
Mixin有点像C语言的宏（macro），是可以重用的代码块。

使用@mixin命令，定义一个代码块。

```
　　@mixin left {
　　　　float: left;
　　　　margin-left: 10px;
　　}
```

使用@include命令，调用这个mixin。

```
　　div {
　　　　@include left;
　　}
```

参数与缺省值

边距设置

```
@mixin common($value1,$value2,$defaultValue:12px){
    display:block;
    margin-left:$value1;
    margin-right:$value2;
    padding:$defaultValue;
}
.class1{
    font-size:16px;
    @include common(12px,13px,15px);
}
.class2{
    font-size:16px;
    @include common(12px,13px);
}
```

浏览器前缀设置设置

下面是一个mixin的实例，用来生成浏览器前缀。

```
　　@mixin rounded($vert, $horz, $radius: 10px) {
　　　　border-#{$vert}-#{$horz}-radius: $radius;
　　　　-moz-border-radius-#{$vert}#{$horz}: $radius;
　　　　-webkit-border-#{$vert}-#{$horz}-radius: $radius;
　　}
```

使用的时候，可以像下面这样调用：
```
　　#navbar li { @include rounded(top, left); }
　　#footer { @include rounded(top, left, 5px); }
```
编程式方法

流程控制

条件语句

<font size=3 face="黑体" color=red> @if </font>可以用来判断：
```
　　p {
　　　　@if 1 + 1 == 2 { border: 1px solid; }
　　　　@if 5 < 3 { border: 2px dotted; }
　　}
```
配套的还有<font size=3 face="黑体" color=red> @else </font>命令：
```
　　@if lightness($color) > 30% {
　　　　background-color: #000;
　　} @else {
　　　　background-color: #fff;
　　}
```

循环语句

SASS支持for循环：

```
　　@for $i from 1 to 10 {
　　　　.border-#{$i} {
　　　　　　border: #{$i}px solid blue;
　　　　}
　　}
```

也支持while循环：

```
　　$i: 6;
　　@while $i > 0 {
　　　　.item-#{$i} { width: 2em * $i; }
　　　　$i: $i - 2;
　　}
```

each命令，作用与for类似：

```
　　@each $member in a, b, c, d {
　　　　.#{$member} {
　　　　　　background-image: url("/image/#{$member}.jpg");
　　　　}
　　}
```
函数

Sass允许用户自定义函数，原型如下所示：
```
	　　@function double($n) {
	　　　　@return $n * 2;
	　　}
	
	　　#sidebar {
	　　　　width: double(5px);
	　　}
```
颜色函数

SASS提供了一些内置的颜色函数，以便生成系列颜色。
```
		lighten(#cc3, 10%)  // #d6d65c
		darken(#cc3, 10%)  //  #a3a329
		grayscale(#cc3) // #808080
		complement(#cc3) // #33c
```

# scss

