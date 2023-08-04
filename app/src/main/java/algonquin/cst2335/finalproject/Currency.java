package algonquin.cst2335.finalproject;

/**
 * Represents a currency, with code and description.
 */
public class Currency {

    private String code;
    private String description;

    /**
     * Constructs a new Currency with the given code and description.
     *
     * @param code        the currency code, typically an ISO 4217 code
     * @param description a human-readable description of the currency
     */
    public Currency(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets the currency code.
     *
     * @return the currency code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the currency description.
     *
     * @return the currency description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the currency.
     *
     * @return a string representation of the currency
     */
    @Override
    public String toString() {
        return code + " - " + description;
    }

    /**
     * Sets the currency code.
     *
     * @param code the new currency code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the currency description.
     *
     * @param description the new currency description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
