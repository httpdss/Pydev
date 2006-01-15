/*
 * Created on Jan 14, 2006
 */
package org.python.pydev.core;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IProjectModulesManager {

    /**
     * This is the maximun number of deltas that can be generated before saving everything in a big chunck and 
     * clearing the deltas
     */
    public static final int MAXIMUN_NUMBER_OF_DELTAS = 100;

    /**
     * Set the project this modules manager works with.
     * 
     * @param project the project related to this manager
     * @param restoreDeltas says whether deltas should be restored (if they are not, they should be discarded)
     */
    public abstract void setProject(IProject project, boolean restoreDeltas);

    public abstract void processUpdate(ModulesKey data);

    public abstract void processDelete(ModulesKey key);

    public abstract void processInsert(ModulesKey key);

    public abstract void endProcessing();

    /**
     * @param nature this is the nature for this project modules manager (can be used if no project is set)
     */
    public abstract void setPythonNature(IPythonNature nature);

    /**
     * @return the nature related to this manager
     */
    public abstract IPythonNature getNature();

    public abstract ISystemModulesManager getSystemModulesManager();

    /**
     * @return a set with the names of all available modules
     */
    public abstract Set<String> getAllModuleNames();

    public abstract ModulesKey[] getOnlyDirectModules();

    public abstract ModulesKey[] getAllModules();

    public abstract IModule getModule(String name, IPythonNature nature, boolean dontSearchInit);

    public abstract IModule getModule(String name, IPythonNature nature, boolean checkSystemManager, boolean dontSearchInit);

    /**
     * @param member the member we want to know if it is in the pythonpath
     * @param container the project where the member is
     * @return true if it is in the pythonpath and false otherwise
     */
    public abstract boolean isInPythonPath(IResource member, IProject container);

    /**
     * @param member this is the member file we are analyzing
     * @param container the project where the file is contained
     * @return the name of the module given the pythonpath
     */
    public abstract String resolveModule(IResource member, IProject container);

    /**
     * resolve module for all, including the system manager.
     * 
     * @see org.python.pydev.editor.codecompletion.revisited.ModulesManager#resolveModule(java.lang.String)
     */
    public abstract String resolveModule(String full);

    /**
     * @param full the full file-system path of the file to resolve
     * @return the name of the module given the pythonpath
     */
    public abstract String resolveModule(String full, boolean checkSystemManager);

    public abstract void changePythonPath(String pythonpath, IProject project, IProgressMonitor monitor);

    /**
     * @see org.python.pydev.editor.codecompletion.revisited.ModulesManager#getSize()
     */
    public abstract int getSize();

    /**
     * Forced builtins are only specified in the system.
     * 
     * @see org.python.pydev.editor.codecompletion.revisited.ModulesManager#getBuiltins()
     */
    public abstract String[] getBuiltins();

    /**
     * @return the paths that constitute the pythonpath as a list of strings
     */
    public abstract List<String> getCompletePythonPath();

}