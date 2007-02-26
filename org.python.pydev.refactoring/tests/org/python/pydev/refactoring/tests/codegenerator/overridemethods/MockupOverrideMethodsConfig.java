package org.python.pydev.refactoring.tests.codegenerator.overridemethods;

import java.util.ArrayList;

import org.python.pydev.refactoring.ast.adapters.offsetstrategy.IOffsetStrategy;

public class MockupOverrideMethodsConfig {

	private int classSelection;

	private ArrayList<Integer> methodSelection;

	private int offsetStrategy;

	private int editClass;

	public MockupOverrideMethodsConfig() {
		this.classSelection = 0;
		this.offsetStrategy = IOffsetStrategy.AFTERINIT;
		this.methodSelection = new ArrayList<Integer>();
		methodSelection.add(0);
		this.editClass = 0;
	}

	public ArrayList<Integer> getMethodSelection() {
		return methodSelection;
	}

	public int getClassSelection() {
		return classSelection;
	}

	public int getOffsetStrategy() {
		return offsetStrategy;
	}

	public int getEditClass() {
		return editClass;
	}
}