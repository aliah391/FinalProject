package algonquin.cst2335.finalproject;
public class NameOfflight {
    String flightName;
    String delay;
    String gate;
    String Terminal;
    String destination;
    public long id;
    public NameOfflight(String nof){
        flightName=nof;
    }
    public String getFlightName(){return flightName;}
    public String getDelay(){return delay;}
    public String getGate(){return gate;}
    public String getTerminal(){return Terminal;}
    public String getDestination(){return destination;}


}
