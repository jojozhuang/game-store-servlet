package johnny.gamestore.servlet.data;


import java.util.HashMap;

import johnny.gamestore.servlet.beans.Game;

public class GameHashMap {
    public static HashMap<String, Game> ElectronicArts = new HashMap<String, Game>();
    public static HashMap<String, Game> Activision = new HashMap<String, Game>();
    public static HashMap<String, Game> TakeTwoInteractive = new HashMap<String, Game>();

    public static final String CONST_ELECTRONICARTS = "Electronic Arts";
    public static final String CONST_ACTIVISION = "Activision";
    public static final String CONST_TAKETWOINTERACTIVE = "Take-Two Interactive";

    public static final String CONST_ELECTRONICARTS_LOWER = "electronicarts";
    public static final String CONST_ACTIVISION_LOWER = "activision";
    public static final String CONST_TAKETWOINTERACTIVE_LOWER = "taketwointeractive";

    public GameHashMap() {
        if(ElectronicArts.isEmpty()){
            Game ea_fifa = new Game("ea_fifa", CONST_ELECTRONICARTS, "FIFA 2016",59.99,"games/ea_fifa.jpg","Electronic Arts","New",10);
            Game ea_nfs = new Game("ea_nfs", CONST_ELECTRONICARTS,"Need for Speed",59.99,"games/ea_nfs.jpg","Electronic Arts","New",10);
            ElectronicArts.put("ea_fifa", ea_fifa);
            ElectronicArts.put("ea_nfs", ea_nfs);
        }
        if(Activision.isEmpty()){
            Game activision_cod = new Game("activision_cod", CONST_ACTIVISION, "Call Of Duty",54.99,"games/activision_cod.jpg","Activision","New",10);
            Activision.put("activision_cod", activision_cod);
        }
        if(TakeTwoInteractive.isEmpty()){
            Game tti_evolve = new Game("tti_evolve", CONST_TAKETWOINTERACTIVE, "Evolve",49.99,"games/tti_evolve.jpg","Take-Two Interactive","New",10);
            TakeTwoInteractive.put("tti_evolve", tti_evolve);
        }
    }
}
