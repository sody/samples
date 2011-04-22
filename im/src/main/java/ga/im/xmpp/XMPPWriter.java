package ga.im.xmpp;

import ga.im.Connection;
import ga.im.ConnectionSupport;

import java.io.Writer;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Ivan Khalopik
 * @version $Revision$ $Date$
 */
public class XMPPWriter extends ConnectionSupport {
	private final BlockingQueue<Packet> messageQueue = new ArrayBlockingQueue<Packet>(500, true);
	private Writer writer;

	public XMPPWriter(Connection connection) {
		super(connection, "XMPPWriter");
	}

	@Override
	protected void init(Socket socket) throws Exception {
		writer = createWriter(socket);
	}

	void addPacket(Packet packet) {
		try {
			messageQueue.put(packet);
		} catch (InterruptedException e) {
			reportException(e);
		}
	}

	@Override
	protected void process() throws Exception {
		final String message = messageQueue.take().serialize();
		System.out.println("[WRITER] " + message);
		writer.write(message);
		writer.flush();
	}
}
