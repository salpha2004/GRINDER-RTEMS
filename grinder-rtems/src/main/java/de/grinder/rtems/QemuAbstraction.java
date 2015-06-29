package de.grinder.rtems;

import de.grinder.util.cue.CUEAbstraction;

public class QemuAbstraction implements CUEAbstraction {
	@Override
	public void start() {
		System.out.println ("Salaam!");
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void runExperiment() {
		
	}

	@Override
	public void reset() {
		
	}
}
