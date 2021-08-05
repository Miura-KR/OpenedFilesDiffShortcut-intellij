package com.k.pmpstudy

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import org.jetbrains.annotations.NotNull

class OpenedFilesDiffAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = (project != null)
    }

    override fun actionPerformed(@NotNull e: AnActionEvent) {
        val data = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY)
        Messages.showMessageDialog(data.toString(), "Error", Messages.getInformationIcon())
    }
}