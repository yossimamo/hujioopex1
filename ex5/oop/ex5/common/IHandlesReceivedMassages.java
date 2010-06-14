package oop.ex5.common;

import oop.ex5.messages.Message;

public interface IHandlesReceivedMassages {

	public void HandleReceivedMessage(ProcessThread processThread,
			Message incomingMessage) ;

}
