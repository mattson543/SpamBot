package io.github.mattson543.spambot;

/**
 * Creates a group of browsers.
 *
 * @author mattson543
 */
public class SpamController
{
	public static void main(String[] args)
	{
		//Allow use of WebDrivers
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		System.setProperty("webdriver.firefox.marionette", "geckodriver.exe");
		System.setProperty("webdriver.edge.driver", "MicrosoftWebDriver.exe");

		//Arbitrary limit
		int threadLimit = 12;

		//Create initial window
		Thread[] threads = createThreads(1);

		while (threads.length < threadLimit)
			for (Thread thread1 : threads)
				if (!thread1.isAlive())
				{
					//If any browsers in the set were closed, kill the others
					for (Thread thread2 : threads)
						thread2.interrupt();

					//Create new set of threads
					threads = createThreads(threads.length + 1);

					break;
				}
	}

	/**
	 * Create and start an array of threads for monitoring.
	 *
	 * @param threadCount
	 *            Number of threads to create
	 * @return Threads
	 */
	private static Thread[] createThreads(int threadCount)
	{
		Thread[] threads = new Thread[threadCount];

		for (int i = 0; i < threads.length; i++)
		{
			//Create a Browser for each thread
			threads[i] = new Thread(new Browser(threads.length, i));

			//Start
			threads[i].start();
		}

		return threads;
	}
}