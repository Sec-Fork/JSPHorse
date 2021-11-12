package org.sec.module;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

public class XORModule {
    private static final Logger logger = Logger.getLogger(XORModule.class);

    public static void doXOR(MethodDeclaration method) {
        logger.info("do integer xor operate in method");
        doCallableXOR(method);
    }

    public static void doXORForConstruct(ConstructorDeclaration cd) {
        logger.info("do integer xor operate in constructor");
        doCallableXOR(cd);
    }

    private static void doCallableXOR(CallableDeclaration<?> c) {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        List<IntegerLiteralExpr> integers = c.findAll(IntegerLiteralExpr.class);
        for (IntegerLiteralExpr i : integers) {
            int value = Integer.parseInt(i.getValue());
            int key = random.nextInt(1000000) + 1000000;
            int cipherNum = value ^ key;
            EnclosedExpr enclosedExpr = new EnclosedExpr();
            BinaryExpr binaryExpr = new BinaryExpr();
            binaryExpr.setLeft(new IntegerLiteralExpr(String.valueOf(cipherNum)));
            binaryExpr.setRight(new IntegerLiteralExpr(String.valueOf(key)));
            binaryExpr.setOperator(BinaryExpr.Operator.XOR);
            enclosedExpr.setInner(binaryExpr);
            i.replace(enclosedExpr);
        }
    }
}
