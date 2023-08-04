package algonquin.cst2335.finalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a conversion query in a database.
 * Each object of this class represents a single row in a table named 'conversion_query'.
 * The conversion queries are characterized by their source and target currency types,
 * the amount of source currency and the resultant amount in the target currency.
 */
@Entity(tableName = "conversion_query")
public class ConversionQuery {

    /**
     * Unique ID of the query. It is auto-generated and used as the primary key.
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * The currency that the amount is originally in.
     */
    public String fromCurrency;

    /**
     * The currency that the amount is converted to.
     */
    public String toCurrency;

    /**
     * The amount in the 'fromCurrency' that is to be converted.
     */
    public double amount;

    /**
     * The result of the conversion of 'amount' into 'toCurrency'.
     */
    public double result;

    /**
     * Constructs a new ConversionQuery with specified values.
     *
     * @param fromCurrency The original currency of the amount.
     * @param toCurrency The currency to which the amount is converted.
     * @param amount The amount in the original currency.
     * @param result The resultant amount in the target currency.
     */
    public ConversionQuery(String fromCurrency, String toCurrency, double amount, double result) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.result = result;
    }
}
