package com.leosoto.bingo.debug.ui.actions;
import org.eclipse.jface.action.IAction;
import org.python.pydev.ui.actions.project.PyRemoveNature;

import com.leosoto.bingo.debug.ui.launching.DjangoManagementRunner;

public class DjangoTest extends PyRemoveNature{

    public void run(IAction action) {
        try {
        	DjangoManagementRunner.launch(selectedProject, "test");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
