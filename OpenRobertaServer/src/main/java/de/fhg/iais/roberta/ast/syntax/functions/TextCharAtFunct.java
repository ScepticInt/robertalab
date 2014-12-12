package de.fhg.iais.roberta.ast.syntax.functions;

import java.util.List;

import de.fhg.iais.roberta.ast.syntax.BlocklyBlockProperties;
import de.fhg.iais.roberta.ast.syntax.BlocklyComment;
import de.fhg.iais.roberta.ast.syntax.IndexLocation;
import de.fhg.iais.roberta.ast.syntax.expr.Assoc;
import de.fhg.iais.roberta.ast.syntax.expr.Expr;
import de.fhg.iais.roberta.ast.transformer.AstJaxbTransformerHelper;
import de.fhg.iais.roberta.ast.visitor.AstVisitor;
import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Mutation;
import de.fhg.iais.roberta.dbc.Assert;

/**
 * This class represents the <b>text_charAt</b> block from Blockly into the AST (abstract syntax tree).<br>
 * <br>
 * The user must provide name of the function and list of parameters. <br>
 * To create an instance from this class use the method {@link #make(FunctionNames, List, BlocklyBlockProperties, BlocklyComment)}.<br>
 * The enumeration {@link FunctionNames} contains all allowed functions.
 */
public class TextCharAtFunct<V> extends Function<V> {
    private final IndexLocation location;
    private final List<Expr<V>> param;

    private TextCharAtFunct(IndexLocation name, List<Expr<V>> param, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(Kind.TEXT_CHAR_AT_FUNCT, properties, comment);
        Assert.isTrue(name != null && param != null);
        this.location = name;
        this.param = param;
        setReadOnly();
    }

    /**
     * Creates instance of {@link TextCharAtFunct}. This instance is read only and can not be modified.
     *
     * @param name of the function,
     * @param param list of parameters for the function,
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment that user has added to the block,
     * @return read only object of class {@link TextCharAtFunct}
     */
    public static <V> TextCharAtFunct<V> make(IndexLocation name, List<Expr<V>> param, BlocklyBlockProperties properties, BlocklyComment comment) {
        return new TextCharAtFunct<V>(name, param, properties, comment);
    }

    /**
     * @return name of the function
     */
    public IndexLocation getLocation() {
        return this.location;
    }

    /**
     * @return list of parameters for the function
     */
    public List<Expr<V>> getParam() {
        return this.param;
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public Assoc getAssoc() {
        return Assoc.LEFT;
    }

    @Override
    protected V accept(AstVisitor<V> visitor) {
        return visitor.visitTextCharAtFunct(this);
    }

    @Override
    public String toString() {
        return "TextCharAtFunct [" + this.location + ", " + this.param + "]";
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();
        Mutation mutation = new Mutation();
        AstJaxbTransformerHelper.setBasicProperties(this, jaxbDestination);

        mutation.setAt(false);
        AstJaxbTransformerHelper.addField(jaxbDestination, "WHERE", getLocation().name());
        AstJaxbTransformerHelper.addValue(jaxbDestination, "VALUE", getParam().get(0));
        if ( getLocation() == IndexLocation.FROM_START || getLocation() == IndexLocation.FROM_END ) {
            mutation.setAt(true);
            AstJaxbTransformerHelper.addValue(jaxbDestination, "AT", getParam().get(1));
        }
        jaxbDestination.setMutation(mutation);
        return jaxbDestination;
    }
}
