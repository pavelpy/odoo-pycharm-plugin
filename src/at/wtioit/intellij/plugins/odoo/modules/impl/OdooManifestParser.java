package at.wtioit.intellij.plugins.odoo.modules.impl;

import at.wtioit.intellij.plugins.odoo.modules.OdooManifest;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.python.psi.PyExpressionStatement;
import com.jetbrains.python.psi.PyListLiteralExpression;
import com.jetbrains.python.psi.PyStringLiteralExpression;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class OdooManifestParser {

    @Nullable
    @Contract(pure = true)
    static OdooManifest parse(PsiFile manifestFile){
        PyListLiteralExpression dependenciesList = getDependenciesList(getManifestExpression(manifestFile));
        if (dependenciesList == null) return null;
        List<String> dependencies = new ArrayList<>();
        for (PsiElement dependency : dependenciesList.getChildren()) {
            String cleanName = ((PyStringLiteralExpression) dependency).getStringValue();
            dependencies.add(cleanName);
        }
        return new OdooManifestImpl(dependencies, manifestFile.getProject());
    }

    @Contract(value = "null -> null", pure = true)
    private static PyExpressionStatement getManifestExpression(PsiFile manifestFile){
        if (manifestFile == null) return null;
        for (PsiElement element : manifestFile.getChildren()){
            if(element instanceof PyExpressionStatement){
                return (PyExpressionStatement) element;
            }
        }
        return null;
    }

    @Contract("null -> null")
    private static PyListLiteralExpression getDependenciesList(PyExpressionStatement manifest){
        if (manifest == null) return null;
        List<PsiElement> children = Arrays.asList(manifest.getFirstChild().getChildren());
        for (PsiElement element : children){
            if(element.getFirstChild().getText().contains("depends")){
                return (PyListLiteralExpression) element.getLastChild();
            }
        }
        return null;
    }
}
