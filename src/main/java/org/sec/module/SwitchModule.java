package org.sec.module;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SwitchModule {
    private static final Logger logger = Logger.getLogger(SwitchModule.class);

    public static void changeSwitch(MethodDeclaration method, String target) {
        logger.info("change switch statements order");
        String[] a = target.split("\\|");
        SwitchStmt stmt = method.findFirst(SwitchStmt.class).isPresent() ?
                method.findFirst(SwitchStmt.class).get() : null;
        if (stmt == null) {
            return;
        }
        List<SwitchEntry> entryList = method.findAll(SwitchEntry.class);
        for (int i = 0; i < entryList.size(); i++) {
            if (entryList.get(i).getLabels().get(0) instanceof IntegerLiteralExpr) {
                IntegerLiteralExpr expr = (IntegerLiteralExpr) entryList.get(i).getLabels().get(0);
                expr.setValue(a[i]);
            }
        }
        NodeList<SwitchEntry> switchEntries = new NodeList<>();
        Collections.shuffle(entryList);
        switchEntries.addAll(entryList);
        stmt.setEntries(switchEntries);
    }

    public static String shuffle(MethodDeclaration method) {
        logger.info("shuffle dispenser and switch cases");
        Random rand = new Random();
        String result = null;
        rand.setSeed(System.currentTimeMillis());
        List<ArrayInitializerExpr> arrayExpr = method.findAll(ArrayInitializerExpr.class);
        for (ArrayInitializerExpr expr : arrayExpr) {
            Node target = expr.getChildNodes().get(0);
            if (target instanceof StringLiteralExpr) {
                String value = ((StringLiteralExpr) target).getValue();
                if (value.contains("|")) {
                    String[] a = value.split("\\|");
                    int length = a.length;
                    for (int i = length; i > 0; i--) {
                        int randInd = rand.nextInt(i);
                        String temp = a[randInd];
                        a[randInd] = a[i - 1];
                        a[i - 1] = temp;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (String s : a) {
                        sb.append(s).append("|");
                    }
                    String finalStr = sb.toString();
                    finalStr = finalStr.substring(0, finalStr.length() - 1);
                    ((StringLiteralExpr) target).setValue(finalStr);
                    result = finalStr;
                }
            }
        }
        return result;
    }
}
