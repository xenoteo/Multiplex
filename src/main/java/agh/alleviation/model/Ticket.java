package agh.alleviation.model;

import agh.alleviation.util.Rating;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for representation of a ticket.
 * It keeps the information about the seance and its price.
 * Seance's price may be different from the ticket's price, as a customer may have a discount.
 *
 * @author Ksenia Fiodarava
 * @see Seance
 */
@Entity
@Table(name = Ticket.TABLE_NAME)
public class Ticket extends EntityObject {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "ticket";

    /**
     * The type Columns.
     */
    public static class Columns {
        /**
         * The constant SEANCE.
         */
        public static final String SEANCE = "seance";
        /**
         * The constant PRICE.
         */
        public static final String PRICE = "price";

        /**
         * The constant ORDER.
         */
        public static final String ORDER = "order";

        public static final String IS_RATED = "is_rated";

        public static final String IS_RATING_POSITIVE = "is_rating_positive";
    }

    private final ObjectProperty<Seance> seanceProperty = new SimpleObjectProperty<>();
    private final DoubleProperty priceProperty = new SimpleDoubleProperty(this, "price");
    private final ObjectProperty<Order> orderProperty = new SimpleObjectProperty<>();
    private final BooleanProperty isRatedProperty = new SimpleBooleanProperty();
    private final IntegerProperty isRatingPositive = new SimpleIntegerProperty();

    /**
     * Instantiates a new Ticket.
     */
    public Ticket() {
    }

    /**
     * Instantiates a new Ticket.
     *
     * @param seance the seance
     * @param price  the price
     */
    public Ticket(Seance seance, double price) {
        setSeance(seance);
        setPrice(price);
        setIsActive(true);
        setIsRated(false);
    }

    public Ticket(Seance seance) {
        setSeance(seance);
        setIsActive(true);
        setIsRated(false);
    }

    /**
     * Seance property object property.
     *
     * @return the object property
     */
    public ObjectProperty<Seance> seanceProperty() { return seanceProperty; }

    /**
     * Gets seance.
     *
     * @return the seance
     */
    @ManyToOne
    public Seance getSeance() {
        return seanceProperty.getValue();
    }

    /**
     * Sets seance.
     *
     * @param seance the seance
     */
    public void setSeance(Seance seance) { seanceProperty.setValue(seance);}

    /**
     * Price property double property.
     *
     * @return the double property
     */
    public DoubleProperty priceProperty() { return priceProperty; }

    /**
     * Get price double.
     *
     * @return the double
     */
    @Column(name = Columns.PRICE)
    public double getPrice() {
        return priceProperty.get();
    }

    /**
     * Gets order.
     *
     * @return the order
     */
//    @JoinColumn(name = Columns.ORDER)
    @ManyToOne
    public Order getOrder() { return orderProperty.get(); }

    /**
     * Sets order.
     *
     * @param order the order
     */
    public void setOrder(Order order) { orderProperty.set(order); }

    /**
     * Set price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        priceProperty.set(price);
    }

    @Column(name = Columns.IS_RATED)
    public boolean getIsRated() { return isRatedProperty.get(); }

    public void setIsRated(boolean isRated) { isRatedProperty.set(isRated); }

    @Transient
    public BooleanProperty isRatedProperty() { return isRatedProperty; }

    @Column(name = Columns.IS_RATING_POSITIVE)
    public Rating getIsRatingPositive() { return Rating.fromOrdinal(isRatingPositive.get()); }

    public void setIsRatingPositive(Rating rating) { isRatingPositive.set(rating.ordinal()); }

    @Transient
    public IntegerProperty isRatingPositiveProperty() { return isRatingPositive; }

//    @Override
//    public List<EntityObject> update() {
//        super.update();
//        List<EntityObject> updatedObjects = new ArrayList<>();
//        updatedObjects.addAll(getSeance().getMovie().update());
//        return updatedObjects;
//    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(getSeance());
        out.writeObject(getPrice());
        out.writeObject(getOrder());
        out.writeObject(getIsRated());
        out.writeObject(getIsRatingPositive());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setSeance((Seance) in.readObject());
        setPrice(in.readInt());
        setOrder((Order) in.readObject());
        setIsRated(in.readBoolean());
        setIsRatingPositive(Rating.fromOrdinal(in.readInt()));
    }
}
