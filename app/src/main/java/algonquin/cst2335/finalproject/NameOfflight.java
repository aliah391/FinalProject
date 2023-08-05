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

    public  NameOfflight( String nof,String D, String t, String g, String d){
        flightName=nof;
        destination=D;
        Terminal=t;
        gate=g;
        delay=d;

    }

    public String getFlightName(){return flightName;}
//    public String getDelay(String d){return delay=d;}
//    public String getGate(String g){return gate=g;}
//    public String getTerminal(String t){return Terminal=t;}
//    public String getDestination(String D){return destination=D;}


}
