package com.ninetyninepercentcasino.net
public class BJSplit extends DTO:
	private final Hand hand1
	private final Hand hand2
	public BJSplit(Hand hand1, Hand hand2):
		this.hand1 = hand1
		this.hand2 = hand2
	public Hand getHand1():
		return hand1
	public Hand getHand2():
		return hand2
