package pt.uc.dei.cms.incerto.interfaces.gui;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;

public class InternalAxiom {

	public Formula f;
	public boolean use;
	public InternalAxiom(Formula f, boolean use) {
		this.f = f;
		this.use = use;
	}
}
