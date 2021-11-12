package org.sec.module;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import org.apache.log4j.Logger;
import org.sec.util.EncodeUtil;

import java.util.List;
import java.util.Random;

public class StringModule {
    private static final Logger logger = Logger.getLogger(StringModule.class);

    public static int encodeString(MethodDeclaration method) {
        logger.info("encode string variables");
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int offset = random.nextInt(9) + 1;
        List<StringLiteralExpr> stringList = method.findAll(StringLiteralExpr.class);
        for (StringLiteralExpr s : stringList) {
            if (s.getParentNode().isPresent()) {
                if (s.getParentNode().get() instanceof ArrayInitializerExpr) {
                    String encode = EncodeUtil.encryption(s.getValue(), offset);
                    encode = encode.replace(System.getProperty("line.separator"), "");
                    s.setValue(encode);
                }
            }
        }
        return offset;
    }

    public static void changeRef(MethodDeclaration method, int offset) {
        logger.info("change variable name in global array");
        List<ArrayAccessExpr> arrayExpr = method.findAll(ArrayAccessExpr.class);
        for (ArrayAccessExpr expr : arrayExpr) {
            if (expr.getName().asNameExpr().getNameAsString().equals("globalArr")) {
                MethodCallExpr methodCallExpr = new MethodCallExpr();
                methodCallExpr.setName("dec");
                methodCallExpr.setScope(null);
                NodeList<Expression> nodeList = new NodeList<>();
                ArrayAccessExpr a = new ArrayAccessExpr();
                a.setName(expr.getName());
                a.setIndex(expr.getIndex());
                nodeList.add(a);
                IntegerLiteralExpr intValue = new IntegerLiteralExpr();
                intValue.setValue(String.valueOf(offset));
                nodeList.add(intValue);
                methodCallExpr.setArguments(nodeList);
                expr.replace(methodCallExpr);
            }
        }
    }
}
