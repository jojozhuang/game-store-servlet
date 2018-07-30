package johnny.gamestore.servlet.data;
import java.util.HashMap;

import johnny.gamestore.servlet.beans.Accessory;
import johnny.gamestore.servlet.beans.Console;

public class ConsoleHashMap{
    public static HashMap<String, Console> Microsoft = new HashMap<String, Console>();
    public static HashMap<String, Console> Sony = new HashMap<String, Console>();
    public static HashMap<String, Console> Nintendo = new HashMap<String, Console>();

    public static final String CONST_MICROSOFT = "Microsoft";
    public static final String CONST_SONY = "Sony";
    public static final String CONST_NINTENDO = "Nintendo";
    public static final String CONST_MICROSOFT_LOWER = "microsoft";
    public static final String CONST_SONY_LOWER = "sony";
    public static final String CONST_NINTENDO_LOWER = "nintendo";

    public ConsoleHashMap() {
        HashMap<String, Accessory> accessories;
        if(Microsoft.isEmpty()){
            Accessory xboxone_wc = new Accessory("xboxone_wc", "xboxone", "Controller", 40.00, "accessories/XBOX controller.jpg", "Microsoft","New",10);
            Accessory xboxone_sh = new Accessory("xboxone_sh", "xboxone", "Turtle Beach Headset", 50.00, "accessories/Turtle Beach Headset.jpg", "Microsoft","New",10);
            accessories = new HashMap<String, Accessory>();
            accessories.put("xboxone_wc", xboxone_wc);
            accessories.put("xboxone_sh", xboxone_sh);
            Console xboxone = new Console("xboxone", CONST_MICROSOFT, "XBox One",399.00,"consoles/xbox1.jpg",CONST_MICROSOFT,"New",10,accessories);
            Microsoft.put("xboxone", xboxone);

            Accessory xbox360_mr = new Accessory("xbox360_mr", "xbox360", "Speeding Wheel", 40.00, "accessories/XBOX360-SpeedWheel.jpg", "Microsoft","New",10);
            Accessory xbox360_wa = new Accessory("xbox360_wa", "xbox360", "Wireless Adapter", 50.00, "accessories/xbox360_wa.png", "Microsoft","New",10);
            accessories = new HashMap<String, Accessory>();
            accessories.put("xbox360_mr", xbox360_mr);
            accessories.put("xbox360_wa", xbox360_wa);
            Console xbox360 = new Console("xbox360", CONST_MICROSOFT, "XBox 360",299.00,"consoles/xbox360.jpg",CONST_MICROSOFT,"New",10,accessories);
            Microsoft.put("xbox360", xbox360);
        }
        if(Sony.isEmpty()){
            Accessory ps3_wc = new Accessory("ps3_wc", "ps3", "Wireless Controller", 19.99, "accessories/ps3_controller.jpg", CONST_SONY,"New",10);
            Accessory ps3_dc = new Accessory("ps3_dc", "ps3", "Disc Remote Control", 24.99, "accessories/ps3_diskcontroller.jpg", CONST_SONY,"New",10);
            accessories = new HashMap<String, Accessory>();
            accessories.put("ps3_wc", ps3_wc);
            accessories.put("ps3_dc", ps3_dc);
            Console ps3 = new Console("ps3", CONST_SONY, "PS3",219.00,"consoles/ps3-console.jpg",CONST_SONY,"New",10,accessories);
            Sony.put("ps3", ps3);
            
            Accessory ps4_cb = new Accessory("ps4_cb", "ps4", "Chartboost - Black", 19.99, "accessories/chartboost.jpg", CONST_SONY,"New",10);
            Accessory ps4_cc = new Accessory("ps4_cc", "ps4", "Dual Controller Charger", 24.99, "accessories/ps4_controllercharger.jpg", CONST_SONY,"New",10);
            accessories = new HashMap<String, Accessory>();
            accessories.put("ps4_cb", ps4_cb);
            accessories.put("ps4_cc", ps4_cc);
            Console ps4 = new Console("ps4", CONST_SONY, "PS4",349.00,"consoles/PS4-console-bundle.jpg",CONST_SONY,"New",10,accessories);
            Sony.put("ps4", ps4);
        }
        if(Nintendo.isEmpty()){
            Accessory wii_cs = new Accessory("wii_cs", "wii", "Charging System - Black", 21.99, "accessories/wii_chargingsystem.jpg", CONST_NINTENDO,"New",10);
            Accessory wii_rp = new Accessory("wii_rp", "wii", "Wii Remote Plus", 39.99, "accessories/wii_remoteplus.jpg", CONST_NINTENDO,"New",10);            
            accessories = new HashMap<String, Accessory>();
            accessories.put("wii_cs", wii_cs);
            accessories.put("wii_rp", wii_rp);
            Console wii = new Console("wii", CONST_NINTENDO, "Wii",269.00,"consoles/wii.jpg",CONST_NINTENDO,"New",10,accessories);
            Nintendo.put("wii", wii);

            Accessory wiiu_fp = new Accessory("wiiu_fp", "wiiu", "Fight Pad", 16.99, "accessories/wiiu_fightingpad.jpg", CONST_NINTENDO,"New",10);
            Accessory wiiu_gc = new Accessory("wiiu_gc", "wiiu", "GameCube Controller", 29.99, "accessories/wiiu_gamecube.jpg", CONST_NINTENDO,"New",10);            
            accessories = new HashMap<String, Accessory>();
            accessories.put("wiiu_fp", wiiu_fp);
            accessories.put("wiiu_gc", wiiu_gc);
            Console wiiu = new Console("wiiu", CONST_NINTENDO, "WiiU",299.99,"consoles/wiiu.jpg",CONST_NINTENDO,"New",10,accessories);
            Nintendo.put("wiiu", wiiu);
        }
    }
}
