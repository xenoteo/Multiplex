package agh.alleviation.model;

import agh.alleviation.util.Rating;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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
        /**
         * The constant IS_RATED.
         */
        public static final String IS_RATED = "is_rated";
        /**
         * The constant IS_RATING_POSITIVE.
         */
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

    /**
     * Instantiates a new Ticket.
     *
     * @param seance the seance
     */
    public Ticket(Seance seance) {
        setSeance(seance);
        setPrice(seance.getPrice());
        setIsActive(true);
        setIsRated(false);
    }

    /**
     * Returns the seance object property.
     *
     * @return the seance object property
     */
    public ObjectProperty<Seance> seanceProperty() { return seanceProperty; }

    /**
     * Gets the seance.
     *
     * @return the seance
     */
    @ManyToOne
    public Seance getSeance() {
        return seanceProperty.getValue();
    }

    /**
     * Sets the seance.
     *
     * @param seance the seance
     */
    public void setSeance(Seance seance) { seanceProperty.setValue(seance);}

    /**
     * Returns the price double property.
     *
     * @return the price double property
     */
    public DoubleProperty priceProperty() { return priceProperty; }

    /**
     * Gets the price.
     *
     * @return the price
     */
    @Column(name = Columns.PRICE)
    public double getPrice() {
        return priceProperty.get();
    }

    /**
     * Gets the order.
     *
     * @return the order
     */
    @ManyToOne
    public Order getOrder() { return orderProperty.get(); }

    /**
     * Sets the order.
     *
     * @param order the order
     */
    public void setOrder(Order order) { orderProperty.set(order); }

    /**
     * Sets the price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        priceProperty.set(price);
    }

    /**
     * Gets is rated.
     *
     * @return is rated boolean
     */
    @Column(name = Columns.IS_RATED)
    public boolean getIsRated() { return isRatedProperty.get(); }

    /**
     * Sets is rated.
     *
     * @param isRated is rated boolean
     */
    public void setIsRated(boolean isRated) { isRatedProperty.set(isRated); }

    /**
     * Returns the is rated boolean property.
     *
     * @return the is rated boolean property
     */
    @Transient
    public BooleanProperty isRatedProperty() { return isRatedProperty; }

    /**
     * Gets is rating positive.
     *
     * @return is rating positive boolean
     */
    @Column(name = Columns.IS_RATING_POSITIVE)
    public Rating getIsRatingPositive() { return Rating.fromOrdinal(isRatingPositive.get()); }

    /**
     * Sets is rating positive.
     *
     * @param rating the rating
     */
    public void setIsRatingPositive(Rating rating) { isRatingPositive.set(rating.ordinal()); }

    /**
     * Returns the is rating positive integer property.
     *
     * @return the is rating positive integer property
     */
    @Transient
    public IntegerProperty isRatingPositiveProperty() { return isRatingPositive; }

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
