package com.k.pmpstudy

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.CheckBoxList
import org.jetbrains.annotations.Nullable
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class FileSelectDialog(files: Array<VirtualFile>) : DialogWrapper(true) {
    private val files: Array<VirtualFile>
    private val checkBoxList = CheckBoxList<VirtualFile>()

    init {
        title = "Opened Files Diff"
        init()
        this.files = files
        checkBoxList.setItems(files.toList(), VirtualFile::toString)
        checkBoxList.setItemSelected(files[0], true)
    }

    @Nullable
    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel(BorderLayout())

        val label = JLabel("Select 2 files to compare.")
        label.preferredSize = Dimension(100, 50)
        dialogPanel.add(label, BorderLayout.NORTH)
        dialogPanel.add(checkBoxList, BorderLayout.CENTER)

        return dialogPanel
    }

    fun showChoose2FilesDialog(): Array<VirtualFile> {
        if (showAndGet()) {
            return files.filter { checkBoxList.isItemSelected(it) }.toTypedArray()
        }
        throw RuntimeException("Exit Error has occurred.")
    }

    override fun doValidate(): ValidationInfo? {
        if (files.count { checkBoxList.isItemSelected(it) } == 2) {
            return null
        }
        return ValidationInfo("Select just 2 files.", checkBoxList).asWarning()
    }
}