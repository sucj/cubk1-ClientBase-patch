package org.union4dev.base.events.update;

import org.union4dev.base.events.base.Event;

public class TickEvent implements Event {
    public enum Phase {
		START, END;
	}

	public final Phase phase;

    public TickEvent(Phase phase) {
		this.phase = phase;
	}
}
