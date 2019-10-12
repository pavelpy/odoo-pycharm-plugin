package at.wtioit.intellij.plugins.odoo.modules;

import at.wtioit.intellij.plugins.odoo.models.OdooModel;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

public interface OdooModule {

    @NotNull
    String getName();

    @Nullable
    PsiElement getDirectory();

    @Nullable
    Icon getIcon();

    @Nullable
    String getRelativeLocationString();

    @NotNull
    Collection<OdooModule> getDependencies();

    @NotNull
    List<OdooModel> getModels();

    void setModels(List<OdooModel> models);

    boolean dependsOn(OdooModule module);
}
