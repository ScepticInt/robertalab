package de.fhg.iais.roberta.mode.actors.arduino.botnroll;

import de.fhg.iais.roberta.inter.mode.action.IActorPort;

public enum ActorPort implements IActorPort {
    NO_PORT( "NO_PORT" ), A( "A", "MA" ), B( "B", "MB" ), C( "C", "MC" ), D( "D", "MD" ), TEST1( "Test1" ), TEST2( "Test2" );

    private final String[] values;

    private ActorPort(String... values) {
        this.values = values;
    }

    @Override
    public String[] getValues() {
        return this.values;
    }

    @Override
    public String getPortNumber() {
        return this.values[0];
    }
}