package de.grinder.rtems;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.grinder.util.cue.CUEAbstraction;
import de.grinder.GrinderClient;
import de.grinder.database.Campaign;
import de.grinder.database.ExperimentRun;

public class QemuAbstraction implements CUEAbstraction {
	private final Logger LOGGER;
	private Process qemu;
	
	public QemuAbstraction () {
		LOGGER = LoggerFactory.getLogger(String.format("%s",
			QemuAbstraction.class.getName()));
	}
	
	private short parseTestOutput (BufferedReader testOut) throws IOException {
		String outputStr;
		while ( (outputStr = testOut.readLine()) != null )
			System.out.println (outputStr);
		return 5;
	}
	
	@Override
	public void start() {
		String execCmd = "qemu-system-i386 -kernel ";
		LOGGER.info ("Starting Qemu...");
		try {
			/* [REMAINED] how to get the testcase path the better way?
			 * more generic than campaign0.testcase0 */
			/* get path to the test case image from DB. */
			Campaign campaign0 = (Campaign)(GrinderClient.getGrinder().getDatabase().getCampaigns().toArray()[0]);
			if (campaign0 != null) {
				ExperimentRun experiment = new ExperimentRun (campaign0.getTestCases().get(0), campaign0);
				execCmd = execCmd + campaign0.getTestCases().get(0).getModule() + " -serial /dev/stdout";
				qemu = Runtime.getRuntime().exec(execCmd);
				BufferedReader qemuOutput = new BufferedReader(
					new InputStreamReader(qemu.getInputStream()));
				experiment.setResult (parseTestOutput (qemuOutput));
				experiment.save ();
			}
		}
		catch (IOException ioEx) {
			ioEx.printStackTrace ();
		}
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
