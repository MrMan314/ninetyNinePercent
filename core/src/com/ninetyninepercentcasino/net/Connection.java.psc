package com.ninetyninepercentcasino.net
public abstract class Connection extends Thread:
	protected Socket clientSocket
	protected ObjectInputStream in
	protected ObjectOutputStream out
	protected List clients
	private boolean isServer
	public Connection(Socket clientSocket):
		printf("New connection to %s\n", clientSocket.getRemoteSocketAddress().toString())
		this.clientSocket = clientSocket
		timerThreads = new ArrayList()
		isServer = false
		try:
			in = new ObjectInputStream(clientSocket.getInputStream())
			out = new ObjectOutputStream(clientSocket.getOutputStream())
			timer.scheduleAtFixedRate(keepAliveTimeout, 5000, 5000)
		catch StreamCorruptedException e:
			alive = false
			finish()
			return
		alive = true
	public Connection(Socket clientSocket, List clients):
		printf("New connection from %s\n", clientSocket.getRemoteSocketAddress().toString())
		this.clients = clients
		this.clientSocket = clientSocket
		timerThreads = new ArrayList()
		isServer = true
		try:
			out = new ObjectOutputStream(clientSocket.getOutputStream())
			in = new ObjectInputStream(clientSocket.getInputStream())
			timer.scheduleAtFixedRate(keepAliveTimeout, 5000, 5000)
		catch StreamCorruptedException e:
			alive = false
			finish()
			return
		alive = true
	public void finish():
		alive = false
		try:
			timer.cancel()
			timer.purge()
		catch IllegalStateException e:
		clientSocket.close()
		Thread.currentThread().interrupt()
		try:
			in.close()
		catch NullPointerException e:
		try:
			out.close()
		catch SocketException e:
		for Thread timerThread in timerThreads:
			timerThread.interrupt()
		if isServer:
			clients.remove(this)
		dispose()
		printf("Connection closed from %s\n", clientSocket.getRemoteSocketAddress().toString())
	protected String aliveMessage = ""
	protected boolean alive = false
	protected Timer timer = new Timer()
	protected List timerThreads
	protected TimerTask keepAliveTimeout = new TimerTask():
		public void run():
			Thread timerThread = null
			timerThread = new Thread():
				public void run():
					try:
						if !clientSocket.isConnected():
							finish()
							return
						synchronized aliveMessage:
							aliveMessage.notify()
							aliveMessage = ""
							NetMessage pingMessage = new NetMessage(NetMessage.MessageType.PING, "ping")
							synchronized out:
								out.writeObject(pingMessage)
							aliveMessage.wait()
						if aliveMessage.isEmpty():
							finish()
							return
						timerThreads.remove(this)
					catch IllegalMonitorStateException | InterruptedException e:
						return
					catch SocketException | NullPointerException e:
						try:
							finish()
						catch IOException f:
							f.printStackTrace()
						return
					catch IOException e:
						e.printStackTrace()
						return
			timerThread.start()
			timerThreads.add(timerThread)
	public Socket getClientSocket():
		return clientSocket
	public void message(NetMessage message):
		out.writeObject(message)
	public abstract void dispose()
	public abstract void run()
