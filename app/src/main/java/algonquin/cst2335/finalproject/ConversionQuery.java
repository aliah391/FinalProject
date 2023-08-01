package algonquin.cst2335.finalproject;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "conversion_query")
public class ConversionQuery {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fromCurrency;
    public String toCurrency;
    public double amount;
    public double result;

    public ConversionQuery(String fromCurrency, String toCurrency, double amount, double result) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.result = result;
    }
}