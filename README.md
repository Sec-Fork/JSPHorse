# JSPHorse

![](https://img.shields.io/badge/build-passing-brightgreen)
![](https://img.shields.io/badge/JavaParser-3.23.1-blue)
![](https://img.shields.io/badge/Java-8-red)

## 简介

一个JSP免杀Webshell生成器，目前仅做了普通回显Webshell：`1.jsp?pwd=xxx&cmd=whoami`

后续可能支持其他Webshell，例如蚁剑冰蝎等

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

特点：

- Java反射调用
- ScriptEngine调用JS免杀
- 使用分割和注释绕过可能的JS黑名单
- Javac动态编译class免杀（参考三梦师傅代码）
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

生成标准形式Webshell

`java -jar JSPHorse.jar -p your_password`

全局Unicode编码（JSP支持全局Unicode编码）

`java -jar JSPHorse.jar -p your_password -u`

生成进阶版Webshell（Javac动态编译class）

`java -jar JSPHorse.jar -p your_password --super`

进阶版Webshell基础上全局Unicode编码

`java -jar JSPHorse.jar -p your_password --super -u`

使用ScriptEngine调用JS免杀

`java -jar JSPHorse.jar -p your_password --js`

使用ScriptEngine调用JS免杀基础上全局Unicode编码

`java -jar JSPHorse.jar -p your_password --js -u`

如何使用？

1.jsp?pwd=your_password&cmd=calc.exe

## 感谢

参考天下大木头师傅的Webshell：https://github.com/KpLi0rn/Shell

参考三梦师傅的Webshell：https://github.com/threedr3am/JSP-Webshells

## 免责申明

未经授权许可使用`JSPHorse`攻击目标是非法的

本程序应仅用于授权的安全测试与研究目的


