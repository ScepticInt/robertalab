package de.fhg.iais.roberta.syntax.sensors.arduino.mbot;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.factory.IRobotFactory;
import de.fhg.iais.roberta.inter.mode.sensor.ISensorPort;
import de.fhg.iais.roberta.inter.mode.sensor.IVoltageSensorMode;
import de.fhg.iais.roberta.syntax.BlockTypeContainer;
import de.fhg.iais.roberta.syntax.BlocklyBlockProperties;
import de.fhg.iais.roberta.syntax.BlocklyComment;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.sensor.ExternalSensor;
import de.fhg.iais.roberta.syntax.sensor.SensorMetaDataBean;
import de.fhg.iais.roberta.transformer.Jaxb2AstTransformer;
import de.fhg.iais.roberta.visitor.AstVisitor;
import de.fhg.iais.roberta.visitors.arduino.MbotAstVisitor;

public class VoltageSensor<V> extends ExternalSensor<V> {

    public VoltageSensor(IVoltageSensorMode mode, ISensorPort port, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(mode, port, BlockTypeContainer.getByName("MAKEBLOCK_VOLTAGE_GET_SAMPLE"), properties, comment);
        setReadOnly();
    }

    /**
     * Create object of the class {@link VoltageSensor}.
     *
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of {@link VoltageSensor}
     */
    public static <V> VoltageSensor<V> make(IVoltageSensorMode mode, ISensorPort port, BlocklyBlockProperties properties, BlocklyComment comment) {
        return new VoltageSensor<>(mode, port, properties, comment);
    }

    @Override
    public String toString() {
        return "Voltage [" + this.getMode() + ", " + this.getPort() + "]";
    }

    @Override
    protected V accept(AstVisitor<V> visitor) {
        return ((MbotAstVisitor<V>) visitor).visitVoltageSensor(this);
    }

    /**
     * Transformation from JAXB object to corresponding AST object.
     *
     * @param block for transformation
     * @param helper class for making the transformation
     * @return corresponding AST object
     */
    public static <V> Phrase<V> jaxbToAst(Block block, Jaxb2AstTransformer<V> helper) {
        IRobotFactory factory = helper.getModeFactory();
        SensorMetaDataBean sensorData = extractPortAndMode(block, helper);
        String mode = sensorData.getMode();
        String port = sensorData.getPort();
        return VoltageSensor
            .make(factory.getVoltageSensorMode(mode), factory.getSensorPort(port), helper.extractBlockProperties(block), helper.extractComment(block));
    }

}