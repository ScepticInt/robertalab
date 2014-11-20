package de.fhg.iais.roberta.ast.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.fhg.iais.roberta.ast.syntax.action.ActorPort;
import de.fhg.iais.roberta.ast.syntax.action.DriveDirection;
import de.fhg.iais.roberta.ast.syntax.action.MotorSide;
import de.fhg.iais.roberta.ast.syntax.sensor.SensorPort;
import de.fhg.iais.roberta.brickconfiguration.ev3.EV3Actor;
import de.fhg.iais.roberta.brickconfiguration.ev3.EV3BrickConfiguration;
import de.fhg.iais.roberta.brickconfiguration.ev3.EV3Sensor;
import de.fhg.iais.roberta.hardwarecomponents.ev3.HardwareComponentEV3Actor;
import de.fhg.iais.roberta.hardwarecomponents.ev3.HardwareComponentEV3Sensor;

public class EV3BrickConfigurationTest {
    private final String expectedBrickConfigurationGenerator = //
        "privateEV3BrickConfigurationbrickConfiguration=newEV3BrickConfiguration.Builder()"
            + ".setWheelDiameter(0.0)"
            + ".setTrackWidth(0.0)"
            + ".addActor(ActorPort.A,newEV3Actor(HardwareComponentEV3Actor.EV3_LARGE_MOTOR,true,DriveDirection.FOREWARD,MotorSide.LEFT))"
            + ".addActor(ActorPort.B,newEV3Actor(HardwareComponentEV3Actor.EV3_MEDIUM_MOTOR,true,DriveDirection.FOREWARD,MotorSide.RIGHT))"
            + ".addSensor(SensorPort.S1,newEV3Sensor(HardwareComponentEV3Sensor.EV3_ULTRASONIC_SENSOR))"
            + ".addSensor(SensorPort.S4,newEV3Sensor(HardwareComponentEV3Sensor.EV3_COLOR_SENSOR))"
            + ".build();";

    @Test
    public void testBuilder() {
        EV3BrickConfiguration.Builder builder = new EV3BrickConfiguration.Builder();
        builder.addActor(ActorPort.A, new EV3Actor(HardwareComponentEV3Actor.EV3_LARGE_MOTOR, true, DriveDirection.FOREWARD, MotorSide.LEFT));
        builder.addActor(ActorPort.B, new EV3Actor(HardwareComponentEV3Actor.EV3_MEDIUM_MOTOR, true, DriveDirection.FOREWARD, MotorSide.RIGHT));
        builder.addSensor(SensorPort.S1, new EV3Sensor(HardwareComponentEV3Sensor.EV3_ULTRASONIC_SENSOR));
        builder.addSensor(SensorPort.S4, new EV3Sensor(HardwareComponentEV3Sensor.EV3_COLOR_SENSOR));
        EV3BrickConfiguration conf = builder.build();

        assertEquals(HardwareComponentEV3Actor.EV3_LARGE_MOTOR, conf.getActorOnPort(ActorPort.A).getComponentType());
        assertEquals(HardwareComponentEV3Actor.EV3_MEDIUM_MOTOR, conf.getActorOnPort(ActorPort.B).getComponentType());
        assertNull(conf.getActorOnPort(ActorPort.C));
        assertEquals(HardwareComponentEV3Sensor.EV3_ULTRASONIC_SENSOR, conf.getSensorOnPort(SensorPort.S1).getComponentType());
        assertEquals(HardwareComponentEV3Sensor.EV3_COLOR_SENSOR, conf.getSensorOnPort(SensorPort.S4).getComponentType());
        assertNull(conf.getSensorOnPort(SensorPort.S2));

        assertEquals(this.expectedBrickConfigurationGenerator, conf.generateRegenerate().replaceAll("\\s+", ""));
    }

    @Test
    public void testBuilderFluent() {
        EV3BrickConfiguration conf =
            new EV3BrickConfiguration.Builder()
                .addActor(ActorPort.A, new EV3Actor(HardwareComponentEV3Actor.EV3_LARGE_MOTOR, true, DriveDirection.FOREWARD, MotorSide.LEFT))
                .addActor(ActorPort.B, new EV3Actor(HardwareComponentEV3Actor.EV3_MEDIUM_MOTOR, true, DriveDirection.FOREWARD, MotorSide.RIGHT))
                .addSensor(SensorPort.S1, new EV3Sensor(HardwareComponentEV3Sensor.EV3_ULTRASONIC_SENSOR))
                .addSensor(SensorPort.S4, new EV3Sensor(HardwareComponentEV3Sensor.EV3_COLOR_SENSOR))
                .build();

        assertEquals(HardwareComponentEV3Actor.EV3_LARGE_MOTOR, conf.getActorOnPort(ActorPort.A).getComponentType());
        assertEquals(HardwareComponentEV3Actor.EV3_MEDIUM_MOTOR, conf.getActorOnPort(ActorPort.B).getComponentType());
        assertNull(conf.getActorOnPort(ActorPort.C));
        assertEquals(HardwareComponentEV3Sensor.EV3_ULTRASONIC_SENSOR, conf.getSensorOnPort(SensorPort.S1).getComponentType());
        assertEquals(HardwareComponentEV3Sensor.EV3_COLOR_SENSOR, conf.getSensorOnPort(SensorPort.S4).getComponentType());
        assertNull(conf.getSensorOnPort(SensorPort.S2));

        assertEquals(this.expectedBrickConfigurationGenerator, conf.generateRegenerate().replaceAll("\\s+", ""));
    }

    //    @Test
    //    public void testVisitorBuilder() {
    //        EV3BrickConfiguration.Builder builder = new EV3BrickConfiguration.Builder();
    //        builder.visiting("regulated", "large", "left", "off");
    //        builder.visitingActorPort("A");
    //        builder.visiting("regulated", "middle", "right", "off");
    //        builder.visitingActorPort("B");
    //        builder.visiting("ultrasonic");
    //        builder.visitingSensorPort("1");
    //        builder.visiting("Farbe");
    //        builder.visitingSensorPort("4");
    //        EV3BrickConfiguration conf = builder.build();
    //
    //        assertEquals(HardwareComponentType.EV3LargeRegulatedMotor, conf.getActorOnPort(ActorPort.A).getComponentType());
    //        assertEquals(HardwareComponentType.EV3MediumRegulatedMotor, conf.getActorOnPort(ActorPort.B).getComponentType());
    //        assertEquals(HardwareComponentType.EV3UltrasonicSensor, conf.getSensorOnPort(SensorPort.S1).getComponentType());
    //        assertEquals(HardwareComponentType.EV3ColorSensor, conf.getSensorOnPort(SensorPort.S4).getComponentType());
    //
    //        assertEquals(this.expectedBrickConfigurationGenerator, conf.generateRegenerate().replaceAll("\\s+", ""));
    //    }
    //
    //    @Test(expected = DbcException.class)
    //    public void testVisitorExc1() {
    //        EV3BrickConfiguration.Builder builder = new EV3BrickConfiguration.Builder();
    //        builder.visiting("regulated", "latsch");
    //        builder.visitingActorPort("A");
    //    }
    //
    //    @Test(expected = DbcException.class)
    //    public void testVisitorExc2() {
    //        EV3BrickConfiguration.Builder builder = new EV3BrickConfiguration.Builder();
    //        builder.visiting("regulated", "large");
    //        builder.visitingActorPort("X");
    //    }
}