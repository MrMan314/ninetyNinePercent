package com.ninetyninepercentcasino.net;

import java.util.ArrayList;
import java.util.Random;

public class ClientGroup {
	private ArrayList<Connection> clients = new ArrayList<Connection>();
	private ArrayList<ClientGroup> clientGroups;

	private String joinCode;
	private String name;
	private boolean isPublic;

	private final int lowChar = 0x40;
	private final int highChar = 0x5A;
	private final int codeLength = 6;

	private Random random;

	public ClientGroup(String name, boolean isPublic, ArrayList<ClientGroup> clientGroups) {
		random = new Random();
		this.name = name;
		this.isPublic = isPublic;
		boolean validCode = false;
		while (!validCode) {
			this.joinCode = random.ints(lowChar, highChar + 1)
				.limit(codeLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
			validCode = true;
			for(ClientGroup group: clientGroups) {
				if(this.joinCode.equals(group.getJoinCode())) {
					validCode = false;
					continue;
				}
			}
		}
	}

	public String getJoinCode() {
		return joinCode;
	}

	public String getName() {
		return name;
	}

	public boolean getPublicity() {
		return isPublic;
	}

	public ArrayList<Connection> getClients() {
		return clients;
	}
}