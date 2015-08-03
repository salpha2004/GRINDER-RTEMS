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
		/* initialize logger. */
		LOGGER = LoggerFactory.getLogger(String.format("%s",
			QemuAbstraction.class.getName()));
	}
	
	/* output of Qemu is passed to this function to process it and extract
	 * each test case's result. currently it returns a fix number to test
	 * that storing the result in DB works. */
	private short parseTestOutput (BufferedReader testOut) throws IOException {
		String outputStr;
		while ( (outputStr = testOut.readLine()) != null )
			System.out.println (outputStr);
		return 5;
	}
	
	/* "start" is supposed to initialize target system. for instance if
	 * there is a debugger attached, start-up debugger and prepare the
	 * environment. in this implementation, initialization is simple and
	 * this functions runs experiment as well. */
	@Override
	public void start() {
		/* build execution command which will run in Terminal. */
		String execCmd = "qemu-system-i386 -kernel ";
		LOGGER.info ("Starting Qemu...");
		try {
			/* [REMAINED] how to get the testcase path the better way?
			 * more generic than campaign0.testcase0 */
			
			/* path to the image under test is stored at DB. an interface
			 * is provided by GrinderClient to get the path. */
			/* get the first campaign. */
			Campaign campaign0 = (Campaign)(GrinderClient.getGrinder().getDatabase().getCampaigns().toArray()[0]);
			if (campaign0 != null) {
				/* get the first test case (in the first campaign). */
				ExperimentRun experiment = new ExperimentRun (campaign0.getTestCases().get(0), campaign0);
				/* get image under test's path from the test case.
				 * this value has been initialized manually via
				 * mysql's command line interface. */
				execCmd = execCmd + campaign0.getTestCases().get(0).getModule() + " -serial /dev/stdout";
				qemu = Runtime.getRuntime().exec(execCmd);
				BufferedReader qemuOutput = new BufferedReader(
					new InputStreamReader(qemu.getInputStream()));
				experiment.setResult (parseTestOutput (qemuOutput));
				/* store back test's result in DB. */
				experiment.save ();
			}
		}
		catch (IOException ioEx) {
			ioEx.printStackTrace ();
		}
	}

	/* stop target system. in this case, simply kill qemu process. */
	@Override
	public void stop() {
		LOGGER.info ("Stopping Qemu...");
		qemu.destroy ();
	}

	/* "runExperiment" is called after "start" to actually run the experiment.
	 * in this implementation "start" takes care of running the experiment
	 * as well. */
	@Override
	public void runExperiment() {}

	/* reset, that is, "stop", "start", and "runExperiment" again. */
	@Override
	public void reset() {
		
	}
}
