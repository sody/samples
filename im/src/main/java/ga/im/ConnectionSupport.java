package ga.im;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public abstract class ConnectionSupport {
	private final ExecutorService executor;
	private final Connection connection;
	private final Runnable workUnit;

	private boolean connected;
	private static final String DEFAULT_ENCODING = "UTF-8";

	public ConnectionSupport(Connection connection, final String threadName) {
		assert connection != null;

		this.connection = connection;
		workUnit = new WorkUnit();
		executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			public Thread newThread(Runnable runnable) {
				final Thread thread = new Thread(runnable, threadName);
				thread.setDaemon(true);
				return thread;
			}
		});
	}

	public void start(Socket socket) throws Exception {
		assert socket != null;

		init(socket);
		connected = true;
		executor.execute(workUnit);
	}

	public void stop() {
		connected = false;
	}

	protected BufferedReader createReader(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream(), DEFAULT_ENCODING));
	}

	protected BufferedWriter createWriter(Socket socket) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), DEFAULT_ENCODING));
	}

	protected void reportException(Exception e) {
		e.printStackTrace();//todo: log exception
	}

	protected abstract void init(Socket socket) throws Exception;

	protected abstract void process() throws Exception;

	class WorkUnit implements Runnable {
		public void run() {
			try {
				while (connected) {
					process();
				}
			} catch (Exception e) {
				reportException(e);
				stop();
				connection.disconnect();
			}
		}
	}
}
